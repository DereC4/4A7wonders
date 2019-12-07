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
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	board = super.getBoard();
    }
    
	public void paint(Graphics g)
	{
		if (!board.gameFinished())
		{
			try
			{
				if(board.ageOver())
				{
					if (board.isOnWards())
						board.setOnWards(false);
					else
						board.setOnWards(true);
					
					board.calcWarPoints(); //for previous age
					board.setCurrentAge(board.getCurrentAge()+1);
					out.println(board.getCurrentAge());
					//System.out.print("New Age is " + board.getCurrentAge());
					board.deal(board.getCurrentAge());
					for (Player p : board.getPlayerList())
					{
						board.getDeck().getDiscard().add(p.getHand().get(0)); //adds remaining card to discard pile
						p.getHand().remove(0); //Discard remaining card from previous age
						//out.println(p.getHand());
						
						Wonder wonder = p.getWonder();
						int stage = p.getWonderStage();
						if (wonder.getName().equals("Halikarnassus") && stage >= 2)
							p.setIgnoreCost(true);
					}
				}
				
				//check temp card storage 
				
				//update round 
				ArrayList<Player> players = board.getPlayerList();
				boolean update = true;
				for (int i = 0; i < players.size(); i++)
				{
					Player p = players.get(i);
					if (p.getTempPlayedCards().size() == 0)
						update = false;
				}
				
				if (update)
				{
					for (int i = 0; i < players.size(); i++)
					{
						Player p = players.get(i);
						for (int j = 0; j < p.getTempPlayedCards().size(); j++)
						{
							Card temp = p.getTempPlayedCards().get(j);
							//p.addToPlayedCards(p.getTempPlayedCard());
							if (p.isBuildWonder()) //buildWonder1 is for unique cases (ie effects that don't involve VP)
							{
								Wonder wonder = p.getWonder();
								int stage = wonder.getCurrentStage();
								board.buildWonder(p, stage+1, temp);
								
								ArrayList<String> vpCases = new ArrayList<>();
								vpCases.add("scienceAll");
								vpCases.add("VP 5");
								vpCases.add("VP 5");
								vpCases.add("VP 5");
								if (!vpCases.contains(wonder.getEffect(stage)))
									p.setHas_VP_Effect(true);
								
								if (!p.has_VP_Effect())
								{
									board.decodeWonderEffect(wonder.getEffect(stage));
								}
							}
							else if (p.isBurnCard())
							{
								board.getDeck().getDiscard().add(temp);
								p.setMoney(p.getMoney() + 3);
								p.setBurnCard(false);
							}
							else 
							{
								board.payCosts(p.getIndex());
								board.decodeEffect(temp, p);
							}
							//must also pay card cost
							p.getTempPlayedCards().clear();
						}
					}
					board.incrementHandLocations();
				}
				
				super.paint(g);
				//BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
				//BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\" + board.getCurrentPlayer().getWonder().getName()+ ".png"));
				//BufferedImage currentage = ImageIO.read(new File("images\\assets\\age"+board.getCurrentAge()+".png"));
				BufferedImage coin = ImageIO.read(new File("images\\assets\\coin.png"));
				BufferedImage burn = ImageIO.read(new File("images\\assets\\trash.png"));
				BufferedImage burnactivated = ImageIO.read(new File("images\\assets\\trashactivated.png"));
				
	//			JLabel warminuspoints = new JLabel();
	//			warminuspoints.setText(""+board.getCurrentPlayer().getWarMinusPoints());
	//			warminuspoints.setForeground(Color.black);
			
				//g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
				//g.drawImage(sampleWonder, 250, 250, 1100, 342, null);
				//g.drawImage(currentage, 750, 100, 100, 100, null);
				g.drawImage(coin, 1411, 275, 50, 50, null);
				g.setColor(Color.gray);
				g.fillRect(1375, 425, 125, 125); //button to burn cards
				
				if (board.getCurrentPlayer().isBurnCard())
				{
//					g.setColor(Color.red);
//					g.fillRect(1375, 425, 125, 125); //button to burn cards
					//out.println("Burn mode: "+ board.getCurrentPlayer().isBurnCard());
					g.drawImage(burnactivated, 1405, 450, 56, 70, null);
				}
				else
				{
//					out.println("Burn mode: "+ board.getCurrentPlayer().isBurnCard());
					g.drawImage(burn, 1405, 450, 56, 70, null);
				}
//				g.fillRect(1375, 425, 125, 125); //button to burn cards
//				g.drawImage(burn, 1405, 450, 56, 70, null);
				
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.PLAIN, 10)); 
				g.drawString("WarMinusPoints", 125, 300);
				g.drawString(""+board.getCurrentPlayer().getWarMinusPoints(), 160, 315);
				g.drawString("WarPlusPoints", 125, 475);
				g.drawString(""+board.getCurrentPlayer().getWarPlusPoints(), 160, 490);
				g.drawString("Coins", 1425, 350);
				g.drawString(""+board.getCurrentPlayer().getMoney(), 1435, 365);
				
				//g.setFont(new Font("Arial", Font.PLAIN, 50));
				//int currentplayer = board.getCurrentPlayer().getIndex()+1;
				//g.drawString("Player " + currentplayer, 350, 175);
				paintCards(g);
				showDiscardWindow(); //only runs when applicable
				
				//Derek's space. Click to open new window
				//BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
				//g.drawImage(clicktoshowcards, 1025, 100, 100, 100, null);
				//g.setColor(new Color(0, 102, 225));
				//Area to click; shows cards that player has played
				//g.drawRect(1025, 100, 100, 100); 
				
				g.setColor(Color.black);
				g.drawRect(50, 50, 100, 100); //Show previous player's wonder
				g.drawRect(1450, 50, 100, 100); //Show next player's wonder
				g.drawRect(100, 250, 125, 125); //war minus points
				g.drawRect(100, 425, 125, 125); //war plus points	
				g.drawRect(1375, 250, 125, 125); //coins
				
			}
			catch (IOException e)
			{
				out.println(e);
				//out.println(board.getCurrentPlayer().getWonder().getName());
			}
		}
	}
	
	public void paintCards(Graphics g) //100, 675, 1400, 300
	{
		ArrayList<Card> cards = board.getCurrentPlayer().getHand();
		try
		{
			//out.println(cards);
			//out.println(cards.get(0).getName().toLowerCase());
			BufferedImage sampleCard;
			for (int i = 0; i < cards.size(); i++)
			{
				sampleCard = ImageIO.read(new File("images\\cards\\" + cards.get(i).getName().toLowerCase() + ".png"));
				g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
			}
		}
		catch (IOException e)
		{
			//out.println(e);
		}
	}
	
	public void showDiscardWindow()
	{
		Player p = board.getCurrentPlayer();
		if (board.isDrawDiscard())
		{
			DiscardWindow discardWindow = new DiscardWindow(board);
		}
	}
	
	public void mouseReleased(MouseEvent event) 
	{
		Player player = board.getCurrentPlayer();
		//g.drawRect(50, 50, 100, 100); //Show previous player's wonder
		//g.drawRect(1450, 50, 100, 100); //Show next player's wonder
		super.mouseReleased(event);
		
		try 
		{
			if (event.getY() > 50 && event.getY() < 150)
			{
				if (event.getX() > 50 && event.getX() < 150)
				{
					out.println("Works");
					int previous = player.getIndex()-1;
					if (previous < 0)
						previous = 2;
					PlayerFrame frame = new PlayerFrame(board.getPlayerList().get(previous));
				}
			}
			
			if (event.getY() > 50 && event.getY() < 150)
			{
				if (event.getX() > 1450 && event.getX() < 1550)
				{
					out.println("Works");
					int next = player.getIndex()+1;
					if (next > 2)
						next = 0;
					PlayerFrame frame = new PlayerFrame(board.getPlayerList().get(next));
				}
			}
		}
		catch(IOException e)
		{
		}
		
		//g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
		//g.drawRect(1375, 425, 125, 125); //button to burn cards
		if (event.getX()>1375 && event.getX()<1500 && event.getY()>425 && event.getY()<550) //burn cards
		{
			if (player.isBurnCard())
				player.setBurnCard(false);
			else
				player.setBurnCard(true);
			repaint();
		}
		/*
		g.drawRect(325, 510, 280, 85); //Wonder stage 1
		g.drawRect(655, 510, 280, 85); //Wonder stage 2
		g.drawRect(980, 510, 280, 85); //Wonder stage 3
		*/
		if (event.getY()>510 && event.getY()<595) //Build wonders
		{
			if (event.getX()>325 && event.getX()<605)
			{
				player.setBuildWonder(true);
				repaint();
			}
			else if (event.getX()>655 && event.getX()<935)
			{
				player.setBuildWonder(true);
				repaint();
			}
			else if (event.getX()>980 && event.getX()<1260)
			{
				player.setBuildWonder(true);
				repaint();
			}
		}
				
		if (event.getY()>675 && event.getY()<956)
		{
			try 
			{ 
				Card temp;
				if (event.getX()>100 && event.getX()<285)
					temp = player.getHand().get(0);
				else if (event.getX()>295 && event.getX()<480)
					temp = player.getHand().get(1);
				else if (event.getX()>490 && event.getX()<675)
					temp = player.getHand().get(2);
				else if (event.getX()>685 && event.getX()<870)
					temp = player.getHand().get(3);
				else if (event.getX()>880 && event.getX()<1065)
					temp = player.getHand().get(4);
				else if (event.getX()>1075 && event.getX()<1260)
					temp = player.getHand().get(5);
				else if (event.getX()>1270 && event.getX()<1455)
					temp = player.getHand().get(6);
				else
					temp = null;
				//out.println("Resources: " + board.getCurrentPlayer().getResources());
				//out.println(player.getHand().size());
				//out.println("Player: " + board.getCurrentPlayer().getIndex());
				//out.println("Name: " + temp.getName() + " Cost:" + temp.getCost());
				if (player.isBurnCard() || board.playable(temp) || player.isBuildWonder())
				{
					//if (temp.getCost().get(0).toString().equals("C 1"))
					//board.getCurrentPlayer().setMoney(board.getCurrentPlayer().getMoney()-1);
					player.play(temp);
					board.incrementPlayerLocation(); //Changed to increment player location
				}
			}
			catch (IndexOutOfBoundsException e)
			{
				//out.println(e);
			}
			catch (NullPointerException e)
			{
			}
			repaint();
		}
	}
}
