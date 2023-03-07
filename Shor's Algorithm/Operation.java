/**
* This class defines various operations which are not defined
* in the main class so it looks more neat.
**/
import java.util.Scanner;
import java.lang.Math;
import java.io.*; //first 2 packages for input/output files
import java.util.*; 
import java.util.regex.*;
import java.util.Scanner;
public class Operation
{
	
	static boolean iUsedShorsAlgorithm = false;
	
	
	public static double[] multiply(double[][] matrix, double[] vector)
	{
		int n = matrix.length;
		double[] result = new double[n];
		for(int i=0; i < n; i++)
		{
			for(int j=0; j < n; j++)
			{  
				result[i]+=matrix[i][j]*vector[j];
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	public static double[][] multiply(double[][] matrix1, double[][] matrix2)
	{
		int n = matrix1.length;
		double[][] result = new double[n][n];
		for(int k=0; k < n; k++)
			for(int i=0; i < n; i++)
			{
				for(int j=0; j < n; j++)
				{  
					result[k][i]+=matrix1[k][j]*matrix2[j][i];
				}
			}
			return result;
	}
	
	
	
	
	
	
	/**
	*  Method takes a vector and normalises it.
	**/
	public static void normalise(double[] vector)
	{
		int n = vector.length;
		double dummy=0; //dummy variable;
		for(int i=0; i < n; i++)
		{
			dummy+=vector[i]*vector[i]; // sum of squares of amplitudes
		}
		for(int i=0; i < n; i++)
		{
			vector[i]=vector[i]/(Math.sqrt(dummy));
			System.out.println(i+"th component: " + vector[i]);
		}
	}
	
	
	
	
	
	
	/**
	*  Method takes a superposition and generates a random number to simulate
	* a measurement.
	**/
	public static void measure(double[] vector, PrintWriter q)
	{
		double random = Math.random(); //introduce random number generator to 
		//simulate the randomness of measurement;
		System.out.println(" Random number is: " + random);
		int n = vector.length;
		double dummy=0; //dummy variable;
		int i=0;
		while(random>dummy)
		{
			dummy+=vector[i]*vector[i];
			if(random<dummy)break;
			if(i<n-1) i++;
		}
		int length=vector.length;
		int noOfQubits=0;
		while(length>1)
		{
			length/=2;
			noOfQubits++;
		}
		System.out.println("i is " + i + " and this corresponds to the " +(i+1)+ "th entry." );
		convertToBinaryUsingString(i, noOfQubits); //convert a number
		// i.e 6 to binary i.e 110;
	}
	
	/**
	*  Method takes a superposition and generates a random number to simulate
	* a measurement. It then returns a number i, corresponding to which of the
	* basis states it collapses to.
	**/
	public static int measure(double[] vector)
	{
		double random = Math.random(); //introduce random number generator to 
		//simulate the randomness of measurement;
		System.out.println(" Random number is: " + random);
		int n = vector.length;
		double dummy=0; //dummy variable;
		int i=0;
		while(random>dummy)
		{
			dummy+=vector[i]*vector[i];
			if(random<dummy)break;
			if(i<n-1) i++;
		}
		return i;
	}
	
	
	
	//convert to binary methods as we want vector
	// to show in the form 001,010,111, etc.
	/*  static void convertToBinary(int no){
	int container[] = new int[100];
	int i = 0;
	while (no > 0){
	container[i] = no%2;
	i++;
	no = no/2;
	}
	for (int j = i -1 ; j >= 0 ; j--){
	System.out.print(container[j]);
	}
    }*/
    
    
    
    
    
    
    /**
    *  convert to binary methods as we want vector
	*  to show in the form 001,010,111, etc. but does not print as the other method
	* PRINTS IN REVERSE ORDER
    **/
	public static int[] convertToBinary(int no){
        int container[] = new int[100];
        int i = 0;
        
        while (no > 0)
        {
            container[i] = no%2;
            i++;
            no = no/2;
        }
        
        /*
        i--;
        for (int j = 0 ; j <= i ; j++)
        {
        aux[j]=container[i-j];
        // arranges elements such that aux[i max] = the first entry in binary
        // notation
        }*/
        return container;
    }
    
    
    
    
    
    
    
    /**
    *same thing but uses strings instead of integers
    **/
    static void convertToBinaryUsingString(int no,int noOfQubits){
        StringBuilder result = new StringBuilder();
        int i,j=i=0;
        while (no > 0 || j<noOfQubits) // 3 steps assure that 2 will be printed as
        	// 010 instead of just 10;
        {
            result.append(no%2);
            i++;
            no = no/2;
            j++;
        }
        result.append("|");
        result.reverse();
        result.append(">");
        System.out.println(result);
    }
    
    /**
    *  Print vector on screen.
    **/
	public static void printVector(double[] vector)
	{
		for(int i=0; i < vector.length; i++)
		{
			System.out.println(vector[i] + " ");
		}
	}

    
    /**
    *  Print vector on screen.
    **/
	public static void printVector(int[] vector)
	{
		for(int i=0; i < vector.length; i++)
		{
			System.out.println(vector[i] + " ");
		}
	}
	
	
		
	    /**
    *  Print vector in output file.
    **/
	public static void printVector(double[] vector, PrintWriter q)
	{
		for(int i=0; i < vector.length; i++)
		{
			q.println(vector[i] + " ");
		}
	}
	
	
    /**
    *  The Grover iteration FOR 3 QUBITS! The extended version appears later;
    **/
	public static double[] applyGrover(double[] result, double[][] Hadamard1, double[][] Hadamard2, double[][] Hadamard3, double[][] Pi, double[][] Oracle, double[][] J)
	{
		int n=Hadamard1.length;
		result = multiply(Oracle,result);
		result = multiply(Hadamard1,result);
		result = multiply(Hadamard2,result);
		result = multiply(Hadamard3,result);
		result = multiply(J,result);
		result = multiply(Hadamard1,result);
		result = multiply(Hadamard2,result);
		result = multiply(Hadamard3,result);
		return result;
	}
	
	/**
    *  Method prints matrix into Output file
    **/
	public static void printMatrix(double[][] matrix, PrintWriter q)
	{
		
		for(int i=0; i < matrix.length; i++)
		{
			for(int j=0; j < matrix[0].length; j++)
			{
				q.print(matrix[i][j] + " ");
			}
			q.println();
		}
	}
	
	
	/**
	* Method creates a 8x8(3 qubit) Hadamard gate acting on qubit 2 using only the
	* 1 qubit Hadamard gate matrix
	*/
	public static void createH2(double[][] H, PrintWriter q)
	{
		double[][] dummyGate = new double[8][8];
		
		int k,l,m,n;
		
		for(int i=0 ; i < 8 ; i++)
		{
			for(int j=0 ; j < 8 ; j++)
			{
				if(j%2==i%2) 
				{
					m=i/2;
					n=j/2;
					k=m%2;
					l=n%2;
					m=m/2;
					n=n/2;
					if(m%2==n%2)
					{
						dummyGate[i][j]=H[k][l];
					}
					else dummyGate[i][j]=0;
				}
				else
				{
					dummyGate[i][j]=0;
				}
			}
		}
		
		q.println("Gate is:");
		printMatrix(dummyGate, q);
	}
	
	
	
	
	
	
	
	/**
	*  Method creates a NxN(N qubits) Hadamard gate acting on a qubit of choice!
	**/
	public static double[][] createHadamard(double[][] H, PrintWriter q, int noOfQubits, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		target=noOfQubits-target-1; // because convertToBinary method reverses order
		// and substracts 1 because notation begins from 0;
        
	 	double[][] dummyGate = new double[size][size];
	 	q.println("Target is: " + target);
		for(int i=0 ; i < size ; i++)
		{
			for(int j=0 ; j < size ; j++)
			{
				boolean verdict=true; 
				
				int m[] = convertToBinary(i); // converts i,j to an array of
				int n[] = convertToBinary(j); // 1s and 0s;
				
				
				for(int k=0; k < noOfQubits ; k++)
				{
					if(k!=target && m[k]!=n[k] && verdict)
					{
						dummyGate[i][j]=0;
						verdict = false; //if delta function gives 0 there no 
						// need to compute other elements since answer is 0;
					}
				}
				if(verdict)
				{
					dummyGate[i][j]=H[m[target]][n[target]];
				}
				
			}
		}
		
		return dummyGate;
	}
	
	
	
	
	
	
	
	/**
	*  Method creates a NxN(N qubits) sparse Hadamard gate acting on a qubit of choice!
	**/
	public static double[][] createSparseHadamard(double[][] H, PrintWriter q, int noOfQubits, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		target=noOfQubits-target-1; // because convertToBinary method reverses order
		// and substracts 1 because notation begins from 0;
		
		
		// sparsing begins with the realisation that rows have equal numbers of
		// non-zero elements. Therefore to calculate the size of the sparse
		// matrix is enough to calculate the number of non-zero elements
		// in the first row.
		int sparseSize=0;
		for(int j=0 ; j < size ; j++)
		{
			boolean verdict=true; 
			
			int m[] = convertToBinary(0); // converts i,j to an array of
			int n[] = convertToBinary(j); // 1s and 0s;
			
			for(int k=0; k < noOfQubits ; k++)
			{
				if(k!=target && m[k]!=n[k] && verdict)
				{
					verdict = false; //if delta function gives 0 there no 
					// need to compute other elements since answer is 0;
				}
			}
			if(verdict)
			{
				sparseSize++;
			}
		}
		
	 	double[][] sparseGate = new double[size][sparseSize];
	 	
	 	q.println("Target is: " + target);
		for(int i=0 ; i < size ; i++)
		{
			int l=0;
			for(int j=0 ; j < size ; j++)
			{
				boolean verdict=true; 
				
				int m[] = convertToBinary(i); // converts i,j to an array of
				int n[] = convertToBinary(j); // 1s and 0s;
				
				for(int k=0; k < noOfQubits ; k++)
				{
					if(k!=target && m[k]!=n[k] && verdict)
					{
						verdict = false; //if delta function gives 0 there no 
						// need to compute other elements since answer is 0;
					}
				}
				if(verdict)
				{
					System.out.println(l);
					sparseGate[i][l]=H[m[target]][n[target]];
					l++;
				}
				
			}
		}
		
		return sparseGate;
	}
	
	
	
	
	
	
	
	/**
	* Converts a normal matrix to a sparse matrix and a column index matrix
	* ALTHOUGH CAN'T RETURN THE INDEX MATRIX
	* I SUBSEQUENTLY BUILT SPARSEMATRIX CLASS TO DEFINE THIS OBJECT
	**/
	public static double[][] convertToSparse(double[][] matrix, PrintWriter q)
	{
		
		int size = matrix.length;
		int noOfQubits=0;
		while(size>1)
		{
			size/=2;
			noOfQubits++;
		}
		size = matrix.length;
		// sparsing begins with the realisation that rows have equal numbers of
		// non-zero elements. Therefore to calculate the size of the sparse
		// matrix is enough to calculate the number of non-zero elements
		// in the first row.
		int sparseSize=0;
		for(int j=0 ; j < size ; j++)
		{
			if(matrix[0][j]>0.1) // >0.1 to avoid issues related to double approximations
			{
				sparseSize++;
			}
		}
		
	 	double[][] sparseMatrix = new double[size][sparseSize];
	 	double[][] columnIndexMatrix = new double[size][sparseSize];
		for(int i=0 ; i < size ; i++)
		{
			int l=0;
			for(int j=0 ; j < size ; j++)
			{				
				if(matrix[0][j]>0.1) // >0.1 to avoid issues related to double approximations
				{
					System.out.println(l);
					sparseMatrix[i][l]=matrix[i][j];
					columnIndexMatrix[i][l]=j;
					l++;
				}
				
			}
		}
		
		return sparseMatrix;
	}
	
	
	
	
	
	/**
	* Method creates a 8x8 Cnot gate acting on qubit 2 using only the
	* 1 qubit Cnot gate matrix
	*/
	public static void createC21(double[][] Cnot, PrintWriter q)
	{
		double[][] dummyGate = new double[8][8];
		
		int m,n;
		
		for(int i=0 ; i < 8 ; i++)
		{
			for(int j=0 ; j < 8 ; j++)
			{
				if(j%2==i%2) // verifies whether the last digit in the binary
					// notation of the two numbers is the same, as dictated by 
				// the equation in Candela's paper page 9/16
				{
					m=i/2;
					n=j/2;
					if(m!=3 && m!=0)
					{
						m=3-m;
					}
					if(n!=3 && n!=0)
					{
						n=3-n;
					}
					dummyGate[i][j]=Cnot[m][n];
				}
				else
				{
					dummyGate[i][j]=0;
				}
			}
		}
		
		q.println("Gate is:");
		printMatrix(dummyGate, q);
	}
	
	
	
	
	
	/**
	* Method creates a NxN CNOT gate from 4x4 Cnot gate.
	* The target and control are to be defined by the user.
	**/
	public static double[][] createCnot(double[][] Cnot, PrintWriter q,int noOfQubits, int control, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		double[][] dummyGate = new double[size][size];
		
		int c=control; int t=target;
		target=noOfQubits-target-1; // because convertToBinary method reverses order
		control=noOfQubits-control-1; // because convertToBinary method reverses order
		
		
		for(int i=0 ; i < size ; i++)
		{
			for(int j=0 ; j < size ; j++)
			{
				boolean verdict=true; 
				int m[] = convertToBinary(i); // converts i,j to an array of
				int n[] = convertToBinary(j); // 1s and 0s;
				
				
				
				for(int k=0; k < noOfQubits ; k++)
				{					
					if(k!=target && k!=control && m[k]!=n[k] && verdict)
					{
						
						dummyGate[i][j]=0;
						verdict = false; //if delta function gives 0 there no 
						// need to compute other elements since answer is 0;
					}
				}
				if(verdict)
				{
					// in the predefined 4x4 CNOT gate the control qubit comes 
					// first, therefore we need it to make it come first here as
					// well. 
					// convert back to base 10; number will be between 0 and 3
					int aux1=0;
					int aux2=0;
					aux1=m[target] + 2*m[control];			
					aux2=n[target] + 2*n[control];			
					
					dummyGate[i][j]=Cnot[aux1][aux2]; //Cnot[control][target];
					
				}
				
			}	
		}
		q.println("CNOT gate controlled by qubit " + (c+1) + " acting on qubit " + (t+1));

		System.out.println("A CNOT gate was created");
		return dummyGate;
	}
	
	
	
	
	
	
	/**
	* Creates the matrix Oracle of a given size to be used in Grover's Algorthm
	**/
	public static double[][] createO( PrintWriter q, int noOfQubits, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		target=target-1;
		
	 	double[][] O = new double[size][size];
		for(int i=0 ; i < size ; i++)
		{
			O[i][i]=1;
		}
		
		O[target][target]=-1;
		q.println("Gate is:");
		printMatrix(O, q);	
		q.println();
		
		return O;
	}
	
	
	
	
	
	/**
	* Creates the matrix J of a given size to be used in Grover's Algorithm
	**/
	public static double[][] createJ( PrintWriter q, int noOfQubits)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
	 	double[][] J = new double[size][size];
		for(int i=0 ; i < size ; i++)
		{
			J[i][i]=1;			
		}
		
		J[0][0]=-1;
		q.println("Gate is:");
		printMatrix(J, q);
		q.println();
		
		return J;
	}
	
	
	
	
	
	
	
	/**
    *  The Grover iteration for n qubits 
    *  @param gate is an array of Hadamard gates on different qubits
    **/
	public static double[] applyGrover2(double[] result, double[][] gate[], double[][] Oracle, double[][] J)
	{
		int length=Oracle.length;
		int dummy=0;
		while(length>1)
		{
			length/=2;
			dummy++;
		}
		result = multiply(Oracle,result);
		for(int i=0; i < dummy; i++)
			result = multiply(gate[i],result);
		result = multiply(J,result);
		for(int i=0; i < dummy ; i++)
			result = multiply(gate[i],result);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	* UNFINISHED
	**/
	public static ComplexNumber[][] createPhaseGate(ComplexNumber[][] phaseGate, PrintWriter q, int noOfQubits, int control, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		
		ComplexNumber[][] dummyGate = new ComplexNumber[size][size];
		for(int i=0 ; i < size ; i++)
			for(int j=0 ; j < size ; j++)
			dummyGate[i][j] = new ComplexNumber();
		
		int c=control; int t=target;
		target=noOfQubits-target-1; // because convertToBinary method reverses order
		control=noOfQubits-control-1; // because convertToBinary method reverses order
		
		
		for(int i=0 ; i < size ; i++)
		{
			for(int j=0 ; j < size ; j++)
			{
				boolean verdict=true; 
				int m[] = convertToBinary(i); // converts i,j to an array of
				int n[] = convertToBinary(j); // 1s and 0s;
				
				
				
				for(int k=0; k < noOfQubits ; k++)
				{					
					if(k!=target && k!=control && m[k]!=n[k] && verdict)
					{
						
						dummyGate[i][j].setTo0();
						verdict = false; //if delta function gives 0 there no 
						// need to compute other elements since answer is 0;
					}
				}
				if(verdict)
				{
					// in the predefined 4x4 phase gate the control qubit comes 
					// first, therefore we need it to make it come first here as
					// well. 
					// convert back to base 10; number will be between 0 and 3
					int aux1=0;
					int aux2=0;
					aux1=m[target] + 2*m[control];			
					aux2=n[target] + 2*n[control];			
					
					dummyGate[i][j].equateTo(phaseGate[aux1][aux2]); //phaseGate[control][target];
					
				}
				
			}	
		}
		
				q.println("Phase gate PI/" + (2*(t-c)) + " controlled by qubit " + (c+1) + " acting on qubit " + (t+1));
		System.out.println("A phase gate was created");
		return dummyGate;
	}
	
	
	
	
	
	
	/**
	*  This method creates one permutation matrix to be used in Shor's Algorithm
	**/
	public static double[][] createShorsFunctionGate(PrintWriter q, int noOfQubits, int control, int L, int noToFactorise, int A)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		int j;
		
		double[][] dummyGate = new double[size][size];
		
		// because convertToBinary method reverses order
		//so 1,2,3,4,5,6,7 = 7,6,5,4,3,2,1 = m0 m1 m2 m3 l0 l1 l2
		control = noOfQubits - (L - control); // because by Candela's convention
		// l_2 comes 1st, l_1 second and l_0 third;
		// so for example user initiating control = 1 will give 
		// control = 4 which is l0
		
		System.out.println("Control is: " + control);
		
		for(int k=0 ; k < size ; k++)
		{
			int m[] = convertToBinary(k); // converts i,j to an array of 0s and 1s
			if(m[control] == 0) 
				j=k;
			else{
				int f=0;
				for(int w=0 ; w < noOfQubits - L ; w++) //
					f+=m[w]*Math.pow(2.,w); // converts f-register to a number and
				// assigns f that value
				if(f >= noToFactorise)
					j=k; 
				else{
					int fPrime = (A * f)  % noToFactorise;
					int n[] = convertToBinary(fPrime);
					j = 0;
					for(int w=0 ; w < noOfQubits - L ; w++)
						j+=n[w]*Math.pow(2.,w);
					for(int w= noOfQubits - L ; w < noOfQubits ; w++)
						j+=m[w]*Math.pow(2.,w); 
					// converts j to a number base 
					// 10 after we have performed the given algorithms
				}
			}
			dummyGate[j][k]=1;
		}	
		
		q.println("Function gate is:");
		printMatrix(dummyGate, q);
		return dummyGate;
	}
	
	
	
	/**
	* returns greatest common denominator of two number using Euclid's algorithm
	**/
	public static int GCD(int a, int b) {
		if (b==0) return a;
		return GCD(b,a%b);
	}
	
	
	
	
	
	/**
    *  Method prints complex matrix into Output file
    **/
	public static void printComplexMatrix(ComplexNumber[][] matrix, PrintWriter q)
	{
		
		for(int i=0; i < matrix.length; i++)
		{
			for(int j=0; j < matrix[0].length; j++)
			{
				q.print(matrix[i][j].real() + " i" + matrix[i][j].imaginary() + "   ");
			}
			q.println();
		}
	}
	
	
	
	/**
	*  Complex multiplication! 
	**/
	public static ComplexNumber[] complexMultiply(ComplexNumber[][] matrix, double[] vector)
	{
		int n = matrix.length;
		ComplexNumber[] result = new ComplexNumber[n];
		for(int i=0; i < n; i++)
			result[i] = new ComplexNumber();
		
		
		for(int i=0; i < n; i++)
		{
			for(int j=0; j < n; j++)
			{  
				result[i].add(ComplexNumber.multiply(matrix[i][j],vector[j]));
			}
		}
		return result;
	}
	
	
	
	
	/**
	*  Complex multiplication! 
	**/
	public static ComplexNumber[] complexMultiply(ComplexNumber[][] matrix, ComplexNumber[] vector)
	{
		int n = matrix.length;
		ComplexNumber[] result = new ComplexNumber[n];
		for(int i=0; i < n; i++)
			result[i] = new ComplexNumber();
		
		for(int i=0; i < n; i++)
		{
			for(int j=0; j < n; j++)
			{  
				result[i].add(ComplexNumber.multiply(matrix[i][j],vector[j]));
			}
		}
		return result;
	}
	
	
		
	
	public static ComplexNumber[] complexMultiply(double[][] matrix, double[] vector)
	{
		int n = matrix.length;		
		ComplexNumber[] result = new ComplexNumber[n];
		for(int i=0; i < n; i++)
			result[i] = new ComplexNumber();

		
		/*for(int i=0; i < n; i++)
		result[i].print();*/
		
		for(int i=0; i < n; i++)
		{
			for(int j=0; j < n; j++)
			{  
				result[i].add(ComplexNumber.multiply(matrix[i][j],vector[j]));

			}
		}
		
		

		return result;
	}
	
	
	
	
		
	/**
	*  Complex multiplication! 
	**/
	public static ComplexNumber[] complexMultiply(double[][] matrix, ComplexNumber[] vector)
	{
		int n = matrix.length;
		ComplexNumber[] result = new ComplexNumber[n];
		for(int i=0; i < n; i++)
			result[i] = new ComplexNumber();
		
		
		for(int i=0; i < n; i++)
		{
			for(int j=0; j < n; j++)
			{  
				result[i].add(ComplexNumber.multiply(vector[j],matrix[i][j]));
			}
		}
		return result;
	}
	
	
	
	
		
	
	/**
	*  Method takes a vector and normalises it.
	**/
	public static void normalise(ComplexNumber[] vector)
	{
		int n = vector.length;
		double dummy=0; //dummy variable;
		for(int i=0; i < n; i++)
		{
			dummy+=ComplexNumber.multiply(vector[i],vector[i].conjugate()).magnitude(); // sum of squares of amplitude 
		}
		for(int i=0; i < n; i++)
		{
			vector[i] = ComplexNumber.multiply(vector[i],(1./(Math.sqrt(dummy))));
			System.out.println(i+"th component: " + vector[i].real() + "+ i" + vector[i].imaginary());
		}
	}
	
	
	
	
	
	
	/**
	*  Method takes a superposition and generates a random number to simulate
	* a measurement.
	**/
	public static void measure(ComplexNumber[] vector, PrintWriter q)
	{
		double random = Math.random(); //introduce random number generator to 
		//simulate the randomness of measurement;
		System.out.println(" Random number is: " + random);
		int n = vector.length;
		double dummy=0; //dummy variable;
		int i=0;
		while(random>dummy)
		{
			
			dummy+=ComplexNumber.multiply(vector[i],vector[i].conjugate()).magnitude();
			if(random<dummy)break;
			if(i<n-1) i++;
		}
		int length=vector.length;
		int noOfQubits=0;
		while(length>1)
		{
			length/=2;
			noOfQubits++;
		}
		System.out.println("i is " + i + " and this corresponds to the " +(i+1)+ "th entry." );
		convertToBinaryUsingString(i, noOfQubits); //convert a number
		// i.e 6 to binary i.e 110;}
		
		if(iUsedShorsAlgorithm) Cnot.dummy = i;
	}
	

	
	public static void iUsedShorsAlgorithm(){ iUsedShorsAlgorithm = true; }

	
	
	
	
	
		
	/**
	* UNFINISHED
	**/
	public static ComplexNumber[][] createVGate(ComplexNumber[][] VGate, PrintWriter q, int noOfQubits, int control, int target)
	{
		int size=1;
		for(int i=0; i < noOfQubits; i++)
			size*=2; // each qubit has 2 computational basis states
		
		
		ComplexNumber[][] dummyGate = new ComplexNumber[size][size];
		for(int i=0 ; i < size ; i++)
			for(int j=0 ; j < size ; j++)
			dummyGate[i][j] = new ComplexNumber();
		
		int c=control; int t=target;

		target=noOfQubits-target-1; // because convertToBinary method reverses order
		control=noOfQubits-control-1; // because convertToBinary method reverses order
		
		
		for(int i=0 ; i < size ; i++)
		{
			for(int j=0 ; j < size ; j++)
			{
				boolean verdict=true; 
				int m[] = convertToBinary(i); // converts i,j to an array of
				int n[] = convertToBinary(j); // 1s and 0s;
				
				
				
				for(int k=0; k < noOfQubits ; k++)
				{					
					if(k!=target && k!=control && m[k]!=n[k] && verdict)
					{
						
						dummyGate[i][j].setTo0();
						verdict = false; //if delta function gives 0 there no 
						// need to compute other elements since answer is 0;
					}
				}
				if(verdict)
				{
					// in the predefined 4x4 phase gate the control qubit comes 
					// first, therefore we need it to make it come first here as
					// well. 
					// convert back to base 10; number will be between 0 and 3
					int aux1=0;
					int aux2=0;
					aux1=m[target] + 2*m[control];			
					aux2=n[target] + 2*n[control];			
					
					dummyGate[i][j].equateTo(VGate[aux1][aux2]); //VGate[control][target];
					
				}
				
			}	
		}
		if(VGate[2][3].imaginary()>0)
		q.println("Inverse V gate controlled by qubit " + (c+1) + " acting on qubit " + (t+1));
	else
		q.println("V gate controlled by qubit " + (c+1) + " acting on qubit " + (t+1));
		
		System.out.println("A V gate was created");
		return dummyGate;
	}
	
	
		/**
	**/
	public static void equateVectors(double[] vector1, double[] vector2)
	{
		for(int i=0; i < vector1.length; i++)
		{
			vector2[i]=vector1[i];
		}
		
	}
	
	/**
	**/
	public static void addVectors(double[] vector1, double[] vector2)
	{
		for(int i=0; i < vector1.length; i++)
		{
			vector2[i]+=vector1[i];
		}
		
	}
}