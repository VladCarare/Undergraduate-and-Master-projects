/**
*  Class reads the matrix form input of the different gates
**/
import java.io.*;
import java.util.*; 
import java.util.Scanner;

public class Input      
{
	/**
	**/
	public static void read(double[][] Hadamard1, double[][] Hadamard2, double[][] Hadamard3, double[][] Pi, double[][] Oracle, double[][] J, double[][] C23, double[][] H, double[][] Cnot)
	{
		int n = Hadamard1.length;
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
			int counter=0;
			while (scanner.hasNext())
			{
				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						Hadamard1[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}
				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						Hadamard2[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}
				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						Hadamard3[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}
				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						Pi[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						Oracle[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						J[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}				
				for(int i=0; i < n; i++)
				{
					for(int j=0; j < n; j++)
					{  
						C23[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}		
				for(int i=0; i < 2; i++)
				{
					for(int j=0; j < 2; j++)
					{  
						H[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}				
				for(int i=0; i < 4; i++)
				{
					for(int j=0; j < 4; j++)
					{  
						Cnot[i][j] = scanner.nextDouble();
						counter+=1; 
					}
				}
			}
		}
		// catch the type miss match exception if it exists
		catch (InputMismatchException e) {
			System.out.println ("Mismatch exception:" + e );
		}
	}
}