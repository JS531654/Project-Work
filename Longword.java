import java.lang.Math;
public class Longword {
	private Bit[] bits = new Bit[32]; 
	public Longword(int value)
	{
		int absValue = Math.abs(value); 
		for(int i= 0; i <= 31; i++)
		{
			bits[i] = new Bit(false);
		}
		int i = 0; //Counter so each index is reached 
		while(absValue >= 1) //Makes sure the value doesn't go into decimal
		{
			
				if(absValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
				{
					bits[i].set(); //Sets the value at i to 1
				}
				else
				{
					bits[i].clear(); //Sets the value at i to 0
				}
				i++; //Goes to the next index 
				absValue = absValue/2; //Cuts the value in half. 
		}
		if(value < 0)
		{
			for(int j = 0; j < 32; j++)
			{
				bits[j].toggle();
			}
			int counter = 0; 
			while(bits[counter].getValue() != false)//Used to add the 1 at the end of twos compliment 
			{
				bits[counter].toggle(); //Switches the true values to false as the carry is being added
				counter++;
			}
			bits[counter].toggle();//toggles the final value to true 
		}
			
			
	}
	
	public Bit getBit(int i) throws Exception
	{
		if(i > 31)
		{
			throw new Exception("Not in the longword");  //Prevents an array out of bounds error
		}
		Bit index = new Bit(bits[i].getValue()); //Creates the new bit to be returned 
		return index; 
	}
	public void setBit(int i, Bit value) throws Exception
	{
		if (i > 31)
		{
			throw new Exception("Not in the longword"); //Prevents an array out of bounds error
		}
		Bit setter = new Bit(value.getValue());
		bits[i] = setter;  // Sets the bit at the index to the value given
	}
	public Longword and(Longword other) throws Exception
	{
		Bit trueBit = new Bit(true); //Bit to be used if the and returns true
		Bit falseBit = new Bit(false); //Bit to be used if the and returns false 
		Longword result = new Longword(0); 
		for(int i = 0; i <= 31; i++)
		{
			if((bits[i].and(other.getBit(i)).getValue() == true)) //Runs and on two bits to see if the values are the same 
			{
				result.bits[i] = trueBit;  //If the bits are the same it sets the index of the longword to true 
			}
			else
			{
				result.bits[i] = falseBit; //Sets the value at the index of the longword to false 
			}
		}
		
		return result; 
	}
	public Longword or(Longword other) throws Exception
	{
		Bit trueBit = new Bit(true);  //Bit to be used if the or returns true
		Bit falseBit = new Bit(false); //Bit to be used if the or returns false 
		Longword result = new Longword(0);  //Longword that will be returned 
		for(int i = 0; i <= 31; i++)
		{
			if((bits[i].or(other.getBit(i)).getValue() == true)) //Or test on each bit in the array
			{
				result.bits[i] = trueBit; //Sets to true if or is true 
			}
			else
			{
				result.bits[i] = falseBit; //Sets to false if or is false
			}
		}
		return result; 
	}
	public Longword xor(Longword other) throws Exception
	{
		Bit trueBit = new Bit(true); //Bit to be used if the xor returns true
		Bit falseBit = new Bit(false); //Bit to be used if the xor returns false 
		Longword result = new Longword(0); //Longword that will be returned 
		for(int i = 0; i <= 31; i++)
		{
			if((bits[i].xor(other.getBit(i)).getValue() == true))//xor test on each bit in the array
			{
				result.bits[i] = trueBit; //Sets to true if xor is true 
			}
			else 
			{
				result.bits[i] = falseBit; //Sets to true if xor is false 
			}
		}
		return result; 
	}
	public Longword not()
	{
		Longword result = new Longword(0); //Longword that will be returned 
		for(int i = 0; i <= 31; i++)
		{
			result.bits[i] = bits[i].not();  //Runs not on each bit in the original arrray and adds it to the result array to be returned 
		}
		return result; 
	}
	public Longword rightShift(int amount)
	{
		Longword result = new Longword(0); //Longword result after the shift, Longword to be returned. 
		for(int i = 31; i > amount; i--)
		{
			result.bits[i].clear();  //Sets the amount of bits the user entered to zero
		}
		for(int i = 31; i <= amount; i--)
		{
			result.bits[i - amount] = bits[i]; //Makes the array continue after the zeros have been added 
		}
		return result; 	
	}
	public Longword leftShift(int amount)
	{
		Longword result = new Longword(0); //Longword result after the shift, Longword to be returned. 
		for(int i = 0; i<= amount; i++)
		{
			result.bits[i].clear();
		}
		for(int i = amount; i+ amount <= 31; i++)
		{
			result.bits[i].set(bits[i - amount].getValue());
		}
		return result; 	
	}
	@Override 
	public String toString()
	{
		String result = ""; //Creates an empty string
		for(int i = 0; i <= 31; i++)
		{
			result += bits[i].toString(); //Adds the individual toString of each Bit in the array to the new string
			result += ",";  //Adds a comma to separate the result of each bit
		}
		return result; 
	}
	public long getUnsigned()
	{
		long result = 0; // New long to be returned 
		for(int i = 0; i <= 31; i++)
		{
			if(bits[i].getValue() == true) //Test to see if the value at index i is 1
			{
				result += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal
			}
		}
		return result; 
	}
	public int getSigned()
    { 
           int result = 0; // New int to be returned 
           for(int i = 0; i <= 30; i++)
           {
        	   if(bits[31].getValue() == false)
        	   {
        		   if(bits[i].getValue() == true) //Test to see if the value at index i is 1
                   { 
                 	  result += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
                   }
        	   }
        	   else
        	   {
        		   if(bits[i].getValue() == false) //Test to see if the value at index i is 1
                   { 
                 	  result += Math.pow(2, i); //Adds two raised to the i to convert the binary number to decimal      
                   }
        	   }
                  
           }
           if(bits[31].getValue() == true)
           {
        	   	  result = result + 1;
                  result = result * -1;                   
           }
          
          
           return result; 
    }

	public void copy(Longword other)
	{
		for(int i = 0; i <= 31; i++ )
		{
			other.bits[i] = bits[i]; //Makes the value of the new longword the same as the original longword 
		}
	}
	public void set(int value)
	{
		int absValue = Math.abs(value); 
		for(int i= 0; i <= 31; i++)
		{
			bits[i] = new Bit(false);
		}
		int i = 0; //Counter so each index is reached 
		while(absValue >= 1) //Makes sure the value doesn't go into decimal
		{
			
				if(absValue % 2 == 1) // Binary conversion, tests the remainder. When the remainder is 1, 1 is added at the given index 
				{
					bits[i].set(); //Sets the value at i to 1
				}
				else
				{
					bits[i].clear(); //Sets the value at i to 0
				}
				i++; //Goes to the next index 
				absValue = absValue/2; //Cuts the value in half. 
		}
		if(value < 0)
		{
			for(int j = 0; j < 32; j++)
			{
				bits[j].toggle();
			}
			int counter = 0; 
			while(bits[counter].getValue() != false)//Used to add the 1 at the end of twos compliment 
			{
				bits[counter].toggle(); //Switches the true values to false as the carry is being added
				counter++;
			}
			bits[counter].toggle();//toggles the final value to true 
		}
	}
	public Longword getTwosCompliment() throws Exception
	{
		Longword one = new Longword(1); 
		Longword result = new Longword(0); //Creates a new longword to be returned 
		for(int i = 0; i <= 31; i++)//Iterate through the array
		{
			result.setBit(i, bits[i].not());  //Runs not on each bit in the original array and adds it to the result array to be returned 
		}
		result = rippleAdder.add(result, one);
		return result; 
	}


}
