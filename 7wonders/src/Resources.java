public class Resources 
{
	//simple class :D
    private String resource;
    public Resources(String resource)
    {
        this.resource = resource;
    }
    public String getResource()
    {
        return resource;
    }
    public void setResource(String resource)
    {
        this.resource = resource;
    }
    public boolean isR() //is Resource not loom, glass, or papyrus
    { 
    	return !(resource.equals("Papyrus") || resource.equals("Glass") || resource.equals("Loom"));
    }
    public String toString()
    {
    	return getResource();
    }
    @Override
    public boolean equals(Object obj)
    {
    	Resources r = (Resources) obj;
    	if (getResource().equals(r.getResource()))
    		return true;
    	return false;
    }
}