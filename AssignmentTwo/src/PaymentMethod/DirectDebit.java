
package PaymentMethod;

/**
 *
 * @author Mitchell Wilson
 * class for Storage of Direct Debit information
 * extends PaymentMethod
 */
public class DirectDebit extends PaymentMethod{
	private String bankNo;
	private String accountNo;
    
    
    public DirectDebit()
    {
    	
    }
    public DirectDebit(String[] details)
	{
    	bankNo = details[0];
    	accountNo = details[1];	
    	}
    
    public DirectDebit(String BN, String ano)
    {
    	accountNo = ano;
        bankNo = BN;
    }
    
    public DirectDebit(DirectDebit d)
    {
    	this.accountNo = d.accountNo;
    	this.bankNo = d.bankNo;
    }
    
    public String getAccountNo()
    {
        return accountNo;
    }
    public String getBankNo()
    {
        return bankNo;
    }
	
    public String toString()
    {
    	String str = new String("Class=DirectDebit["+bankNo+ ","+accountNo +"]");
    	return str;
    }
    
}
