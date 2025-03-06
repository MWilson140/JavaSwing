
package Magazine;
import Supplement.*;
import java.util.ArrayList;

/**
 *
 * @author Mitchell Wilson
 * Class for handling and storing information on the magazine
 */
public class Magazine {
    private String name;
    private float cost;
    
    public Magazine()
    {}
    public Magazine(String s)
    {
        String[] split = s.split(",");
        name = split[0].substring(split[0].indexOf('[')+1);
        cost = (Float.parseFloat(split[1].substring(0, split[1].indexOf(']'))));
    }
    public Magazine (String s, float MG)
    {
        name = s; 
       // magSup = MS;
        cost = MG;
    }
    
    public float getCost()
    {
        return cost;
    }
    public void setCost(Float f)
    {
    	cost = f;
    }
    
    public void setName(String s)
    {
        name = s;
    }


    public String getName()
    {
        return name;
    }
    
    public String toString()
    {
    	String str = ("Class=Magazine["+name+","+cost+"]");
    	return str;
    }
    
    
}
