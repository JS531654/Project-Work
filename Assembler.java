
public class Assembler {
	public static String[] assemble(String [] writtenInstructions) throws Exception
	{
		String[] opCode = new String[writtenInstructions.length];  //String array to store the instructions as bits
		for(int i = 0; i < opCode.length; i++)
		{
			writtenInstructions[i] = writtenInstructions[i].toLowerCase(); //Prevents a case sensitive error 
			opCode[i] = ""; //Prevents any index in the array from being null 
		}
		
		for(int i = 0; i < writtenInstructions.length; i++)
		{
			
			String[] splitString = writtenInstructions[i].split(" ");
			if(splitString[0].equals("halt"))
			{
				opCode[i] = "0000 0000 0000 0000"; //Sets the halt bit opCode
			}
			else if(splitString[0].equals("move")) 
			{
				opCode[i] += "0001 ";  //The move opCode 
				opCode[i] += getRegister(splitString[1]);  //Gets the register and stores it in the opCode 
				opCode[i] += getMoveValue(splitString[2]);  //Move value to be stored 
				
			}
			else if(splitString[0].equals("interrupt"))
			{ 
				if(splitString[1].equals("1")) //Test to see if interrupt 1 or 2 is called
				{
					opCode[i] = "0010 0000 0000 0000";  //Interrupt 1 opCode
				}
				else
				{
					opCode[i] = "0010 0000 0000 0001"; //Interrupt 2 opCode
				}
			}
			else if(splitString[0].equals("jump"))
			{
				opCode[i] += "0011 0000  "; //Jump first 4 bits
				opCode[i] += getJumpValue(splitString[1]); 
				
			}
			else if(splitString[0].equals("compare"))
			{
				opCode[i] += "0100 0000 "; //Compare first 4 bits
				opCode[i] += getRegister(splitString[1]); 
				opCode[i] += getRegister(splitString[2]); 
			}
			else if(splitString[0].equals("branchifequal"))
			{
				opCode[i] += "0101 01"; //branch if equal first 6 bits
				opCode[i] += getBranchValue(splitString[1]); 
			}
			else if(splitString[0].equals("branchifnotequal"))
			{
				opCode[i] += "0101 10"; //branch if not equal first 6 bits
				opCode[i] += getBranchValue(splitString[1]); 

			}
			else if(splitString[0].equals("branchifgreaterthan"))
			{
				opCode[i] += "0101 10"; //branch if greater than first 6 bits
				opCode[i] += getBranchValue(splitString[1]); 

			}
			else if(splitString[0].equals("branchifgreaterthanorequal"))
			{
				opCode[i] += "0101 11"; //branch if greater than or equal to first 6 bits
				opCode[i] += getBranchValue(splitString[1]); 

			}
			else if(splitString[0].equals("stack"))
			{
				opCode[i] += "0110 "; //stack first 4 bits
			}
			else if(splitString[0].equals("multiply"))
			{
				opCode[i] += "0111 "; //Multiply opCode
				String firstRegister = getRegister(splitString[1]);  //Gets the first register being multiplied 
				String secondRegister = getRegister(splitString[2]); //Gets the second register being multiplied 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result  
				opCode[i] += firstRegister; //Adds the register bit pattern to the string
				opCode[i] += secondRegister; //Adds the register bit pattern to the string
				opCode[i] += storeRegister; //Adds the register bit pattern to the string

			}
			else if(splitString[0].equals("add"))
			{
				opCode[i] += "1000 "; //Add opCode
				String firstRegister = getRegister(splitString[1]); //Gets the first register being added 
				String secondRegister = getRegister(splitString[2]); //Gets the second register being added 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result 
				opCode[i] += firstRegister; //Adds the register bit pattern to the string
				opCode[i] += secondRegister; //Adds the register bit pattern to the string
				opCode[i] += storeRegister; //Adds the register bit pattern to the string
			}
			else if(splitString[0].equals("or"))
			{
				opCode[i] += "1001 ";  //Or oPCode
				String firstRegister = getRegister(splitString[1]); //Gets the first register value that is being or-ed 
				String secondRegister = getRegister(splitString[2]); //Gets the register value of the second register being or-ed 
				String storeRegister = getRegister(splitString[3]);  //Gets the Register value used to store the result of the or
				opCode[i] += firstRegister;  //Adds the registers bit pattern to the string
				opCode[i] += secondRegister; //Adds the register bit pattern to the string
				opCode[i] += storeRegister; //Adds the register bit pattern to the string

			}
			else if(splitString[0].equals("xor"))
			{
				opCode[i] += "1010 "; //xor Opcode
				String firstRegister = getRegister(splitString[1]);  //The first register xor is running on
				String secondRegister = getRegister(splitString[2]);  //The second register xor is running on
				String storeRegister = getRegister(splitString[3]); //The register the result will be stored in 
				opCode[i] += firstRegister;  //Adds the registers bit pattern to the string 
				opCode[i] += secondRegister; //Adds the registers bit pattern to the string
				opCode[i] += storeRegister; //Adds the registers bit pattern to the string

			}
			else if(splitString[0].equals("not"))
			{
				opCode[i] += "1011 "; //not opcode
				String firstRegister = getRegister(splitString[1]); 
				String secondRegister = getRegister(splitString[2]); 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result 
				opCode[i] += firstRegister;  //Adds the registers bit pattern to the string
				opCode[i] += secondRegister; //Adds the registers bit pattern to the string
				opCode[i] += storeRegister; //Adds the registers bit pattern to the string

			}
			else if(splitString[0].equals("leftshift"))
			{
				opCode[i] += "1100 "; //leftShift opcode 
				String firstRegister = getRegister(splitString[1]); 
				String secondRegister = getRegister(splitString[2]); 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result 
				opCode[i] += firstRegister; //Adds the registers bit pattern to the string
				opCode[i] += secondRegister; //Adds the registers bit pattern to the string
				opCode[i] += storeRegister; //Adds the registers bit pattern to the string

			}
			else if(splitString[0].equals("rightshift"))
			{
				opCode[i] += "1101 "; //rightShift opCode
				String firstRegister = getRegister(splitString[1]); 
				String secondRegister = getRegister(splitString[2]); 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result 
				opCode[i] += firstRegister; //Adds the registers bit pattern to the string
				opCode[i] += secondRegister; //Adds the registers bit pattern to the string
				opCode[i] += storeRegister; //Adds the registers bit pattern to the string

			}
			
			else if(splitString[0].equals("subtract"))
			{
				opCode[i] += "1111 "; //Subtract opcode
				String firstRegister = getRegister(splitString[1]); 
				String secondRegister = getRegister(splitString[2]); 
				String storeRegister = getRegister(splitString[3]); //Gets the register used to store the result 
				opCode[i] += firstRegister; //Adds the registers bit pattern to the string
				opCode[i] += secondRegister; //Adds the registers bit pattern to the string
				opCode[i] += storeRegister; //Adds the registers bit pattern to the string

			}
			else if(splitString[0].equals("return"))
			{
				opCode[i] += "0110 1100 0000 0000"; //Return op code 
			}
			else if(splitString[0].equals("push"))
			{
				opCode[i] += "0110 0000 0000 "; //First 12 bits of the push instruction
				opCode[i] += getRegister(splitString[1]);  //Adds the register bit pattern to the string
			}
			else if(splitString[0].equals("pop"))
			{
				opCode[i] += "0110 0100 0000 "; //First 12 bits of the pop instruction
				opCode[i] += getRegister(splitString[1]); //Adds the register bit pattern to the string
			}
			else if(splitString[0].equals("call"))
			{
				opCode[i] += "0110 10"; //First six bits of the call opcode
				opCode[i] += getCallValue(splitString[1]); //Address of the call being added to the string 
			}
			else
			{
				throw new Exception("Not Valid input");  //Exception in the case a non defined instruction is input
			}
			
			
		}
		for(int q = 0; q < opCode.length; q++)
		{
			System.out.println(opCode[q]); 
		}
		
		return opCode; 
	}
	public static String getRegister(String register) throws Exception
	{
		String result = ""; //Empty String to be returned 
		if(register.equals("r0")) 
		{
			result += "0000 "; //Register zero bit pattern
		}
		else if(register.equals("r1"))
		{
			result += "0001 "; //Register one bit pattern
		}
		else if(register.equals("r2"))
		{
			result += "0010 "; //Register two bit pattern
		}
		else if(register.equals("r3"))
		{
			result += "0011 "; //Register three bit pattern
		}
		else if(register.equals("r4"))
		{
			result += "0100 "; //Register four bit pattern
		}
		else if(register.equals("r5"))
		{
			result += "0101 "; //Register five bit pattern
		}
		else if(register.equals("r6"))
		{
			result += "0110 "; //Register six bit pattern
		}
		else if(register.equals("r7")) 
		{
			result += "0111 "; //Register seven bit pattern
		}
		else if(register.equals("r8"))
		{
			result += "1000 "; //Register eight bit pattern
		}
		else if(register.equals("r9"))
		{
			result += "1001 "; //Register nine bit pattern
		}
		else if(register.equals("r10"))
		{
			result += "1010 "; //Register ten bit pattern
		}
		else if(register.equals("r11"))
		{
			result += "1011 "; //Register eleven bit pattern
		}
		else if(register.equals("r12"))
		{
			result += "1100 "; //Register twelve bit pattern
		}
		else if(register.equals("r13"))
		{
			result += "1101 "; //Register thirteen bit pattern
		}
		
		else if(register.equals("r14"))
		{
			result += "1110 "; //Register fourteen bit pattern
		}
		else if(register.equals("r15"))
		{
			result += "1111 "; //Register fifteen bit pattern
		}
		else
		{
			throw new Exception("Invalid Register");  //In the case a register not in in memory is called 
		}
		return result; 
	}
	public static String getMoveValue(String number)
	{
		int bitPattern[] = new int[8]; //Array to store the value of the number in binary 
		for(int i = 0; i < 8; i++)
		{
			bitPattern[i] = 0; //Defaults all values to zero 
		}
		int value; //Create a value variable 
		String backwardsBinary = ""; //Empty string that will be used to store the first half of the binary number 
		value = Integer.parseInt(number); //Sets the variable to the string being passed in 
		String opCodeValue = ""; 
		int i = 0; //Counter so each index is reached 
		while(i < 8) //Makes sure the value doesn't go into decimal
		{
			if(value % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
			{
				backwardsBinary += 1; //adds a 1 to the binary string
			}
			else
			{
				backwardsBinary += 0; //adds a 0 to the binary string
			}
			i++; //Goes to the next index 
			value = value/2; //Cuts the value in half. 
		}

		for(int j = 7; j > 3; j--)
		{
			opCodeValue += backwardsBinary.charAt(j); //Reverses the number because it was originally backwards 
		}
		opCodeValue += " "; //Adds the space in the middle of the binary number 
		for(int j = 3; j >= 0; j--)
		{
			opCodeValue += backwardsBinary.charAt(j); //Reverses the number because it was originally backwards 
		}
		
		
		return opCodeValue; 
	}
	public static String getJumpValue(String value)
	{
		int[] binaryNumber = new int[8];  //Array to store the binary representation of the number 
		for(int i = 7; i >= 0; i--)
		{
			binaryNumber[i] = 0; //Incase the full binary number isn't reached defaults to zero
		}
		int numValue; 
		String jumpValue = "";  //String to be returned
		numValue = Integer.parseInt(value);  //Converts the inputed number to an int
		int i = 8; 
		while(numValue >= 1) //Makes sure the value doesn't go into decimal
		{
			
				if(numValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
				{
					binaryNumber[i] = 1; //Sets the value at i to 1
				}
				else
				{
					binaryNumber[i] = 0; //Sets the value at i to 0
				}
				i--; //Goes to the next index 
				numValue = numValue/2; //Cuts the value in half. 
		}
		for(int j = 0; j < 4; j++) //Puts the first four bits into the string 
		{
			if(binaryNumber[j] == 1)
			{
				jumpValue += "1"; 
			}
			else
			{
				jumpValue += "0"; 
			}
		}
		jumpValue += " "; //adds a space in the middle of the 8 bits 
		for(int j = 4; j < 8; j++) //Adds the final 4 bits to the string 
		{
			if(binaryNumber[j] == 1)
			{
				jumpValue += "1"; 
			}
			else
			{
				jumpValue += "0"; 
			}
		}
		return jumpValue; 
	}
	public static String getBranchValue(String value)
	{
		int[] binaryNumber = new int[9];
		 String branchValue = ""; 
		 int decValue = Integer.parseInt(value); 
		 int absValue = Math.abs(decValue);
		 int counter = 8; //Counter so each index is reached 
			while(absValue >= 1) //Makes sure the value doesn't go into decimal
			{
				
					if(absValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
					{
						binaryNumber[counter] = 1; //Sets the value at i to 1
					}
					else
					{
						binaryNumber[counter] = 0; //Sets the value at i to 0
					}
					counter--; //Goes to the next index 
					absValue = absValue/2; //Cuts the value in half. 
			}
			counter = 0; 
			if(decValue < 0)
			{
				for(int j = 0; j < 10; j++)
				{
					if(binaryNumber[j] == 1)
					{
						binaryNumber[j] = 0; 
					}
					else
					{
						binaryNumber[j] = 1; 
					}
					
				}
				while(binaryNumber[counter] == 1)//Used to add the 1 at the end of twos compliment 
				{
					binaryNumber[counter] = 0; //Switches the true values to false as the carry is being added
					counter++;
				}
				binaryNumber[counter] = 1;//toggles the final value to true 
			}
			for(int i = 0; i < 2; i++)
			{
				if(binaryNumber[i] == 1)
				{
					branchValue += "1"; 
				}
				else
				{
					branchValue += "0"; 
				}
			}
			branchValue += " "; 
			for(int i = 2; i < 6; i++)
			{
				if(binaryNumber[i] == 1)
				{
					branchValue += "1"; 
				}
				else
				{
					branchValue += "0"; 
				}
			}
			branchValue += " "; 
			for(int i = 6; i < 10; i++)
			{
				if(binaryNumber[i] == 1)
				{
					branchValue += "1"; 
				}
				else
				{
					branchValue += "0"; 
				}
			}
		 
		 return branchValue; 
	}
	public static String getCallValue(String value)
	{
		int[] binaryNumber = new int[10];  //Array to store the binary representation of the number 
		for(int i = 9; i >= 0; i--)
		{
			binaryNumber[i] = 0; //In case the full binary number isn't reached defaults to zero
		}
		int numValue; 
		String callValue = "";  //String to be returned
		numValue = Integer.parseInt(value);  //Converts the inputed number to an int
		int i = 9; 
		while(numValue >= 1) //Makes sure the value doesn't go into decimal
		{
			
				if(numValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
				{
					binaryNumber[i] = 1; //Sets the value at i to 1
				}
				else
				{
					binaryNumber[i] = 0; //Sets the value at i to 0
				}
				i--; //Goes to the next index 
				numValue = numValue/2; //Cuts the value in half. 
		}
		for(int j = 0; j < 2; j++) //Puts the first four bits into the string 
		{
			if(binaryNumber[j] == 1)
			{
				callValue += "1"; 
			}
			else
			{
				callValue += "0"; 
			}
		}
		callValue += " "; //adds a space in the middle of the 8 bits 
		for(int j = 2; j < 6; j++) //Adds the next 4 bits to the string 
		{
			if(binaryNumber[j] == 1)
			{
				callValue += "1"; 
			}
			else
			{
				callValue += "0"; 
			}
		}
		
		callValue += " "; //adds a space in the middle of the 8 bits 
		for(int j = 6; j < 10; j++) //Adds the next 4 bits to the string 
		{
			if(binaryNumber[j] == 1)
			{
				callValue += "1"; 
			}
			else
			{
				callValue += "0"; 
			}
		}
		return callValue; 
	}
	
	
	
	
}
