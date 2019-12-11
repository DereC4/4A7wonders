import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
public class Board 
{
    private int currentAge;
    private boolean onWards; // direction
    private int currentPlayer;
    private ArrayList < Player > playerList;
    private Deck deck;
    private int Age1CardQuantity;
    private int Age2CardQuantity;
    private boolean drawDiscard;
    private int Age3CardQuantity;
    public Board() throws IOException {
        deck = new Deck();
        playerList = new ArrayList < Player > ();
        currentAge = 1;
        onWards = true;
        currentPlayer = 0; // players are 0,1,2
        drawDiscard = false;
        for (int i = 0; i < 3; i++) playerList.add(new Player(i));
        deal(currentAge);
        ArrayList < Wonder > WonderList = new ArrayList < Wonder > ();
        for (String s: Wonder.WONDERS) {
            WonderList.add(new Wonder(s));
        }
        for (int i = 0; i < playerList.size(); i++) {
            int index = (int)(Math.random() * WonderList.size());
            playerList.get(i).setWonder(WonderList.remove(index));
            playerList.get(i).addToResources(playerList.get(i).getWonder().getProduct());
        }
    }
    public void decodeWonderEffect(String effect) {
        if (effect.equalsIgnoreCase("VP 3")) {
            getCurrentPlayer().addVP(3);
        } else if (effect.equalsIgnoreCase("VP 7")) {
            getCurrentPlayer().addVP(7);
        } else if (effect.equalsIgnoreCase("VP 5")) // Call right before calc VP
        {
            getCurrentPlayer().addVP(5);
        } else {
            Player p = getCurrentPlayer();
            if (effect.equalsIgnoreCase("WP 2")) {
                p.setArmies(getCurrentPlayer().getArmies() + 2);
            } else if (effect.equalsIgnoreCase("resourceAll")) {
                p.addToResources(new Resources("Clay/Ore/Wood/Stone"));
            } else if (effect.equalsIgnoreCase("scienceAll")) // call at end of game right before VP calc //broken
            {
            	p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                /*TreeMap < String, Integer > sciListL = new TreeMap < String, Integer > ();
                TreeMap < String, Integer > sciListM = new TreeMap < String, Integer > ();
                TreeMap < String, Integer > sciListG = new TreeMap < String, Integer > ();
                for (String key: p.getSciList().keySet()) {
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
                if (lit >= math && lit > gear) {
                    p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                } else if (math > lit && math > gear) {
                    p.getSciList().put("math", p.getSciList().get("math") + 1);
                } else if (gear > lit && gear > math) {
                    p.getSciList().put("gear", p.getSciList().get("gear") + 1);
                }
            } else if (effect.equalsIgnoreCase("C 9")) {
                getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + 9);
            } else if (effect.equalsIgnoreCase("ignoreCost")) // call be used once per age
            {
                getCurrentPlayer().setIgnoreCost(true);
            */
            } else if (effect.equalsIgnoreCase("drawDiscard")) // call once at the end of turn (not age)
            {
                setDrawDiscard(true);
            }
        }
    }
    public void decodeEffect(Card c, Player p) {
        if (c != null) {
            String imports = c.getEffect();
            String[] enigma = imports.split(" ");
            if (enigma[0].equalsIgnoreCase("VP")) {} else if (enigma[0].contains("C")) {
                // For C and VPC Cards
                if (enigma[1].equals("D")) // down
                {
                    if (enigma[2].equalsIgnoreCase("Wonder")) // dispen
                    {
                        int y = p.getWonder().getCurrentStage();
                        p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                    } else // Lighthouse
                    {
                        ArrayList < Card > list = p.getPlayedCards().get(enigma[2]);
                        int y = 0;
                        if (list != null) {
                            y = list.size();
                        } else {
                            y = 0;
                        }
                        p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                    }
                }
                if (enigma[1].equalsIgnoreCase("LRD")) // vineyard
                {
                    // For LRD Cards\
                    ArrayList < Card > test = p.getPlayedCards().get(enigma[2]);
                    int y = 0;
                    if (test == null) {
                        y = 0;
                    } else {
                        y = test.size();
                    }
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                    int index = p.getIndex();
                    int lower = index - 1;
                    if (lower == -1) {
                        lower = playerList.size() - 1;
                    }
                    int upper = index + 1;
                    if (upper == playerList.size()) {
                        upper = 0;
                    }
                    Player p1 = playerList.get(lower);
                    Player p2 = playerList.get(upper);
                    ArrayList < Card > t = p1.getPlayedCards().get(enigma[2]);
                    y = 0;
                    if (t != null) {
                        y = t.size();
                    }
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                    t = p2.getPlayedCards().get(enigma[2]);
                    y = 0;
                    if (t != null) {
                        y = t.size();
                    }
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
                if (enigma[1].equalsIgnoreCase("LR")) {
                    // For LR Cards
                    int index = p.getIndex();
                    int lower = index - 1;
                    if (lower == -1) {
                        lower = playerList.size() - 1;
                    }
                    int upper = index + 1;
                    if (upper == playerList.size()) {
                        upper = 0;
                    }
                    Player p1 = playerList.get(lower);
                    Player p2 = playerList.get(upper);
                    ArrayList < Card > test = p1.getPlayedCards().get(enigma[2]);
                    int y = 0;
                    if (test == null) {
                        y = 0;
                    } else y = test.size();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                    test = p2.getPlayedCards().get(enigma[2]);
                    y = 0;
                    if (test == null) {
                        y = 0;
                    } else y = test.size();
                    p.setMoney(p.getMoney() + y * Integer.parseInt(enigma[3]));
                }
            } else if (enigma[0].contains("R")) {
                // For Resource/Commodity
                p.addToResources(new Resources(enigma[1]));
            } else if (enigma[0].contains("TP")) {
                // For Trading Posts
                TreeMap < String, Boolean > rl = p.getReducedList();
                if (enigma[1].contains("R")) // right
                {
                    if (enigma[2].equals("R")) {
                        rl.put("rightR", true);
                    }
                    if (enigma[2].equals("C")) {
                        rl.put("rightC", true);
                    }
                }
                if (enigma[1].contains("L")) {
                    if (enigma[2].equals("R")) {
                        rl.put("leftR", true);
                    }
                    if (enigma[2].equals("C")) {
                        rl.put("leftC", true);
                    }
                }
            } else if (enigma[0].equals("WP")) {
                // WarCards
                p.setArmies(p.getArmies() + Integer.parseInt(enigma[1]));
            } else if (enigma[0].equals("S")) {
                if (enigma[1].equalsIgnoreCase("All")) {
                    TreeMap < String, Integer > temp = p.getSciList();
                    int x = temp.get("lit"); // ISSUE
                    int y = temp.get("math");
                    int z = temp.get("gear");
                    temp.put("lit", x);
                    temp.put("math", y);
                    temp.put("gear", z);
                    p.setSciList(temp);
                } else if (enigma[1].equalsIgnoreCase("Lit")) {
                    TreeMap < String, Integer > temp = p.getSciList();
                    int i = temp.get("lit") + 1;
                    temp.put("lit", i);
                    p.setSciList(temp);
                } else if (enigma[1].equalsIgnoreCase("Math")) {
                    TreeMap < String, Integer > temp = p.getSciList();
                    int i = temp.get("math") + 1;
                    temp.put("math", i);
                    p.setSciList(temp);
                } else if (enigma[1].equalsIgnoreCase("Gear")) {
                    TreeMap < String, Integer > temp = p.getSciList();
                    int i = temp.get("gear") + 1;
                    temp.put("gear", i);
                    p.setSciList(temp);
                }
            }
            // Removes Card from hand
            p.addToPlayedCards(c);
        }
    }
    public void calcVP(Player p) {
        TreeMap < String, ArrayList < Card >> playedCards = p.getPlayedCards();
        TreeMap < String, Integer > vpSources = p.getVpSources();
        int vp = 0;
        // adds VP for coins
        int vpFromCoins = p.getMoney() / 3;
        vpSources.put("Coins", vpFromCoins);
        vp += vpFromCoins;
        // adds VP for wonders
        int vpFromWonders = 0;
        for (int i = 1; i <= p.getWonder().getCurrentStage(); i++) {
            String effect = p.getWonder().getEffect(i);
            String[] com = effect.split(" ");
            if (effect.contains("VP")) {
                vp += Integer.parseInt(com[1]);
                vpFromWonders += Integer.parseInt(com[1]);
            }
        }
        vpSources.put("Wonder", vpFromWonders);
        int war = p.getWarPlusPoints() - p.getWarMinusPoints();
        vpSources.put("War", war);
        vp += war;
        int blueCards = 0;
        // adds VP for blue
        ArrayList < Card > temp = playedCards.get("blue"); // Example: VP 5, VP 7
        if (temp != null) {
            for (Card c: temp) {
                String effect = c.getEffect();
                effect.trim();
                String[] com = effect.split(" ");
                blueCards += Integer.parseInt(com[1]);
                vp += Integer.parseInt(com[1]);
            }
        }
        vpSources.put("BlueCards", blueCards);
        int yellowCards = 0;
        // VP for yellow
        temp = playedCards.get("yellow"); //broken
        if (temp != null) {
            for (Card c: temp) {
                String effect = c.getEffect();
                effect.trim();
                String[] com = effect.split(" ");
                int index = p.getIndex();
                int lower = index - 1;
                if (lower == -1) {
                    lower = 2;
                }
                int upper = index + 1;
                if (upper == 3) {
                    upper = 0;
                }
                Player pl = playerList.get(lower);
                Player p2 = playerList.get(upper);
                if (com[0].equals("VP")) {
                    if (com[0].equalsIgnoreCase("wonder")) {
                        if (com[1].equals("LR")) {
                            vp += pl.getWonder().getCurrentStage();
                            vp += p2.getWonder().getCurrentStage();
                            yellowCards += pl.getWonder().getCurrentStage();
                            yellowCards += p2.getWonder().getCurrentStage();
                        }
                        if (com[1].equals("LRD")) {
                            vp += pl.getWonder().getCurrentStage();
                            vp += p2.getWonder().getCurrentStage();
                            vp += p.getWonder().getCurrentStage();
                            yellowCards += pl.getWonder().getCurrentStage();
                            yellowCards += p2.getWonder().getCurrentStage();
                            yellowCards += p.getWonder().getCurrentStage();
                        }
                    } else {
                        if (com[1].equals("D")) {
                            ArrayList < Card > one = p.getPlayedCards().get(com[2]);
                            int y = 0;
                            if (one == null) {
                                y = 0;
                            } else {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            yellowCards += y * Integer.parseInt(com[3]);
                        }
                        if (com[1].equals("LR")) {
                            int y = 0;
                            ArrayList < Card > one = pl.getPlayedCards().get(com[2]);
                            if (one != null) {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            y = 0;
                            one = p2.getPlayedCards().get(com[2]);
                            if (one != null) {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            yellowCards += y * Integer.parseInt(com[3]);
                        }
                        if (com[1].equals("LRD")) {
                            ArrayList < Card > one = p.getPlayedCards().get(com[2]);
                            int y = 0;
                            if (one == null) {
                                y = 0;
                            } else {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            yellowCards += y * Integer.parseInt(com[3]);
                            y = 0;
                            one = pl.getPlayedCards().get(com[2]);
                            if (one != null) {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            yellowCards += y * Integer.parseInt(com[3]);
                            y = 0;
                            one = p2.getPlayedCards().get(com[2]);
                            if (one != null) {
                                y = one.size();
                            }
                            vp += y * Integer.parseInt(com[3]);
                            yellowCards += y * Integer.parseInt(com[3]);
                        }
                    }
                }
            }
        }
        vpSources.put("YellowCards", yellowCards);
        int guildCards = 0;
        // Vp for guilds
        temp = playedCards.get("purple"); // Examples: VP LR blue, VP LRD wonder, VP LR minusWar
        if (temp != null) {
            for (Card c: temp) {
                String effect = c.getEffect();
                effect.trim();
                String[] com = effect.split(" ");
                if (com[0].equals("VP")) {
                    if (com[1].equals("LR")) {
                        // com 2 is the type of card searching for
                        int index = p.getIndex();
                        int lower = index - 1;
                        if (lower == -1) {
                            lower = 2;
                        }
                        int upper = index + 1;
                        if (upper == 3) {
                            upper = 0;
                        }
                        Player pl = playerList.get(lower);
                        Player p2 = playerList.get(upper);
                        if (com[2].equalsIgnoreCase("minusWar")) {
                            vp += pl.getWarMinusPoints();
                            vp += p2.getWarMinusPoints();
                            guildCards += pl.getWarMinusPoints();
                            guildCards += p2.getWarMinusPoints();
                        } else if (com[2].equalsIgnoreCase("silver")) {
                            ArrayList < Card > te = pl.getPlayedCards().get(com[2]);
                            int y = 0;
                            if (te != null) {
                                y = te.size() * Integer.parseInt(com[3]);
                            }
                            vp += y;
                            guildCards += y;
                            ArrayList < Card > ta = p2.getPlayedCards().get(com[2]);
                            int z = 0;
                            if (ta != null) {
                                z = ta.size() * Integer.parseInt(com[3]);;
                            }
                            vp += z;
                            guildCards += z;
                        } else {
                            ArrayList < Card > te = pl.getPlayedCards().get(com[2]);
                            int y = 0;
                            if (te != null) {
                                y = te.size();
                            }
                            vp += y;
                            guildCards += y;
                            ArrayList < Card > ta = p2.getPlayedCards().get(com[2]);
                            int z = 0;
                            if (ta != null) {
                                z = ta.size();
                            }
                            vp += z;
                            guildCards += z;
                        }
                    }
                }
                int index = p.getIndex();
                int lower = index - 1;
                if (lower == -1) {
                    lower = 2;
                }
                int upper = index + 1;
                if (upper == 3) {
                    upper = 0;
                }
                if (upper == -1) {
                    upper = 0;
                }
                Player pl = playerList.get(lower);
                Player p2 = playerList.get(upper);
                if (com[1].equals("LRD")) {
                    vp += pl.getWonder().getCurrentStage();
                    vp += p.getWonder().getCurrentStage();
                    vp += p2.getWonder().getCurrentStage();
                    guildCards += pl.getWonder().getCurrentStage();
                    guildCards += p.getWonder().getCurrentStage();
                    guildCards += p2.getWonder().getCurrentStage();
                }
                if (com[1].equals("S All")) { //broken
                	p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                    /*TreeMap < String, Integer > sciListL = new TreeMap < String, Integer > ();
                    TreeMap < String, Integer > sciListM = new TreeMap < String, Integer > ();
                    TreeMap < String, Integer > sciListG = new TreeMap < String, Integer > ();
                    for (String key: p.getSciList().keySet()) {
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
                    if (lit >= math && lit > gear) {
                        p.getSciList().put("lit", p.getSciList().get("lit") + 1);
                    } else if (math > lit && math > gear) {
                        p.getSciList().put("math", p.getSciList().get("math") + 1);
                    } else if (gear > lit && gear > math) {
                        p.getSciList().put("gear", p.getSciList().get("gear") + 1);
                    }*/
                }
                if (com[1].equals("D")) {
                    if (p.getPlayedCards().get("blue") != null) {
                        vp += p.getPlayedCards().get("blue").size();
                        guildCards += p.getPlayedCards().get("blue").size();
                    }
                    if (p.getPlayedCards().get("silver") != null) {
                        vp += p.getPlayedCards().get("silver").size();
                        guildCards += p.getPlayedCards().get("silver").size();
                    }
                    if (p.getPlayedCards().get("brown") != null) {
                        vp += p.getPlayedCards().get("brown").size();
                        guildCards += p.getPlayedCards().get("brown").size();
                    }
                }
            }
        }
        vpSources.put("GuildCards", guildCards);
        int sci = 0;
        // adds VP for sci
        vp += calcSci(p);
        sci = calcSci(p);
        vpSources.put("Science", sci);
        p.setVp(vp);
    }
    public int calcSci(Player p) {
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
    public int calcSci(TreeMap < String, Integer > tree) {
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
    public boolean gameFinished() {
        if (currentAge == 3 && playerList.get(0).getHand().size() == 1 && playerList.get(1).getHand().size() == 1 && playerList.get(2).getHand().size() == 1) {
            return true;
        }
        return false;
    }
    public void deal(int age) {
        ArrayList < Card > d;
        d = new ArrayList < Card > ();
        if (age == 1) {
            d = deck.getAgeOne();
        } else if (age == 2) {
            d = deck.getAgeTwo();
        } else if (age == 3) {
            d = deck.getAgeThree();
        }
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = 6; j >= 0; j--) {
                Card temp = d.remove(j);
                playerList.get(i).addToHand(temp);
            }
        }
    }
    public void incrementPlayerLocation() {
        int l = currentPlayer;
        l++;
        if (l == 3) {
            l = 0;
        }
        currentPlayer = l;
    }
    public void incrementHandLocations() {
        int lower = currentPlayer - 1;
        int higher = currentPlayer + 1;
        if (lower - 1 < 0) lower = 2;
        if (higher + 1 > 2) higher = 0;
        ArrayList < Card > currentPlayerHand = getCurrentPlayer().getHand();
        ArrayList < Card > lowerPlayerHand = getPlayerList().get(lower).getHand();
        ArrayList < Card > higherPlayerHand = getPlayerList().get(higher).getHand();
        if (onWards) {
            getPlayerList().get(lower).setHand(higherPlayerHand);
            getPlayerList().get(currentPlayer).setHand(lowerPlayerHand);
            getPlayerList().get(higher).setHand(currentPlayerHand);
        } else {
            getPlayerList().get(lower).setHand(currentPlayerHand);
            getPlayerList().get(currentPlayer).setHand(higherPlayerHand);
            getPlayerList().get(higher).setHand(lowerPlayerHand);
        }
    }
    public boolean playable(Card c) {
        if (c == null) return false;
        Player p = getCurrentPlayer();
        TreeMap < String, ArrayList < Card >> played = playerList.get(currentPlayer).getPlayedCards();
        for (String key: played.keySet()) {
            ArrayList < Card > temp = played.get(key);
            if (temp.contains(c)) {
                return false; // has card been played
            }
        }
        if (p.isIgnoreCost()) {
            return true;
        }
        if (c.isFree()) // is card free
        {
            return true;
        }
        for (String s: played.keySet()) // does card chain off another card
        {
            for (Card i: played.get(s)) {
                if (i.getName().equalsIgnoreCase(c.getChain())) return true;
            }
        }
        if (c.getCost().toString().contains("C 1")) // does card have coin cost
        {
            if (getCurrentPlayer().getMoney() >= 1) return true;
        }
        // Resource check
        ArrayList < Resources > tempR = playerList.get(currentPlayer).getResources(); // Player's resources
        ArrayList < Resources > cost = c.getCost(); // card cost
        // ArrayList<Resources> costRemove=cost;
        ArrayList < Resources > resources = new ArrayList < Resources > ();
        for (int i = 0; i < tempR.size(); i++) resources.add(tempR.get(i));
        tempR = null;
        for (int i = cost.size() - 1; i >= 0; i--) {
            for (int j = resources.size() - 1; j >= 0; j--) {
                if (resources.get(j).toString().contains(cost.get(i).toString())) {
                    cost.remove(i);
                    resources.remove(j);
                    break;
                }
            }
        }
        if (cost.size() == 0) // if player already has resources
        {
            return true;
        } else {
            TreeMap < Integer, ArrayList < Resources >> trade = getCurrentPlayer().getTrade();
            int costLeft = 0;
            int costRight = 0;
            for (Resources r: cost) {
                int lower = currentPlayer - 1;
                if (lower < 0) {
                    lower = 2;
                }
                int higher = currentPlayer + 1;
                if (higher > 2) {
                    higher = 0;
                }
                ArrayList < Resources > leftResources = playerList.get(lower).getResources();
                ArrayList < Resources > rightResources = playerList.get(higher).getResources();
                if (!leftResources.contains(r) && !rightResources.contains(r)) {
                    trade.clear();
                    return false;
                } else if (leftResources.contains(r) && rightResources.contains(r)) {
                    int tempCostLeft = determineCost(r, false, currentPlayer);
                    int tempCostRight = determineCost(r, true, currentPlayer);
                    if (tempCostLeft <= tempCostRight) {
                        costLeft += tempCostLeft;
                        if (trade.get(lower) == null) {
                            ArrayList < Resources > tempList = new ArrayList < > ();
                            tempList.add(r);
                            trade.put(lower, tempList);
                        } else {
                            ArrayList < Resources > tempList = trade.get(lower);
                            tempList.add(r);
                            trade.put(lower, tempList);
                        }
                    } else {
                        costRight += tempCostRight;
                        if (trade.get(higher) == null) {
                            ArrayList < Resources > tempList = new ArrayList < > ();
                            tempList.add(r);
                            trade.put(higher, tempList);
                        } else {
                            ArrayList < Resources > tempList = trade.get(higher);
                            tempList.add(r);
                            trade.put(higher, tempList);
                        }
                    }
                } else if (leftResources.contains(r)) {
                    costLeft += determineCost(r, false, currentPlayer);
                    if (trade.get(lower) == null) {
                        ArrayList < Resources > tempList = new ArrayList < > ();
                        tempList.add(r);
                        trade.put(lower, tempList);
                    } else {
                        ArrayList < Resources > tempList = trade.get(lower);
                        tempList.add(r);
                        trade.put(lower, tempList);
                    }
                } else {
                    costRight += determineCost(r, true, currentPlayer);
                    if (trade.get(higher) == null) {
                        ArrayList < Resources > tempList = new ArrayList < > ();
                        tempList.add(r);
                        trade.put(higher, tempList);
                    } else {
                        ArrayList < Resources > tempList = trade.get(higher);
                        tempList.add(r);
                        trade.put(higher, tempList);
                    }
                }
            }
            if (playerList.get(currentPlayer).getMoney() >= costLeft + costRight) {
                return true;
            }
            trade.clear();
            return false;
        }
    }
    public void payCosts(int id) // Pays coins costs
    {
        Player p = getPlayerList().get(id);
        if (p.isIgnoreCost()) {
            p.setIgnoreCost(false);
        } else if (p.isBurnCard()) {
            return;
        } else if (p.getTrade().size() == 0) {
            Card temp = p.getTempPlayedCards().get(0);
            if (temp.getCost().toString().contains("C 1")&&temp!=null) {
                p.setMoney(p.getMoney() - 1);
            }
        } else {
            for (int index: p.getTrade().keySet()) {
                Player toTrade = getPlayerList().get(index);
                ArrayList < Resources > resources = p.getTrade().get(index);
                for (int i = 0; i < resources.size(); i++) {
                    trade(p, toTrade, resources.get(i));
                }
                resources.clear();
            }
            p.getTrade().clear();
        }
    }
    public int determineCost(Resources r, boolean isRight, int index) {
        Player p = playerList.get(index);
        // returns coin cost for a resource
        if (isRight) {
            if (r.isR()) // if is resource
            {
                if (p.getReducedList().get("rightR")) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (p.getReducedList().get("rightC")) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {
            if (r.isR()) {
                if (p.getReducedList().get("leftR")) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (p.getReducedList().get("leftC")) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
    }
    public void trade(Player p1, Player p2, Resources r) {
        if (p1.getMoney() >= 0) {
            int index = p1.getIndex();
            int temp = p2.getIndex();
            boolean isRight;
            if (index == 0) {
                if (temp == 1) isRight = true;
                else isRight = false;
            } else if (index == 1) {
                if (temp == 2) isRight = true;
                else isRight = false;
            } else {
                if (temp == 0) isRight = true;
                else isRight = false;
            }
            int cost = determineCost(r, isRight, p1.getIndex());
            if (p1.getMoney() >= cost) {
                p1.setMoney(p1.getMoney() - cost);
                p2.setMoney(p2.getMoney() + cost);
            }
        }
    }
    public boolean ageOver() {
        ArrayList < Player > players = getPlayerList();
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getHand().size() > 1) return false;
        return true;
    }
    public void calcWarPoints() {
        ArrayList < Player > players = getPlayerList();
        for (int i = 0; i < players.size(); i++) {
            int lower = i - 1;
            int current = i;
            int higher = i + 1;
            if (lower - 1 < 0) lower = 2;
            if (higher + 1 > 2) higher = 0;
            Player currentP = players.get(current);
            Player lowerP = players.get(lower);
            Player higherP = players.get(higher);
            if (lowerP.getArmies() > currentP.getArmies() || higherP.getArmies() > currentP.getArmies()) {
                currentP.setWarMinusPoints(currentP.getWarMinusPoints() + 1);
            } else {
                if (getCurrentAge() == 1) currentP.setWarPlusPoints(currentP.getWarMinusPoints() + 1);
                else if (getCurrentAge() == 2) currentP.setWarPlusPoints(currentP.getWarMinusPoints() + 3);
                else currentP.setWarPlusPoints(currentP.getWarMinusPoints() + 5);
            }
        }
    }
    public boolean canBuildWonder(Player p, int stage) {
        Player player = p;
        Wonder wonder = player.getWonder();
        int currentStage = wonder.getCurrentStage();
        if (currentStage == 3) return false;
        else if (stage <= currentStage) return false;
        else if (stage > currentStage + 1) return false;
        ArrayList < Resources > cost = new ArrayList < Resources > ();
        ArrayList < Resources > resources = new ArrayList < Resources > (); // local copy of resources
        for (Resources r: player.getResources()) resources.add(r);
        String temp = wonder.getCost(stage);
        String[] stageCost = temp.split(",");
        for (int i = 0; i < stageCost.length; i++) cost.add(new Resources(stageCost[i]));
        for (int j = resources.size() - 1; j >= 0; j--) {
            for (int i = cost.size() - 1; i >= 0; i--) {
                if (resources.get(j).toString().contains(cost.get(i).toString())) {
                    resources.remove(j);
                    cost.remove(i);
                    break;
                }
            }
        }
        if (cost.size() == 0) // Player has all necessary resources
        {
            return true;
        } else {
            TreeMap < Integer, ArrayList < Resources >> trade = player.getTrade();
            int costLeft = 0;
            int costRight = 0;
            for (Resources r: cost) {
                int lower = p.getIndex() - 1;
                if (lower < 0) {
                    lower = 2;
                }
                int higher = p.getIndex() + 1;
                if (higher > 2) {
                    higher = 0;
                }
                ArrayList < Resources > leftResources = playerList.get(lower).getResources();
                ArrayList < Resources > rightResources = playerList.get(higher).getResources();
                if (!leftResources.contains(r) && !rightResources.contains(r)) {
                    return false;
                } else if (leftResources.contains(r) && rightResources.contains(r)) {
                    int tempCostLeft = determineCost(r, false, currentPlayer);
                    int tempCostRight = determineCost(r, true, currentPlayer);
                    if (tempCostLeft <= tempCostRight) {
                        costLeft += tempCostLeft;
                        if (trade.get(lower) == null) {
                            ArrayList < Resources > tempList = new ArrayList < > ();
                            tempList.add(r);
                            trade.put(lower, tempList);
                        } else {
                            ArrayList < Resources > tempList = trade.get(lower);
                            tempList.add(r);
                            trade.put(lower, tempList);
                        }
                    } else {
                        costRight += tempCostRight;
                        if (trade.get(higher) == null) {
                            ArrayList < Resources > tempList = new ArrayList < > ();
                            tempList.add(r);
                            trade.put(higher, tempList);
                        } else {
                            ArrayList < Resources > tempList = trade.get(higher);
                            tempList.add(r);
                            trade.put(higher, tempList);
                        }
                    }
                } else if (leftResources.contains(r)) {
                    costLeft += determineCost(r, false, currentPlayer);
                    if (trade.get(lower) == null) {
                        ArrayList < Resources > tempList = new ArrayList < > ();
                        tempList.add(r);
                        trade.put(lower, tempList);
                    } else {
                        ArrayList < Resources > tempList = trade.get(lower);
                        tempList.add(r);
                        trade.put(lower, tempList);
                    }
                } else {
                    costRight += determineCost(r, true, currentPlayer);
                    if (trade.get(higher) == null) {
                        ArrayList < Resources > tempList = new ArrayList < > ();
                        tempList.add(r);
                        trade.put(higher, tempList);
                    } else {
                        ArrayList < Resources > tempList = trade.get(higher);
                        tempList.add(r);
                        trade.put(higher, tempList);
                    }
                }
            }
            if (playerList.get(currentPlayer).getMoney() >= costLeft + costRight) {
                return true;
            }
            trade.clear();
            return false;
        }
    }
    public void printEverything() {
        System.out.println("Player " + (getCurrentPlayer().getIndex()) + 1 + " is currently playing");
        for (int i = 0; i < 3; i++) {
            System.out.println("Player " + (i + 1) + "'s hand is " + playerList.get(i).getHand().toString());
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Player " + (i + 1) + "'s played cards are " + playerList.get(i).getPlayedCards().toString());
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Player " + (i + 1) + "'s played cards are " + playerList.get(i).getResources().toString());
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("Player " + (i + 1) + " has built " + playerList.get(i).getWonder().getCurrentStage() + " stages of their wonder");
        }
    }
    public void buildWonder(Player p, int stage, Card card) {
        Wonder wonder = p.getWonder();
        wonder.setCurrentStage(stage);
        p.getHand().remove(card);
        p.setBuildWonder(false);
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
    public Player getCurrentPlayer() {
        return playerList.get(currentPlayer);
    }
    public int getCurrentTurn() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public ArrayList < Player > getPlayerList() {
        return playerList;
    }
    public void setPlayerList(ArrayList < Player > playerList) {
        this.playerList = playerList;
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
    public boolean isDrawDiscard() {
        return drawDiscard;
    }
    public void setDrawDiscard(boolean drawDiscard) {
        this.drawDiscard = drawDiscard;
    }
    public void setAge3CardQuantity(int age3CardQuantity) {
        Age3CardQuantity = age3CardQuantity;
    }
    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}