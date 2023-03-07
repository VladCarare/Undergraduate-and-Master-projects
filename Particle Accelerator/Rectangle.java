
/**
 * A Class to simulate a rectangle with a given size and orientation
 * @author Vlad Carare
 * @version 1.0
 */

import java.lang.Math;

public class Rectangle {

	private double length;
	private double width;
	private PhysicsVector orientation;
	private PhysicsVector centre;

	/**
	 * Default constructor, set everything to zero
	 */
	public Rectangle() {
		length = 0;
		width = 0;
		orientation = new PhysicsVector();
		centre = new PhysicsVector();
	}

	/**
	 * Consider infinite length, centred at origin and oriented along x-axis
	 * 
	 * @param widthIn the smaller side of the rectangle
	 */
	public Rectangle(double widthIn) {
		this();
		width = widthIn;
		orientation = new PhysicsVector(1, 0, 0);
	}

	/**
	 * Create rectange of given size, centred at origin and oriented along x-axis
	 * 
	 * @param lengthIn the larger side of the rectangle
	 * @param widthIn the smaller side of the rectangle
	 */
	public Rectangle(double lengthIn, double widthIn) {
		this(widthIn);
		length = lengthIn;
	}

	/**
	 * Create rectangle of given size with given orientation, centred at origin.
	 * 
	 * @param lengthIn      the larger side of the rectangle
	 * @param widthIn       the smaller side of the rectangle
	 * @param orientationIn the orientation in space, normalised
	 */
	public Rectangle(double lengthIn, double widthIn, PhysicsVector orientationIn) {
		this(lengthIn, widthIn);
		orientation = new PhysicsVector(orientationIn).getUnitVector();
	}

	/**
	 * Set size, orientation and centre of rectangle
	 * 
	 * @param lengthIn      the larger side of the rectangle
	 * @param widthIn       the smaller side of the rectangle
	 * @param orientationIn the orientation in space, normalised
	 * @param centreIn      the centre of the rectangle
	 */
	public Rectangle(double lengthIn, double widthIn, PhysicsVector orientationIn, PhysicsVector centreIn) {
		this(lengthIn, widthIn, new PhysicsVector(orientationIn).getUnitVector());
		centre = new PhysicsVector(centreIn);
	}

	/**
	 * The copy constructor
	 * 
	 * @param rectangleIn the rectangle object to copy
	 */
	public Rectangle(Rectangle rectangleIn) {
		length = rectangleIn.getLength();
		width = rectangleIn.getWidth();
		orientation = rectangleIn.getOrientation();
		centre = rectangleIn.getCentre();
	}

	/**
	 * @return length of rectangle
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @return width of rectangle
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return orientation of rectangle
	 */
	public PhysicsVector getOrientation() {
		return new PhysicsVector(orientation);
	}

	/**
	 * @return centre position of rectangle
	 */
	public PhysicsVector getCentre() {
		return new PhysicsVector(centre);
	}

	/**
	 * Check if point is in rectangle
	 * 
	 * @param aPoint the point we want to check whether it's inside the rectangle
	 * @return whether given point in is rectangle
	 */
	public boolean contains(PhysicsVector aPoint) {

		boolean inRectangle = false;

		PhysicsVector vectorAlongLength = PhysicsVector.scale(length, orientation);
		PhysicsVector vectorAlongWidth = PhysicsVector.scale(width,
				PhysicsVector.cross(new PhysicsVector(0, 0, 1), orientation));

		PhysicsVector auxiliaryVector = PhysicsVector.subtract(centre, aPoint);

		if (Math.abs(PhysicsVector.dot(auxiliaryVector, vectorAlongLength.getUnitVector())) <= length)
			if (Math.abs(PhysicsVector.dot(auxiliaryVector, vectorAlongWidth.getUnitVector())) <= width)
				inRectangle = true;

		return inRectangle;
	}

}
