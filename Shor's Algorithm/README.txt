This program simulates a quantum circuit on a classical computer, by implementing quantum states as array vectors, quantum operations as matrix operations and quantum measurement as classical random number generators.
Details and benchmarking of the code are provided in sections 2.3,2.4,2.5 of the report herein. 


***********Run by compiling Cnot.java***************

Unfortunately the project is not greatly documented, but
 you can run the Shor's algorithm by compiling Cnot.java and then running the Cnot.class.

You can also run Grover's Search algorithm by going to Cnot.java and in the main() method
do chooseGrover() instead of chooseShorsExplicitFunctionGate(); 

Note there are 2 ways of running Shor's Algorithm. One is to use the chooseShorsExplicitFunctionGate() function, which uses an explicit decomposition of the main Shor's gate, found in the literature.
Alternatively you can use the chooseShors() method, which will create the function gate from scratch but may take longer and need more memory. 