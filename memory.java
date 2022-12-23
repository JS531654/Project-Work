
public class memory {
	private Bit[] memory = new Bit[8192];
	public memory()
	{
		for(int i = 0; i <= 8191; i++)
		{
			memory[i] = new Bit(false); 

		}
	}
	public  Longword read(Longword address)throws Exception
	{
		
		if((address.getSigned() * 8) > 8191)
		{
			throw new Exception("Not a Valid Address");  //Prevents an array out of bounds error
		}
	
		Longword result = new Longword(0);//Longword to be returned 
		int counter = 0;  //Number to be used if the end of the array is reached 
		for(int i = 0; i <= 31; i++)
		{   
			if(address.getSigned() * 8 == 8192) //Prevents an out of bounds error
			{
				break; 
			}
			if(i < 8192) //If the array will not go out of bounds, just copy the current values into the new Longword 
			{
				result.setBit(i, memory[address.getSigned() * 8 + i]); //Copies the long
			}
			counter++; 
		}
		if(counter != 32) //Test to see if the first loop completed writing the reading the entire longword 
		{
			int index = 0; //Used to know what index the number will be read from 
			for(int i = 32 - counter; i < 32; i++)
			{
				result.setBit(i, memory[i]); //Picks up where the previous loop left off reading from the beginning 
				index++; //Increment the index
			}
		}
		return result; 
	}
	public  void write(Longword address, Longword value) throws Exception
	{
		if((address.getSigned() * 8) > 8191)
		{
			throw new Exception("Not a Valid Address");  //Prevents an array out of bounds error
		}
	
		int counter = 0; //Counter to be used if the end of the array is reached 
		int signedAddress = 8 * address.getSigned(); //Assigns the address into an integer
		int writeValue = value.getSigned(); //Turns the value being written into an integer
		Longword writtenValue = new Longword(writeValue);
		for(int i = 0; i < 32; i++)
		{
			if(signedAddress + i == 8192)
			{
				break; 
			}
			memory[i + signedAddress] = writtenValue.getBit(i); 
			counter++; 
			writeValue = writeValue /2; 
			
		}
		if(counter != 32) //Test to see if the first completed write or if it hit the end of the array
		{
			while(writeValue >= 1) //Makes sure the value doesn't go into decimal
			{
				int index = 0; //The index the number is being assigned to 
				if(signedAddress == 8191) //Prevents an out of bounds error
				{
					break; 
				}
				if(writeValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
				{
					memory[index].set(); //Sets the value at index to 1
				}
				else
				{
					memory[index].clear(); //Sets the value at index to 0
				}
				index++; //Goes to the next index 
				writeValue = writeValue/2; //Cuts the value in half.
			}
		}
	
	}
	public Bit getMemory(int index)
	{
		return memory[index]; 
	}
}