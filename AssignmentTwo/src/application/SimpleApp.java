package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import Customer.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.geometry.Orientation;
import java.util.ArrayList;
import Supplement.*;
import javafx.collections.ObservableList;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import Address.*;
import PaymentMethod.*;

public class SimpleApp extends Application {
	buisness b = buisness.getInstance();
	static FlowPane errorPane;
	static Label errorLabel;
	static Scene errorScene;
	static Stage errorStage;
   @Override
   public void start(Stage PM) {	
	   errorPane = new FlowPane();
	   errorLabel = new Label();
	   errorLabel = new Label();
	   errorPane.getChildren().add(errorLabel);
	   Scene errorScene = new Scene(errorPane);
	   PM.setTitle("mine");
	   errorStage = new Stage();
	   errorStage.setTitle("Notification");
	   errorStage.setScene(errorScene);
	   Scene s = createStart(PM);
	   PM.setScene(s);
	   PM.setWidth(950);
	   PM.setHeight(400);
	   PM.show();
   }
   
   public ScrollPane createPerm(Stage PM)
   { 
	   ScrollPane sp = new ScrollPane();
	   GridPane mainPane = new GridPane();
	   mainPane.setId("rightPane");
	   sp.setContent(mainPane);
	   return sp;
   }
   public Scene createStart(Stage PM)
   {
	   Button viewB = new Button("View");
	   Button write = new Button("write to file");
	   write.setOnAction(event -> {
		   try
		   {
			   b.openOutPutFile();
		   }catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
		   }
		   
	   });
	   Button read = new Button("read from file");
	   read.setOnAction(event-> {
		   try {
		   b.readFromFile(PM);
		   errorLabel.setText("Successfully read from file");
		   errorStage.show();
		   swapToView(PM);
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
			
		   }
		   });
	   viewB.setOnAction(event ->
	   {
		   swapToView(PM);
	   });
	   Button weekly = new Button("Week events");
	   weekly.setOnAction(event -> {
		   try {
			   b.weekly();
			   errorLabel.setText("Weekly events completed");
			   errorStage.show();
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
		   }
	   });
	   Button monthly = new Button("Month events");
	   monthly.setOnAction(event ->
	   {
		   b.monthly();
		   errorLabel.setText("Monthly Events completed");
		   errorStage.show();
	   });
	   Button createB = new Button ("Create");
	   createB.setOnAction(event -> 
	   {
		  swapToCreate(PM); 
	   });
	   SplitPane splitPaneLeft= new SplitPane();
	   FlowPane leftButtons = new FlowPane(viewB, createB, read, write, weekly, monthly);
	   leftButtons.setMinHeight(20);
	   VBox leftMain = new VBox();
	   leftMain.setId("leftMain");
	   ScrollPane rightFlow = createPerm(PM);
	   splitPaneLeft.setOrientation(Orientation.VERTICAL);
	   splitPaneLeft.setDividerPositions(0.1f);
	   leftButtons.setMaxHeight(20f);
	   splitPaneLeft.getItems().addAll(leftButtons, leftMain);  
	   SplitPane mainSplit = new SplitPane();
	   mainSplit.getItems().addAll(splitPaneLeft, rightFlow);   
	   Scene s = new Scene(mainSplit);
	   return s;
	   }  

   public void swapToView(Stage PM)
   {
	   Scene ts = PM.getScene();
	   VBox leftMain = (VBox)ts.lookup("#leftMain");
	   TreeView CTV= createUserTree(PM);
	   CTV.setMaxWidth(250);
	   CTV.setMaxHeight(200);
	   ListView<String> SLV = new ListView<String>();
	   ArrayList<Supplement> supList = b.getSups();
	   for (Supplement sup : supList)
	   {
		   String s = sup.getName();
		   SLV.getItems().add(s);  
	   }
	   SLV.setMaxWidth(250);
	   SLV.setMaxHeight(200);
	   SLV.setMinHeight(50);
	   SLV.getSelectionModel().selectedItemProperty().addListener((prop, old, n) ->
	   {
		   setupSup(PM);
		   displaySupInfo(PM, n.toString());
	   });
	   Button magazine = new Button("Magazine");
	   magazine.setOnAction(event -> {
		   try
		   {
			   displayMagazine(PM);
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
		   }
	   });
	   leftMain.getChildren().clear();
	   SLV.maxHeight(20);
	   leftMain.getChildren().addAll(CTV, SLV, magazine);  
   }
   
   public void displayMagazine(Stage PM)throws Exception
   {
		   if (b.getMagazine() ==null)
		   {
			  throw new Exception("No magazine in the System");
		   }
	   setupMag(PM);
	   Scene wholescene = PM.getScene();
	   GridPane wholeright = (GridPane)wholescene.lookup("#rightPane");
	   Node supinfo= wholeright.lookup("#magInfo");
	   ObservableList<Node> node = ((GridPane)supinfo).getChildren();
	   TextField nameT = (TextField)node.get(2); //name
	   TextField priceT = (TextField)node.get(4); //price
	   nameT.setDisable(true);
	   priceT.setDisable(true);
	   nameT.setText(b.getMagazine().getName());
	   priceT.setText(Float.toString(b.getMagazine().getCost()));
	   addEditButtons(PM);
   }
   
   
   public void displaySupInfo(Stage PM, String supname)
   {
	   Scene wholescene = PM.getScene();
	   GridPane wholeright = (GridPane)wholescene.lookup("#rightPane");
	   Node supinfo= wholeright.lookup("#supInfo");
	   ObservableList<Node> node = ((GridPane)supinfo).getChildren();
	   TextField nameT = (TextField)node.get(2); //name
	   TextField priceT = (TextField)node.get(4); //price
	   Supplement sup =null;
	   ObservableList<Node>  wholerightnode= wholeright.getChildren();
	   for(Supplement s : b.getSupplements())
	   {
		   if (s.getName().equals(supname))
		   {
			   sup = s;
		   }
	   }
	   ((TextField)nameT).setText(sup.getName());
	   nameT.setDisable(true);
	   priceT.setDisable(true);
	   ((TextField)priceT).setText((Float.toString(sup.getCost())));
	   addEditButtons(PM);   
   }
   
   public void displayUserInfo(String user, Stage PM)
   {
	   if (user.equals("Customers"))
	   {
		   return;
	   }
	   Customer cust = null;
	   String originalCust = user;
	   for (int i = 0; i <b.getCustomers().size(); i++)
	   {
		   if (b.getCustomers().get(i).getName().equals(originalCust))
			{
				cust = b.getCustomers().get(i);
			}
	   }
	   ArrayList<String> custsup = cust.getIntSup();
	   Scene scene = PM.getScene();
		GridPane wholeright = (GridPane)scene.lookup("#rightPane");
		FlowPane radiobuttons = (FlowPane)wholeright.lookup("#customerType");
		ObservableList<Node>  radiobuttonnode= radiobuttons.getChildren();
		if (cust instanceof AssociateCustomer)
		{
			RadioButton tempradio = (RadioButton)radiobuttonnode.get(2);
			tempradio.setSelected(true);
			tempradio.setDisable(true);
			((RadioButton)radiobuttonnode.get(0)).setDisable(true);
			((RadioButton)radiobuttonnode.get(1)).setDisable(true);

		}
		else if (((PayingCustomer)cust).getPayment() instanceof CreditCard)
		{
			RadioButton tempradio = (RadioButton)radiobuttonnode.get(1);
			tempradio.setSelected(true);
			tempradio.setDisable(true);
			((RadioButton)radiobuttonnode.get(0)).setDisable(true);
			((RadioButton)radiobuttonnode.get(2)).setDisable(true);
		}
		else if (((PayingCustomer)cust).getPayment() instanceof DirectDebit)
		{
			RadioButton tempradio = (RadioButton)radiobuttonnode.get(0);
			tempradio.setSelected(true);
			tempradio.setDisable(true);
			((RadioButton)radiobuttonnode.get(1)).setDisable(true);
			((RadioButton)radiobuttonnode.get(2)).setDisable(true);
		}
		GridPane custinfopane = (GridPane)wholeright.lookup("#customerDetails");
		ObservableList<Node>  custinfo= custinfopane.getChildren();
		//this will be filling out the cust details.
		Address custAdd = cust.getAddress();
		((TextField)custinfo.get(1)).setText(cust.getName());
		((TextField)custinfo.get(3)).setText(cust.getEmail());
		((TextField)custinfo.get(5)).setText((Integer.toString(custAdd.getPostCode())));
		((TextField)custinfo.get(7)).setText(custAdd.getSuburb());
		((TextField)custinfo.get(9)).setText(custAdd.getStreetName());
		((TextField)custinfo.get(11)).setText(Integer.toString(custAdd.getStreetNumber()));
		for (int i = 1; i < custinfo.size(); i +=2)
		{
			((TextField)custinfo.get(i)).setDisable(true);
		} 
		//filling out payment details
		if (!(cust instanceof AssociateCustomer))
		{
			GridPane paymentinfopane = (GridPane)wholeright.lookup("#paymentDetails");
			ObservableList<Node>  paymentinfo= paymentinfopane.getChildren();
			PaymentMethod pay= ((PayingCustomer)cust).getPayment();
			String pay1 = null;
			String pay2 = null;
			if (pay instanceof CreditCard)
			{
				pay1 = ((CreditCard)pay).getCreditCardNo();
				pay2 = ((CreditCard)pay).getCSV();
			}
			if (pay instanceof DirectDebit)
			{
				pay1 = ((DirectDebit)pay).getBankNo();
				pay2 = ((DirectDebit)pay).getAccountNo();
			}
			((TextField)paymentinfo.get(1)).setText(pay1);
			((TextField)paymentinfo.get(1)).setDisable(true);
			((TextField)paymentinfo.get(3)).setText(pay2);
			((TextField)paymentinfo.get(3)).setDisable(true);
			ScrollPane scroll = new ScrollPane();
			scroll.setMinHeight(100);
			scroll.setMaxHeight(200);
			ArrayList<String> recipt =((PayingCustomer)cust).getRecipt();
			GridPane reciptArea = new GridPane();
			reciptArea.addRow(0, new Label("Recipt:"));
			Label amountDue = new Label("Amount due: " + Float.toString(((PayingCustomer)cust).getAmountDue()));
			reciptArea.addRow(1, amountDue);
			int row = 2;
			for(String s : recipt)
			{
				if (s != "")
				{
					Label reciptItem= new Label(s);
					reciptArea.addRow(row,reciptItem);
					row ++;
				}
			}
			scroll.setContent(reciptArea);
			wholeright.addRow(wholeright.getChildren().size()+1,scroll);
		}
		else
		{
			FlowPane assocCustomer = (FlowPane)wholeright.lookup("#assocCustomers");
			ObservableList<Node>  cbnode= assocCustomer.getChildren();
			ComboBox cb = (ComboBox)cbnode.get(0);
			cb.setDisable(true);
			cb.setValue(((AssociateCustomer)(cust)).getWhoIsPaying());
		}
		//supplements
		VBox suppvbox = (VBox)wholeright.lookup("#supplementVbox");
		//suppvbox.setId("supplementVbox");
		ObservableList<Node>  supinfo= suppvbox.getChildren();
		for (int i = 0; i < supinfo.size(); i++)
		{
			for (int j = 0; j< custsup.size(); j ++)
			{

				if (supinfo.get(i).toString().contains(custsup.get(j))) 
					{
						((CheckBox)supinfo.get(i)).setSelected(true);
					}
			}
			((CheckBox)supinfo.get(i)).setDisable(true);
		}	
		addEditButtons(PM);
   }
   
   public void addEditButtons(Stage PM)
   {
	   Scene scene = PM.getScene();
	   GridPane gp = (GridPane)scene.lookup("#rightPane");
	   Button edit = new Button("Edit");
	   edit.setOnAction(event -> {
		   swapToEditMode(PM);
	   });
	   Button save = new Button("save");
	   save.setOnAction(event ->
	   {
		   try {
		   b.saveChanges(gp);
		   errorLabel.setText("Successfully Saved");
		   errorStage.show();
		   swapToView(PM);
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
			   
		   }
	   });
	   Button delete = new Button("delete");
	   delete.setOnAction(event ->
	   {
		   try
		   {
		   b.delete(gp);
		   errorLabel.setText("Successfully deleted");
		   errorStage.show();	
		   swapToView(PM);
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
		   }
	   });
	   save.setDisable(true);
	   delete.setDisable(true);
	   FlowPane editbuttons = new FlowPane();
	   editbuttons.getChildren().addAll(edit, save, delete);
	   gp.addRow(gp.getChildren().size() + 1, editbuttons);
   }
  

   public void swapToEditMode(Stage PM)
   {
	   Scene wholescene = PM.getScene();
	   GridPane wholeright = (GridPane)wholescene.lookup("#rightPane");
	   ObservableList<Node>  wholerightnode= wholeright.getChildren();
	   for (Node n : wholerightnode)
	   {
		   if (n instanceof GridPane)
		   {
			   ObservableList<Node> textfields= ((GridPane)n).getChildren();
			   for (Node no : textfields)
			   {
				   if (no instanceof TextField)
				   {
					   {
						   if (no.getId() == null)
						   no.setDisable(false);
					   }
				   }
			   }
		   }
		   else if (n instanceof VBox)
		   {
			   ObservableList<Node> clickables= ((VBox)n).getChildren();
			   for (Node no : clickables)
			   {
				   no.setDisable(false);
			   }
		   }
		   else if (n instanceof FlowPane)
		   {
			   ObservableList<Node> buttons= ((FlowPane)n).getChildren();
			   for (Node b : buttons)
			   {
					   b.setDisable(false);
			   }
		   }
		   else if(n instanceof TextField)
		   {
			   if (n.getId() == null)
			   {
				   n.setDisable(false);
			   }
		   }
	   }
   }
   
   public void swapToCreate(Stage PM)
   {
	   Stage tempStage = new Stage();
	   tempStage.setTitle("Creation");
	   Label n = new Label("Create what?");
	   Button mag = new Button("Magazine");
	   mag.setOnAction(event -> {
		   setupMag(PM);
		   addCreationButtons(PM, "magazine");
		   tempStage.hide();
	   });
	   Button user = new Button("Customer");
	   user.setOnAction(event ->
	   {
		  setupUser(PM); 
		  addCreationButtons(PM, "customer");
		  tempStage.hide();
	   });
	   Button sup = new Button("Supplement");
	   sup.setOnAction(event ->
	   {
		   setupSup(PM);
		   addCreationButtons(PM, "supplement");
		   tempStage.hide();
	   });
	   FlowPane fp= new FlowPane();
	   fp.getChildren().addAll(n, mag, user, sup);
	   Scene s = new Scene(fp);
	   tempStage.setScene(s);
	   tempStage.show();
	   
   }

   public void setupUser(Stage PM) //radio buttons do not work, either disable or swap (easy vs hard)
   //layout is getting messed up by adding stuff. should fix but not needed tbh
   //missing s
   {
	   Scene scene = PM.getScene();
	   GridPane wholeright = (GridPane)scene.lookup("#rightPane");
	   wholeright.getChildren().clear();  
	   Label title = new Label("Customer");
	   title.setId("title");
	   wholeright.addRow(0, title);
	   ToggleGroup group = new ToggleGroup();
	   RadioButton bank = new RadioButton("Bank deposit");
	   RadioButton credit = new RadioButton("Credit Card");
	   RadioButton assoc = new RadioButton("Associate Customer");
	   bank.selectedProperty().addListener((p, o, n) -> {
		   wholeright.addRow(3, createBank());
	   });
	   credit.selectedProperty().addListener((p, o, n) -> {
		   wholeright.addRow(3, createCredit());   
	   });
	   assoc.selectedProperty().addListener((p, o, n) ->{
		   wholeright.addRow(3, displayAssoc()); 
	   });
	   bank.setToggleGroup(group);
	   credit.setToggleGroup(group);
	   assoc.setToggleGroup(group);
	   FlowPane radioFlow = new FlowPane();
	   radioFlow.setId("customerType");
	   radioFlow.getChildren().addAll(bank, credit, assoc);
	   wholeright.addRow(1, radioFlow);
	   wholeright.addRow(2, displayCust());
	   wholeright.addRow(4, displaySup());
   }
   
   public void addCreationButtons(Stage PM, String mode)
   {
	   Scene scene = PM.getScene();
	   GridPane gp = (GridPane)scene.lookup("#rightPane");
	   Button make = new Button("Create");
	   if (mode.equals("supplement"))
	   {
		   make.setOnAction(event -> {
			   try {
				   if (b.newSupplement(gp))
				   {
					   errorLabel.setText("Supplement Created");
					   errorStage.show();
					   swapToView(PM);
				   }
			   }catch (Exception e)
			   {
				   errorLabel.setText(e.getMessage());
				   errorStage.show();
			   }
		   });
	   }
	  if (mode.equals("customer"))
	   make.setOnAction(event -> {
		   try {
			   try {
			   b.addToList((b.newCustomer(gp)));
			   }catch (Exception e)
			   {
				   throw e;
			   }
			   errorLabel.setText("Customer Created");
			   errorStage.show();
			   swapToView(PM);
		   }
		   catch (Exception e)
		   {
			   errorLabel.setText(e.getMessage());
			   errorStage.show();
		   }
	   });
	  if (mode.equals("magazine"))
	  {
		  make.setOnAction(event -> 
		  {
			  try {
			 if (b.createMag(gp))
			 {
				 errorLabel.setText("Magazine created");
				 errorStage.show();
				 swapToView(PM);
			 }
			  }
			  catch(Exception e)
			  {
				  errorLabel.setText(e.getMessage());
				  errorStage.show();
			  }
		  });
	  }
	   FlowPane contbuttos = new FlowPane();
	   contbuttos.getChildren().addAll(make);
	   gp.addRow(gp.getChildren().size() + 1, contbuttos);
	   
   }
   public VBox displaySup() //clickers for creating user
   {
	   VBox v = new VBox();
	   v.setId("supplementVbox");
	   for (Supplement sup : b.getSups())
	   {
		   CheckBox cb = new CheckBox(sup.getName());
		   v.getChildren().add(cb);
	   }   
	   return v;
   }
   
   public FlowPane displayAssoc() //need work for when file input is done
   {
	   FlowPane g = new FlowPane();
	   g.setId("assocCustomers");
	   ComboBox combo = new ComboBox();
	   for (Customer cust : b.getCustomers())
	   {
		   if (cust instanceof PayingCustomer)
		   {
			   combo.getItems().add(cust.getName());
		   }
		  // String s = new String(Integer.toString(i));
	   }
	   g.getChildren().add(combo); 	   
	   return g;
   }
   
   public GridPane createBank()
   {
	   GridPane g = new GridPane();
	   g.setId("paymentDetails");
	   TextField banknoT = new TextField();	   
	   TextField accountnoT = new TextField();   
	   Label bankNumL = new Label("Bank Number");
	   Label accountNumL = new Label("Account Number");
	   g.addRow(0,bankNumL,banknoT);
	   g.addRow(1, accountNumL, accountnoT);
	   FlowPane recipt = new FlowPane();
	   Label rec = new Label("Recipt:");
	   
	   return g;
   }
   public GridPane createCredit()
   {
	   Label creditNumL = new Label("Credit Card Number");
	   Label creditCSVL = new Label("Credit Card CSV");
	   GridPane g = new GridPane();
	   g.setId("paymentDetails");
	   TextField creditnumt = new TextField();	   
	   TextField creditcsvt = new TextField();   
	   g.addRow(0,creditNumL,creditnumt);
	   g.addRow(1, creditCSVL, creditcsvt);
	   return g;
	   
   }
   public void setupSup(Stage PM)
   {	
	   Scene scene = PM.getScene();
	   GridPane wholeright = (GridPane)scene.lookup("#rightPane");
	   Label title = new Label("Supplement");
	   title.setId("title");
	   TextField nameT = new TextField();
	   nameT.setId("name");
	   Label nameL = new Label("Name");

	   Label priceL = new Label("Price");
	   TextField priceT = new TextField();
	   GridPane temp = new GridPane();
	   temp.setId("supInfo");
	   wholeright.getChildren().clear();
	   temp.addRow(0, title);
	   temp.addRow(1, nameL, nameT);
	   temp.addRow(2, priceL, priceT);
	   wholeright.addRow(0, temp);
   }
   public void setupMag(Stage PM)
   {

	   Label nameL = new Label("Magazine Name");
	   TextField nameT = new TextField();
	   nameT.setId("name");
	   Label priceL = new Label("Price");
	   TextField priceT = new TextField();
	   Scene scene = PM.getScene();
	   GridPane wholeright = (GridPane)scene.lookup("#rightPane");
	   wholeright.getChildren().clear();
	   GridPane temp = new GridPane();
	   temp.setId("magInfo");
	   Label title = new Label("Magazine");
	   title.setId("title");
	   temp.addRow(0, title);
	   temp.addRow(1, nameL, nameT);
	   temp.addRow(2, priceL, priceT);
	   wholeright.addRow(0, temp);
   }
   public TreeView createUserTree(Stage PM) //creating the tree to visualise
   {
	   ArrayList<Customer> temp = b.getCustomers();
	   ArrayList<Customer> hangingCusts = new ArrayList<Customer>();
	   TreeItem<String> rootItem = new TreeItem<String>("Customers");
	   for (Customer d : temp)
	   {
		   boolean found = false;
		   if (d instanceof AssociateCustomer)
		   {
			   String payingcustname = ((AssociateCustomer) d).getWhoIsPaying();
			   for (TreeItem<String> item: rootItem.getChildren())
			   {
				   if(item.getValue().equals(payingcustname))
				   {
					   TreeItem<String> assocleaf = new TreeItem<String>(d.getName());
					   item.getChildren().add(assocleaf);
					   found = true;
				   }
			   }
			   if (!found)
			   {
				   hangingCusts.add(d);
			   }
		   }
		   else
		   {
			   String n = d.getName();
			   TreeItem<String> custleaf = new TreeItem<String>(n);
			   rootItem.getChildren().add(custleaf); 
		   }
	   }
	   for (Customer toBeDeleted : hangingCusts)
	   {
		   b.deleteAssoc(toBeDeleted);
	   }
	   TreeView<String> TV = new TreeView<String>(rootItem);
	   TV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		   if (!(newValue.getValue().equals("Customers")))
		   {
		   setupUser(PM);
		   displayUserInfo(newValue.getValue(), PM);
		   }
	});	   
	   return TV;
   }

   
   public GridPane displayCust()
   {
	   GridPane gp = new GridPane();
	   gp.setId("customerDetails");
	   TextField name = new TextField();
	   name.setId("name");
	   TextField email = new TextField();
	   TextField postcode = new TextField();
	   TextField suburb = new TextField();
	   TextField streetname = new TextField();
	   TextField streetnum = new TextField();
	   Label nameL = new Label("Name");
	   Label emailL = new Label("Email");
	   Label streetnameL = new Label("Street Name");
	   Label streetnumL = new Label ("Street number");
	   Label suburbL = new Label ("Suburb");
	   Label postcodeL = new Label("Post Code");
	   gp.addRow(0, nameL, name);
	   gp.addRow(1, emailL, email);
	   gp.addRow(2, postcodeL, postcode);
	   gp.addRow(3, suburbL, suburb);
	   gp.addRow(4, streetnameL,streetname);
	   gp.addRow(5, streetnumL, streetnum); 
	   return gp;   
   }

   public static void main(String[] args) {
	   try
	   {
		   launch(args);
	   }
	   catch (Exception e){
	   }
   }
   
}