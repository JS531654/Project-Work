
public class ALU {
	public static Longword doOp(Bit one,Bit two, Bit three, Bit four, Longword a, Longword b) throws Exception 
	{
		Longword result = new Longword(0); 
		if(one.getValue() == true && two.getValue() == false && three.getValue() == false && four.getValue() == false) //Test the value of the array that was inputed 
		{
			result = a.and(b); //Runs and on the inputed longwords
		}
		else if(one.getValue() == true && two.getValue() == false && three.getValue() == false && four.getValue() == true)//Test the value of the array that was inputed
		{
			result = a.or(b); //Runs or on the inputed longwords
		}
		else if(one.getValue() == true && two.getValue() == false && three.getValue() == true && four.getValue() == false)//Test the value of the array that was inputed
		{
			result = a.xor(b); //Runs xor on the inputed longwords
		}
		else if(one.getValue() == true && two.getValue() == false && three.getValue() == true && four.getValue() == true)//Test the value of the array that was inputed
		{
			result = a.not();//Runs not on the inputed longword (first longword entered) 
		}
		else if(one.getValue() == true && two.getValue() == true && three.getValue() == false && four.getValue() == false)//Test the value of the array that was inputed
		{ 
			int amount = b.getSigned(); //Gets the value of the second longword
			a.leftShift(amount); //Calls left shift on the inputed longword using the other longword as the number of bits to shift
		}
		else if(one.getValue() == true && two.getValue() == true && three.getValue() == false && four.getValue() == true)//Test the value of the array that was inputed
		{
			int amount = b.getSigned();//Gets the value of the second longword
			a.rightShift(amount); //Calls right shift on the inputed longword using the other longword as the number of bits to shift
		}
		else if(one.getValue() == true && two.getValue() == true && three.getValue() == true && four.getValue() == false)//Test the value of the array that was inputed
		{
			result = rippleAdder.add(a, b); //Runs add on the inputed longwords
		}
		else if(one.getValue() == true && two.getValue() == true && three.getValue() == true && four.getValue() == true)//Test the value of the array that was inputed
		{
			result = rippleAdder.subtract(a, b); //Runs subtract on the inputed longwords
		}
		else if(one.getValue() == false && two.getValue() == true && three.getValue() == true && four.getValue() == true)//Test the value of the array that was inputed
		{
			result = Multiplier.multiply(a, b); //Runs multiply  on the inputed longwords
		}
		return result; 
	}
}
