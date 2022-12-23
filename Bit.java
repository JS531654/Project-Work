
public class Bit {
	private boolean value;
    public Bit(boolean value)
    {
        this.value = value; //Assigns the given value to the bit
    }

    public void set(boolean value) {
        this.value = value; //Allows the user to give the bit what they want
    }
   
  
    public void toggle()
    {
        if(value == true) //Test to check the current value of the bit
        {
            value = false; // Set the value to false
        }
        else
        {
            value = true; // Sets the value to true
        }
    }
    public void set() // sets the value of the bit to true
    {
        value = true;
    }
    public void clear() //Sets the value of the bit to false
    {
        value = false;
    }
    public boolean getValue() // Returns the current value of the bit 
    {
        return value;
    }
    public Bit and(Bit other)
    {
        Bit endValue = new Bit(false); //Created a new bit to be returned 
        if(value == true) //Tests to see if the two bits share the same value
        {
            if(other.value == true)
            {
            	endValue.set();
            }
            else
            {
            	endValue.clear();
            }
        }
        else
        {
            endValue.clear();//Sets the value of the new bit to false because the bits have different values 
        }
        return endValue; 
    }
    public Bit or(Bit other)
    {
        Bit endValue = new Bit(value);
        if(other.getValue() == true) //Test the value of the bit
        {
            endValue.set(); // Sets the value to true because at least one bit is true
        }
        else if (value == true)
        {
            endValue.set();; // Sets the value to true because at least one bit is true
        }
        else
        {
            endValue.clear(); //Sets the value to false 
        }
        return endValue;
    }
    public Bit xor(Bit other)
    {
        Bit endValue = new Bit(value); 
        if(other.getValue() == true) //Test the value of the bit
        {
            if(value == false) //Makes sure the second bit does not contain the same value 
            {
                endValue.set();
            }
            else
            {
                endValue.clear();
            }
        }
        else if(other.getValue() == false) //Test value of the one bit 
        {
            if(value == true) //Makes sure the second bit does not contain the same value 
            {
                endValue.set();;
            }
            else
            {
                endValue.clear();
            }
        }
        return endValue; 
    }
    public Bit not() 
    {
    	Bit result = new Bit(false); 
    	result.set(this.value);
    	result.toggle();
    	return result; 
    }
    @Override 
    public String toString()
    {
        if(value == true)
        {
        	return "t"; 
        }
        else
        {
        	return "f"; 
        }
    }
}