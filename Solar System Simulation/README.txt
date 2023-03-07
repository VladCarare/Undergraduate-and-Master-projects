
***********Run by compiling Simulation.java***************
Make sure you have all the files including InputFile.txt!


**********Simulation.java************

* Includes the main method
* The program simulates the solar system.
* 
* The user will be prompted to enter some values and then
* all the information about the planets is taken from InputFile.txt
* It includes the first 17 most massive bodies in our the solar system.
*
* The origin of the coordinate system is placed at the barycentre.



**********Input.java************

*  Class reads the input from a previously created file i.e. InputFile.txt.
*  The file contains information about the first 17 most massive
*  astronomical objects in our solar system.
*
*  In our case the file has data from JPL HORIZON for the
*  position and velocity of celestial bodies in the solar system
*  on 5-dec-1996.



**********Particle.java************

* The class defines a celestial body with mass, velocity, position, name 
* and offers methods for retrieving this properties. It also
* includes the Euler, Euler-Cramer and Verlat algorithms.
* Furthermore, it associates a gravitational acceleration on each 
* particle using the inner class GravField!



**********AlgorithmSelector.java************

* Class contains no objects but encloses the method
* which selects which algorithm to implement based on the choice
* of the person.
*
* The algoritms included are: Euler, Euler-Cramer, Verlet 
* and Euler-Richardson.



**********calculator.java************

* Class has multiple methods which work by summing the contribution
* of each astronomical object to a certain quantity, such as energy
* momentum, angular momentum and position of the centre of mass.
* Class has been constructed with the intent of freeing up space in 
* the main class.



**********PhysicsVector.java************

*No need to introduce this one