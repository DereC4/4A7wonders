public class Wonder {
	private String name;
	private int currentStage;
	private Resources product; // resource that the wonder produces

	public Wonder(String s) {
		if (s.equals(getWonders()[0])) {
			name = "rhodos";
			currentStage = 0;
			product = new Resources("Ore");
		}
		if (s.equals(getWonders()[1])) {
			name = s.toLowerCase();
			currentStage = 0;
			product = new Resources("Loom");
		}
		if (s.equals(getWonders()[2])) {
			name = "alexandria";
			currentStage = 0;
			product = new Resources("Glass");
		}
		if (s.equals(getWonders()[3])) {
			name = "babylon";
			currentStage = 0;
			product = new Resources("Clay");
		}
		if (s.equals(getWonders()[4])) {
			name = s.toLowerCase();
			currentStage = 0;
			product = new Resources("Papyrus");
		}
		if (s.equals(getWonders()[5])) {
			name = "gizah";
			currentStage = 0;
			product = new Resources("Stone");
		}
		if (s.equals(getWonders()[6])) {
			name = "olympia";
			currentStage = 0;
			product = new Resources("Wood");
		}
		setCurrentStage(0);
	}

	public static final String[] WONDERS = { "Colossus of Rhodos", "Halikarnassus", "Lighthouse of Alexandria",
			"Hanging Gardens of Babylon", "Ephesos", "Great Pyramid of Giza", "Statue of Zeus at Olympia" };

	public String getEffect(int stage) {
		if (stage == 0) {
			return "";
		} else if (stage == 1) {
			return "VP 3";
		} else if (stage == 3) {
			return "VP 7";
		} else {
			if (getName().equalsIgnoreCase("rhodos"))
				return "WP 2";
			else if (getName().equalsIgnoreCase("halikarnassus"))
				return "drawDiscard"; // Draw from discard
			else if (getName().equalsIgnoreCase("alexandria"))
				return "resourceAll"; // Resource of choice
			else if (getName().equalsIgnoreCase("babylon"))
				return "scienceAll"; // Provides science of choice
			else if (getName().equalsIgnoreCase("gizah"))
				return "VP 5";
			else if (getName().equalsIgnoreCase("ephesos"))
				return "C 9";
			else // Olympia
				return "ignoreCost";
		}
	}

	public String getCost(int stage) {
		if (getName().equalsIgnoreCase("rhodos")) {
			if (stage == 1)
				return "Wood,Wood";
			else if (stage == 2)
				return "Clay,Clay,Clay";
			else
				return "Ore,Ore,Ore,Ore";
		} else if (getName().equalsIgnoreCase("Halikarnassus")) {
			if (stage == 1)
				return "Clay,Clay";
			else if (stage == 2)
				return "Ore,Ore,Ore";
			else
				return "Loom,Loom";
		} else if (getName().equalsIgnoreCase("alexandria")) {
			if (stage == 1)
				return "Stone,Stone";
			else if (stage == 2)
				return "Ore,Ore";
			else
				return "Glass,Glass";
		} else if (getName().equalsIgnoreCase("babylon")) {
			if (stage == 1)
				return "Clay,Clay";
			else if (stage == 2)
				return "Wood,Wood,Wood";
			else
				return "Clay,Clay,Clay,Clay";
		} else if (getName().equalsIgnoreCase("gizah")) {
			if (stage == 1)
				return "Stone,Stone";
			else if (stage == 2)
				return "Wood,Wood,Wood";
			else
				return "Stone,Stone,Stone,Stone";
		} else if (getName().equalsIgnoreCase("ephesos")) {
			if (stage == 1)
				return "Stone,Stone";
			else if (stage == 2)
				return "Wood,Wood";
			else
				return "Lit,Lit";
		} else // Olympia
		{
			if (stage == 1)
				return "Wood,Wood";
			else if (stage == 2)
				return "Stone,Stone";
			else
				return "Ore,Ore";
		}
	}

	/*
	 * public void buildWonder(int stage) { int currentStage =
	 * wonder.getCurrentStage(); if (currentStage == 3) return; else if (stage <=
	 * currentStage) return;
	 * 
	 * ArrayList<Resources> cost = new ArrayList<Resources>(); ArrayList<Resources>
	 * resources = new ArrayList<Resources>(); //local copy of resources for
	 * (Resources r : this.resources) resources.add(r);
	 * 
	 * String temp = wonder.getCost(stage); String[] tempCost = temp.split(","); for
	 * (int i = 0; i < tempCost.length; i++) cost.add(new Resources(tempCost[i]));
	 * 
	 * for (int j = resources.size()-1; j >= 0; j--) { for (int i = cost.size()-1; i
	 * >= 0; i--) { if (resources.get(j).toString().equals(cost.get(i))) {
	 * resources.remove(j); cost.remove(i); break; } } } if (cost.size() == 0) {
	 * wonder.setCurrentStage(stage); } else {
	 * 
	 * } }
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}

	public Resources getProduct() {
		return product;
	}

	public static String[] getWonders() {
		return WONDERS;
	}

	public void setProduct(Resources product) {
		this.product = product;
	}
}