import static java.lang.System.out;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PlayerFrame extends JFrame implements MouseListener
{
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
    private static Board board;
    Player player;

	public PlayerFrame(Player player) throws IOException
	{
		super("Seven Wonders");
		board = new Board();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        addMouseListener(this);
        this.player = player;
	}
	
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
        this.player = board.getCurrentPlayer();
	}
	
	public void setBoard(Board board2)
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
				BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\" + player.getWonder().getName()+ ".png"));
				BufferedImage currentage = ImageIO.read(new File("images\\assets\\age" + board.getCurrentAge()+".png"));
			
				g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
				g.drawImage(sampleWonder, 250, 250, 1100, 342, null);
				g.drawImage(currentage, 750, 100, 100, 100, null);
				
				int currentplayer = player.getIndex()+1;
				g.drawString("Player " + player, 350, 175);
				//Derek's space. Click to open new window
				BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
				g.drawImage(clicktoshowcards, 1025, 100, 100, 100, null);
				g.setColor(new Color(0, 102, 225));
				//Area to click; shows cards that player has played
				g.drawRect(1025, 100, 100, 100); 
				
				g.setColor(Color.black);
				g.drawRect(50, 50, 100, 100); //Show previous player's wonder
				g.drawRect(1450, 50, 100, 100); //Show next player's wonder
				g.setColor(Color.black);
				g.drawRect(100, 675, 1400, 300); //Current player's hand
				g.drawRect(250, 250, 1100, 342); //Current player's wonder
				g.drawRect(100, 250, 125, 125); //war minus points
				g.drawRect(100, 425, 125, 125); //war plus points	
				g.drawRect(1375, 250, 125, 125); //coins
				g.drawRect(750, 100, 100, 100); //current age
				g.setColor(Color.red);
				g.drawRect(325, 510, 280, 85); //Wonder stage 1
				g.drawRect(655, 510, 280, 85); //Wonder stage 2
				g.drawRect(980, 510, 280, 85); //Wonder stage 3
			}
			catch (IOException e)
			{
				out.println(e);
				//out.println(board.getCurrentPlayer().getWonder().getName());
			}
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
	}
	public void mouseReleased(MouseEvent event) 
	{
		Player player = this.player;
		
		if(event.getX()<1125 && event.getY()<200 && event.getX()>1025 && event.getY()>100) //shows card window
		{
//			out.println("Pressed!");
			CardWindow cards = new CardWindow(player);
		}
		repaint();
	}
	public Board getBoard()
	{
		return board;
	}
}
