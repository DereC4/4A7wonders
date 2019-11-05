public class Wonder 
{
	private String name;
	private int currentStage;
	private Resource product; //resource that the wonder produces
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
	public void setProduct(Resource product) 
	{
		this.product = product;
	}
}