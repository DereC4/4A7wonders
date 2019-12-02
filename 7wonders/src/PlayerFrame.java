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

public class PlayerFrame extends JFrame implements MouseListener
{
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
    private static Board board;
    private ArrayList<Player> players;

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
        this.players = board.getPlayerList();
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
				
	//			JLabel warminuspoints = new JLabel();
	//			warminuspoints.setText(""+board.getCurrentPlayer().getWarMinusPoints());
	//			warminuspoints.setForeground(Color.black);
	
				ArrayList<Player> players = board.getPlayerList();
				
				
				g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
				g.drawImage(sampleWonder, 250, 250, 1100, 342, null);
				g.drawImage(currentage, 750, 100, 100, 100, null);
				g.setFont(new Font("Arial", Font.PLAIN, 10)); 
				g.drawString("WarMinusPoints", 125, 300);
				g.drawString(""+board.getCurrentPlayer().getWarMinusPoints(), 160, 315);
				g.drawString("WarPlusPoints", 125, 475);
				g.drawString(""+board.getCurrentPlayer().getWarPlusPoints(), 160, 490);
				g.drawString("Coins", 1425, 300);
				g.drawString(""+board.getCurrentPlayer().getMoney(), 1435, 315);
				//250, 250, 1100, 342
				g.setFont(new Font("Arial", Font.PLAIN, 50));
				g.drawString("Player " + board.getCurrentPlayer().getIndex(), 350, 175);
				paintCards(g);
				
				//check temp card storage 
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
						//p.addToPlayedCards(p.getTempPlayedCard());
						if (p.isBurnCard())
						{
							Card temp = p.getTempPlayedCard();
							board.getDeck().getDiscard().add(temp);
							p.setMoney(p.getMoney() + 3);
						}
						else 
						{
							board.decodeEffect(p.getTempPlayedCard(), p);
						}
						//must also pay card cost
						p.setTempPlayedCard(null);
						
					}
				}
				
	//			super.add(warminuspoints);
	//			warminuspoints.setBounds(900, 100, 200, 100);
	
				//War minus points
				
				//Derek's space. Click to open new window
				BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
				g.drawImage(clicktoshowcards, 1025, 100, 100, 100, null);
				g.setColor(new Color(0, 102, 225));
				g.drawRect(1025, 100, 100, 100); //Area to click; shows cards that player has played
				
			}
			catch (IOException e)
			{
				out.println(e);
				out.println(board.getCurrentPlayer().getWonder().getName());
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
			//System.out.println(cards);
			//System.out.println(cards.get(0).getName().toLowerCase());
			BufferedImage sampleCard;
			for (int i = 0; i < cards.size(); i++)
			{
				sampleCard = ImageIO.read(new File("images\\cards\\" + cards.get(i).getName().toLowerCase() + ".png"));
				g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
			}
		}
		catch (IOException e)
		{
			//System.out.println(e);
		}
		
	}
	@SuppressWarnings("unused")
	public void mouseClicked(MouseEvent e) 
	{
		Player player = board.getCurrentPlayer();
		if(e.getX()<1125 && e.getY()<200 && e.getX()>1025 && e.getY()>100)
		{
//			out.println("Pressed!");
			CardWindow cards = new CardWindow(player);
		}
		//g.drawImage(sampleCard, 100 + (i * 195), 675, 185, 281, null);
		//g.drawRect(1375, 425, 125, 125); //button to burn cards
		if (e.getX()>1375 && e.getX()<1500 && e.getY()>425 && e.getY()<550)
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
		if (e.getY()>510 && e.getY()<595)
		{
			if (e.getX()>325 && e.getX()<605)
			{
				 
			}
			else if (e.getX()>655 && e.getX()<935)
			{
				
			}
			else if (e.getX()>980 && e.getX()<1260)
			{
				
			}
		}
		
		if (e.getY()>675 && e.getY()<956)
		{
			Card temp;
			if (e.getX()>100 && e.getX()<285)
				temp = player.getHand().get(0);
			else if (e.getX()>295 && e.getX()<480)
				temp = player.getHand().get(1);
			else if (e.getX()>490 && e.getX()<675)
				temp = player.getHand().get(2);
			else if (e.getX()>685 && e.getX()<870)
				temp = player.getHand().get(3);
			else if (e.getX()>880 && e.getX()<1065)
				temp = player.getHand().get(4);
			else if (e.getX()>1075 && e.getX()<1260)
				temp = player.getHand().get(5);
			else if (e.getX()>1270 && e.getX()<1455)
				temp = player.getHand().get(6);
			else
				temp = null;
			
			if (player.isBurnCard())
			{
				player.play(temp);
				board.incrementLocation();
			}
			else if (board.playable(temp))
			{
				player.play(temp);
				board.incrementLocation();
			}
		}
		repaint();
	}
	
	public void mouseEntered(MouseEvent arg0) 
	{
		
	}
	public void mouseExited(MouseEvent arg0) 
	{
		
	}
	public void mousePressed(MouseEvent arg0) 
	{
		//System.out.println(arg0.getX() + " " + arg0.getY());
		
	}
	public void mouseReleased(MouseEvent arg0) 
	{
		
	}
	public Board getBoard()
	{
		return board;
	}
}
