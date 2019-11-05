import java.util.ArrayList;

public class Card 
{
	private int age;
	private String name;
	private ArrayList<Resource> cost; //Resource cost
	private String chain;
	private boolean isFree;
	private String type; //also color
	private String effect;
	
	public String toString()
	{
		return getName() + "," + getEffect();
	}
	
	public int getAge() 
	{
		return age;
	}
	
	public void setAge(int age) 
	{
		this.age = age;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public ArrayList<Resource> getCost() 
	{
		return cost;
	}
	
	public void setCost(ArrayList<Resource> cost) 
	{
		this.cost = cost;
	}
	
	public String getChain() 
	{
		return chain;
	}
	
	public void setChain(String chain) 
	{
		this.chain = chain;
	}
	
	public boolean isFree() 
	{
		return isFree;
	}
	
	public void setFree(boolean isFree) 
	{
		this.isFree = isFree;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String getEffect() 
	{
		return effect;
	}
	
	public void setEffect(String effect) 
	{
		this.effect = effect;
	}
	
	
	
}
