/**
* Class has multiple methods which work by summing the contribution
* of each astronomical object to a certain quantity, such as energy
* momentum, angular momentum and position of the centre of mass.
* Class has been constructed with the intent of freeing up space in 
* the main class.
**/
public class Calculator
{
    /**
    *  Method takes the array of particles as an argument
    *  and uses returnMomentum method defined in Particle Class
    *  to find the total momentum of the solar system.
    *  @Return the total momentum of the solar system.
    **/
    public static PhysicsVector calculateMomentum(Particle[] solarSystem)
    
    {
    	PhysicsVector totalMomentum = new PhysicsVector();
    	
    	for(int i = 0; i< solarSystem.length; i++)
    		totalMomentum.increaseBy(solarSystem[i].returnMomentum());
    	
    	return totalMomentum;
    }
    
    /**
    *  Method takes the array of particles as an argument
    *  and uses returnAngularMomentum method defined in Particle Class
    *  to find the total angular momentum of the solar system.
    *  @Return the total angular momentum of the solar system.
    **/
    public static PhysicsVector calculateAngularMomentum(Particle[] solarSystem)
    {
    	PhysicsVector totalAngularMomentum = new PhysicsVector();
    	
    	for(int i = 0; i< solarSystem.length; i++)
    		totalAngularMomentum.increaseBy(solarSystem[i].returnAngularMomentum());
    	
    	return totalAngularMomentum;
    }
    
    /**
    *  Method takes the array of particles as an argument
    *  and uses returnEnergy method defined in Particle Class
    *  to find the total energy of the solar system.
    *  @Return the total energy of the solar system.
    **/
    public static double calculateEnergy(Particle[] solarSystem)
    {
    	double energy = 0;
    	
       	for(int i = 0; i< solarSystem.length; i++)
       		//introduce argument i to avoid double counting
       		energy += solarSystem[i].returnEnergy(solarSystem, i);
       	
    	return energy;
    }
    
    /**
    *  Method takes the array of particles as an argument and 
    *  uses equation R = Sum(m_i * r_i)/total mass to find the
    *  vector positon of the centre of mass.
    *  @Return the position vector of the centre of mass of the solar system.
    *  
    **/
    public static PhysicsVector calculateCM(Particle[] solarSystem)
    {
    	PhysicsVector centreOfMass = new PhysicsVector();
    	double totalMass = 0;
    	for(int i = 0; i < solarSystem.length; i++)
       		totalMass += solarSystem[i].returnMass();
    	
       	//loop adds the contribution of each body.
       	for(int i = 0; i < solarSystem.length; i++)
       	{
       		PhysicsVector auxiliary = new PhysicsVector();
       		//auxiliary vector makes reading easier
       		auxiliary.setVector(solarSystem[i].returnPosition());
       		auxiliary.scale(solarSystem[i].returnMass());
       		
       		centreOfMass.increaseBy(auxiliary);
       	}
       	centreOfMass.scale(1/totalMass);
       	return centreOfMass;
    }
    
}