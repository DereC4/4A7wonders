import java.util.ArrayList;

public class Board 
{
	private int currentAge;
	private boolean onWards; // direction of rotation
	private int currentPlayer;
	private ArrayList<Player> playerList;
	private Deck deck; // Deck class not made yet
	private int Age1CardQuantity;
	private int Age2CardQuantity;
	private int Age3CardQuantity;
	
	
	
	public int totalVP() 
	{
		int vp = 0;
		vp += calcSci();
		ArrayList<Card> temp = playedCards.get("blue");
		for (Card c : temp) 
		{
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) 
			{
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

		temp = playedCards.get("purple");
		for (Card c : temp) 
		{
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) 
			{
				// placeholder, will add later
			}
		}
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