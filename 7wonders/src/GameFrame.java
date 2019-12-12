import static java.lang.System.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class GameFrame extends PlayerFrame
{
    private Board board; //reference to super 
    public GameFrame() throws IOException
    {
        super();
        //super.setMain(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = super.getBoard();
        //RulesWindow rulesoftheduel = new RulesWindow();
    }
    public void paint(Graphics g)
    {
        if (!board.gameFinished()) 
        {
            try
            {
                if (board.ageOver())
                {
                	WarWindow mongoltage = new WarWindow(board);
                    if (board.isOnWards()) board.setOnWards(false);
                    else board.setOnWards(true);
                    board.calcWarPoints(); //for previous age
                    board.setCurrentAge(board.getCurrentAge() + 1);
                    board.deal(board.getCurrentAge());
                    for (Player p: board.getPlayerList())
                    {
                        board.getDeck().getDiscard().add(p.getHand().get(0)); //adds remaining card to discard pile
                        p.getHand().remove(0); //Discard remaining card from previous age
                        Wonder wonder = p.getWonder();
                        int stage = p.getWonderStage();
                        if (wonder.getName().equals("Olympia") && stage >= 2) p.setIgnoreCost(true);
                    }
                }
                //check temp card storage 
                //update round 
                ArrayList < Player > players = board.getPlayerList();
                boolean update = true;
                for (int i = 0; i < players.size(); i++)
                {
                    Player p = players.get(i);
                    if (p.getTempPlayedCards().size() == 0) update = false;
                }
                if (update)
                {
                    for (int i = 0; i < players.size(); i++)
                    {
                        Player p = players.get(i);
                        for (int j = p.getTempPlayedCards().size()-1; j >= 0; j--)
                        {
                            Card temp = p.getTempPlayedCards().get(j);
                            Wonder wonder = p.getWonder();
                            int stage = wonder.getCurrentStage();
                            
                            if (p.isBuildWonder() && board.canBuildWonder(p, stage + 1))
                            {
                                board.buildWonder(p, stage + 1, temp);
                                stage = wonder.getCurrentStage();
                                ArrayList<String> vpCases = new ArrayList < > ();
                                vpCases.add("scienceAll");
                                vpCases.add("VP 3");
                                vpCases.add("VP 5");
                                vpCases.add("VP 7");
                                if (vpCases.contains(wonder.getEffect(stage))) 
                                	p.setHas_VP_Effect(true);
                                else
                                	p.setHas_VP_Effect(false);
                                //out.println(wonder.getEffect(stage));
                                //out.println(vpCases);
                                if (!p.has_VP_Effect())
                                {
                                	//out.println(wonder.getEffect(stage));
                                    board.decodeWonderEffect(wonder.getEffect(stage), p);
                                }
                            }
                            else if (p.isBurnCard())
                            {
                                board.getDeck().getDiscard().add(temp);
                                p.setMoney(p.getMoney() + 3);
                            }
                            else
                            {
                                board.decodeEffect(temp, p);
                            }
                            board.payCosts(p.getIndex()); 
                            p.setBurnCard(false);
                            p.setBuildWonder(false);
                            p.getTempPlayedCards().remove(j);
                            out.println("Player " + (p.getIndex()+1) + " " + p.getTempPlayedCards());
                        }
                    }
                    
                    board.incrementHandLocations();
                }
                super.paint(g);
                BufferedImage burn = ImageIO.read(new File("images\\assets\\trash.png"));
                BufferedImage burnactivated = ImageIO.read(new File("images\\assets\\trashactivated.png"));
                g.setColor(new Color(26, 109, 176));
                g.fillRect(1250, 100, 100, 100);
                g.setColor(Color.gray);
                g.fillRect(1375, 425, 125, 125); //button to burn cards
                g.setColor(Color.black);
                g.drawRect(1375, 425, 125, 125);
                if (board.getCurrentPlayer().isBurnCard())
                {
                    g.drawImage(burnactivated, 1405, 450, 56, 70, null);
                }
                else
                {
                    g.drawImage(burn, 1405, 450, 56, 70, null);
                }
                paintCards(g);
                g.setColor(Color.black);
                g.drawRect(0, 0, 200, 150); //Show previous player's wonder
                g.drawRect(1400, 0, 200, 150); //Show next player's wonder
                g.setColor(Color.gray);
                g.fillRect(0, 0, 200, 150);
                g.fillRect(1400, 0, 200, 150);
                
                if (board.getCurrentPlayer().isBuildWonder()) g.setColor(Color.green);
                else g.setColor(Color.gray);
                g.drawRect(1250, 100, 100, 100); //build wonders
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("Build Wonder", 1253, 150);
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("WarMinusPoints", 108, 350);
                g.drawString("" + board.getCurrentPlayer().getWarMinusPoints(), 155, 365);
                g.drawString("WarPlusPoints", 110, 525);
                g.drawString("" + board.getCurrentPlayer().getWarPlusPoints(), 155, 540);
                g.drawString("Coins", 1420, 350);
                g.drawString("" + board.getCurrentPlayer().getMoney(), 1435, 365);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Previous Player", 10, 50);
                g.drawString("Next Player", 1410, 50);
                g.setFont(new Font("Arial", Font.PLAIN, 50));
                int currentplayer = board.getCurrentPlayer().getIndex() + 1;
                g.drawString("Player " + currentplayer, 350, 175);
                
                paintDiscardWindow(g); //only runs when applicable
            }
            catch (IOException e)
            {}
        }
        else {
        	board.calcWarPoints();
        	for (Player p : board.getPlayerList())
        	{
        		board.calcVP(p);
        		//if (p.getWonder().getEffect(2).equals(anObject))
        	}
        	paintGameOver(g);
        	
        }
    }
    public void paintCards(Graphics g) //100, 675, 1400, 300
    {
        ArrayList < Card > cards = board.getCurrentPlayer().getHand();
        try
        {
            BufferedImage sampleCard;
            for (int i = 0; i < cards.size(); i++)
            {
                sampleCard = ImageIO.read(new File("images\\cards\\" + cards.get(i).getName().toLowerCase() + ".png"));
                g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
            }
        }
        catch (IOException e)
        {}
    }
    public void paintGameOver(Graphics g)
    {
    	g.setColor(new Color(10, 200, 20));
    	g.fillRect(650, 700, 250, 100);
    	g.setColor(Color.black);
    	g.drawRect(650, 700, 250, 100);
    	g.setFont(new Font("Arial", Font.BOLD, 45));
    	g.drawString("Continue", 675, 760);
    }
    public void paintDiscardWindow(Graphics g)
    {
        for (Player p : board.getPlayerList())
        {
        	if (p.isDrawDiscard())
        	{
        		//DiscardWindow discardWindow = new DiscardWindow(board, p);
        		board.setToDrawDiscard(p);
                g.setColor(new Color(10, 200, 20));
                g.fillRect(650, 350, 300, 100);
        		g.setColor(Color.black);
        		g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawRect(650, 350, 300, 100);
                g.drawString("Show Player " + (p.getIndex()+1) + "'s", 675, 390);
                g.drawString("Graveyard", 675, 430);
        		break;
        	}
        }
    }
    public void mouseReleased(MouseEvent event)
    {
        Player player = board.getCurrentPlayer();
        super.mouseReleased(event);
        //g.fillRect(650, 350, 300, 100);
        if (event.getY()>350 && event.getY()<450)
        {
        	if (event.getX()>650 && event.getX()<950)
        	{
        		Player p = board.getToDrawDiscard();
        		if (p != null)
        		{
        			DiscardWindow window = new DiscardWindow(board, p);
        		}
        	}
        }
        
        if (event.getY()>700 && event.getY()<800)
        {
        	if (event.getX()>650 && event.getX()<900)
        	{
        		if (board.gameFinished())
        		{
        			VictoryWindow window = new VictoryWindow(board);
        		}
        	}
        }
        
        try
        {
            if (event.getY() > 0 && event.getY() < 150)
            {
                if (event.getX() > 0 && event.getX() < 200)
                {
                    int previous = player.getIndex() - 1;
                    if (previous < 0) previous = 2;
                    PlayerFrame frame = new PlayerFrame(board.getPlayerList().get(previous), board);
                }
            }
            if (event.getY() > 0 && event.getY() < 150)
            {
                if (event.getX() > 1400 && event.getX() < 1600)
                {
                    int next = player.getIndex() + 1;
                    if (next > 2) next = 0;
                    PlayerFrame frame = new PlayerFrame(board.getPlayerList().get(next), board);
                }
            }
        }
        catch (IOException e)
        {}
        if (event.getX() > 1375 && event.getX() < 1500 && event.getY() > 425 && event.getY() < 550) //burn cards
        {
        	player.setBuildWonder(false);
            if (player.isBurnCard()) 
            	player.setBurnCard(false);
            else 
            	player.setBurnCard(true);
        }
        if (event.getY() > 100 && event.getY() < 200)
        {
            if (event.getX() > 1250 && event.getX() < 1350)
            {
            	player.setBurnCard(false);
                if (!player.isBuildWonder()) player.setBuildWonder(true);
                else player.setBuildWonder(false);
            }
        }
        if (event.getY() > 675 && event.getY() < 956)
        {
            try
            {
                Card temp;
                ArrayList<Card> tempPlayed = player.getTempPlayedCards();
                ArrayList<Card> hand = player.getHand();
                if (event.getX() > 100 && event.getX() < 285) temp = player.getHand().get(0);
                else if (event.getX() > 295 && event.getX() < 480) temp = player.getHand().get(1);
                else if (event.getX() > 490 && event.getX() < 675) temp = player.getHand().get(2);
                else if (event.getX() > 685 && event.getX() < 870) temp = player.getHand().get(3);
                else if (event.getX() > 880 && event.getX() < 1065) temp = player.getHand().get(4);
                else if (event.getX() > 1075 && event.getX() < 1260) temp = player.getHand().get(5);
                else if (event.getX() > 1270 && event.getX() < 1455) temp = player.getHand().get(6);
                else temp = null;
                if (!player.isBuildWonder() && (player.isBurnCard() || board.playable(temp)))
                {
                    player.play(temp);
                    board.incrementPlayerLocation(); //Changed to increment player location
                }
                else if (player.isBuildWonder() && board.canBuildWonder(player, player.getWonderStage() + 1))
                {
                	player.play(temp);
                    board.incrementPlayerLocation();
                }
            }
            catch (IndexOutOfBoundsException e)
            {
            }
            catch (NullPointerException e)
            {}
            repaint();
        }
    }
}