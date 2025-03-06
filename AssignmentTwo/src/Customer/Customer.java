
package Customer;
import Supplement.*;
//import PaymentMethod.*;
import Magazine.*;
import Address.*;

import java.util.ArrayList;

public class Customer 
{
    protected String name;
    protected String email;
    protected Address address;
    protected ArrayList <String> intSups;
    public Customer()
    {
        intSups = new ArrayList<String>();
    }
    
    public Customer(String[] custA, String[] addA)
    {
    	
    }
    
    public Customer(String n, String e, Address add, ArrayList<String> IS)
    {
    	this.name = n;
    	this.email = e;
    	this.address = add;
    	this.intSups = IS;
    }
    
    public Customer (Customer c)
    {
        this.name = c.name;
        this.email = c.email;
        this.intSups = c.intSups;
        this.address = c.address;
    }
    public Customer(String s)
    {
        intSups = new ArrayList<String>();
        email =s;
    }
    public Customer(String n, String e, ArrayList<String> IS)
    {
        name = n;
        email = e;
        intSups = IS;
    }
    Customer (String n, String e)
    {
        name = n;
        email = e;
        intSups = new ArrayList<String>();
    }
    
    public void setName(String s)
    {
    	name = s;
    }
    public String getEmail()
    {
        return email;
    }
    public void addSupps(String sup)
    {
        intSups.add(sup);
    }
    public void setEmail(String s)
    {
        email = s;
    }
    
    public String toString()
    {
    	String s = (name + email + address.toString() + intSups.toString());
    	return s;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setIntSup(ArrayList<String> sup)
    {
    	intSups = sup;
    }
    
    public ArrayList<String> getIntSup()
    {
        return intSups;
    }
    
    public void setAddress(Address ad)
    {
    	address = ad;
    }
    
    public Address getAddress()
    {
    	return address;
    }
}
