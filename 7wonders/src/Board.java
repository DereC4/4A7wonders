import java.util.ArrayList;
import java.util.TreeMap;
public class Board
{
    private int currentAge;
    private boolean onWards; //direction
    private int currentPlayer;
    private ArrayList < Player > playerList;
    private Deck deck; 
    private int Age1CardQuantity;
    private int Age2CardQuantity;
    private int Age3CardQuantity;
    public Board()
    {
//    	for(int i = 0; i < 3; i++)
//    		playerList.add(new Player); //finish later
    }
    public void decodeEffect(Card c, Player p)
    {
        String imports = c.getEffect();
        String[] enigma = imports.split(" ");
        if (enigma[0].equals("VP"))
        {}
        else if (enigma[0].contains("C"))
        {
        	//For C and VPC Cards
            if (enigma[1].equals("D"))
            {
                if (enigma[2].equals("wonder"))
                {
                    int y = p.getWonder().getCurrentStage();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
                else
                {
                    int y = p.getPlayedCards().get(enigma[2]).size();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
            }
            if (enigma[1].equals("LRD"))
            { 
            	//For LRD Cards
                int y = p.getPlayedCards().get(enigma[2]).size();
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                int index = p.getIndex();
                int lower = index--;
                if (lower == -1)
                {
                    lower = playerList.size() - 1;
                }
                int upper = index++;
                if (upper == playerList.size())
                {
                    upper = 0;
                }
                Player p1 = playerList.get(lower);
                Player p2 = playerList.get(upper);
                y = p1.getPlayedCards().get(enigma[2]).size();
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                y = p2.getPlayedCards().get(enigma[2]).size();
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
            }
            if (enigma[1].equals("LR"))
            { 
            	//For LR Cards
                int index = p.getIndex();
                int lower = index--;
                if (lower == -1)
                {
                    lower = playerList.size() - 1;
                }
                int upper = index++;
                if (upper == playerList.size())
                {
                    upper = 0;
                }
                Player p1 = playerList.get(lower);
                Player p2 = playerList.get(upper);
                int y = p1.getPlayedCards().get(enigma[2]).size();
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                y = p2.getPlayedCards().get(enigma[2]).size();
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
            }
        }
        else if (enigma[0].contains("R"))
        {
        	//For Resource/Commodity
            p.addToResources(new Resources(enigma[1]));
            p.addToPlayedCards(c);
            p.getHand().remove(c);
        }
        else if (enigma[0].contains("TP"))
        { 
        	//For Trading Posts
            TreeMap < String, Boolean > rl = p.getReducedList();
            if (enigma[1].contains("R"))
            {
                if (enigma[2].equals("R"))
                {
                    rl.put("rightR", true);
                }
                if (enigma[2].equals("C"))
                {
                    rl.put("rightC", true);
                }
            }
            if (enigma[1].contains("L"))
            {
                if (enigma[2].equals("R"))
                {
                    rl.put("leftR", true);
                }
                if (enigma[2].equals("C"))
                {
                    rl.put("leftC", true);
                }
            }
        }
        else if (enigma[0].equals("WP"))
        {
        	//WarCards
            p.setArmies(p.getArmies() + Integer.parseInt(enigma[1]));
        }
        else if (enigma[0].equals("S"))
        {
            if (enigma[1].equals("All"))
            {
            	 TreeMap < String, Integer > temp = p.getSciList();
                 int x = temp.get("lit") + 1;
                 int y = temp.get("math") + 1;
                 int z = temp.get("git") + 1;
                 temp.put("lit", x);
                 temp.put("math", y);
                 temp.put("git", z);
                 p.setSciList(temp);
            }
            else if (enigma[1].equals("Lit"))
            {
                TreeMap < String, Integer > temp = p.getSciList();
                int i = temp.get("lit") + 1;
                temp.put("lit", i);
                p.setSciList(temp);
            }
            else if (enigma[1].equals("Math"))
            {
                TreeMap < String, Integer > temp = p.getSciList();
                int i = temp.get("math") + 1;
                temp.put("math", i);
                p.setSciList(temp);
            }
            else if (enigma[1].equals("Gear"))
            {
                TreeMap < String, Integer > temp = p.getSciList();
                int i = temp.get("gear") + 1;
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
        TreeMap < String, ArrayList < Card >> playedCards = p.getPlayedCards();
        int vp = 0;
        //adds VP for coins
        vp += p.getMoney() / 3;
        //adds VP for wonders
        for (int i = 1; i <= p.getWonder().getCurrentStage(); i++)
        {
            String effect = p.getWonder().wonderEffect(i);
            String[] com = effect.split(" ");
            if (effect.contains("VP"))
            {
                vp += Integer.parseInt(com[1]);
            }
        }
        
        //adds VP for sci
        vp += calcSci(p);
        //adds VP for war
        vp -= p.getWarMinusPoints();
        vp += p.getWarPlusPoints();
        //adds VP for blue
        ArrayList < Card > temp = playedCards.get("blue"); //Example: VP 5, VP 7
        for (Card c: temp)
        {
            String effect = c.getEffect();
            String[] com = effect.split(" ");
            if (com[0].equals("VP"))
            {
                vp += Integer.parseInt(com[1]);
            }
        }
        //VP for yellow
        temp = playedCards.get("yellow");
        for (Card c: temp)
        {
            String effect = c.getEffect();
            String[] com = effect.split(" ");
            int index = p.getIndex();
            int lower = index--;
            if (lower == -1)
            {
                lower = playerList.size() - 1;
            }
            int upper = index++;
            if (upper == playerList.size())
            {
                upper = 0;
            }
            Player pl = playerList.get(lower);
            Player p2 = playerList.get(upper);
            if (com[0].equals("VP"))
            {
            	//Placeholders
            	if (com[0].equals("wonder")) {
            		if (com[1].equals("LR")) {
            			vp+=pl.getWonder().getCurrentStage();
            			vp+=p2.getWonder().getCurrentStage();
            		}
            		if (com[1].equals("LRD")) {
            			vp+=pl.getWonder().getCurrentStage();
            			vp+=p2.getWonder().getCurrentStage();
            			vp+=p.getWonder().getCurrentStage();
            		}
            	}
            }
        }
        //Vp for guilds
        temp = playedCards.get("purple"); //Examples: VP LR blue, VP LRD wonder, VP LR minusWar
        for (Card c: temp)
        {
            String effect = c.getEffect();
            String[] com = effect.split(" ");
            if (com[0].equals("VP"))
            {
                if (com[1].equals("LR"))
                {
                    //com 2 is the type of card searching for
                    int index = p.getIndex();
                    int lower = index--;
                    if (lower == -1)
                    {
                        lower = playerList.size() - 1;
                    }
                    int upper = index++;
                    if (upper == playerList.size())
                    {
                        upper = 0;
                    }
                    Player pl = playerList.get(lower);
                    Player p2 = playerList.get(upper);
                    if (com[2].equals("minusWar"))
                    {
                        vp += pl.getWarMinusPoints();
                        vp += p2.getWarMinusPoints();
                    }
                    else if (com[2].equals("silver"))
                    {
                        ArrayList < Card > te = pl.getPlayedCards().get(com[2]);
                        vp += te.size() * Integer.parseInt(com[3]);
                        ArrayList < Card > ta = p2.getPlayedCards().get(com[2]);
                        vp += ta.size() * Integer.parseInt(com[3]);
                    }
                    else
                    {
                        ArrayList < Card > te = pl.getPlayedCards().get(com[2]);
                        vp += te.size();
                        ArrayList < Card > ta = p2.getPlayedCards().get(com[2]);
                        vp += ta.size();
                    }
                }
            }
            int index = p.getIndex();
            int lower = index--;
            if (lower == -1)
            {
                lower = playerList.size() - 1;
            }
            int upper = index++;
            if (upper == playerList.size())
            {
                upper = 0;
            }
            Player pl = playerList.get(lower);
            Player p2 = playerList.get(upper);
            if (com[1].equals("LRD"))
            {
                vp += pl.getWonder().getCurrentStage();
                vp += p.getWonder().getCurrentStage();
                vp += p2.getWonder().getCurrentStage();
            }
            if (com[1].equals("S All"))
            {
                TreeMap<String,Integer>sciListL=new TreeMap<String,Integer>();
                TreeMap<String,Integer>sciListM=new TreeMap<String,Integer>();
                TreeMap<String,Integer>sciListG=new TreeMap<String,Integer>();
                
                for (String key:p.getSciList().keySet()) {
                	sciListL.put(key,p.getSciList().get(key));
                	sciListM.put(key,p.getSciList().get(key));
                	sciListG.put(key,p.getSciList().get(key));
                }
                
                int l=p.getSciList().get("lit");
                int m=p.getSciList().get("math");
                int g=p.getSciList().get("gear");
                
                sciListL.put("lit", l+1);
                sciListM.put("math", m+1);
                sciListG.put("gear", g+1);
                
                int pn1=calcSci(sciListL);
                int pn2=calcSci(sciListM);
                int pn3=calcSci(sciListG);
                
                if (pn1>=pn2&&pn1>pn3) {
                	p.getSciList().put("lit", p.getSciList().get("lit")+1);
                }
                if (pn2>pn1&&pn2>pn3) {
                	p.getSciList().put("math", p.getSciList().get("math")+1);
                }
                if (pn3>pn1&&pn3>pn2) {
                	p.getSciList().put("gear", p.getSciList().get("gear")+1);
                }
            }
            if (com[1].equals("D"))
            {
                vp += p.getPlayedCards().get("blue").size();
                vp += p.getPlayedCards().get("silver").size();
                vp += p.getPlayedCards().get("brown").size();
            }
        }
        return vp;
    }
    public int calcSci(Player p)
    {
        TreeMap < String, Integer > sciList = p.getSciList();
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
    
    public int calcSci(TreeMap<String,Integer>tree)
    {
        TreeMap < String, Integer > sciList = tree;
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
    public boolean gameFinished()
    {
        if (currentAge == 3 && playerList.get(0).getHand().size() == 0 && playerList.get(1).getHand().size() == 0 && playerList.get(2).getHand().size() == 0)
        {
            return true;
        }
        return false;
    }
    public int getCurrentAge()
    {
        return currentAge;
    }
    public void setCurrentAge(int currentAge)
    {
        this.currentAge = currentAge;
    }
    public boolean isOnWards()
    {
        return onWards;
    }
    public void setOnWards(boolean onWards)
    {
        this.onWards = onWards;
    }
    public Player getCurrentPlayer()
    {
        return playerList.get(currentPlayer);
    }
    public void setCurrentPlayer(int currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
    public ArrayList < Player > getPlayerList()
    {
        return playerList;
    }
    public void setPlayerList(ArrayList < Player > playerList)
    {
        this.playerList = playerList;
    }
    public Deck getDeck()
    {
        return deck;
    }
    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }
    public int getAge1CardQuantity()
    {
        return Age1CardQuantity;
    }
    public void setAge1CardQuantity(int age1CardQuantity)
    {
        Age1CardQuantity = age1CardQuantity;
    }
    public int getAge2CardQuantity()
    {
        return Age2CardQuantity;
    }
    public void setAge2CardQuantity(int age2CardQuantity)
    {
        Age2CardQuantity = age2CardQuantity;
    }
    public int getAge3CardQuantity()
    {
        return Age3CardQuantity;
    }
    public void setAge3CardQuantity(int age3CardQuantity)
    {
        Age3CardQuantity = age3CardQuantity;
    }
}