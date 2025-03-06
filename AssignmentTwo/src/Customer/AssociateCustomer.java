package Customer;
import Magazine.*;
import Supplement.*;
import java.util.ArrayList;
import Customer.PayingCustomer;
import Address.*;

/**
 *
 * @author zealo
 */
public class AssociateCustomer extends Customer
{
    private String whoIsPaying;

    public AssociateCustomer()
    {
        super();
    }
    
    public AssociateCustomer(String str)
    {
    	String[] split = str.split("Class=");
    	int first; 
    	int last;
    	for(int i = 0; i < split.length; i ++)
    	{
    		first = (split[i].indexOf('['));
    		last = (split[i].lastIndexOf(']'));
    		if (!(first < 0 || last <= first))
    		{
	    		split[i] = split[i].substring(first+1, last);
    		}
    	}
    	String[] custDetails = split[1].split(",");
    
    	this.name = custDetails[0];
    	this.email = custDetails[1];
    	this.whoIsPaying = custDetails[2];
    	
    	first = (split[1].indexOf('['));
		last = (split[1].indexOf(']'));
		String sups = split[1].substring(first+1,last);
		String[] supSplit = sups.split(",");
		for (int i = 0; i < supSplit.length; i ++)
		{
			if (supSplit[i] != "")
			{
				intSups.add(supSplit[i]);
			}
		}
		String[] addressSplit = split[2].split(",");
		address = new Address(addressSplit);
    }
    
    public AssociateCustomer(Customer cust, String s)
    {
        super(cust);
        whoIsPaying = s;
    }
    public AssociateCustomer(String n, String e, ArrayList<String> sup, String c, Address add)
    {
        super(n,e,add, sup);
        whoIsPaying = c;    
    }
    
    
    public String getWhoIsPaying()
    {
        return whoIsPaying;
    }
    public void setWhoIsPaying(String s)
    {
    	whoIsPaying = s;
    }

    public String toString()
    {
    	String str = new String("Class=AssociateCustomer[" +name+ "," + email+ 
    			"," + whoIsPaying+ ","+ intSups.toString()+ "]" + address.toString());
    	
    	return str;
    }
}
