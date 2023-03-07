/**
*  Class reads the input from a previously created file.
*  The file contains information about the first 17 most massive
*  astronomical objects in our solar system.
*
*  In our case the file has data from JPL HORIZON for the
*  position and velocity of celestial bodies in the solar system
*  on 5-dec-1996.
**/
import java.io.*;
import java.util.*; 
import java.util.Scanner;

public class Input      
{
	/**
	*  The method loops over the file in order to
	*  transfer the information to the Particle Constructor
	*  which takes a name, a position, a velocity and a mass.
	*  @param solarSystem the array of the celestial bodies
	*  @param gravField the array of each body's gravitational attraction to other bodies
	**/
	public static void read(
		Particle[] solarSystem,
		Particle.GravField gravField[]
		)
	{
		File file = null;
		file = new File("InputFile.txt");
		Scanner scanner = null; 
		try {  // Create a scanner to read the file
			scanner = new Scanner (file);
		}
		catch (FileNotFoundException e){
			System.out.println ("File not found!");
			// Stop program if no file found
			System.exit (0);
		}
		// try reading the file
		// and throw an exception if it fails
		try {
			// check that there is a next entry of any type
			// and stops at the chosen number of astronomical objects
			int counter=0;
			while (scanner.hasNext())
			{
				
				for(int i=0; i < solarSystem.length; i++)
				{  
					
					String name = scanner.next();
					//Inputs are converted from KM/S and KM to M/S and M;
					double Rx = scanner.nextDouble()*1000;
					double Ry = scanner.nextDouble()*1000;
					double Rz = scanner.nextDouble()*1000;
					double Vx = scanner.nextDouble()*1000;
					double Vy = scanner.nextDouble()*1000;
					double Vz = scanner.nextDouble()*1000;
					double mass = scanner.nextDouble();
					
					solarSystem[i] = new Particle(name,Rx,Ry,Rz,Vx,Vy,Vz,mass);
					counter+=1; 
				}
				
				//instantialise the classes Particle and GravField
				for(int i=0; i < solarSystem.length; i++)
				{
					//Particle SolarSystem[i] = new Particle();
					gravField[i] = solarSystem[i].new GravField();
				}
				
				//if statement prevents an error from occuring
				// in the case when the chosen number of bodies is less
				// than the total number that the input file contains
				// and thus there is information left in the file.
				if(counter == solarSystem.length) break;
			}
		}
		// catch the type miss match exception if it exists
		catch (InputMismatchException e) {
			System.out.println ("Mismatch exception:" + e );
		}
	}
}