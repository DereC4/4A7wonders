import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

public class SevenWondersFrame extends JFrame implements MouseListener
{
	private Board board = new Board();
	private static final int LENGTH = 1600;
    private static final int HEIGHT = 1000;
	
	public SevenWondersFrame()
	{
		super("Seven Wonders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		
		g.setColor(Color.black);
		g.drawRect(425, 250, 700, 300); //Current player's wonder
		g.drawRect(50, 50, 100, 100); //Show previous player's wonder
		g.drawRect(1450, 50, 100, 100); //Show next player's wonder
		g.drawRect(275, 725, 1000, 200); //Current player's hand
		g.drawRect(425, 575, 100, 75); //button to show cards current player has built
		g.drawRect(625, 575, 100, 75); //war minus points
		g.drawRect(825, 575, 100, 75); //war plus points
		g.drawRect(1025, 575, 100, 75); //coins
		g.drawRect(500, 100, 75, 75); //current rotation
		g.drawRect(700, 100, 75, 75); //current age
		g.drawRect(1350, 725, 100, 75); //button to burn cards 
	}
	
	public void paintWonder(Graphics g)
	{
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args)
	{
		SevenWondersFrame g = new SevenWondersFrame();
	}
	
}
