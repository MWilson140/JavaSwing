package Address;

public class Address {
	
	private int postCode;
	private String suburb;
	private String streetName;
	private int streetNumber;

	public Address() {}
	public Address(String[] details)
	{
		postCode=Integer.parseInt(details[0]);
		suburb = details[1];
		streetName = details[2];
		streetNumber = Integer.parseInt(details[3]);
		
	}
	public Address(int pc, String sub, String sname, int snum)
	{
		this.postCode = pc;
		this.suburb = sub;
		this.streetName = sname;
		this.streetNumber = snum;
	}
	public int getPostCode()
	{
		return postCode;
	}
	public String getSuburb()
	{
		return suburb;
	}
	public String getStreetName()
	{
		return streetName;
	}
	public int getStreetNumber()
	{
		return streetNumber;
	}
	
	public String toString()
	{
		String str = new String("Class=Address[" +postCode +"," + suburb + "," + streetName  + "," +streetNumber + "]");
		return str;
	}
	
}


