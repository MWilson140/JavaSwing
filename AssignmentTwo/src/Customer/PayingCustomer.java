package Customer;
import Magazine.*;
import java.util.ArrayList;
import java.util.*;
import PaymentMethod.*;
import Supplement.*;
import Address.*;


public class PayingCustomer extends Customer{
    private PaymentMethod paymentDetails;
    private float amountDue;
    private ArrayList<String> recipt;
  
    public PayingCustomer()
    {
        super();
        recipt = new ArrayList<String>();
    }
    
    public PayingCustomer(Customer cust, PaymentMethod pay)
    {
    	super(cust);
    	paymentDetails = pay;
    	amountDue = 0.0f;
    	recipt = new ArrayList<String>();
    }
	public PayingCustomer(String n, String e, ArrayList<String>sups, PaymentMethod PM, Address add,
			float ad, ArrayList<String> rec)
	{
		super(n, e, add, sups);
		this.paymentDetails = PM;
		this.amountDue = ad;
		this.recipt= rec;
	}
    public PayingCustomer(String n, String e, ArrayList<String> IS, PaymentMethod PM, Address add)
    {
    	super(n,e, add, IS);
    	this.paymentDetails = PM;
    	this.recipt = new ArrayList<String>();
    }
    //assoc cust
    public PayingCustomer(String n, String e, ArrayList<String> IS, 
            PaymentMethod PM, ArrayList<Customer> AC)
    {
        super(n, e, IS);
        paymentDetails = PM;
        amountDue = 0;
        recipt = new ArrayList<String>();
    }
    //no assoc cust
    public PayingCustomer(String n, String e, ArrayList<String> IS, 
            PaymentMethod PM)
    {
        super(n, e, IS);
        paymentDetails = PM;
        amountDue = 0;
        recipt = new ArrayList<String>();
    }
    
    public PayingCustomer(String str)//redo this for toString input
    {
    	boolean directDebit = false;
    	if (str.contains("DirectDebit"))
    	{
    		directDebit = true;
    	}
		this.recipt = new ArrayList<String>();
		this.address = new Address();
		this.paymentDetails = new DirectDebit();
    	int first;
    	int last;
    	String[] split = str.split("Class=");
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
    	this.amountDue = (Float.parseFloat(custDetails[2]));
		first = (split[1].indexOf('['));
		last = (split[1].indexOf(']'));
		String sups = split[1].substring(first+1,last);
		String[] supSplit = sups.split(",");
		for (int i = 0; i < supSplit.length; i ++)
		{
			if (supSplit[i] != "")
			{
				supSplit[i] = supSplit[i].trim();
				intSups.add(supSplit[i]);
			}
		}
		first = (split[1].lastIndexOf('['));
		last = (split[1].lastIndexOf(']'));
		String tempRecipt = split[1].substring(first+1,last);
		String[] reciptSplit = tempRecipt.split(",");
		for (int i = 0; i < reciptSplit.length; i ++)
		{
			if (reciptSplit[i] != "")
			{
				reciptSplit[i] = reciptSplit[i].trim();
				recipt.add(reciptSplit[i]);
			}
		}
		//address
		String[] addressSplit = split[2].split(",");
		address = new Address(addressSplit);
		String[] paymentSplit = split[3].split(",");
		if (directDebit)
		{
			paymentDetails = new DirectDebit(paymentSplit);
		}
		else
		{
			paymentDetails = new CreditCard(paymentSplit);
		}
    }
    
    public void addToRecipt(ArrayList<String> extra)
    {
    	recipt.addAll(extra);
    }
    
    public String toString()
    {
    	String str1 = new String("Class=PayingCustomer[" +name + "," +email + 
    	 "," +amountDue +  "," +intSups.toString() + "," +
    	    	  recipt.toString() +"]" +address.toString()  +"," +paymentDetails.toString());  
    	return str1;
    }
    
    PayingCustomer(PaymentMethod PM)
    {
        paymentDetails = PM;
        amountDue = 0;
    }
     PayingCustomer(PaymentMethod PM, ArrayList<Customer> AC)
    {
        paymentDetails = PM;
        //AssocCustomers = AC;
        amountDue = 0;
    }
     
    public ArrayList<String> getRecipt()
    {
        return recipt;
    }
    public float getAmountDue()
    {
        return amountDue;
    }
    public void setAmountDue(float n)
    {
        amountDue = n;
    }
    public void addToAmountDue(Float n)
    {
    	
    	amountDue += n;
    	amountDue  = (float)Math.round(amountDue*100)/100;
    }
    
    public void emptyRecipt()
    {
    	recipt.clear();
    }
    
    public void addToRecipt(String s)
    {
    	recipt.add(s);
    }
    
    public void setPayment(PaymentMethod pay)
    {
    	this.paymentDetails = pay;
    }
    
    public PaymentMethod getPayment()
    {
        return paymentDetails;
    }

  }

