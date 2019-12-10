import static java.lang.System.out;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
/*
 *  NOTE
 * THIS ASSUMES PLAYER KNOWS HOW TO PLAY CARDS AND STUFF
 * XD
 */
public class RulesWindow extends JFrame
{
	public static final int LENGTH = 1370;
    public static final int HEIGHT = 900;
	
	public RulesWindow()
	{
		super("Rules of the Duel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        //out.println(playedCards);
	}
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage cardinfo = ImageIO.read(new File("images\\rules\\cardinfo.png"));
			BufferedImage resources = ImageIO.read(new File("images\\rules\\resources.png"));
			BufferedImage moreresource = ImageIO.read(new File("images\\rules\\morefunctions.png"));
			BufferedImage guilds1 = ImageIO.read(new File("images\\rules\\guilds1.png"));
			BufferedImage guilds2 = ImageIO.read(new File("images\\rules\\guilds2.png"));
			BufferedImage endofage = ImageIO.read(new File("images\\rules\\endofage.png"));
			BufferedImage boards = ImageIO.read(new File("images\\rules\\boards.png"));
			BufferedImage finalfunctions = ImageIO.read(new File("images\\rules\\finalfunctions.png"));
			BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
			
			
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			
			g.drawImage(resources, 0, 25, resources.getWidth(), resources.getHeight(), null);
			g.drawImage(boards, 0, 510, resources.getWidth(), boards.getHeight(), null);
			g.drawImage(moreresource, 350, 25, moreresource.getWidth(), moreresource.getHeight(), null);
			
			g.drawImage(cardinfo, 689, 25, cardinfo.getWidth(), cardinfo.getHeight(), null);
			g.drawImage(endofage, 689, 25+cardinfo.getHeight(), endofage.getWidth(), endofage.getHeight(), null);
			
			g.drawImage(guilds1, LENGTH-guilds1.getWidth(), 25, guilds1.getWidth(), guilds1.getHeight(), null);
//			g.drawImage(guilds2, 689+guilds1.getWidth(), 25, guilds2.getWidth(), guilds2.getHeight(), null);
			g.drawImage(guilds2, LENGTH-guilds1.getWidth(), 25+guilds1.getHeight(), guilds1.getWidth(), guilds2.getHeight(), null);

		}
		catch (IOException e)
		{
			out.println(e);
		}
	}
	public static void main (String args[])
	{
		RulesWindow x = new RulesWindow();
	}
}