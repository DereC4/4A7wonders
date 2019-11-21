import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GameFrame extends PlayerFrame
{
    private Board board; //reference to super 
    
    public GameFrame() throws IOException
    {
    	super(); 
    	board = super.getBoard();
    }
    
    
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.black);
		g.drawRect(50, 50, 100, 100); //Show previous player's wonder
		g.drawRect(1450, 50, 100, 100); //Show next player's wonder
		g.drawRect(275, 725, 1000, 200); //Current player's hand
		g.drawRect(425, 575, 100, 75); //button to show cards current player has built
		g.drawRect(500, 100, 75, 75); //current rotation
		g.drawRect(700, 100, 75, 75); //current age
		g.drawRect(1350, 725, 100, 75); //button to burn cards 
		
	}
	public void mousePressed(MouseEvent arg0) 
	{
		//System.out.println(arg0.getX() + " " + arg0.getY());
		//1450, 50, 100, 100
		if (arg0.getX() > 1450 && arg0.getX() < 1550 && arg0.getY() > 50 && arg0.getY() < 150)
		{
			try 
			{
				PlayerFrame test = new PlayerFrame();
				test.revalidate();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
	{
		try
		{
			GameFrame temp = new GameFrame();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
