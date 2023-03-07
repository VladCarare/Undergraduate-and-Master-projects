
/**
* A Class to simulate a bunch of protons in a (mostly) uniform magnetic field.
* @author Vlad Carare
* @version 1
*/

import java.lang.Math;
import java.util.*;
import java.io.*;

public class BunchSim implements NumericalAlgorithms {

	public final static double k = 1 / (4 * Math.PI * 8.85418782E-12);// 1/4\pi\epsilon_0

	/**
	 * Main method to simulate the motion of a single bunch of protons in a uniform
	 * B field or an equivalent Coulomb field.
	 *
	 */
	public static void main(String[] args) {

		// Simulation controls
		double timeStep = 1.0e-5; // time step in seconds
		double decreasedTimeStep = 1.0e-9; // time step to be used when at least one particle is in the accelerating
											// region
		long maxTime = 3000; // maximum simulation time in seconds
		int maxRev = 100; // maximum number of orbital revolutions for proton bunch
		String outFileName = "week5.data"; // output file name for bunch trajectory data
		char rDist = 'U'; // the random distribution to use (U for uniform, G for Gaussian)
		int pStep = 1000; // print output on every pStep time steps
		boolean checkSpread = false; // whether to check for spreadX == spreadY
		double spreadTol = 0.1; // the tolerance to use in checking for the spreads to be equal
		int iAlgo = nEulerCromer; // the numerical algorithm to use
		boolean useEField = false; // flag to switch between using the uniform B field and an equivalent (in 2d)
									// Coulomb E field.
		boolean printBoundingRegions = false; // print the position of the points inside the bounded regions of electric
												// fields

		// B parameters
		double bMag = 1.0e-7; // magnetic flux density in Tesla

		// Bunch parameters
		int nProtons = 10;
		double pSpeed = 0.1; // initial average speed in ms^-1
		double pEnergy = 0.5 * Proton.pMass * (pSpeed) * (pSpeed); // initial average energy of particles in bunch
																	// (non-relativistic)
		double pESpread = 0.00 * pEnergy; // absolute spread in energy of particles in the bunch
		double frequency = (Proton.pCharge * bMag / (2 * Math.PI * Proton.pMass)); // expected frequency of bunch orbit
		double radius = Proton.pMass * pSpeed / (Proton.pCharge * bMag); // expected radius of bunch orbit

		// E parameters
		double eMag = 1.0e-7; // electric field in Newtons/Coulomb
		PhysicsVector eDirection = new PhysicsVector(0, 1, 0);
		double eBound = radius * 0.05;
		Rectangle boundingRegion = new Rectangle(eBound); // the bounding region for the accelerating fields is assumed
															// to be a rectangle
		double phase = Math.PI / 2; // phase of the sinosuidally varying electric field

		PhysicsVector pDirn = new PhysicsVector(0, 1, 0); // direction of bunch at start of simulation
		PhysicsVector pDirnSpread = new PhysicsVector(0, 0, 0); // relative spread in the direction of the bunch at the
																// start of the simulation
		PhysicsVector pOrigin = new PhysicsVector(); // nominal average position of bunch at start of simulation
		PhysicsVector pSpread = new PhysicsVector(0 * radius / 100, 0 * radius / 100, 0); // absolute spread in position
																							// of particles in bunch

		// Set up the cyclotron fields
		ArrayList<GeneralEMField> cyclotron = new ArrayList<GeneralEMField>();

		GeneralEMField bendingField;

		if (useEField) {
			double sourceQ = -1 * Proton.pMass * pSpeed * pSpeed * radius / (Proton.pCharge * k); // the equivalent
																									// charge for a
																									// proton's circular
																									// orbit

			ChargedParticle fixedCharge = new ChargedParticle(0.0, sourceQ, new PhysicsVector(radius, 0, 0),
					new PhysicsVector());
			bendingField = new Coulomb(fixedCharge);
		} else {
			bendingField = new EMField();
			((EMField) bendingField).setElectric(new PhysicsVector());
			((EMField) bendingField).setMagnetic(new PhysicsVector(0, 0, bMag));
		}
		cyclotron.add(bendingField);

		GeneralEMField acceleratingField = new LocalisedEField();
		int choiceOfEFields = 2;

		switch (choiceOfEFields) {
		case 1:
			// adding an electric field localised in the region bounded by eBound on the
			// y-axis and infinite on the x-axis.
			acceleratingField = new LocalisedEField(PhysicsVector.scale(eMag, eDirection), boundingRegion);
			cyclotron.add(acceleratingField);
			break;

		case 2: // create two accelerating regions, both lying on the x-axis, with equal
				// electric fields

			// adding an electric field, centred at origin, localised in the region bounded
			// by eBound on the
			// y-axis and by radius/4 on the x-axis.
			boundingRegion = new Rectangle(radius / 4, eBound, new PhysicsVector(1, 0, 0), new PhysicsVector());
			acceleratingField = new LocalisedEField(PhysicsVector.scale(eMag, eDirection), boundingRegion);
			cyclotron.add(acceleratingField);

			// adding an electric field, centred on the x-axis, at a value given by
			// 0.02953814581173211 (was found empirically to be the radius of the first
			// orbit),
			// bounded by eBound on the y-axis and by radius/4 on the x-axis.
			boundingRegion = new Rectangle(radius / 4, eBound, new PhysicsVector(1, 0, 0),
					new PhysicsVector(0.02953814581173211, 0, 0)); // this double value is specific for E=e-7 and phase
																	// = pi/2
			acceleratingField = new LocalisedEField(PhysicsVector.scale(eMag, eDirection), boundingRegion);
			cyclotron.add(acceleratingField);

		default: // no electric field
			break;
		}

		// Open a file to save the bunch positions in
		final PrintWriter outFile;
		PrintWriter tryFile = null;
		try {
			tryFile = new PrintWriter(outFileName);
		} catch (IOException e) {
			System.out.println("Exception opening file: " + e.getMessage());
			System.exit(4);
		} finally {
			outFile = tryFile;
		}

		// prints position of points inside the bounding regions of the localised
		// electric fields.
		// only works for the two cases defined above.
		if (printBoundingRegions)
			for (double i = (-radius / 2); i < (0.02953814581173211 + radius / 2); i += (radius / 80))
				for (double j = (-2 * eBound); j < (2 * eBound); j += (eBound / 40))
					for (GeneralEMField field : cyclotron)
						if (field instanceof LocalisedEField)
							if (((LocalisedEField) field).getBoundingRegion().contains(new PhysicsVector(i, j, 0)))
								outFile.println(new PhysicsVector(i, j, 0).returnSimple2DString());

		// Print initial conditions
		outFile.println("Time step: " + timeStep + " | " + "Decreased time step: " + decreasedTimeStep + " | "
				+ "Maximum number of revolutions: " + maxRev + " | " + "Algorithm used: " + naNames[iAlgo] + " | "
				+ "Number of protons: " + nProtons + " | " + "Magnetic field strength: " + bMag + " | "
				+ "Electric field strength: " + eMag + " | " + "Boundary as fraction of radius: " + eBound / radius
				+ " | ");

		// Make a bunch of protons
		Proton aProton = new Proton();
		Bunch<Particle> pBunch = new Bunch<Particle>();

		for (int i = 1; i <= nProtons; i++) {
			pBunch.addParticle(new Proton(aProton));
		}

		pBunch.setAlgo(iAlgo);
		pBunch.setDist(rDist);
		pBunch.setPosition(pOrigin, pSpread);
		pBunch.setVelocity(pDirn, pDirnSpread, pEnergy, pESpread);
		System.out.println(pBunch);

		// Set up the simulation
		double time = 0.0;
		int nRev = 0; // number of orbits the proton bunch completes
		double lastTime = 0.0; // The time at which the last 'turn' ended
		double spreadX = 0.0;
		double spreadY = 0.0;
		OrbitTracker<Particle> bunchOrbit = new OrbitTracker<Particle>(pOrigin); // Start tracking the orbit of the
																					// bunch

		boolean decreaseTimeStep = false; // flag to indicate when the program needs to decrease the time step. 
										  // it needs to be initialised to false.

		while (nRev < maxRev && time < maxTime) {// Loop over time

			boolean isInside = false;
			boolean justCrossedBorder = false; // check whether a particle just crossed border, so to go backwards
												// in
												// time and do the calculation with a reduced time step

			// Move all particles in the bunch
			Iterator<Particle> bunchIt = pBunch.iterator();
			while (bunchIt.hasNext()) {
				Particle aParticle = bunchIt.next();
				PhysicsVector acceleration = new PhysicsVector();
				// Loop over all fields
				for (GeneralEMField field : cyclotron) {
					if (aParticle instanceof ChargedParticle) {
						if (field instanceof LocalisedEField) {
							// since E = E_0 * sin (wt), and the Lorentz force is directly proportional with
							// the electric field in the absence of magnetic field, we get that the
							// acceleration has the same sinusoidal dependence as the electric field.
							// This is easier to do in the main class as
							// we do not need to pass the time to other classes.
							PhysicsVector auxiliaryVector = PhysicsVector.scale(
									Math.sin(2 * Math.PI * frequency * time + phase),
									field.getAcceleration((ChargedParticle) aParticle));
							acceleration.increaseBy(auxiliaryVector);

							// check if a particle has entered a region of accelerating electric field,
							// and if so, flag to move back time by an interation and decrease time step
							if (!isInside)
								if (boundingRegion.contains(aParticle.getPosition())) {
									if (!decreaseTimeStep)
										justCrossedBorder = true;
									isInside = true;
								}

						} else
							acceleration.increaseBy(field.getAcceleration((ChargedParticle) aParticle));
					}

				}

				// if a particle just crossed border to accelerating region move back time and
				// decrease time step
				// while any particle is inside this area. When no particle is in an
				// accelerating region
				// revert back to normal time step.
				if (justCrossedBorder) {
					aParticle.update(timeStep, PhysicsVector.scale(-1, acceleration)); // go back in time and
																						// decrease
																						// time step
					time -= timeStep;
					decreaseTimeStep = true;
				} else if (isInside) {
					aParticle.update(decreasedTimeStep, acceleration);
					time += decreasedTimeStep;
				} else if (!isInside) {
					aParticle.update(timeStep, acceleration);
					time += timeStep;
					decreaseTimeStep = false;
				}

			}

			if (((int) (time / timeStep)) % pStep == 1) { // Write out the bunch position at intervals
				/*
				 * outFile.println(time + " " + (pBunch.getPosition()).returnSimpleString() +
				 * " " + (pBunch.getVelocity()).returnSimpleString() + " " + pBunch.getTotalKE()
				 * + " " + totalPE(cyclotron, pBunch) + " " + (pBunch.getTotalKE() +
				 * totalPE(cyclotron, pBunch))); outFile.flush();
				 */

				outFile.println(time + " " + (pBunch.getPosition()).returnSimpleString() + " "
						+ (pBunch.getTotalKE() + totalPE(cyclotron, pBunch)));
				outFile.flush();
			}

			if (bunchOrbit.hasOrbited(pBunch)) {
				// bunch has completed an orbit

				nRev += 1;
				System.out.printf("Revolution number %3d at time %10.6f s\n", nRev, time);
				System.out.printf("Period of this revolution is  %10.6f s\n", (time - lastTime));
				System.out.println(pBunch);
				System.out.println();
				lastTime = time;
				PhysicsVector spread = pBunch.getSpreadMax();
				spreadX = spread.getX();
				spreadY = spread.getY();
				if (checkSpread && spreadTest(spreadX, spreadY, spreadTol)) { // Check the spreads and quit if they
																				// are
																				// equal within tolerance.
					break;
				}
			}
		}

		// After simulation, calculate the average periodic orbit
		double calcPeriod = 1 / frequency;
		double simPeriod = time / nRev;
		double radius2 = Proton.pMass * (pBunch.getVelocity()).magnitude() / (Proton.pCharge * bMag);
		System.out.println("\n\n");
		System.out.println("Magnetic flux density " + bMag + " Tesla ");
		System.out.println("Electric field strength " + eMag + " N/C");
		System.out.println("Calculated period of orbit " + calcPeriod + " s");
		System.out.println("Simulated period of orbit " + simPeriod + " s");
		System.out.println("Fractional difference " + (calcPeriod - simPeriod) / calcPeriod + "\n");
		System.out.println("Initial radius of orbit (calculated) " + radius + " m");
		System.out.println("Final radius of orbit (calculated) " + radius2 + " m\n\n");

		outFile.close();
	}

	/**
	 * Method to test whether spreads in X and Y are equal within tolerance
	 *
	 * @param spreadX   Bunch spread in X
	 * @param spreadY   Bunch spread in Y
	 * @param spreadTol relative difference in spreadX and spreadY
	 * @return true if spreads are equal within tolerance. False otherwise.
	 */
	public static boolean spreadTest(double spreadX, double spreadY, double spreadTol) {
		return ((Math.abs(spreadX - spreadY) <= (spreadTol * spreadX))
				&& (Math.abs(spreadX - spreadY) <= (spreadTol * spreadY)));
	}

	/**
	 * Method to calculate the total potential energy of a bunch of particles in the
	 * fields of an accelerator
	 *
	 * @param acceleratorFields the e/m fields of the accelerator
	 * @param aBunch            the bunch of particles
	 * @return the potential energy in J
	 */
	public static double totalPE(ArrayList<GeneralEMField> acceleratorFields, Bunch<Particle> aBunch) {
		Iterator<Particle> bunchIt = aBunch.iterator();
		double potE = 0.0;
		while (bunchIt.hasNext()) {
			Particle aParticle = bunchIt.next();
			// Loop over all fields
			for (GeneralEMField field : acceleratorFields) {
				if (aParticle instanceof ChargedParticle) {
					potE += (field.getPotentialE((ChargedParticle) aParticle));
				}

			}
		}

		return potE;
	}
}
