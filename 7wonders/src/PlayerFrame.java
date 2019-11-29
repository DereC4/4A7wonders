import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
//       if (newBoard)
//       {
//      	 setBoard(new Board());
//       }
	}
	public void setBoard(Board board2) throws IOException 
	{
		board = board2;
	}
	
	public void paint(Graphics g)
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
//			super.add(warminuspoints);
//			warminuspoints.setBounds(900, 100, 200, 100);

			//War minus points
			
			//Derek's space. Click to open new window
			BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
			g.drawImage(clicktoshowcards, 1025, 100, 100, 100, null);
			g.setColor(new Color(0, 102, 225));
			g.drawRect(1025, 100, 100, 100); //Area to click; shows cards that player has played
			
			paintCards(g);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.out.println(board.getCurrentPlayer().getWonder().getName());
		}
		
		g.setColor(Color.black);
		g.drawRect(50, 50, 100, 100); //Show previous player's wonder
		g.drawRect(1450, 50, 100, 100); //Show next player's wonder
		
		//g.setColor(Color.red); 
		//g.drawRect(475, 475, 175, 75); //Wonder stage 1
		//g.drawRect(685, 475, 175, 75); //Wonder stage 2
		//g.drawRect(895, 475, 175, 75); //Wonder stage 3 
		
		g.setColor(Color.black);
		g.drawRect(100, 675, 1400, 300); //Current player's hand
		g.drawRect(250, 250, 1100, 342); //Current player's wonder
		g.drawRect(100, 250, 125, 125); //war minus points
		g.drawRect(100, 425, 125, 125); //war plus points
		g.drawRect(1375, 250, 125, 125); //coins
		g.drawRect(1375, 425, 125, 125); //button to burn cards
		g.drawRect(475, 100, 100, 100); //current rotation
		g.drawRect(750, 100, 100, 100); //current age
		
		g.setFont(new Font("Arial", Font.PLAIN, 10)); 
		g.drawString("WarMinusPoints", 125, 300);
		g.drawString(""+board.getCurrentPlayer().getWarMinusPoints(), 160, 315);
		g.drawString("WarPlusPoints", 125, 475);
		g.drawString(""+board.getCurrentPlayer().getWarPlusPoints(), 160, 490);
		g.drawString("Coins", 1425, 300);
		g.drawString(""+board.getCurrentPlayer().getMoney(), 1435, 315);
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
	public void mouseClicked(MouseEvent arg0) 
	{
		if(arg0.getX()<1125 && arg0.getY()<200 && arg0.getX()>1025 && arg0.getY()>100)
		{
			System.out.println("Pressed!");
			try 
			{
				derekPaintCards();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public void derekPaintCards() throws IOException 
	{
		int turn = board.getCurrentTurn()+1;
		JFrame playercards = new JFrame("Player "+turn+"'s Cards");
		playercards.setVisible(true);
		playercards.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playercards.setResizable(false);
        playercards.setBounds(150,25,LENGTH,HEIGHT);
        
//		JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("images\\background.jpg"))));
//		background.setBounds(0, 0, LENGTH, HEIGHT);
//		playercards.add(background);
		
//			System.out.println("test");
		ArrayList <Card> cards = board.getCurrentPlayer().getHand();
		
		int length = 50;
		int row = 50;
		
		for (int i = 0; i < cards.size(); i++)
		{
			JLabel card = new JLabel(new ImageIcon(ImageIO.read(new File("images\\cards\\" + cards.get(i).getName().toLowerCase() + ".png"))));
			if(length+50>playercards.getWidth())
			{
				length = 50;
				row += card.getHeight();
				card.setBounds(new Rectangle(new Point(length, row), card.getPreferredSize()));
			}
			else
				card.setBounds(new Rectangle(new Point(length, row), card.getPreferredSize()));
			playercards.add(card);
			length+=card.getWidth();
		}
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
