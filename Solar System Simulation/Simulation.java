/**
* The program simulates the solar system.
* It includes the first 17 most massive bodies in our the solar system.
*
* The origin of the coordinate system is placed at the barycentre;
* 
* @author Vlad Carare
**/
import java.io.*; //first 2 packages for input/output files
import java.util.*; 
import java.util.regex.*;
import java.util.Scanner;
import java.lang.Math;


public class Simulation
{
    public static void main(String[] args) throws IOException
    {
    	
    	String magnitude = new String();
        Scanner sc = new Scanner(System.in);
        System.out.println("\n    The list containes the first 17 most massive celestial bodies\n" +
        	"  in the Solar System, choose the first N-bodies you want to introduce:");
        int numberBodies = sc.nextInt(); //A number of 17 most massive bodies in the Solar System are included.
        System.out.println("\n    Choose the magnitude of the timestep you want to use,\n" + 
        	"i.e.: seconds, minutes, hours, days, weeks, months(30 days) or years(365 days) \n" +
        	"    (lowercase please):");   
        magnitude = sc.next();
        System.out.println("\n    How many " + magnitude + " ?");
        double timeStep = sc.nextDouble(); // dt is the time step;    
        double countTimeStep = timeStep; // used for print statement
        //converts to seconds as our program uses seconds
        switch(magnitude)
        {
        case "seconds": break;
        case "minutes": timeStep*=60; break;
        case "hours": timeStep*=60*60; break;
        case "days": timeStep*=60*60*24; break;
        case "weeks": timeStep*=60*60*24*7; break;
        case "months": timeStep*=60*60*24*30; break;
        case "years": timeStep*=60*60*24*365; break;
        	default : System.out.print("    Magnitude string is invalid. Rerun and choose from options."); 
        	System.exit(0);
        	//exits if users introduces a different word than those given above.
        }
        
        
        System.out.println("\n    Choose the magnitude of the interval you want to evaluate it for,\n" + 
        	"i.e.: seconds, minutes, hours, days, weeks, months(30 days) or years(365 days) \n" +
        	"    (lowercase please):");
        String magnitude3 = sc.next();
        System.out.println("\n    How many " + magnitude3 + " ?");
        double interval= sc.nextDouble(); //after which time you want results 
        double count = interval; // used for print statement
        
        //converts to seconds as our program uses seconds
        switch(magnitude3)
        {
        case "seconds": break;
        case "minutes": interval*=60; break;
        case "hours": interval*=60*60; break;
        case "days": interval*=60*60*24; break;
        case "weeks": interval*=60*60*24*7; break;
        case "months": interval*=60*60*24*30; break;
        case "years": interval*=60*60*24*365; break;
        	default : System.out.print("    Magnitude string is invalid. Rerun and choose from options."); 
        	System.exit(0);
        	//exits if users introduces a different word than those given above.
        }
        
        //checks if user wants intermediate results
        System.out.println("\n    Do you also want intermediate results? (yes/no)");
        String answer = sc.next();
        int decision=2;
        double intermediateInterval=interval;
        switch(answer)
        {
        case "no": decision = 0; break;
        case "yes": decision = 1; break;
        	default : System.out.print("   Invalid entry. Rerun and choose from options."); 
        	System.exit(0);
        	//exits if users introduces a different word than those given above.
        }
        
        
        double countIntermediate=1;
        String magnitude2 = new String();
        if(decision==1)
        {
        	System.out.println("\n    Choose the magnitude of the interval between the results,\n" + 
        		"i.e.: seconds, minutes, hours, days, weeks, months(30 days) or years(365 days) \n" +
        		"    (lowercase please):");
        	magnitude2 = sc.next();
        	System.out.println("\n    How many " + magnitude2 + " per intermediate interval?");
        	intermediateInterval = sc.nextDouble(); //the interval of time between results
        	countIntermediate = intermediateInterval;
        	
        	//converts to seconds as our program uses seconds
        	switch(magnitude2)
        	{
        	case "seconds": break;
        	case "minutes": intermediateInterval*=60; break;
        	case "hours": intermediateInterval*=60*60; break;
        	case "days": intermediateInterval*=60*60*24; break;
        	case "weeks": intermediateInterval*=60*60*24*7; break;
        	case "months": intermediateInterval*=60*60*24*30; break;
        	case "years": intermediateInterval*=60*60*24*365; break;
        		default : System.out.print("    Magnitude string is invalid. Rerun and choose from options."); 
        		System.exit(0);
        		//exits if users introduces a different word than those given above.
        	}       	
        }
        
		System.out.println("\n    Which method do you want to use? (enter number)\n" +
			"  1.Euler 2.Euler-Cramer 3.Verlat 4.Euler-Richardson");
		int choice = sc.nextInt(); // which algorithm to implement
		
		
		// Creates an array of particles where each entry is a astronomical object.
		// Also creates an array of gravitational fields, one for each planet.
		// GravField is a Inner Class enclosed by the Particle Class
		Particle[] solarSystem = new Particle[numberBodies];
		Particle.GravField gravField[] = new Particle.GravField[numberBodies];
		
		
		// Reads position, velocity and mass of the first "numberBodies" most massive bodies
		// in the SolarSystem with respect to the BARYCENTRE. 		//
		// Maximum number is 17.
		//
		// Data is taken from JPL HORIZON for 5th of December 1996.
		Input.read(solarSystem, gravField);        
		
		
		//Prints the output into a newly created file Output.txt.	
		java.io.File file2 = new java.io.File ("Output.txt");
		if(file2.exists())
		{
			System.out.println("The file already exists" ) ;
			System.exit(0) ; // quit the program
		}
		
		java.io.PrintWriter q = new PrintWriter(file2);
		
		q.println();
		switch(choice)
        {
        case 1 : q.println("    You used Euler algorithm."); break;
        case 2 : q.println("    You used Euler-Cramer algorithm."); break;
        case 3 : q.println("    You used Verlat algorithm."); break;
        case 4 : q.println("    You used Euler-Richardson algorithm."); break;
        }
        q.println();
	    q.println("    You used a time step of " + countTimeStep + " " + magnitude + ".");
		q.println();
		q.println("    The results after " + count + " " + magnitude3 + " is:");
		q.println();
		q.println("    Intermediate interval is " + countIntermediate + " " + magnitude2 + ".");
		
		
		//Calculates the kinetic and potential energy of all the bodies summed up.
		double totalEnergy = 0;
		totalEnergy = Calculator.calculateEnergy(solarSystem);
		q.println();
		q.println("    Initial energy(joules) is: ");
		q.println(totalEnergy);
		
		
		//Calculate the initial total momentum and angular momentum of the system.
		PhysicsVector totalMomentum = new PhysicsVector();
		PhysicsVector totalAngularMomentum = new PhysicsVector();
		
		totalMomentum.setVector(Calculator.calculateMomentum(solarSystem));
		q.println();
		q.println("    Initial momentum(newtons*seconds) is: ");
	    q.println(totalMomentum.returnSimpleString());		
		
		totalAngularMomentum.setVector(Calculator.calculateAngularMomentum(solarSystem));
		q.println();
		q.println("    Initial angular momentum(joules*seconds) is:");
	    q.println(totalAngularMomentum.returnSimpleString());		
	    
	    //Gives the initial vector position of the centre of mass.
	    PhysicsVector centreOfMass = new PhysicsVector();
	    centreOfMass.setVector(Calculator.calculateCM(solarSystem));
		q.println();
		q.println("    Initial position(metres) of the centre of mass is:");
		q.println(centreOfMass.returnSimpleString());
		
		if(decision==0) //if user does not want intermediate results
		{
			//Picks and implements the chosen algorithm.
			AlgorithmSelector.select(choice, timeStep, interval, solarSystem, gravField, decision);
			
			//Gives the difference between the final and initial total energy.
			totalEnergy = Calculator.calculateEnergy(solarSystem) - totalEnergy;
			q.println();
			q.println();
			q.println();
			q.println("    Total energy(joules) has changed by: ");
			q.println(totalEnergy);
			
			//Calculates the differences between the initial and final momenta.
			totalMomentum.decreaseBy(Calculator.calculateMomentum(solarSystem));
			totalMomentum.scale(-1); // we want P final-P initial; 
			q.println();
			q.println("    Total momentum(newtons*seconds) has changed by:");
			q.println(totalMomentum.returnSimpleString());		
			
			totalAngularMomentum.decreaseBy(Calculator.calculateAngularMomentum(solarSystem));
			totalAngularMomentum.scale(-1);
			q.println();
			q.println("    Total angular momentum(joules*seconds) has changed by:");
			q.println(totalAngularMomentum.returnSimpleString());
			
			//calculates and prints the distance that the centre of mass has moved.
			centreOfMass.decreaseBy(Calculator.calculateCM(solarSystem));
			centreOfMass.scale(-1);
			q.println();
			q.println("    The centre of mass has moved(metres) by:");
			q.println(centreOfMass.magnitude());
			
			q.println();
			q.println("    Result format is:");
			q.println("    x  y  z");
			q.println("    Vx  Vy  Vz");
			q.println("    Results are quoted in KM and KM/S.");
			q.println();
			
			
			//Prints the properties of the bodies from the most massive to the least massive.
			for(int i = 0 ; i < solarSystem.length ; i++)
			{
				String name = new String(solarSystem[i].returnName());
				//Convert from M and M/S back to KM/S and KM, to provide a comprehensive comparison.
				String position = new String(PhysicsVector.scale(0.001,solarSystem[i].returnPosition()).returnSimpleString());
				String velocity = new String(PhysicsVector.scale(0.001,solarSystem[i].returnVelocity()).returnSimpleString());
				q.println("                        " + name);
				q.println(position);
				q.println(velocity);
				q.println();
			}
			
			q.close();
		}
		else //if user wants intermediate results
		{
			//save initial conditions
			double initialTotalEnergy = Calculator.calculateEnergy(solarSystem);
			PhysicsVector initialTotalMomentum = new PhysicsVector(Calculator.calculateMomentum(solarSystem));
			PhysicsVector initialTotalAngularMomentum = new PhysicsVector(Calculator.calculateAngularMomentum(solarSystem));
			PhysicsVector initialCentreOfMass = new PhysicsVector(Calculator.calculateCM(solarSystem));
			
			//double initialMagnitude = solarSystem[1].returnPosition().magnitude();
			
			boolean[] verify = new boolean[15];//to be later used for tracking months passed	
			for(double p = 0; interval - p > 0.0001 ; p+=intermediateInterval)
			{
				//Picks and implements the chosen algorithm.
				AlgorithmSelector.select(choice, timeStep, intermediateInterval, solarSystem, gravField, decision);
				
				//Gives the difference between the final and initial total energy.
				totalEnergy = Calculator.calculateEnergy(solarSystem);
				q.println();
				q.println();
				q.println();
				q.println("    Total energy(joules) is:");
				q.println(totalEnergy); 
				
				//Calculates the differences between the initial and final momenta.
				totalMomentum.setVector(Calculator.calculateMomentum(solarSystem));
				q.println();
				q.println("    Total momentum(newtons*seconds) is:");
				q.println(totalMomentum.returnSimpleString());		
				
				totalAngularMomentum.setVector(Calculator.calculateAngularMomentum(solarSystem));
				q.println();
				q.println("    Total angular momentum(joules*seconds) is:");
				q.println(totalAngularMomentum.returnSimpleString());
				
				//calculates and prints the distance that the centre of mass has moved.
				centreOfMass.setVector(Calculator.calculateCM(solarSystem));
				q.println();
				q.println("    The position of the centre of mass is:");
				q.println(centreOfMass.magnitude());
				q.println(centreOfMass.returnSimpleString());
				
				q.println();
				q.println("    Result format is:");
				q.println("    x  y  z");
				q.println("    Vx  Vy  Vz");
				q.println("    Results are quoted in KM and KM/S.");
				q.println();
				
				
				//Prints the properties of the bodies from the most massive to the least massive.
				for(int i = 0 ; i < solarSystem.length ; i++)
				{ 
					String name = new String(solarSystem[i].returnName());
					//Convert from M and M/S back to KM/S and KM, to provide a comprehensive comparison.
					String position = new String(PhysicsVector.scale(0.001,solarSystem[i].returnPosition()).returnSimpleString());
					String velocity = new String(PhysicsVector.scale(0.001,solarSystem[i].returnVelocity()).returnSimpleString());
					q.println("                        " + name);
					q.println(position);
					//q.println((initialMagnitude - solarSystem[0].returnPosition().magnitude())/initialMagnitude*100 + "%");
					
					q.println(velocity);
					q.println();
				}
				
				if (p>(12*2592000)&&!verify[14]){ verify[14]=true; System.out.println("A year has passed"); }
				else
					if (p>(11*2592000)&&!verify[13]){ verify[13]=true; System.out.println("Eleven months have passed"); }
				else
					if (p>(10*2592000)&&!verify[12]){ verify[12]=true; System.out.println("Ten months have passed");}
				else
					if (p>(9*2592000)&&!verify[11]){ verify[11]=true; System.out.println("Nine months have passed");}
				else
					if (p>(8*2592000)&&!verify[10]){ verify[10]=true; System.out.println("Eight months have passed");} 
				else
					if (p>(7*2592000)&&!verify[9]){ verify[9]=true; System.out.println("Seven months have passed");}
				else
					if (p>(6*2592000)&&!verify[8]){ verify[8]=true; System.out.println("Half a year has passed"); }
				else 
					if (p>(5*2592000)&&!verify[7]){ verify[7]=true; System.out.println("Five months have passed"); }
				else
					if (p>(4*2592000)&&!verify[6]){ verify[6]=true; System.out.println("Four months have passed"); }
				else
					if (p>(3*2592000)&&!verify[5]){ verify[5]=true; System.out.println("Three months have passed"); }
				else
					if (p>(2*2592000)&&!verify[4]){ verify[4]=true; System.out.println("Two months have passed"); }
				else
					if(p>2592000&&!verify[3]){ verify[3]=true; System.out.println("A month has passed"); }
				else
					if (p>(3*604800)&&!verify[2]){ verify[2]=true; System.out.println("Three weeks have passed");} 
				else
					if (p>(2*604800)&&!verify[1]){ verify[1]=true; System.out.println("Two weeks have passed"); }
				else
					if (p>604800&&!verify[0]){ verify[0]=true; System.out.println("A week has passed"); }
				
				
				
				//Gives the difference between the final and initial total energy.
				initialTotalEnergy = Calculator.calculateEnergy(solarSystem) - initialTotalEnergy;
				q.println();
				q.println();
				q.println();
				q.println("    Total energy(joules) has changed by: ");
				q.println(initialTotalEnergy);
				
				//Calculates the differences between the initial and final momenta.
				initialTotalMomentum.decreaseBy(Calculator.calculateMomentum(solarSystem));
				initialTotalMomentum.scale(-1); // we want P final-P initial; 
				q.println();
				q.println("    Total momentum(newtons*seconds) has changed by:");
				q.println(initialTotalMomentum.returnSimpleString());		
				
				initialTotalAngularMomentum.decreaseBy(Calculator.calculateAngularMomentum(solarSystem));
				initialTotalAngularMomentum.scale(-1);
				q.println();
				q.println("    Total angular momentum(joules*seconds) has changed by:");
				q.println(initialTotalAngularMomentum.returnSimpleString());
				
				//calculates and prints the distance that the centre of mass has moved.
				initialCentreOfMass.decreaseBy(Calculator.calculateCM(solarSystem));
				initialCentreOfMass.scale(-1);
				q.println();
				q.println("    The centre of mass has moved(metres) by:");
				q.println(initialCentreOfMass.magnitude());
			}
			q.close();
			
		}
	}
}