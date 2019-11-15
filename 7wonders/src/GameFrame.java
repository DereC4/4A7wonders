import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameFrame extends PlayerFrame
{
    private Board board = super.getBoard();
    
    public GameFrame()
    {
    	super();
    }
    
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
			BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\babylon.png"));
			int rand = (int)(Math.random()*(7))+1;
			System.out.println(rand);
			if(rand==1)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\alexandria.png"));
			}
			if(rand==2)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\babylon.png"));
			}
			if(rand==3)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\ephesos.png"));
			}
			if(rand==4)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\gizah.png"));
			}
			if(rand==5)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\halikarnassus.png"));
			}
			if(rand==6)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\olympia.png"));
			}
			if(rand==7)
			{
				sampleWonder = ImageIO.read(new File("images\\wonders\\rhodos.png"));
			}
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			g.drawImage(sampleWonder, 425, 250, 700, 300, null);
		}
		catch (IOException e) //Print the error
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
	public void mousePressed(MouseEvent arg0) 
	{
		//System.out.println(arg0.getX() + " " + arg0.getY());
		//1450, 50, 100, 100
		if (arg0.getX() > 1450 && arg0.getX() < 1550 && arg0.getY() > 50 && arg0.getY() < 150)
		{
			PlayerFrame test = new PlayerFrame();
		}
	}
	public static void main(String[] args)
	{
		GameFrame temp = new GameFrame();
	}
}
