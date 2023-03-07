
/**
 * Represents a uniform electric localised to a specific region in space. SI
 * units are used throughout.
 *
 * @author Vlad Carare
 * @version 1.0
 */

public class LocalisedEField extends EMField {

	protected Rectangle boundingRegion;

	/**
	 * Default constructor. Set data members to zero.
	 *
	 */
	public LocalisedEField() {
		super();
	}

	/**
	 * Constructor with one input - the electric field strength
	 *
	 * @param electricIn     The electric field strength
	 * @param boundingRegion The region bounding the accelerating field, assumed to
	 *                       be a rectangle
	 *
	 */
	public LocalisedEField(PhysicsVector electricIn, Rectangle boundingRegionIn) {
		super(electricIn, new PhysicsVector());
		boundingRegion = new Rectangle(boundingRegionIn);
	}

	/**
	 * 
	 * @param aPosition The position at which the electric field is evaluated
	 * @return The current value of the electric field strength
	 */
	@Override
	public PhysicsVector getElectric(PhysicsVector aPosition) {
		if (boundingRegion.contains(aPosition))
			return getElectric();
		else
			return new PhysicsVector();
	}

	/**
	 * Return the electric field strength if the particle is within the region of
	 * non-zero field
	 *
	 * @param aParticle The particle at whose position the electric field is evaluated
	 * @return The current value of the electric field strength
	 */
	@Override
	public PhysicsVector getElectric(Particle aParticle) {
		if (boundingRegion.contains(aParticle.getPosition()))
			return getElectric();
		else
			return new PhysicsVector();

	}

	/**
	 * @return the Rectangle object defining the region of electric field
	 */
	public Rectangle getBoundingRegion() {
		return new Rectangle(boundingRegion);
	}

}
