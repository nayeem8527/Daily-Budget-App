package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
/*
 * Mohamad Nayeem
 * 2014147
 */
public class controller 
{
	@FXML
	private TextField amt;
	
	@FXML
	private LocalDate localdate;
	
	@FXML
	private Label lb1,Expense_Budget,Expense_Available,lb2,expense_Budget,expense_Available;
	
	@FXML
	private Button submit;
	
	@FXML
	private TextField cat,money;
	
	@FXML
	private DatePicker date;
	
	@FXML
	TableView<Expense> table;
	
	@FXML
	PieChart chr;
	ObservableList<PieChart.Data> f = FXCollections.observableArrayList();
	
	private BufferedReader br;
	private BufferedWriter writer;
	int linecounter=0;
	String Budget,Available;
	String EnteredBudget;
	int Available1;
	int expense = 0;
	
	private ObservableList<Expense> data = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Expense, String> firstcolumn;
	@FXML
	private TableColumn<Expense,Number> secondcolumn;
	@FXML
	private TableColumn<Expense,String> thirdcolumn;
	
	@FXML
	private void initialize(){
		firstcolumn.setCellValueFactory(cellData -> cellData.getValue().firstcolumnProperty());
		secondcolumn.setCellValueFactory(cellData -> cellData.getValue().secondcolumnProperty());
		thirdcolumn.setCellValueFactory(cellData -> cellData.getValue().thirdcolumnProperty());
	}
	public void fileWrite(String EnteredBudget) throws IOException
	{
		writer = new BufferedWriter(new FileWriter("./src/Expenses.txt"));
		writer.write("BudgetRs. " + EnteredBudget +"\n");
		writer.write("AvailableRs. " + EnteredBudget + "\n");
		writer.write("\n");		
		writer.close();
	}
		
	public void enter(ActionEvent event) throws IOException
	{
		EnteredBudget = amt.getText();
		if(EnteredBudget.compareTo("")== 0)
			lb1.setText("Enter something");
		else
		{
			Available1 = Integer.parseInt(EnteredBudget);
			lb1.setText("Entered");
			lb1.setTextFill(Color.GREEN);
			fileWrite(EnteredBudget);
		}
	}
	
	public void load1(Event event)
	{
		Expense_Budget.setText(EnteredBudget);
		Expense_Available.setText(EnteredBudget);		
	}
	
	public void changes(ActionEvent event) throws IOException
	{
		String new_cat,new_money,temp,dates;
		int new_money1;
		localdate = date.getValue();
		dates = date.getValue().toString();
		new_cat = cat.getText();
		new_money = money.getText();
		if(new_cat == "" || new_money == "")
		{
			lb2.setText("Enter values");
		}
		new_money1 = Integer.parseInt(new_money);
		if(new_money1>Available1)
		{
			lb2.setText("Budget Exceeded");
			lb2.setTextFill(Color.ORANGE);
		}
		else
		{
			expense = expense + new_money1;
			lb2.setText("Added");
			lb2.setTextFill(Color.GREEN);
			Available1 = Available1-new_money1;
			temp = Integer.toString(Available1);
			Expense_Available.setText(temp);
			RandomAccessFile q = new RandomAccessFile("./src/Expenses.txt","rw");
			q.readLine();
			q.write(("AvailableRs. " + temp).getBytes());
			q.seek(0);
			while(q.readLine()!=null)
			{
				q.readLine();
			}
			q.write((new_cat + " " + new_money + " " + localdate.getYear() + "/" + localdate.getMonthValue() + "/" + localdate.getDayOfMonth()+ "\n").getBytes());
			q.close();
			data.add(new Expense(new_cat,new_money1,dates));
			table.setItems(data);
		}
	}
	
	public void load2(Event event)
	{
		expense_Budget.setText(EnteredBudget);
		String a = Integer.toString(expense);
		expense_Available.setText(a);
		
	}
	
	public void load3(Event event)
	{
		f.clear();
		String extra;
		String[] extra1;
		HashMap<String,Integer> h = new HashMap<String,Integer>();
		try
		{
			BufferedReader x = new BufferedReader(new FileReader("./src/Expenses.txt"));
			extra = x.readLine();
			extra = x.readLine();
			extra = x.readLine();
			while((extra = x.readLine())!=null)
			{
				extra1 = extra.split(" ");
				if(h.containsKey(extra1[0]))
				{
					h.put(extra1[0], h.get(extra1[0])+Integer.parseInt(extra1[1]));
				}
				h.put(extra1[0], Integer.parseInt(extra1[1]));
			}
			Set<String> keys = h.keySet();
			for(String key:keys){
				f.add(new PieChart.Data(key, h.get(key)));
			}
			x.close();
			chr.setData(f);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
