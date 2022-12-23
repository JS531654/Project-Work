
public class rippleAdder {
	public static Longword add(Longword A, Longword B) throws Exception
	{
		Longword result = new Longword(0);  //New longword to be returned 
		int carry = 0; //Value to be stored in addition
		Bit trueBit = new Bit(true); //Bit to be used when a the value needs to be set to true
		Bit falseBit = new Bit(false); //Bit to be used when a the value needs to be set to false
		for(int i = 0; i <= 31; i++)//Iterates all the way through both longwords 
		{
			if(A.getBit(i).getValue() == true && B.getBit(i).getValue() == true && carry == 1) //Tests to see the status of the bits and the carry
			{
				carry = 1; //Because 2 cannot be represented in binary the two is carried over 
				result.setBit(i, trueBit);  //The sum of the two bits and the carry being one means the bit is true 
			}
			else if(A.getBit(i).getValue() == true && B.getBit(i).getValue() == true && carry == 0)//Tests to see the status of the bits and the carry
			{
				carry = 1; //Because 2 cannot be represented in binary the two is carried over 
				result.setBit(i, falseBit); //Since the sum is two that value goes to the carry and the bit remains false 
			}
			else if(A.getBit(i).xor(B.getBit(i)).getValue() == true && carry == 1)//Tests to see the status of the bits and the carry
			{
				carry = 1; //Given that one of the two bits in the array is true and the carry is one the carry must stay one
				result.setBit(i, falseBit); //The bit at the given index is false because 2 becomes zero and the value is carried 
			}
			else if(A.getBit(i).xor(B.getBit(i)).getValue() == true && carry == 0)//Tests to see the status of the bits and the carry
			{
				carry = 0; //Given that only one value is true and the carry is 0 the carry becomes zero 
				result.setBit(i, trueBit); //The bit becomes true 
			}
			else if(A.getBit(i).or(B.getBit(i)).getValue() == false && carry == 1)//Tests to see the status of the bits and the carry
			{
				carry = 0; //Since the sum is 1 there does not need to be a value in the carry 
				result.setBit(i, trueBit); //The sum being one means the value of the bit is true
			}
			else
			{
				carry = 0; //Since the sum is zero everything becomes zero
				result.setBit(i, falseBit); //Since the sum is zero the value becomes false 
			}
		}
	
	
		return result;
	}
	public static Longword subtract(Longword A, Longword B) throws Exception
	{
		Longword result = new Longword(0); //New longword to be returned 
		Longword twosCompliment = new Longword(0); //New longword that is the twos compliment of the number being subtracted 
		twosCompliment = B.getTwosCompliment();  //Sets the twos compliment value
		result = add(A, twosCompliment); //Calls add with the longword and the twos compliment and sets result to that value
		return result; 
		
	
	}
}
