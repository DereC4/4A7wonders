public class Wonder 
{
	private String name;
	private int currentStage;
	private Resource product; //resource that the wonder produces
	public static final String[] WONDERS = 
	{"Colossus of Rhodes", "Halicarnassus", "Lighthouse of Alexandria", "Hanging Gardens of Babylon", 
	 "Great Pyramid of Giza", "Ephesos", "Statue of Zeus at Olympia"};
	
	public String wonderEffect(int stage)
	{
		if (stage == 1)
			return "VP 3";
		else if (stage == 3)
			return "VP 7";
		
		if (stage == 3)
		{
			if (getName().equals("Colossus of Rhodes"))
				return "WP 2";
			else if (getName().equals("Halicarnassus"))
				return "draw"; //Draw from discard 
			else if (getName().equals("Lighthouse of Alexandria"))
				return "resource all"; //Resource of choice
			else if (getName().equals("Hanging Gardens of Babylon"))
				return "science all"; //Provides science of choice
			else if (getName().equals("Great Pyramid of Giza"))
				return "VP 5";
			else if (getName().equals("Ephesos"))
				return "coin 9";
			else if (getName().equals("Olympia"))
				return 
		}
		
	}
	
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getCurrentStage() 
	{
		return currentStage;
	}
	
	public void setCurrentStage(int currentStage) 
	{
		this.currentStage = currentStage;
	}
	
	public Resource getProduct() 
	{
		return product;
	}
	
	public static String[] getWonders() 
	{
		return WONDERS;
	}

	public void setProduct(Resource product) 
	{
		this.product = product;
	}
}