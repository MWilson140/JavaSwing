package PaymentMethod;

/**
 *
 * @author Mitchell Wilson
 * class for Storage of CreditCard information
 * extends PaymentMethod
 */
public class CreditCard extends PaymentMethod{
    private String creditCardNo;
    private String CSV;
    
    public CreditCard()
    {}
    public CreditCard(String[] details)
	{
    	creditCardNo = details[0];
    	CSV = details[1];
    	
    	
	}
    public CreditCard(String CCN, String csv)
    {
        creditCardNo = CCN;
        CSV = csv;
    }
    public CreditCard(CreditCard c)
    {
    	this.creditCardNo = c.creditCardNo;
    	this.CSV = c.CSV;
    }
    
    public String getCreditCardNo()
    {
        return creditCardNo;
    }
    
    public String getCSV()
    {
        return CSV;
    }
    
    public String toString()
    {
    	String str = new String("Class=CreditCard["+creditCardNo+","+CSV+"]");
    	return str;
    }
}
