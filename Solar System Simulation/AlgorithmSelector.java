/**
* Class contains no objects but encloses the method
* which selects which algorithm to implement based on the choice of the person.
*
* The algoritms included are: Euler, Euler-Cramer, Verlet and Euler-Richardson.
**/
public class AlgorithmSelector
{
	/** 
	* Method uses switch operator to select the chosen method
	* @param choice  the choice in accord to which an algorithm will be chosen
	* @param timeStep the time step chosen by the person
	* @param interval the period of measurements
	* @param solarSystem the array of celestial bodies
	* @param gravField the array of gravitational accelerations
	**/
	public static void select(int choice,
		double timeStep,
		double interval,
		Particle[] solarSystem,
		Particle.GravField gravField[],
		int decision
		)
	{
		boolean[] verify = new boolean[15];//to be used for tracking months passed	
		
		
		//loops over the method until the period is over
		for(double i=0; i < interval; i+=timeStep)
		{
			
			//keeping track of the time
			if(decision==0)
			{
				
				if (i>(12*2592000)&&!verify[14]){ verify[14]=true; System.out.println("A year has passed"); }
				else
					if (i>(11*2592000)&&!verify[13]){ verify[13]=true; System.out.println("Eleven months have passed"); }
					else
						if (i>(10*2592000)&&!verify[12]){ verify[12]=true; System.out.println("Ten months have passed");}
					else
						if (i>(9*2592000)&&!verify[11]){ verify[11]=true; System.out.println("Nine months have passed");}
					else
						if (i>(8*2592000)&&!verify[10]){ verify[10]=true; System.out.println("Eight months have passed");} 
					else
						if (i>(7*2592000)&&!verify[9]){ verify[9]=true; System.out.println("Seven months have passed");}
					else
						if (i>(6*2592000)&&!verify[8]){ verify[8]=true; System.out.println("Half a year has passed"); }
					else 
						if (i>(5*2592000)&&!verify[7]){ verify[7]=true; System.out.println("Five months have passed"); }
					else
						if (i>(4*2592000)&&!verify[6]){ verify[6]=true; System.out.println("Four months have passed"); }
					else
						if (i>(3*2592000)&&!verify[5]){ verify[5]=true; System.out.println("Three months have passed"); }
					else
						if (i>(2*2592000)&&!verify[4]){ verify[4]=true; System.out.println("Two months have passed"); }
					else
						if(i>2592000&&!verify[3]){ verify[3]=true; System.out.println("A month has passed"); }
					else
						if (i>(3*604800)&&!verify[2]){ verify[2]=true; System.out.println("Three weeks have passed");} 
					else
						if (i>(2*604800)&&!verify[1]){ verify[1]=true; System.out.println("Two weeks have passed"); }
					else
						if (i>604800&&!verify[0]){ verify[0]=true; System.out.println("A week has passed"); }
					
			}
			
			
			//Reset the gravitational acceleration so we can calculate it for the new position;
			for(int k=0; k < solarSystem.length; k++)
			{
				gravField[k].resetAcceleration();
			}
			
			//Calculates and stores the acceleration on each planet due to all other planets,
			//at the new position, before it is passed onto the methods.
			for(int k=0; k < solarSystem.length; k++)
			{
				for(int j=0; j < solarSystem.length; j++)
				{
					if(j!=k)
					{
						gravField[k].gravAcceleration(solarSystem[j]);
						
					}
				}
			}
			switch(choice)
			{
			case 1:
				//uses Euler's formulae to calculate the new position of each particle
				for(int k=0; k < solarSystem.length; k++)
				{
					solarSystem[k].applyEuler(timeStep, gravField[k].returnAcceleration());	
				} 
			case 2:
				//uses Euler Cramer formulae to calculate the new position of each particle
				for(int k=0; k < solarSystem.length; k++)
				{
					solarSystem[k].applyEulerCramer(timeStep, gravField[k].returnAcceleration());	
				} break;
			case 3:
				//uses Verlat's formulae to calculate the new position of each particle				
				for(int k=0; k < solarSystem.length; k++)
				{
					solarSystem[k].applyVerlat(timeStep, gravField[k].returnAcceleration(), k, solarSystem, gravField);	
				} break;
			case 4:
				//uses Verlat's formulae to calculate the new position of each particle
				for(int k=0; k < solarSystem.length; k++)
				{
					solarSystem[k].applyEulerRichardson(timeStep, gravField[k].returnAcceleration(), k, solarSystem, gravField);	
				} break;
			}
		}
	}
}