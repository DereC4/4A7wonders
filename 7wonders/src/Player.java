import java.util.ArrayList;
import java.util.TreeMap;

public class Player 
{
	private int money, warMinusPoints, warPlusPoints, armies;
	private TreeMap<String, Boolean> reducedList;
	private TreeMap<String, Integer> sciList;
	private TreeMap<String, ArrayList<Card>> playedCards;
	private ArrayList<Card> hand;

	public Player() 
	{
		money = 3;
		warMinusPoints = 0;
		warPlusPoints = 0;
		armies = 0;
		reducedList = new TreeMap<String, Boolean>();

		reducedList.put("leftR", false); // left Resource
		reducedList.put("leftC", false); // left Commodities
		reducedList.put("rightR", false); // right resources
		reducedList.put("rightC", false); // right commodities

		sciList = new TreeMap<String, Integer>();

		sciList.put("lit", 0); // tablet
		sciList.put("math", 0); // weird triangle
		sciList.put("gear", 0); // engineer

		playedCards = new TreeMap<String, ArrayList<Card>>();
		hand = new ArrayList<Card>();
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

	public int calcSci() 
	{
		int vp = 0;
		int s1 = sciList.get("lit");
		int s2 = sciList.get("math");
		int s3 = sciList.get("gear");
		vp += Math.pow(s1, 2);
		vp += Math.pow(s2, 2);
		vp += Math.pow(s3, 2);
		vp += (Math.min(Math.min(s1, s2), s3) * 7);
		return vp;
	}

}