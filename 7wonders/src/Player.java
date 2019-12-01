import java.util.ArrayList;
import java.util.TreeMap;

public class Player
{
	private int money, warMinusPoints, warPlusPoints, armies, index, vp;
	private TreeMap<String, Boolean> reducedList;
	private TreeMap<String, Integer> sciList;
	private TreeMap<String, ArrayList<Card>> playedCards;
	private ArrayList<Card> hand;
	private Wonder wonder;
	private ArrayList<Resources> resources;
	private Card tempPlayedCard;
	private boolean burnCard;
	
	public Player(int index) 
	{
		vp=0;
		money = 3;
		warMinusPoints = 0;
		warPlusPoints = 0;
		armies = 0;
		reducedList = new TreeMap<String, Boolean>();
		this.index=index;

		reducedList.put("leftR", false); // left Resource
		reducedList.put("leftC", false); // left Commodities
		reducedList.put("rightR", false); // right resources
		reducedList.put("rightC", false); // right commodities

		sciList = new TreeMap<String, Integer>();
		
		resources=new ArrayList<Resources>();

		sciList.put("lit", 0); // tablet
		sciList.put("math", 0); // weird triangle
		sciList.put("gear", 0); // engineer

		playedCards = new TreeMap<String, ArrayList<Card>>();
		hand = new ArrayList<Card>();
	}
	
	public void play(Card c)
	{
		setTempPlayedCard(c);
		getHand().remove(c);
	}
	public boolean isBurnCard() {
		return burnCard;
	}
	public void setBurnCard(boolean burnCard) {
		this.burnCard = burnCard;
	}
	public Card getTempPlayedCard() {
		return tempPlayedCard;
	}
	public void setTempPlayedCard(Card tempPlayedCard) {
		this.tempPlayedCard = tempPlayedCard;
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
	public int getWonderStage()
	{
		return wonder.getCurrentStage();
	}
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	public Wonder getWonder() {
		return wonder;
	}
	public void setWonder(Wonder w) {
		wonder=w;
	}
	public void addVP(int i) {
		vp+=i;
	}
	public ArrayList <Resources> getResources()
	{
		return resources;
	}
	public void addToPlayedCards(Card c) 
	{
		String type = c.getType();
		if (playedCards.containsKey(type)) 
		{
			ArrayList<Card> temp = playedCards.get(type);
			temp.add(c);
			playedCards.put(type, temp);
		} 
		else 
		{
			ArrayList<Card> temp = new ArrayList<Card>();
			temp.add(c);
			playedCards.put(type, temp);
		}
	}
}