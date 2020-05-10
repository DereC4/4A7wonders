public class Resources {
	// simple class :D
	private String resource;

	public Resources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public boolean isR() // is Resource not loom, glass, or papyrus
	{
		return !(resource.equals("Papyrus") || resource.equals("Glass") || resource.equals("Loom"));
	}

	public String toString() {
		return getResource();
	}

	public boolean equals(Object obj) {
		Resources r = (Resources) obj;
		if (getResource().contains(r.getResource()))
			return true;
		else if (r.getResource().contains(getResource()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return resource.hashCode();
	}
}