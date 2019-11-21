import java.awt.Color;
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
	
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
			BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\" + board.getCurrentPlayer().getWonder().getName()+ ".png"));
			ArrayList<Player> players = board.getPlayerList();
			
			
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			g.drawImage(sampleWonder, 425, 250, 700, 300, null);
			
			//War minus points
			
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.out.println(board.getCurrentPlayer().getWonder().getName());
		}
		
		g.setColor(Color.black);
		g.drawRect(425, 250, 700, 300); //Current player's wonder
		g.drawRect(425, 575, 100, 75); //button to show cards current player has built
		g.drawRect(625, 575, 100, 75); //war minus points
		g.drawRect(825, 575, 100, 75); //war plus points
		g.drawRect(1025, 575, 100, 75); //coins
		g.setColor(Color.red); 
		g.drawRect(475, 475, 175, 75); //Wonder stage 1
		g.drawRect(685, 475, 175, 75); //Wonder stage 2
		g.drawRect(895, 475, 175, 75); //Wonder stage 3 
	}
	
	public void paintWonder(Graphics g)
	{
		
	}
	public void mouseClicked(MouseEvent arg0) 
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
		//System.out.println(arg0.getX() + " " + arg0.getY());
	}
	public void mouseReleased(MouseEvent arg0) 
	{
		
	}
	public Board getBoard()
	{
		return board;
	}
	public static ArrayList<Player> getPlayers() 
	{
		return players;
	}
	public static void main(String[] args) throws IOException
	{
		PlayerFrame g = new PlayerFrame();
	}
	
}
