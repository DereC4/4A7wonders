import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import static java.lang.System.out;

public class Board
{
    private int currentAge;
    private boolean onWards; // direction
    private int currentPlayer;
    private ArrayList <Player> playerList;
    private Deck deck;
	private int Age1CardQuantity;
    private int Age2CardQuantity;
    private boolean drawDiscard;
    
    private int Age3CardQuantity;
    
    public Board() throws IOException
    {
    	deck = new Deck();
    	playerList = new ArrayList < Player > ();
    	
        for (int i = 0; i < 3; i++) 
        	playerList.add(new Player(i));
        
        deal(1);
        ArrayList<Wonder>WonderList=new ArrayList<Wonder>();
        
        for (String s:Wonder.WONDERS)
        {
        	WonderList.add(new Wonder(s));
        }
        
        for (int i=0;i < playerList.size(); i++)
        {
        	int index = (int) (Math.random()*WonderList.size());
        	playerList.get(i).setWonder(WonderList.remove(index));
        	playerList.get(i).addToResources(playerList.get(i).getWonder().getProduct());
        } 
        currentAge = 1;
        onWards = true;
        currentPlayer = 0; // players are 0,1,2
        drawDiscard = false;
    }
    
    public void decodeWonderEffect(String effect)
    {
    	if (effect.equals("VP 3"))
    	{
    		getCurrentPlayer().addVP(3);
    	}
    	else if (effect.equals("VP 7"))
    	{
    		getCurrentPlayer().addVP(7);
    	}
    	else if (effect.equals("VP 5")) //Call right before calc VP
		{
			getCurrentPlayer().addVP(5);
		}
    	else
    	{
    		Player p = getCurrentPlayer();
    		if (effect.equals("WP 2"))
    		{
    			p.setArmies(getCurrentPlayer().getArmies() + 2);
    		}
    		else if (effect.equals("resourceAll"))
    		{
    			p.addToResources(new Resources("Clay/Ore/Wood/Stone"));
    		}
    		else if (effect.equals("scienceAll")) //call at end of game right before VP calc
    		{
    			TreeMap<String,Integer> sciListL = new TreeMap<String,Integer>();
    			TreeMap<String,Integer> sciListM = new TreeMap<String,Integer>();
    			TreeMap<String,Integer> sciListG = new TreeMap<String,Integer>();
                for (String key: p.getSciList().keySet())
                {
                    sciListL.put(key, p.getSciList().get(key));
                    sciListM.put(key, p.getSciList().get(key));
                    sciListG.put(key, p.getSciList().get(key));
                }
                int l = p.getSciList().get("lit");
                int m = p.getSciList().get("math");
                int g = p.getSciList().get("gear");
                sciListL.put("lit", l + 1);
                sciListM.put("math", m + 1);
                sciListG.put("gear", g + 1);
                int lit = calcSci(sciListL);
                int math = calcSci(sciListM);
                int gear = calcSci(sciListG);
                if (lit >= math && lit > gear)
                {
                    p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                }
                else if (math > lit && math > gear)
                {
                    p.getSciList().put("math", p.getSciList().get("math") + 1);
                }
                else if (gear > lit && gear > math)
                {
                    p.getSciList().put("gear", p.getSciList().get("gear") + 1);
                }
    		}
    		else if (effect.equals("C 9"))
    		{
    			getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + 9);
    		}
    		else if (effect.equals("ignoreCost")) //call be used once per age
    		{
    			getCurrentPlayer().setIgnoreCost(true);
    		}
    		else if (effect.equals("drawDiscard")) //call once at the end of turn (not age)
    		{
    			setDrawDiscard(true);
    		}
    	}
    }
    
    public void decodeEffect(Card c, Player p)
    {
        String imports = c.getEffect();
        String[] enigma = imports.split(" ");
        if (enigma[0].equals("VP"))
        {}
        else if (enigma[0].contains("C"))
        {
            // For C and VPC Cards
            if (enigma[1].equals("D")) //down
            {
                if (enigma[2].equalsIgnoreCase("Wonder"))
                {
                    int y = p.getWonder().getCurrentStage();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
                else //Lighthouse
                {
                    int y = p.getPlayedCards().get(enigma[2]).size();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
            }
            if (enigma[1].equals("LRD")) //vineyard
            {
                // For LRD Cards
                int y = p.getPlayedCards().get(enigma[2]).size(); //enigma[2] is a card color
                p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                int index = p.getIndex();
                int lower = index-1;
                if (lower == -1)
                {
                    lower = playerList.size() - 1;
                }
                int upper = index+1;
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
                // For LR Cards
                int index = p.getIndex();
                int lower = index-1;
                if (lower == -1)
                {
                    lower = playerList.size() - 1;
                }
                int upper = index+1;
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
            // For Resource/Commodity
            p.addToResources(new Resources(enigma[1]));
        }
        else if (enigma[0].contains("TP"))
        {
            // For Trading Posts
            TreeMap < String, Boolean > rl = p.getReducedList();
            if (enigma[1].contains("R")) //right
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
            // WarCards
            p.setArmies(p.getArmies() + Integer.parseInt(enigma[1]));
        }
        else if (enigma[0].equals("S"))
        {
            if (enigma[1].equals("All"))
            {
                TreeMap<String,Integer> temp = p.getSciList();
                int x = temp.get("lit") + 1; //ISSUE
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
        // Removes Card from hand
        p.addToPlayedCards(c);
    }
    
    public int totalVP(Player p)
    {
        TreeMap < String, ArrayList < Card >> playedCards = p.getPlayedCards();
        int vp = 0;
        // adds VP for coins
        vp += p.getMoney() / 3;
        // adds VP for wonders
        for (int i = 1; i <= p.getWonder().getCurrentStage(); i++)
        {
            String effect = p.getWonder().getEffect(i);
            String[] com = effect.split(" ");
            if (effect.contains("VP"))
            {
                vp += Integer.parseInt(com[1]);
            }
        }
        // adds VP for sci
        vp += calcSci(p);
        // adds VP for war
        vp -= p.getWarMinusPoints();
        vp += p.getWarPlusPoints();
        // adds VP for blue
        ArrayList < Card > temp = playedCards.get("blue"); // Example: VP 5, VP 7
        for (Card c: temp)
        {
            String effect = c.getEffect();
            String[] com = effect.split(" ");
            if (com[0].equals("VP"))
            {
                vp += Integer.parseInt(com[1]);
            }
        }
        // VP for yellow
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
                if (com[0].equals("wonder"))
                {
                    //incomplete, fix later
                    if (com[1].equals("LR"))
                    {
                        vp += pl.getWonder().getCurrentStage();
                        vp += p2.getWonder().getCurrentStage();
                    }
                    if (com[1].equals("LRD"))
                    {
                        vp += pl.getWonder().getCurrentStage();
                        vp += p2.getWonder().getCurrentStage();
                        vp += p.getWonder().getCurrentStage();
                    }
                }
                else
                {
                    if (com[1].equals("D"))
                    {
                        vp += p.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                    }
                    if (com[1].equals("LR"))
                    {
                        vp += pl.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                        vp += p2.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                    }
                    if (com[1].equals("D"))
                    {
                        vp += p.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                        vp += pl.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                        vp += p2.getPlayedCards().get(com[2]).size() * Integer.parseInt(com[3]);
                    }
                }
            }
        }
        // Vp for guilds
        temp = playedCards.get("purple"); // Examples: VP LR blue, VP LRD wonder, VP LR minusWar
        for (Card c: temp)
        {
            String effect = c.getEffect();
            String[] com = effect.split(" ");
            if (com[0].equals("VP"))
            {
                if (com[1].equals("LR"))
                {
                    // com 2 is the type of card searching for
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
                TreeMap < String, Integer > sciListL = new TreeMap < String, Integer > ();
                TreeMap < String, Integer > sciListM = new TreeMap < String, Integer > ();
                TreeMap < String, Integer > sciListG = new TreeMap < String, Integer > ();
                for (String key: p.getSciList().keySet())
                {
                    sciListL.put(key, p.getSciList().get(key));
                    sciListM.put(key, p.getSciList().get(key));
                    sciListG.put(key, p.getSciList().get(key));
                }
                int l = p.getSciList().get("lit");
                int m = p.getSciList().get("math");
                int g = p.getSciList().get("gear");
                sciListL.put("lit", l + 1);
                sciListM.put("math", m + 1);
                sciListG.put("gear", g + 1);
                int lit = calcSci(sciListL);
                int math = calcSci(sciListM);
                int gear = calcSci(sciListG);
                if (lit >= math && lit > gear)
                {
                    p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                }
                else if (math > lit && math > gear)
                {
                    p.getSciList().put("math", p.getSciList().get("math") + 1);
                }
                else if (gear > lit && gear > math)
                {
                    p.getSciList().put("gear", p.getSciList().get("gear") + 1);
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
    
    public int calcSci(TreeMap < String, Integer > tree)
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
    
    public void deal(int age)
    {
        ArrayList<Card> d;
        d = new ArrayList < Card > ();
        if (age == 1)
        {
        	d = deck.getAgeOne();
        }
        else if (age == 2)
        {
            d = deck.getAgeTwo();
        }
        else if (age == 3)
        {
            d = deck.getAgeThree();
        }
        
        for (int i = 0; i < playerList.size(); i++)
        {
            for (int j = 6; j >= 0; j--)
            {
            	Card temp = d.remove(j);
                playerList.get(i).addToHand(temp);
            }
        }
    }
    
    public void incrementPlayerLocation()
    {
        int l = currentPlayer;
        l++;
        if (l == 3)
        {
            l = 0;
        }
        currentPlayer = l;
    }
    
    public void incrementHandLocations()
    {
    	int lower = currentPlayer-1;
    	int higher = currentPlayer+1;
    	if (lower - 1 < 0)
    		lower = 2;
    	if (higher + 1 > 2)
    		higher = 0;
    	ArrayList<Card> currentPlayerHand = getCurrentPlayer().getHand();
    	ArrayList<Card> lowerPlayerHand = getPlayerList().get(lower).getHand();
    	ArrayList<Card> higherPlayerHand = getPlayerList().get(higher).getHand();
    	
    	if (onWards)
    	{
    		getPlayerList().get(lower).setHand(higherPlayerHand);
    		getPlayerList().get(currentPlayer).setHand(lowerPlayerHand);
    		getPlayerList().get(higher).setHand(currentPlayerHand);
    	}
    	else
    	{
    		getPlayerList().get(lower).setHand(currentPlayerHand);
    		getPlayerList().get(currentPlayer).setHand(higherPlayerHand);
    		getPlayerList().get(higher).setHand(lowerPlayerHand);
    	}
    }
    
    public boolean playable(Card c)
    {
    	if (c == null)
    		return false;
    	
    	Player p = getCurrentPlayer();
    	TreeMap <String, ArrayList<Card>> played = playerList.get(currentPlayer).getPlayedCards();
    	
        for (String key : played.keySet())
        {
        	ArrayList<Card> temp = played.get(key);
        	if (temp.contains(c))
        	{
        		out.println("Card of same name has been played");
        		return false; //has card been played
        	}
        }
        
        if (p.isIgnoreCost())
        {
        	return true;
        }
        
        if (c.isFree()) //is card free
        {
            return true;
        }
        
        for (String s: played.keySet()) //does card chain off another card
        {
            for (Card i: played.get(s))
            {
                if (i.getName().equals(c.getChain()))
                    return true;
            }
        }
        
        if (c.getCost().toString().contains("C 1")) //does card have coin cost
        {
        	if (getCurrentPlayer().getMoney() >= 1)
        		return true;
        }
        
        //Resource check
        ArrayList<Resources> tempR = playerList.get(currentPlayer).getResources(); //Player's resources
        ArrayList<Resources> cost = c.getCost(); //card cost
        //ArrayList<Resources> costRemove=cost;
        ArrayList<Resources> resources = new ArrayList<Resources>();
        
        for (int i = 0; i < tempR.size(); i++)
        	resources.add(tempR.get(i));
        tempR = null;
        
        for (int i = cost.size()-1; i >= 0; i--)
        {
        	for (int j = resources.size()-1; j >= 0; j--)
        	{
        		if (resources.get(j).toString().contains(cost.get(i).toString())) 
        		{
        			out.println(cost.get(i).toString());
        			out.println(resources.get(j).toString());
        			cost.remove(i);
        			resources.remove(j);
        			break;
        		}
        	}
        }
        //System.out.println(cost);
        //System.out.println(resources);
        
        if (cost.size()==0) //if player already has resources
        {
        	return true;
        }
        else
        {
        	TreeMap<Integer, ArrayList<Resources>> trade = getCurrentPlayer().getTrade();
        	int costLeft = 0;
        	int costRight = 0;
        	for (Resources r : cost)
        	{
				int lower = currentPlayer-1;
	            if (lower < 0) 
	            {
	                lower = 2;
	            }
	            int higher = currentPlayer+1;
	            if (higher > 2) 
	            {
	                higher = 0;
	            }
	            ArrayList<Resources> leftResources = playerList.get(lower).getResources();
	            ArrayList<Resources> rightResources = playerList.get(higher).getResources();
	            //System.out.println("Left: " + leftResources);
	            //System.out.println("Right " + rightResources);
	            
	           
	            if (!leftResources.contains(r) && !rightResources.contains(r))
                {
	            	out.println("Don't have resources");
                    return false;
                }
                else if (leftResources.contains(r) && rightResources.contains(r))
                {
                	int tempCostLeft = determineCost(r, false, currentPlayer);
    	            int tempCostRight = determineCost(r, true, currentPlayer);
    	            
                	if (tempCostLeft <= tempCostRight)
                	{
                		costLeft += tempCostLeft;
                		if (trade.get(lower) == null)
                		{
                			ArrayList<Resources> tempList = new ArrayList<>();
                			tempList.add(r);
                			trade.put(lower, tempList);
                		}
                		else
                		{
                			ArrayList<Resources> tempList = trade.get(lower);
                			tempList.add(r);
                			trade.put(lower, tempList);
                		}
                	}
                	else
                	{
                		costRight += tempCostRight;
                		if (trade.get(higher) == null)
                		{
                			ArrayList<Resources> tempList = new ArrayList<>();
                			tempList.add(r);
                			trade.put(higher, tempList);
                		}
                		else
                		{
                			ArrayList<Resources> tempList = trade.get(higher);
                			tempList.add(r);
                			trade.put(higher, tempList);
                		}
                	}
                }
                else if (leftResources.contains(r))
                {
                    costLeft += determineCost(r, false, currentPlayer);
                    if (trade.get(lower) == null)
            		{
            			ArrayList<Resources> tempList = new ArrayList<>();
            			tempList.add(r);
            			trade.put(lower, tempList);
            		}
            		else
            		{
            			ArrayList<Resources> tempList = trade.get(lower);
            			tempList.add(r);
            			trade.put(lower, tempList);
            		}
                }
                else
                {
                	costRight += determineCost(r, true, currentPlayer);
                	if (trade.get(higher) == null)
            		{
            			ArrayList<Resources> tempList = new ArrayList<>();
            			tempList.add(r);
            			trade.put(higher, tempList);
            		}
            		else
            		{
            			ArrayList<Resources> tempList = trade.get(higher);
            			tempList.add(r);
            			trade.put(higher, tempList);
            		}
                }
        	}
        	
	        if (playerList.get(currentPlayer).getMoney() >= costLeft + costRight)
	        {
	        	//System.out.println("Has Enough Money");
	            return true;
	        }
	        //out.println("Don't have enough money");
	        playerList.get(currentPlayer).getTrade().clear(); 
	        return false;
        }
    }
    
    public void payCosts(int id) //Pays coins costs
    {
    	Player p = getPlayerList().get(id);
    	
    	if (p.isIgnoreCost())
    	{
    		p.setIgnoreCost(false);
    	}
    	else if (p.getTrade().size() == 0)
    	{
    		Card temp = p.getTempPlayedCards().get(0);
    		if (temp.getCost().toString().contains("C 1"))
    		{
    			p.setMoney(p.getMoney() - 1);
    		}
    	}
    	else
    	{
    		//out.println(p.getTrade());
    		for (int index : p.getTrade().keySet())
    		{
    			Player toTrade = getPlayerList().get(index);
    			ArrayList<Resources> resources = p.getTrade().get(index);
    			//out.println(resources);
    			for (int i = 0; i < resources.size(); i++)
    			{
    				trade(p, toTrade, resources.get(i));
    				//out.println("Done!");
    			}
    			resources.clear();
    		}
    	}
    }
    
    public int determineCost(Resources r, boolean isRight, int index)
    {
        Player p = playerList.get(index);
        // returns coin cost for a resource
        if (isRight)
        {
            if (r.isR()) //if is resource
            {
                if (p.getReducedList().get("rightR"))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
            else
            {
                if (p.getReducedList().get("rightC"))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
        }
        else
        {
            if (r.isR())
            {
                if (p.getReducedList().get("leftR"))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
            else
            {
                if (p.getReducedList().get("leftC"))
                {
                    return 1;
                }
                else
                {
                    return 2;
                }
            }
        }
    }
    
    public void trade(Player p1, Player p2, Resources r)
    {
    	int index = p1.getIndex();
    	int temp = p2.getIndex();
    	boolean isRight;
    	
    	if (index == 0) 
    	{
    		if (temp == 1)
    			isRight = true;
    		else
    			isRight = false;
    	}
    	else if (index == 1)
    	{
    		if (temp == 2)
    			isRight = true;
    		else
    			isRight = false;
    	}
    	else
    	{
    		if (temp == 0)
    			isRight = true;
    		else 
    			isRight = false;
    	}
    	int cost = determineCost(r, isRight, p1.getIndex());
    	p1.setMoney(p1.getMoney() - cost);
    	p2.setMoney(p2.getMoney() + cost);
    }
    
    public boolean ageOver()
    {
    	ArrayList<Player> players = getPlayerList();
    	for (int i = 0; i < players.size(); i++)
    		if (players.get(i).getHand().size() > 1)
    			return false;
    	return true;
    }
    
    public void calcWarPoints()
    {
    	ArrayList<Player> players = getPlayerList();
    	for (int i = 0; i < players.size(); i++)
    	{
    		int lower = i-1;
    		int current = i;
        	int higher = i+1;
        	if (lower - 1 < 0)
        		lower = 2;
        	if (higher + 1 > 2)
        		higher = 0;
        	Player currentP = players.get(current);
        	Player lowerP = players.get(lower);
        	Player higherP = players.get(higher);
        	
        	if (lowerP.getArmies() > currentP.getArmies() || higherP.getArmies() > currentP.getArmies())
        	{
        		currentP.setWarMinusPoints(currentP.getWarMinusPoints()+1);
        	}
        	else
        	{
        		if (getCurrentAge() == 1)
        			currentP.setWarPlusPoints(currentP.getWarMinusPoints()+1);
        		else if (getCurrentAge() == 2)
        			currentP.setWarPlusPoints(currentP.getWarMinusPoints()+3);
        		else
        			currentP.setWarPlusPoints(currentP.getWarMinusPoints()+5);
        	}
    	}
    }
    
    public void buildWonder(Player p, int stage, Card card)
	{ 
    	Player player = p;
    	Wonder wonder = player.getWonder();
		int currentStage = wonder.getCurrentStage();
		if (currentStage == 3)
			return;
		else if (stage <= currentStage)
			return;
		else if (stage > currentStage + 1)
			return;
		
		ArrayList<Resources> cost = new ArrayList<Resources>();
		ArrayList<Resources> resources = new ArrayList<Resources>(); //local copy of resources
		for (Resources r : player.getResources())
			resources.add(r);
		
		String temp = wonder.getCost(stage);
		String[] stageCost = temp.split(",");
		for (int i = 0; i < stageCost.length; i++)
			cost.add(new Resources(stageCost[i]));
			
		for (int j = resources.size()-1; j >= 0; j--)
		{
			for (int i = cost.size()-1; i >= 0; i--)
			{
				if (resources.get(j).toString().contains(cost.get(i).toString()))
				{
					resources.remove(j);
					cost.remove(i);
					break;
				}
			}
		}
		
		if (cost.size() == 0) //Player has all necessary resources
		{
			wonder.setCurrentStage(stage);
			player.getHand().remove(card);
			player.setBuildWonder(false);
		}
		else
		{
			TreeMap<Integer, ArrayList<Resources>> trade = player.getTrade();
        	int costLeft = 0;
        	int costRight = 0;
        	for (Resources r : cost)
        	{
				int lower = currentPlayer-1;
	            if (lower < 0) 
	            {
	                lower = 2;
	            }
	            int higher = currentPlayer+1;
	            if (higher > 2) 
	            {
	                higher = 0;
	            }
	            ArrayList<Resources> leftResources = playerList.get(lower).getResources();
	            ArrayList<Resources> rightResources = playerList.get(higher).getResources();
	            //System.out.println("Left: " + leftResources);
	            //System.out.println("Right " + rightResources);
	            
	           
	            if (!leftResources.contains(r) && !rightResources.contains(r))
                {
	            	out.println("Don't have resources");
                    return;
                }
                else if (leftResources.contains(r) && rightResources.contains(r))
                {
                	int tempCostLeft = determineCost(r, false, currentPlayer);
    	            int tempCostRight = determineCost(r, true, currentPlayer);
    	            
                	if (tempCostLeft <= tempCostRight)
                	{
                		costLeft += tempCostLeft;
                		if (trade.get(lower) == null)
                		{
                			ArrayList<Resources> tempList = new ArrayList<>();
                			tempList.add(r);
                			trade.put(lower, tempList);
                		}
                		else
                		{
                			ArrayList<Resources> tempList = trade.get(lower);
                			tempList.add(r);
                			trade.put(lower, tempList);
                		}
                	}
                	else
                	{
                		costRight += tempCostRight;
                		if (trade.get(higher) == null)
                		{
                			ArrayList<Resources> tempList = new ArrayList<>();
                			tempList.add(r);
                			trade.put(higher, tempList);
                		}
                		else
                		{
                			ArrayList<Resources> tempList = trade.get(higher);
                			tempList.add(r);
                			trade.put(higher, tempList);
                		}
                	}
                }
                else if (leftResources.contains(r))
                {
                    costLeft += determineCost(r, false, currentPlayer);
                    if (trade.get(lower) == null)
            		{
            			ArrayList<Resources> tempList = new ArrayList<>();
            			tempList.add(r);
            			trade.put(lower, tempList);
            		}
            		else
            		{
            			ArrayList<Resources> tempList = trade.get(lower);
            			tempList.add(r);
            			trade.put(lower, tempList);
            		}
                }
                else
                {
                	costRight += determineCost(r, true, currentPlayer);
                	if (trade.get(higher) == null)
            		{
            			ArrayList<Resources> tempList = new ArrayList<>();
            			tempList.add(r);
            			trade.put(higher, tempList);
            		}
            		else
            		{
            			ArrayList<Resources> tempList = trade.get(higher);
            			tempList.add(r);
            			trade.put(higher, tempList);
            		}
                }
        	}
        	
	        if (playerList.get(currentPlayer).getMoney() >= costLeft + costRight)
	        {
	        	//System.out.println("Has Enough Money");
				wonder.setCurrentStage(stage);
				player.getHand().remove(card);
				player.setBuildWonder(false);
	        }
		}
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
    public int getCurrentTurn()
    {
    	return currentPlayer;
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
    public boolean isDrawDiscard() 
    {
		return drawDiscard;
	}

	public void setDrawDiscard(boolean drawDiscard) 
	{
		this.drawDiscard = drawDiscard;
	}

	public void setAge3CardQuantity(int age3CardQuantity)
    {
        Age3CardQuantity = age3CardQuantity;
    }
    public Deck getDeck() 
    {
		return deck;
	}
	public void setDeck(Deck deck) 
	{
		this.deck = deck;
	}
}
