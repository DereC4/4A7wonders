import java.util.ArrayList;
import java.util.TreeMap;

public class Board {
	private int currentAge;
	private boolean onWards; // direction of rotation
	private int currentPlayer;
	private ArrayList<Player> playerList;
	private Deck deck; // Deck class not made yet
	private int Age1CardQuantity;
	private int Age2CardQuantity;
	private int Age3CardQuantity;
	
	
	public int totalVP(Player p) 
	{
		TreeMap<String, ArrayList<Card>>playedCards=p.getPlayedCards();
		int vp = 0;
		vp+=p.getMoney()/3;
		vp += calcSci(p);
		ArrayList<Card> temp = playedCards.get("blue");
		for (Card c : temp) {
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) {
				vp += Integer.parseInt(com[1]);
			}
		}

		temp = playedCards.get("yellow");
		for (Card c : temp) 
		{
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) 
			{
				// placeholder,will add later
			}
		}

		temp = playedCards.get("purple"); //Examples: VP LR blue, VP LRD wonder
		for (Card c : temp) 
		{
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) 
			{
				if (com[1].equals("LR")) { 
					//com 2 is the type of card searching for
					int index=p.getIndex();
					int lower=index--;
					if (lower==-1) {
						lower=playerList.size()-1;
					}
					int upper=index++;
					if (upper==playerList.size()) {
						upper=0;
					}
					Player pl=playerList.get(lower);
					Player p2=playerList.get(upper);
					if (com[2].equals("minusWar")) {
						vp+=pl.getWarMinusPoints();
						vp+=p2.getWarMinusPoints();
					}
					else if(com[2].equals("silver")) {
						ArrayList<Card>te=pl.getPlayedCards().get(com[2]);
						vp+=te.size()*2;
						ArrayList<Card>ta=p2.getPlayedCards().get(com[2]);
						vp+=ta.size()*2;
					}
					else {
						ArrayList<Card>te=pl.getPlayedCards().get(com[2]);
						vp+=te.size();
						ArrayList<Card>ta=p2.getPlayedCards().get(com[2]);
						vp+=ta.size();
					}										
				}
			}
			if (com[1].equals("LRD")) {
				
			}
		}
		return vp;
	}
	
	public int calcSci(Player p) 
	{
		TreeMap<String,Integer>sciList=p.getSciList();
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

	public int getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(int currentAge) {
		this.currentAge = currentAge;
	}

	public boolean isOnWards() {
		return onWards;
	}

	public void setOnWards(boolean onWards) {
		this.onWards = onWards;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getAge1CardQuantity() {
		return Age1CardQuantity;
	}

	public void setAge1CardQuantity(int age1CardQuantity) {
		Age1CardQuantity = age1CardQuantity;
	}

	public int getAge2CardQuantity() {
		return Age2CardQuantity;
	}

	public void setAge2CardQuantity(int age2CardQuantity) {
		Age2CardQuantity = age2CardQuantity;
	}

	public int getAge3CardQuantity() {
		return Age3CardQuantity;
	}

	public void setAge3CardQuantity(int age3CardQuantity) {
		Age3CardQuantity = age3CardQuantity;
	}
}