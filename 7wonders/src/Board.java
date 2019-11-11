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
	
	public void decodeEffect(Card c, Player p) {
		String effect=c.getEffect();
		String[] com=effect.split(" ");
		if (com[0].equals("VP")) { //Only VP Cards
		}
		else if (com[0].contains("C")) { //For C and VPC Cards
			if (com[1].equals("D")) {
				if (com[2].equals("wonder")) {
					int y=p.getWonder().getCurrentStage();
					p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
				}
				else {
					int y=p.getPlayedCards().get(com[2]).size();
					p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
				}
			}
			if (com[1].equals("LRD")) { //For LRD Cards
				int y=p.getPlayedCards().get(com[2]).size();
				p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
				int index=p.getIndex();
				int lower=index--;
				if (lower==-1) {
					lower=playerList.size()-1;
				}
				int upper=index++;
				if (upper==playerList.size()) {
					upper=0;
				}
				Player p1=playerList.get(lower);
				Player p2=playerList.get(upper);
				y=p1.getPlayedCards().get(com[2]).size();
				p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
				y=p2.getPlayedCards().get(com[2]).size();
				p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
			}
			if (com[1].equals("LR")) { //For LR Cards
				int index=p.getIndex();
				int lower=index--;
				if (lower==-1) {
					lower=playerList.size()-1;
				}
				int upper=index++;
				if (upper==playerList.size()) {
					upper=0;
				}
				Player p1=playerList.get(lower);
				Player p2=playerList.get(upper);
				int y=p1.getPlayedCards().get(com[2]).size();
				p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
				y=p2.getPlayedCards().get(com[2]).size();
				p.setMoney(p.getMoney()+y*Integer.parseInt(com[3]));
			}
		}
		else if (com[0].contains("R")) { //For Resource/Commodity
			p.addToResources(new Resources(com[1]));
			p.addToPlayedCards(c);
			p.getHand().remove(c);
		}
		else if (com[0].contains("TP")) { //For Trading Posts
			TreeMap<String,Boolean>rl=p.getReducedList();
			if (com[1].contains("R")) {
				if (com[2].equals("R")) {
					rl.put("rightR",true );
				}
				if (com[2].equals("C")) {
					rl.put("rightC",true );
				}
			}
			if (com[1].contains("L")) {
				if (com[2].equals("R")) {
					rl.put("leftR",true );
				}
				if (com[2].equals("C")) {
					rl.put("leftC",true );
				}
			}
		}
		else if (com[0].equals("WP")) { //WarCards
			p.setArmies(p.getArmies()+Integer.parseInt(com[1]));
		}
		else if(com[0].equals("S")) {
			if (com[1].equals("All")) {
				//placeholder
			}
			else if (com[1].equals("Lit")) {
				TreeMap<String,Integer>temp=p.getSciList();
				int i=temp.get("lit")+1;
				temp.put("lit", i);
				p.setSciList(temp);
			}
			else if (com[1].equals("Math")) {
				TreeMap<String,Integer>temp=p.getSciList();
				int i=temp.get("math")+1;
				temp.put("math", i);
				p.setSciList(temp);
			}
			else if (com[1].equals("Gear")) {
				TreeMap<String,Integer>temp=p.getSciList();
				int i=temp.get("gear")+1;
				temp.put("gear", i);
				p.setSciList(temp);
			}
		}
		
		//Removes Card from hand
		p.addToPlayedCards(c);
		p.getHand().remove(c);
	}
	public int totalVP(Player p) 
	{
		TreeMap<String, ArrayList<Card>>playedCards=p.getPlayedCards();
		int vp = 0;
		
		//adds VP for coins
		vp+=p.getMoney()/3;
		
		//adds VP for wonders
		for (int i=1;i<=p.getWonder().getCurrentStage();i++) {
			String effect=p.getWonder().wonderEffect(i);
			String[] com=effect.split(" ");
			if (effect.contains("VP")) {
				vp+=Integer.parseInt(com[1]);
			}
		}
		
		//adds VP for sci
		vp += calcSci(p);
		
		//adds VP for war
		vp-=p.getWarMinusPoints();
		vp+=p.getWarPlusPoints();
		
		//adds VP for blue
		ArrayList<Card> temp = playedCards.get("blue"); //Example: VP 5, VP 7
		for (Card c : temp) {
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) {
				vp += Integer.parseInt(com[1]);
			}
		}

		//VP for yellow
		temp = playedCards.get("yellow");
		for (Card c : temp) 
		{
			String effect = c.getEffect();
			String[] com = effect.split(" ");
			if (com[0].equals("VP")) 
			{
				// placeholder,will add later
				// ifs for VP and VPC
			}
		}

		//Vp for guilds
		temp = playedCards.get("purple"); //Examples: VP LR blue, VP LRD wonder, VP LR minusWar
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
						vp+=te.size()*Integer.parseInt(com[3]);
						ArrayList<Card>ta=p2.getPlayedCards().get(com[2]);
						vp+=ta.size()*Integer.parseInt(com[3]);
					}
					else {
						ArrayList<Card>te=pl.getPlayedCards().get(com[2]);
						vp+=te.size();
						ArrayList<Card>ta=p2.getPlayedCards().get(com[2]);
						vp+=ta.size();
					}										
				}
			}
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
			if (com[1].equals("LRD")) {
				vp+=pl.getWonder().getCurrentStage();
				vp+=p.getWonder().getCurrentStage();
				vp+=p2.getWonder().getCurrentStage();
			}
			if (com[1].equals("S All")) {
				//placeholders, change later
			}
			if (com[1].equals("D")){
				vp+=p.getPlayedCards().get("blue").size();
				vp+=p.getPlayedCards().get("silver").size();
				vp+=p.getPlayedCards().get("brown").size();
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