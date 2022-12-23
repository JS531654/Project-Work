import java.lang.*;

public class Computer {
	private Bit haltBit = new Bit(true); // The bit to indicate if the computer is halted or not
	private memory mem = new memory(); // Memory member
	Bit compareBitZero = new Bit(false);
	Bit compareBitOne = new Bit(false);	
	Longword PC = new Longword(0); // Program Counter
	Longword currentInstruction = new Longword(0); // Instruction read by decode
	Longword result = new Longword(0); // The final result
	Longword op1 = new Longword(0); // The first register being used by the ALU
	Longword op2 = new Longword(0); // The second register being used by the ALU
	Longword branch = new Longword(0); 
	Longword SP = new Longword(1020); //Longword representation of the stack pointer
	public Longword[] registers = new Longword[16]; // All of the registers used by the Computer
	{
		for (int i = 0; i < registers.length; i++) {
			registers[i] = new Longword(0); // Defaults all the registers to zero
		}
	}

	public void run() throws Exception {
		while (haltBit.getValue() == true) {
			fetch();
			decode();
			execute();
			store();
		}
	}

	public void fetch() throws Exception 
	{
		currentInstruction = mem.read(PC); // Makes the current instruction equal to what is read from the PC's address
		Longword increment = new Longword(2); // New Longword used to increment the PC
		PC = rippleAdder.add(PC, increment); // Increment PC
	}

	public void decode() throws Exception 
	{
		Longword shiftedInstructions = new Longword(currentInstruction.getSigned()); // Creates a new longword with the same value as the currentInstruction but when shifting the currentInstruction will not be effected
		shiftedInstructions = shiftedInstructions.leftShift(4); // Brings the first register to the front of the longword
		for (int i = 0; i < 4; i++) 
		{
			op1.setBit(i, shiftedInstructions.getBit(i));// Sets op1 to the correct register
		}
		shiftedInstructions = shiftedInstructions.leftShift(4); // Brings the second register to the front of the longword
		for (int i = 0; i < 4; i++) 
		{
			op2.setBit(i, shiftedInstructions.getBit(i));
		}

	}

	public void execute() throws Exception {
		Longword register = new Longword(0);
		Longword moveValue = new Longword(0);
		Bit one = new Bit(currentInstruction.getBit(0).getValue()); // new Bit used to get the operation to be called by ALU
		Bit two = new Bit(currentInstruction.getBit(1).getValue()); // new Bit used to get the operation to be called by ALU
		Bit three = new Bit(currentInstruction.getBit(2).getValue()); // new Bit used to get the operation to be called by ALU
		Bit four = new Bit(currentInstruction.getBit(3).getValue()); // new Bit used to get the operation to be called by ALU
		if (one.getValue() == false && two.getValue() == false && three.getValue() == false && four.getValue() == false) // In the case the instruction is Halt
		{
			haltBit.set(false); // Sets the Halt bit to false to stop the CPU from running
		}
		else if (one.getValue() == false && two.getValue() == false && three.getValue() == false
				&& four.getValue() == true)// In the case the instruction is move
		{
			System.out.println("In the move instruction");
			for (int i = 0; i < 4; i++) {
				register.setBit(i, currentInstruction.getBit(i + 4)); // Allows the register to be obtained and later
																		// used
			}
			for (int i = 0; i < 8; i++) {
				moveValue.setBit(i, currentInstruction.getBit(i + 8)); // Gets the number that is going to be put in the
																		// register
			}
			int registerNumber = register.getSigned(); // Creates a variable that assigns which register is being moved
			int moveValueNumber = moveValue.getSigned(); // Creates a variable that assigns what value is being moved
															// into the register
			registers[registerNumber].set(moveValueNumber); // Moves the value into the register
		}
		else if (one.getValue() == false && two.getValue() == false && three.getValue() == true && four.getValue() == false) // In the case the instruction is interrupt
		{
			if (currentInstruction.getBit(15).getValue() == true) // in the case is to print all 1024 bytes of memory to the screen
			{
				for (int i = 0; i < 8192; i++)
				{
					System.out.print(mem.getMemory(i).toString()); // Prints all of the memory to the screen
				}
			} 
			else 
			{
				for (int i = 0; i < 16; i++) 
				{
					System.out.print(registers[i].toString()); // Prints all registers to the screen
				}
			}
		} 
		else if(one.getValue() == false && two.getValue() == true && three.getValue() == false && four.getValue() == false)
		{
			Longword rX = new Longword(0); //First register being compared 
			Longword rY = new Longword(0);  //Second register being compared 
			if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == false) //Test to see if the value is equal to the given register
			{
				rX = registers[0]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[1]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[2]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[3]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[4]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[5]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[6]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == false && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[7]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[8]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[9]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[10]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == false && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[11]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[12]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == false && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[13]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == false)//Test to see if the value is equal to the given register
			{
				rX = registers[14]; 
				
			}
			else if(currentInstruction.getBit(8).getValue() == true && currentInstruction.getBit(9).getValue() == true && currentInstruction.getBit(10).getValue() == true && currentInstruction.getBit(11).getValue() == true)//Test to see if the value is equal to the given register
			{
				rX = registers[15]; 
				
			}
			else 
			{
				throw new Exception("Invalid Register");
			}
			if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false) //Test to see if the value is equal to the given register
			{
				rY = registers[0]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[1]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[2]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[3]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[4]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[5]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[6]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[7]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[8]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[9]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[10]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[11]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[12]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[13]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
			{
				rY = registers[14]; 
				
			}
			else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
			{
				rY = registers[15]; 
				
			}
			else 
			{
				throw new Exception("Invalid Register");
			}
			if(rippleAdder.subtract(rX, rY).getSigned() == 0) //Test to see if registers are equal
			{

				compareBitZero.set(false);
				compareBitOne.set(true);
			} 
			else if(rippleAdder.subtract(rX, rY).getSigned() > 0) //Test to see if register x is greater than register y
			{

				compareBitZero.set(true);
				compareBitOne.set(false);
			}
			else //Test to see if register y is greater than register x
			{
				compareBitZero.set(false);
				compareBitOne.set(false);
			}
			
		}
		else if(one.getValue() == false && two.getValue() == true && three.getValue() == false && four.getValue() == true)
		{
			int jumpValue = 0; 

			
			if(currentInstruction.getBit(4).getValue() == false && currentInstruction.getBit(5).getValue() == false || compareBitZero.getValue() == true && compareBitOne.getValue() == false) //Branch If not equal 
			{
				if(compareBitZero.getValue() == false && compareBitOne.getValue() == false)
				{
						for(int i = 0; i <= 10; i++)
				           {
				        	   if(currentInstruction.getBit(6).getValue() == false)
				        	   {
				        		   if(currentInstruction.getBit(i).getValue() == true) //Test to see if the value at index i is 1
				                   { 
				                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
				                   }
				        	   }
				        	   else
				        	   {
				        		   if(currentInstruction.getBit(i).getValue() == false) //Test to see if the value at index i is 1
				                   { 
				                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
				                   }
				        	   }
				                  
				           }
				           if(currentInstruction.getBit(6).getValue() == true) //If the compare value is negative it negates the value 
				           {
				        	   jumpValue = jumpValue + 1;
				        	   	jumpValue = jumpValue * -1;                   
				           }
					}
				
			}
			else if(currentInstruction.getBit(4).getValue() == true && currentInstruction.getBit(5).getValue() == false || compareBitZero.getValue() == false && compareBitOne.getValue() == true) // Branch if greater than or equal to
			{
				if(compareBitZero.getValue() == true && compareBitOne.getValue() == false || compareBitZero.getValue() == false && compareBitOne.getValue() == true)
				{
					for(int i = 0; i <= 10; i++)
			           {
			        	   if(currentInstruction.getBit(6).getValue() == false) //
			        	   {
			        		   if(currentInstruction.getBit(i+ 7).getValue() == true) //Test to see if the value at index i is 1
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			        	   else
			        	   {
			        		   if(currentInstruction.getBit(i + 7).getValue() == false) //Test to see if the value at index i is 1
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			                  
			           }
			           if(currentInstruction.getBit(6).getValue() == true) //Test to see if the signed bit is true
			           {
			        	   jumpValue = jumpValue + 1; //Converts the number to negative 
			        	   	jumpValue = jumpValue * -1;                   
			           }
				}
			}
			else if(currentInstruction.getBit(4).getValue() == true && currentInstruction.getBit(5).getValue() == false) //Branch if greater than 
			{
				if(compareBitZero.getValue() == true && compareBitOne.getValue() == false)
				{
					for(int i = 0; i <= 10; i++)
			           {
			        	   if(currentInstruction.getBit(6).getValue() == false)
			        	   {
			        		   if(currentInstruction.getBit(i).getValue() == true) //Test to see if the value at index i is 1
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			        	   else
			        	   {
			        		   if(currentInstruction.getBit(i).getValue() == false) //Test to see if the value at index i is 0
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			                  
			           }
			           if(currentInstruction.getBit(6).getValue() == true) //Test to see if the number is negative 
			           {
			        	   jumpValue = jumpValue + 1; //Converts the number to negative 
			        	   	jumpValue = jumpValue * -1;                   
			           }
				}
			}
			else
			{
				if(compareBitZero.getValue() == false && compareBitOne.getValue() == true) // branch if equal 
				{
					for(int i = 0; i <= 10; i++)
			           {
			        	   if(currentInstruction.getBit(6).getValue() == false)
			        	   {
			        		   if(currentInstruction.getBit(i).getValue() == true) //Test to see if the value at index i is 1
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			        	   else
			        	   {
			        		   if(currentInstruction.getBit(i).getValue() == false) //Test to see if the value at index i is 1
			                   { 
			                 	  jumpValue += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
			                   }
			        	   }
			                  
			           }
			           if(currentInstruction.getBit(6).getValue() == true)
			           {
			        	   jumpValue = jumpValue + 1;
			        	   	jumpValue = jumpValue * -1;                   
			           }
				}
			}
			branch.set(jumpValue); //
		}
		else if(one.getValue() == false && two.getValue() == true && three.getValue() == true && four.getValue() == false) //Instruction test
		{
			Longword adressChange = new Longword(4); //Longword that will be used to add or subtract from the stack pointer
			if(currentInstruction.getBit(4).getValue() == false && currentInstruction.getBit(5).getValue() == false) //Push
			{
				Longword registerValue = new Longword(0);  //Longword that will be assigned to register inputed
				if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false) //Test to see if the value is equal to the given register
				{
					registerValue = registers[0]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[1]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[2]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[3]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[4]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[5]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[6]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[7]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[8]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[9]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[10]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[11]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[12]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[13]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = registers[14]; //Sets the register variable equal to the correct register
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = registers[15]; //Sets the register variable equal to the correct register
					
				}
				else 
				{
					throw new Exception("Invalid Register"); //In the case an invalid register is put in by the user 
				}
				mem.write(SP, registerValue); //Writes the register to the correct address according to the stack pointer 
				SP = rippleAdder.subtract(SP, adressChange); //Adjust the value of the stack pointer by 4 bytes
			}
			else if(currentInstruction.getBit(4).getValue() == false && currentInstruction.getBit(5).getValue() == true) //Pop
			{
				int registerValue = 0; 
				if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false) //Test to see if the value is equal to the given register
				{
					registerValue = 0; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 1; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 2; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 3; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 4; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 5; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 6; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == false && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 7; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 8; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 9; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 10; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == false && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 11; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 12; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == false && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 13; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == false)//Test to see if the value is equal to the given register
				{
					registerValue = 14; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else if(currentInstruction.getBit(12).getValue() == true && currentInstruction.getBit(13).getValue() == true && currentInstruction.getBit(14).getValue() == true && currentInstruction.getBit(15).getValue() == true)//Test to see if the value is equal to the given register
				{
					registerValue = 15; //Sets the register variable to the integer according to the inputed bit pattern
					
				}
				else 
				{
					throw new Exception("Invalid Register");
				}
				registers[registerValue] = mem.read(SP); //Pops the value in the stackpointer to the address inputed by the user. 
				SP = rippleAdder.add(SP, adressChange);  //Adjust the value of the stack pointer by 4 bytes
			}
			else if(currentInstruction.getBit(4).getValue() == true && currentInstruction.getBit(5).getValue() == false) //call
			{
				Bit falseBit = new Bit(false); 
					for(int i = 0; i < 12; i++) //Jumps to the instruction given 
					{
						PC.setBit(i, currentInstruction.getBit(i + 4));
					}
					for(int i = 12; i < 32; i++) //Sends the remaining bits to false 
					{
						PC.setBit(i, falseBit);
					}
					mem.write(SP, PC);//Writes the inputed instruction or pushes it onto the stack 
					SP = rippleAdder.subtract(SP, adressChange); //Adjusts the stackpointer by 4 as the address was filled 
				
			}
			else if(currentInstruction.getBit(4).getValue() == true && currentInstruction.getBit(5).getValue() == true) //Return
			{
				PC = mem.read(SP); //Sets the PC and the current instruction read to the value of the stack pointer by popping it off of the stack 
				SP = rippleAdder.add(SP, adressChange); //Adjusts the stackpointer by 4 as the address was popped off the stack 

				
			}
		}
		else 
		{
			result = ALU.doOp(one, two, three, four, op1, op2); // Calls the ALU with the registers and operations given
		}
	}

	public void store() throws Exception {
		Bit falseBit = new Bit(false); 
		Bit one = new Bit(currentInstruction.getBit(0).getValue()); // new Bit used to get the operation to be called by ALU
		Bit two = new Bit(currentInstruction.getBit(1).getValue()); // new Bit used to get the operation to be called by ALU
		Bit three = new Bit(currentInstruction.getBit(2).getValue()); // new Bit used to get the operation to be called by ALU
		Bit four = new Bit(currentInstruction.getBit(3).getValue()); // new Bit used to get the operation to be called by ALU
		if(one.getValue() == false && two.getValue() && false && three.getValue() == true && four.getValue() == true) //Test to see if jump is called
		{
			for(int i = 0; i < 12; i++) //Jumps to the instruction given 
			{
				PC.setBit(i, currentInstruction.getBit(i + 4));
			}
			for(int i = 12; i < 32; i++) //Sends the remaining bits to false 
			{
				PC.setBit(i, falseBit);
			}
		}
		if(one.getValue() == false && two.getValue() && true && three.getValue() == false && four.getValue() == true) //Test to see if branch was called 
		{
			if(branch.getSigned() != 0) // Test to make sure that the condition of branch was met (Branch will be altered to not zero if it was met )
			{
				PC = rippleAdder.add(PC, branch);  // Adds the branch value to the PC
			}
		}
		Longword shiftedInstructions = new Longword(currentInstruction.getSigned()); // Creates a new longword with the same value as the currentInstruction but when shifting the currentInstruction will not be effected
		shiftedInstructions = shiftedInstructions.leftShift(12); // Brings the register for the result to be stored in to the front of the Longword
		Longword registerStore = new Longword(0); // Longword to store the correct register for storing the result
		for (int i = 0; i < 4; i++) {
			registerStore.setBit(i, shiftedInstructions.getBit(i));// Adds value to the storage longword
		}
		registers[registerStore.getSigned()] = result; // Saves result to the correct register.
	}

	public void preload(String[] args) throws Exception {

		for (int i = 0; i < args.length; i++) // Iterates through every string in the array
		{
			args[i] = args[i].replaceAll("\s", ""); // Removes the spaces from every string in the array
		}
		Longword address = new Longword(0); // Longword to store the address being written to
		Longword value = new Longword(0); // Longword to store the value being written
		for (int array = 0; array < args.length; array += 2) // Loops through all of the Strings in the array
		{
			for (int j = 0; j < 16; j++) {
				if (args[array].charAt(j) == '0') {
					value.setBit(j, new Bit(false)); // Accesses the first string and sets bits to true
				} else {
					value.setBit(j, new Bit(true)); // Accesses the first string and sets bits to true

				}
				if (args[array + 1].charAt(j) == '0') {
					value.setBit(j + 16, new Bit(false)); // Accesses the second string and sets bits to false
				} else {
					value.setBit(j + 16, new Bit(true)); // Accesses the second string and sets bits to true
				}
			}
		}
		mem.write(address, value); // Writes the value to memory
		address.set(address.getSigned() + 4); // Increments the address by 4

	}

}
