package Supplement;
/**
 *
 * @author Mitchell Wilson
 * Class for storing supplement information
 */
public class Supplement {
    private String name;
    private float cost;
    
    Supplement()
    {    
    }
    public Supplement(String s)
    {
        String[] split = s.split(",");
        name = split[0].substring(split[0].indexOf('[')+1);
        cost = (Float.parseFloat(split[1].substring(0, split[1].indexOf(']'))));
    }
    Supplement(float c)
    {
        cost = c;
    }
    public Supplement(String s, float c)
    {
        name = s;
        cost = c;
    }
    public float getCost()
    {
        return cost;
    }
    public String getName()
    {
        return name;
    }
    
    public void setCost(float c)
    {
        cost = c;
    }
    public void setName (String s)
    {
        name = s;
    }   
    public String toString()
    {
    	String str = new String("class=Supplement["+name+","+cost+"]");
    	return str;
    }
}
