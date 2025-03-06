package application;

import java.util.ArrayList;
import java.util.Random;
import Customer.*;
import Supplement.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.collections.*;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import Magazine.*;
import PaymentMethod.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import Address.*;
import java.util.*;
import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.*;
import javafx.scene.*;

public class buisness {
    
    private static buisness instance = null;
    
    private ArrayList<Customer> customers;
    private ArrayList<Supplement> supplements;
    private Magazine magazine;
    
    private buisness() {
        customers = new ArrayList<>();
        supplements = new ArrayList<>();
    }
    
    public static buisness getInstance() {
        if (instance == null) {
            instance = new buisness();
        }
        return instance;
    }
	 
	public void readFromFile(Stage PM) throws Exception
	{
		String projectDir = System.getProperty("user.dir");
		File initialDirectory = new File(projectDir);
		FileChooser fChooser = new FileChooser();
		fChooser.setInitialDirectory(initialDirectory);
		File iFile = fChooser.showOpenDialog(PM);
		try
		{
			checkFileName(iFile);
		}
		catch(Exception e)
		{
			throw new Exception (e);
		}
			customers = new ArrayList<Customer>();
			supplements = new ArrayList<Supplement>();
			String allinfo[];
			Customer cust =null;
		    try (RandomAccessFile raf = new RandomAccessFile(iFile, "r")) 
		    {
		        while (true) 
		        {
		            String s = raf.readUTF();
		            allinfo = s.split(",");
		            if (allinfo[0].contains("Supplement"))
		            {
		            	supplements.add(new Supplement(s));
		            }
		            if (allinfo[0].contains("PayingCustomer"))
		            {
		            	customers.add(new PayingCustomer(s));
		            }
		            if (allinfo[0].contains("AssociateCustomer"))
		            {
		            	customers.add(new AssociateCustomer(s));
		            }
		            if (allinfo[0].contains("Magazine"))
		            {
		            	magazine = new Magazine(s);
		            }
		        }
		    } catch (EOFException eof) 
		    {
		    }
		    catch (Exception e) 
		    {
		    	throw new Exception (e);
		    }
		
	}
	
	public Magazine getMagazine()throws Exception
	{
		if (magazine == null)
		{
			throw new Exception ("No Magazine in the System");
			
		}
		return magazine; 
	}
	
	public ArrayList<Supplement>getSupplements()
	{
		return supplements;
	}
	
	
	public void createMag(String[] allinfo)
	{
		magazine.setName(allinfo[1]);
		magazine.setCost(Float.parseFloat(allinfo[2]));
		return;
	}
	
	 public void openOutPutFile() throws Exception
	 //should be multithreaded?
	 {
		 String projectDir = System.getProperty("user.dir");
		 Stage stage = new Stage();
		 Label nameLabel = new Label("File name:");
		 Label extension = new Label(".dat");
		 Label location = new Label("Saving to: " + projectDir);
		 TextField fileName = new TextField("file"); 
		 GridPane gp = new GridPane();
		 Button confirm = new Button("confirm");
		 gp.addRow(0, location);
		 gp.addRow(1, nameLabel, fileName,extension);
		 gp.addRow(2, confirm);
		 Scene newScene = new Scene(gp);
		 stage.setScene(newScene);
		 stage.setTitle("Enter a file name");
		 stage.show();
		 confirm.setOnAction(event ->
		 {
			 String error = null;
			 String fullFile = (fileName.getText() + ".dat");
			 try {
				 RandomAccessFile RAF = new RandomAccessFile(fullFile, "rw");
				 RAF.setLength(0);
				 if (magazine != null)
				 {
				 RAF.writeUTF(magazine.toString());
				 }
				 	for (Supplement sup : supplements)
					 {
						 RAF.writeUTF(sup.toString());
					 }
				 	for (Customer cust : customers)
				 	{
				 		RAF.writeUTF(cust.toString());
				 	}
				 	RAF.close();
				 }catch(Exception e)
				 {
				 }

			 stage.hide();
		 });
	 }
	
	 public boolean nameCheck(String value) throws Exception
	 //checks only 1 hyphen surounded by characters. no non characters
	 {
		 value = value.trim();
		 if (value == null || value.isEmpty()) 
		 {
			 throw new Exception("Non value entered in name");
		 }
		 if (value.equals("Customers"))
		 {
			 throw new Exception ("Name cannot be \"Customers\"");
		 }
		 if (!value.matches("[a-zA-Z-\\s]+")) { // checks if the name has only letters or hyphens
			 throw new Exception("Invalid Character found in string input");
		 }
		 int hyphenCount = value.split("-").length - 1;
		 if (hyphenCount > 1) 
		 { // if there are more than 1 hyphen
			 throw new Exception("Too many hyphens");
		 }
		 if (value.startsWith("-") || value.endsWith("-")) 
		 { // if the hyphen is at the start or end of the string
			 throw new Exception("Hypens must be between characters");
		 }
		 return true;
	 }
	 public boolean numberCheck(String value) throws Exception
	 {
		 if (value == null || value.isEmpty()) 
		 {
			 throw new Exception("Non value entered in number");
		 }
		 if (!value.matches("[0-9]+")) 
		 {
			 throw new Exception ("Non number found in number input");
		 }
		 int number = Integer.parseInt(value);
		 if (number <= 0)
		 {
			 throw new Exception ("Number must be more than 0");
		 }
		 return true;
		}
	 
		 public boolean floatCheck(String value) throws Exception
		 {
			 if (value == null || value.isEmpty()) 
			 {
				 throw new Exception("Non value entered in number");
			 }
			 String[] split = value.split("\\.");
			 if (split.length > 2)
			 {
				 throw new Exception("Too many . in price");
			 }
			 if (split.length > 1)
			 {
				 if (split[1].length() > 2)
				 {
					 throw new Exception ("Two decimal points only");
				 }
			 }
			 if (!value.matches("[0-9.]+")) 
			 {
				 throw new Exception ("Non number found in number input");
			 }
			 float number = Float.parseFloat(value);
			 if (number <= 0)
			 {
				 throw new Exception ("Number must be more than 0");
			 }
			 
			 return true;
			 
		 }
	 
	 public void deleteAssoc(Customer assoc)
	 {
		 customers.remove(assoc);
	 }
	 
	 
	 
	 public boolean emailCheck(String value) throws Exception
	 {
		 if(value == null || value.isEmpty())
		 {
			 throw new Exception("Non value entered in email");
		 }
		 String atSplit[]  = value.split("@");
		 if (atSplit.length != 2)
		 {
			 throw new Exception("Email needs an @ sign");
		 }
		 if (!value.matches("[0-9a-zA-Z-.@]+")) 
		 { // checks if the name has only letters or hyphens
			 throw new Exception("Invalid Character found in email");
		 }
		 if (!atSplit[1].contains("."))
		 {
			 throw new Exception("Invalid email (missing .)");
		 }
		 if (atSplit[1].startsWith(".") || atSplit[1].endsWith(".")) 
		 { // if the hyphen is at	 the start or end of the string
			 throw new Exception(". cannot be at the end of the email");
		 }
		 return true;
	 }
	
	public boolean checkFileName(File iFile) throws Exception
	{
		if (iFile == null)
		{
			throw new Exception("Non value entered");
		}
		String fileName = iFile.getName();
		String[] split = fileName.split("\\.");
		if (split.length < 2)
		{
			throw new Exception("No extension found");
		}
		String extension = split[split.length-1];
		if (!extension.equals("dat"))
		{
			throw new Exception("Wrong extension - expected (.dat)");
		}
		if (!iFile.canRead())
		{
			throw new Exception("File cannot be read");
		}
		
		return true;
	}
	public void weekly()throws Exception
	{
		if (magazine == null)
		{
			throw new Exception("No magazine in system");
		}
		for (Customer cust : customers)
		{
			updateAmountDue(cust);
			sendWeeklyEmail(cust);	
		}
	}
	
	public void sendWeeklyEmail(Customer cust)
	{
		ArrayList<String> recipt = new ArrayList<String>();
		float amount = 0;
		ArrayList<String> custSups = cust.getIntSup();
		for (String custSup : custSups)
		{
			for (Supplement sup : supplements)
			{
				if (custSup.equals(sup.getName()))
				{
					recipt.add(custSup);
					amount += sup.getCost();
				}
			}
			
		}
	}
	
	public void updateAmountDue(Customer cust)
	{
		ArrayList<String> recipt = new ArrayList<String>();
		float amount = 0;
		ArrayList<String> custSups = cust.getIntSup();
		for (String custSup : custSups)
		{
			for (Supplement sup : supplements)
			{
				if (custSup.equals(sup.getName()))
				{
					recipt.add(custSup);
					amount += sup.getCost();
				}
			}
		}
		if (cust instanceof PayingCustomer)
		{
			((PayingCustomer)cust).addToRecipt(magazine.getName());
			((PayingCustomer)cust).addToAmountDue(magazine.getCost());
			((PayingCustomer)cust).addToRecipt(recipt);
			((PayingCustomer)cust).addToAmountDue(amount);
		}
		if (cust instanceof AssociateCustomer)
		{
			Customer assocPayee = null;
			String payee = ((AssociateCustomer)cust).getWhoIsPaying();
			for (Customer custList : customers)
			{
				if (payee.equals(custList.getName()))
				{
					assocPayee = custList;
					break;
				}
			}
			((PayingCustomer)assocPayee).addToRecipt(magazine.getName());
			((PayingCustomer)assocPayee).addToAmountDue(magazine.getCost());	
			((PayingCustomer)assocPayee).addToRecipt(recipt);
			((PayingCustomer)assocPayee).addToAmountDue(amount);	
		}
	}
	
	public void monthly()
	{
		for (Customer cust : customers)
		{
			Stage email = new Stage();
			ScrollPane fp = new ScrollPane();
			fp.setMinWidth(200);
			fp.setMinHeight(100);
			GridPane gp = new GridPane();
			ArrayList<String> emptyAL = new ArrayList<String>();
			if (cust instanceof PayingCustomer)
			{
				Label label;
				label = new Label ("sending email to: " + cust.getName());
				float amountDue = ((PayingCustomer)cust).getAmountDue();
				gp.addRow(0, label);
				gp.addRow(1, label = new Label("AmountDue: " +Float.toString(amountDue)));
				gp.addRow(2, label = new Label("recipt:"));
				int row =  3;
				for (String item : ((PayingCustomer)cust).getRecipt())
				{
					label = new Label(item);
					gp.addRow(row, label);
					row++;
				}
				fp.setContent(gp);
				Scene scene = new Scene(fp);
				email.setScene(scene);
				email.show();
				((PayingCustomer) cust).setAmountDue(0.0f);
				((PayingCustomer)cust).emptyRecipt();
			}
		}
	}
	public PaymentMethod newPayment(String[] allinfo)
	{
		PaymentMethod pay = null;
		int paymentlocation = 0;
		for (int i = 0; i < allinfo.length; i++)
		{
			if (allinfo[i].contains("CreditCard") || (allinfo[i].contains("DirectDebit")))
			{
				paymentlocation = i;
				break;
			}
		}
		String details1 = (allinfo[paymentlocation+1]);
		String details2 = (allinfo[paymentlocation+2]);
		
		if(allinfo[paymentlocation].contains("Credit"))
		{
			pay = new CreditCard(details1, details2);
		}
		if (allinfo[paymentlocation].contains("DirectDebit"))
		{
			pay = new DirectDebit(details1, details2);
		}
		return pay;
	}

	
	public Customer newCustomer(GridPane wholeright) throws Exception
	{
		GridPane custdetails = (GridPane)wholeright.lookup("#customerDetails");
		FlowPane radiobuttons = (FlowPane)wholeright.lookup("#customerType");
		ObservableList<Node> banknode = radiobuttons.getChildren(); //radio buttons themselves
		try
		{
		 if (((RadioButton)banknode.get(2)).isSelected()) //assoc customer
			 {
				 return (createNewAssoc(wholeright));
			 }
		 if (((RadioButton)banknode.get(0)).isSelected() || ((RadioButton)banknode.get(1)).isSelected())//bank deposit
			 {
			 	return (createNewPaying(wholeright));		 
			 }
		 if (!((RadioButton)banknode.get(0)).isSelected() 
				 || ((RadioButton)banknode.get(1)).isSelected()//bank deposit
			 || ((RadioButton)banknode.get(3)).isSelected())
			{
				throw new Exception ("No customer type selected");
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		 return null;
	}

	public Customer createNewPaying(GridPane wholeRight) throws Exception
	{
		GridPane paymentGrid =(GridPane)wholeRight.lookup("#paymentDetails"); //extra pannel details
		FlowPane radiobuttons = (FlowPane)wholeRight.lookup("#customerType");
		ObservableList<Node> banknode = radiobuttons.getChildren(); //radio buttons themselves
		PaymentMethod pay = null;
		if (((RadioButton)banknode.get(0)).isSelected())
			 {
				 pay = newBank((GridPane)paymentGrid);
			 }
		if (((RadioButton)banknode.get(1)).isSelected())
			 {
				 pay = newCredit((GridPane)paymentGrid);
			 }
		PayingCustomer temp= null;
		try 
		{
			 temp= new PayingCustomer(createNewCust(wholeRight), pay);
		}
		catch (Exception e)
		{
			throw e;
		}
		return temp;
	}

	public Customer createNewAssoc(GridPane wholeRight) throws Exception
	{
		FlowPane assoccust =(FlowPane)wholeRight.lookup("#assocCustomers"); //extra pannel details
		ObservableList<Node> comboboxnode = assoccust.getChildren();
		ComboBox cb = (ComboBox)comboboxnode.get(0);
		String custName = (String)cb.getValue();
		Customer payee = null;
		for (Customer cust : customers)
		{
			if (cust.getName().equals(custName))
			{
				payee = cust;
				break;
			}
		}
		if (payee == null)
		{
			throw new Exception ("Paying Customer not found");
		}
		AssociateCustomer temp=null;
		try
		{
			temp= new AssociateCustomer(createNewCust(wholeRight), custName);
		}
		catch (Exception e)
		{
			throw e;
		}
		return temp;
	}
	
	public Customer createNewCust(GridPane wholeRight) throws Exception
	{
		GridPane custdetails = (GridPane)wholeRight.lookup("#customerDetails");
		ObservableList<Node> custnode = custdetails.getChildren(); 
		Customer cust = null;
		String name = ((TextField)(custnode.get(1))).getText();
		String email = ((TextField)(custnode.get(3))).getText();
		String postcode =((TextField)(custnode.get(5))).getText();
		String suburb =  ((TextField)(custnode.get(7))).getText();
		String streetname = ((TextField)(custnode.get(9))).getText();
		String streetnumber = ((TextField)(custnode.get(11))).getText();
		try
		{
			nameCheck(name);
			emailCheck(email);
			numberCheck(postcode);
			nameCheck(suburb);
			nameCheck(streetname);
			numberCheck(streetnumber);
		}
		catch (Exception e)
		{
			throw e;
		}
		Address add = new Address((Integer.parseInt(postcode)),suburb, streetname, Integer.parseInt(streetnumber));
		VBox supinfo = (VBox)wholeRight.lookup("#supplementVbox");
		ArrayList<String> sup = findSup(supinfo);
		cust = new Customer(name, email, add, sup);
		return cust;
	}

	 public Customer findWhoIsPaying(String info)
	 {
		 Customer cust = null;
		 for(Customer pcust : customers)
		 {
			 if (pcust instanceof PayingCustomer)
			 {

				 if (pcust.getName().equals(info))
				 {
					 cust = pcust;
				 }
			 }
		 }
		 return cust;
	 } 
	 public ArrayList<String> findSup (VBox vbox)
	 {
		 ArrayList<String> selected = new ArrayList<String>();
		 ObservableList<Node> gpnode = vbox.getChildren();
		 for (int i  = 0; i < gpnode.size(); i ++)
		 {
			 if (((CheckBox)gpnode.get(i)).isSelected())
			 {
				 selected.add(supplements.get(i).getName());
			 }
		 }
		 return selected;
	 }
	 public CreditCard newCredit(GridPane gp)throws Exception
	 {
		 ObservableList<Node> gpnode = gp.getChildren();
		 TextField tf = new TextField();
		 tf = (TextField)gpnode.get(1);
		 String creditno = tf.getText();
		 tf = (TextField)gpnode.get(3);
		 String csv = tf.getText();
		 try
		 {
			numberCheck(csv);
			numberCheck(creditno);
		 }
		 catch (Exception e)
		 {
			 throw e;
		 }
		 CreditCard cc = new CreditCard(creditno, csv);
		 return cc; 
	 }
	 
	 public DirectDebit newBank(GridPane gp)throws Exception
	 {
		 ObservableList<Node> gpnode = gp.getChildren();
		 TextField tf = new TextField();
		 tf = (TextField)gpnode.get(1);
		 String bankno = tf.getText();
		 tf = (TextField)gpnode.get(3);
		 String accno = tf.getText();
		 try
		 {
			 numberCheck(bankno);
			 numberCheck(accno);
		 }
		 catch(Exception e)
		 {
			 throw e;
		 }
		 DirectDebit dd = new DirectDebit(bankno, accno);
		 return dd;
	 }
	 

	 public ArrayList<Customer> getCustomers()
	 {
		 return customers;
	 }
	
	 public ArrayList<Supplement> getSups()
	 {
		 return supplements;
	 }
	 
	public void saveChanges(GridPane wholeright)throws Exception
	{
		Node node = wholeright.lookup("#title");
		String mode = ((Label)node).getText();
		if (mode.equals("Supplement"))
		{
			try
			{
			updateSupplement(wholeright);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		if(mode.equals("Customer"))
		{
			try {
			updateCustomer(wholeright);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
		if(mode.equals("Magazine"))
		{
			try
			{
			updateMagazine(wholeright);
			}
			catch (Exception e)
			{
				throw e;
			}
		}
	}
	
	public void updateMagazine(GridPane wholeRight)
	{
		Node info = wholeRight.lookup("#magInfo");
		ObservableList<Node> infonodes = ((GridPane)info).getChildren(); 
		String name = ((TextField)infonodes.get(2)).getText();
		String price = ((TextField)infonodes.get(4)).getText();
		magazine.setName(name);
		magazine.setCost((Float.parseFloat(price)));
	}
	
	public void updateSupplement(GridPane gp) throws Exception
	{
		Node info = gp.lookup("#supInfo");
		ObservableList<Node> infonodes = ((GridPane)info).getChildren(); 
		String name = ((TextField)infonodes.get(2)).getText();
		String price = ((TextField)infonodes.get(4)).getText();
		try
		{
			floatCheck(price);
		}
		catch (Exception e)
		{
			throw e;
		}
		for (Supplement sup : supplements)
		{
			if (sup.getName().equals(name))
			{
				sup.setCost(Float.parseFloat(price));
				break;
			}
		}
	}
	
	public void updateCustomer(GridPane gp) throws Exception
	{
		Customer edited = null;
		try {
			edited = newCustomer(gp);
		}
		catch (Exception e)
		{
			throw e;
		}
		Customer original = null;
		String custname = edited.getName();
		for (Customer c : customers)
		{
			if (c.getName().equals(custname))
			{
				original = c;
				break;
			}
		}
		original.setEmail(edited.getEmail());
		original.setIntSup(edited.getIntSup());
		original.setAddress(edited.getAddress());
		if (original instanceof PayingCustomer)
		{
			((PayingCustomer)original).setPayment(((PayingCustomer)edited).getPayment());
		}
		else if (original instanceof AssociateCustomer)
		{
			((AssociateCustomer)original).setWhoIsPaying(((AssociateCustomer)edited).getWhoIsPaying());
		}
	}
	
	public void delete(GridPane wholeright)throws Exception
	{
		Node node = wholeright.lookup("#title");
		String mode = ((Label)node).getText();
		if (mode.equals("Magazine"))
		{
			throw new Exception ("Magazine cannot be deleted");
		}
		else if (mode.equals("Supplement"))
		{
			deleteSupplmenet(wholeright);
		}
		else if(mode.equals("Customer"))
		{
			deleteCustomer(wholeright);
		}
	}
	public void deleteSupplmenet(GridPane wholeright)
	{
		GridPane supinfo= (GridPane)wholeright.lookup("#supInfo");
		ObservableList<Node>infonode = supinfo.getChildren();
		String name = ((TextField)infonode.get(2)).getText();
		for (int i =0; i < supplements.size(); i ++)
		{
			if (supplements.get(i).getName().equals(name))
			{
				supplements.remove(i);
				break;
			}
		} 
	}
	
	public void deleteCustomer(GridPane wholeright)
	{
		GridPane custdetails = (GridPane)wholeright.lookup("#customerDetails");
		 ObservableList<Node> custnode = custdetails.getChildren(); 
		 String name = ((TextField)(custnode.get(1))).getText();
		 for (int i = 0; i < customers.size(); i ++)
		 {
			 if (customers.get(i).getName().equals(name))
			 {
				 customers.remove(i);
				 break;
			 }
		 }
	}


	
	public boolean newSupplement(GridPane fp) throws Exception
	{
    	Node supinfo= fp.lookup("#supInfo");
    	ObservableList<Node> node = ((GridPane)supinfo).getChildren();
    	TextField nameT = (TextField)node.get(2); //name
    	TextField priceT = (TextField)node.get(4); //price
    	String nameS = nameT.getText();
    	String priceS = priceT.getText();
    	for (Supplement sup : supplements)
    	{
    		if (sup.getName().equals(nameS))
    		{
    			throw new Exception("Supplement " + nameS + " already in system");
    		}
    	}
    	
    	
    	try {
    		nameCheck(nameS);
    		floatCheck(priceS);
    	}catch (Exception e)
    	{
    		throw e;
    	}
    	Supplement tempSup = new Supplement(nameS, Float.parseFloat(priceS));
    	supplements.add(tempSup);
    	return true;
	}
	

    public boolean createMag(GridPane fp) throws Exception
    {
    	ObservableList<Node> node = fp.getChildren();
    	GridPane info = (GridPane)node.get(0);
    	ObservableList<Node> infonodes = info.getChildren();
    	TextField name= (TextField)infonodes.get(2);
    	TextField price = (TextField)infonodes.get(4);
    	String magName = name.getText();
    	String magPrice = price.getText();
    	try
    	{
    		nameCheck(magName);
    		floatCheck(magPrice);
    	}catch (Exception e)
    	{
    		throw e;
    	}
    	magazine = new Magazine(magName, Float.parseFloat(magPrice));
    	return true;
    }


	
	public void addToList(Customer cust)throws Exception
	{
		for (Customer c: customers)
		{
			if (c.getName().equals(cust.getName()))
			{
				throw new Exception("Customer already in system");
			}
		}
		customers.add(cust);
	}
	
}
