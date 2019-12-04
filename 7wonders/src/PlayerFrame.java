import static java.lang.System.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class PlayerFrame extends JFrame implements MouseListener
{
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
    private static Board board;

	public PlayerFrame() throws IOException
	{
		super("Seven Wonders");
		board = new Board();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        addMouseListener(this);
	}
	public void setBoard(Board board2) throws IOException 
	{
		board = board2;
	}
	
	public void paint(Graphics g)
	{
		if (!board.gameFinished())
		{
			try
			{
				BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
				BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\" + board.getCurrentPlayer().getWonder().getName()+ ".png"));
				BufferedImage currentage = ImageIO.read(new File("images\\assets\\age"+board.getCurrentAge()+".png"));
				BufferedImage coin = ImageIO.read(new File("images\\assets\\coin.png"));
				BufferedImage burn = ImageIO.read(new File("images\\assets\\trash.png"));
				
	//			JLabel warminuspoints = new JLabel();
	//			warminuspoints.setText(""+board.getCurrentPlayer().getWarMinusPoints());
	//			warminuspoints.setForeground(Color.black);
	
				ArrayList<Player> players = board.getPlayerList();
			
				g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
				g.drawImage(sampleWonder, 250, 250, 1100, 342, null);
				g.drawImage(currentage, 750, 100, 100, 100, null);
				g.drawImage(coin, 1411, 275, 50, 50, null);
				g.drawImage(burn, 1405, 450, 56, 70, null);
				
				g.setFont(new Font("Arial", Font.PLAIN, 10)); 
				g.drawString("WarMinusPoints", 125, 300);
				g.drawString(""+board.getCurrentPlayer().getWarMinusPoints(), 160, 315);
				g.drawString("WarPlusPoints", 125, 475);
				g.drawString(""+board.getCurrentPlayer().getWarPlusPoints(), 160, 490);
				g.drawString("Coins", 1425, 350);
				g.drawString(""+board.getCurrentPlayer().getMoney(), 1435, 365);
				g.setFont(new Font("Arial", Font.PLAIN, 50));
				int currentplayer = board.getCurrentPlayer().getIndex()+1;
				g.drawString("Player " + currentplayer, 350, 175);
				
				paintCards(g);
				
				//check temp card storage 
				
				//update round 
				boolean update = true;
				for (int i = 0; i < players.size(); i++)
				{
					if (players.get(i).getTempPlayedCard() == null)
						update = false;
				}
				
				if (update)
				{
					for (int i = 0; i < players.size(); i++)
					{
						Player p = players.get(i);
						Card temp = p.getTempPlayedCard();
						//p.addToPlayedCards(p.getTempPlayedCard());
						if (p.isBurnCard())
						{
							board.getDeck().getDiscard().add(temp);
							p.setMoney(p.getMoney() + 3);
						}
						else 
						{
							board.payCosts(p.getIndex());
							board.decodeEffect(temp, p);
						}
						//must also pay card cost
						p.setTempPlayedCard(null);
						
					}
				}	
				//Derek's space. Click to open new window
				BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
				g.drawImage(clicktoshowcards, 1025, 100, 100, 100, null);
				g.setColor(new Color(0, 102, 225));
				//Area to click; shows cards that player has played
				g.drawRect(1025, 100, 100, 100); 
			}
			catch (IOException e)
			{
				//out.println(e);
				//out.println(board.getCurrentPlayer().getWonder().getName());
			}
		}
		
		g.setColor(Color.black);
		g.drawRect(50, 50, 100, 100); //Show previous player's wonder
		g.drawRect(1450, 50, 100, 100); //Show next player's wonder
		
		g.setColor(Color.black);
		g.drawRect(100, 675, 1400, 300); //Current player's hand
		g.drawRect(250, 250, 1100, 342); //Current player's wonder
		g.drawRect(100, 250, 125, 125); //war minus points
		g.drawRect(100, 425, 125, 125); //war plus points	
		g.drawRect(1375, 250, 125, 125); //coins
		g.drawRect(1375, 425, 125, 125); //button to burn cards
		g.drawRect(750, 100, 100, 100); //current age
		g.setColor(Color.red);
		g.drawRect(325, 510, 280, 85); //Wonder stage 1
		g.drawRect(655, 510, 280, 85); //Wonder stage 2
		g.drawRect(980, 510, 280, 85); //Wonder stage 3
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
	
	@SuppressWarnings("unused")
	public void mouseClicked(MouseEvent event) 
	{
		
	}
	
	public void mouseEntered(MouseEvent arg0) 
	{
		
	}
	public void mouseExited(MouseEvent arg0) 
	{
		
	}
	public void mousePressed(MouseEvent arg0) 
	{
		//out.println(arg0.getX() + " " + arg0.getY());
	}
	public void mouseReleased(MouseEvent event) 
	{
		Player player = board.getCurrentPlayer();
		if(event.getX()<1125 && event.getY()<200 && event.getX()>1025 && event.getY()>100)
		{
//			out.println("Pressed!");
			CardWindow cards = new CardWindow(player);
		}
		//g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
		//g.drawRect(1375, 425, 125, 125); //button to burn cards
		if (event.getX()>1375 && event.getX()<1500 && event.getY()>425 && event.getY()<550) //burn cards
		{
			
			if (player.isBurnCard())
				player.setBurnCard(false);
			else
				player.setBurnCard(true);
		}
		
		/*
		g.drawRect(325, 510, 280, 85); //Wonder stage 1
		g.drawRect(655, 510, 280, 85); //Wonder stage 2
		g.drawRect(980, 510, 280, 85); //Wonder stage 3
		*/
		if (event.getY()>510 && event.getY()<595)
		{
			if (event.getX()>325 && event.getX()<605)
			{
				 
			}
			else if (event.getX()>655 && event.getX()<935)
			{
				
			}
			else if (event.getX()>980 && event.getX()<1260)
			{
				
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
				if (player.isBurnCard() || board.playable(temp))
				{
					//if (temp.getCost().get(0).toString().equals("C 1"))
						//board.getCurrentPlayer().setMoney(board.getCurrentPlayer().getMoney()-1);
					player.play(temp);
					board.incrementLocation();
				}
			}
			catch (IndexOutOfBoundsException e)
			{
				//out.println(e);
			}
			catch (NullPointerException e)
			{
			}
			
		}
		repaint();
	}
	public Board getBoard()
	{
		return board;
	}
}
