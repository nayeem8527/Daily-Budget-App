package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/*
 * 
 * Mohammad Nayeem
 * 2014147
 */
public class Expense {

	SimpleStringProperty category;
	SimpleIntegerProperty amount;
	SimpleStringProperty dt;

	public Expense(String category, int amount,String dt )
	{
		this.category=new SimpleStringProperty(category);
		this.amount=new SimpleIntegerProperty(amount);
		this.dt=new SimpleStringProperty(dt);
	}

	public String getCategory()
	{
		return category.get();
	}

	public Integer getAmount()
	{
		return amount.get();
	}

	public String getDt()
	{
		return dt.get();
	}

	public void setCategory(String category)
	{
		this.category.set(category);
	}

	public void setAmount(Integer amount)
	{
		this.amount.set(amount);
	}

	public void setDate(String dt)
	{
		this.dt.set(dt);
	}
	
	public StringProperty firstcolumnProperty()
	{
		return category;
	}
	
	public IntegerProperty secondcolumnProperty()
	{
		return amount;
	}
	
	public StringProperty thirdcolumnProperty()
	{
		return dt;
	}
}
