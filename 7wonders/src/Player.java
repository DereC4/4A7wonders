import java.util.ArrayList;
import java.util.TreeMap;

public class Player implements Comparable<Player> {
	private int money, warMinusPoints, warPlusPoints, armies, index, vp;
	private TreeMap<String, Integer> vpSources;
	private TreeMap<String, Boolean> reducedList;
	private TreeMap<String, Integer> sciList;
	private TreeMap<String, ArrayList<Card>> playedCards;
	private ArrayList<Card> hand;
	private Wonder wonder;
	private ArrayList<Resources> resources;
	private ArrayList<Card> tempPlayedCards;
	private boolean burnCard;
	private boolean ignoreCost;
	private boolean buildWonder;
	private boolean Has_VP_Effect;
	private TreeMap<Integer, ArrayList<Resources>> trade; // Player index, resources
	private boolean isDrawDiscard;

	public Player(int index) {
		vp = 0;
		money = 3;
		warMinusPoints = 0;
		warPlusPoints = 0;
		armies = 0;
		reducedList = new TreeMap<String, Boolean>();
		this.index = index;
		trade = new TreeMap<Integer, ArrayList<Resources>>();

		reducedList.put("leftR", false); // left Resource
		reducedList.put("leftC", false); // left Commodities
		reducedList.put("rightR", false); // right resources
		reducedList.put("rightC", false); // right commodities

		sciList = new TreeMap<String, Integer>();

		resources = new ArrayList<Resources>();

		sciList.put("lit", 0); // tablet

		sciList.put("math", 0); // weird triangle
		sciList.put("gear", 0); // engineer

		playedCards = new TreeMap<String, ArrayList<Card>>();
		hand = new ArrayList<Card>();
		setBurnCard(false);
		setIgnoreCost(false);
		setBuildWonder(false);
		setHas_VP_Effect(false);
		tempPlayedCards = new ArrayList<Card>();
		vpSources = new TreeMap<String, Integer>();
		setDrawDiscard(false);
	}

	public void play(Card c) {
		addTempPlayedCard(c);
		getHand().remove(c);
	}

	public TreeMap<Integer, ArrayList<Resources>> getTrade() {
		return trade;
	}

	public void setTrade(TreeMap<Integer, ArrayList<Resources>> trade) {
		this.trade = trade;
	}

	public boolean isBurnCard() {
		return burnCard;
	}

	public void setBurnCard(boolean burnCard) {
		this.burnCard = burnCard;
	}

	public ArrayList<Card> getTempPlayedCards() {
		return tempPlayedCards;
	}

	public void addTempPlayedCard(Card tempPlayedCard) {
		this.tempPlayedCards.add(tempPlayedCard);
	}

	public void addToResources(Resources r) {
		resources.add(r);
	}

	public void addToHand(Card c) {
		hand.add(c);
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getWarMinusPoints() {
		return warMinusPoints;
	}

	public void setWarMinusPoints(int warMinusPoints) {
		this.warMinusPoints = warMinusPoints;
	}

	public int getWarPlusPoints() {
		return warPlusPoints;
	}

	public void setWarPlusPoints(int warPlusPoints) {
		this.warPlusPoints = warPlusPoints;
	}

	public int getArmies() {
		return armies;
	}

	public void setArmies(int armies) {
		this.armies = armies;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TreeMap<String, Boolean> getReducedList() {
		return reducedList;
	}

	public void setReducedList(TreeMap<String, Boolean> reducedList) {
		this.reducedList = reducedList;
	}

	public TreeMap<String, Integer> getSciList() {
		return sciList;
	}

	public void setSciList(TreeMap<String, Integer> sciList) {
		this.sciList = sciList;
	}

	public TreeMap<String, ArrayList<Card>> getPlayedCards() {
		return playedCards;
	}

	public void setPlayedCards(TreeMap<String, ArrayList<Card>> playedCards) {
		this.playedCards = playedCards;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public int getWonderStage() {
		return wonder.getCurrentStage();
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public Wonder getWonder() {
		return wonder;
	}

	public void setWonder(Wonder w) {
		wonder = w;
	}

	public void addVP(int i) {
		vp += i;
	}

	public int getVP() {
		return vp;
	}

	public ArrayList<Resources> getResources() {
		return resources;
	}

	public boolean isIgnoreCost() {
		return ignoreCost;
	}

	public void setIgnoreCost(boolean ignoreCost) {
		this.ignoreCost = ignoreCost;
	}

	public boolean isBuildWonder() {
		return buildWonder;
	}

	public void setBuildWonder(boolean buildWonder) {
		this.buildWonder = buildWonder;
	}

	public boolean has_VP_Effect() {
		return Has_VP_Effect;
	}

	public void setHas_VP_Effect(boolean buildWonder) {
		this.Has_VP_Effect = buildWonder;
	}

	public String toString() {
		return getIndex() + "";
	}

	public int getVp() {
		return vp;
	}

	public void setVp(int vp) {
		this.vp = vp;
	}

	public TreeMap<String, Integer> getVpSources() {
		return vpSources;
	}

	public void setVpSources(TreeMap<String, Integer> vpSources) {
		this.vpSources = vpSources;
	}

	public boolean isDrawDiscard() {
		return isDrawDiscard;
	}

	public void setDrawDiscard(boolean isDrawDiscard) {
		this.isDrawDiscard = isDrawDiscard;
	}

	public int compareTo(Player p) {
		if (p.getVp() > getVp())
			return -1;
		else if (p.getVp() < getVp())
			return 1;
		else
			return 0;
	}

	public void addToPlayedCards(Card c) {
		String type = c.getType().toLowerCase();
		if (playedCards.containsKey(type)) {
			ArrayList<Card> temp = playedCards.get(type);
			temp.add(c);
			playedCards.put(type, temp);
		} else {
			ArrayList<Card> temp = new ArrayList<Card>();
			temp.add(c);
			playedCards.put(type, temp);
		}
	}
}