
public class Multiplier {
	public static Longword multiply(Longword A, Longword B) throws Exception
	{
		Bit trueBit = new Bit(true); 
		Bit falseBit = new Bit(false); 
		Longword result = new Longword(0);//Longword to be returned 
		for(int i = 0; i <= 31; i++)
		{
			for(int j = 0; j <= 31; j++)
			{
				Longword holder = new Longword(0); //Temporary Longword to be used to added to the result in 
				if(A.getBit(i).getValue() == true && B.getBit(i).getValue()== true) // Checking to see if the bits will 1 so the product will be 1
				{
					holder.setBit(j, trueBit);; //Sets the bit to one / true
				}
				else //Checks to see if either bit is false resulting in a false product 
				{
					holder.setBit(j, falseBit);; // Sets the bit to zero / false 		
				}
				holder = holder.leftShift(i); //Shifts the bits the number over based on where on the B binary number you are multiplying from 
				result = rippleAdder.add(result, holder);  //Adds the result to the temporary number 
			}
		}
		return result; 
	}

}
