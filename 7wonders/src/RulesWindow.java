import static java.lang.System.out;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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
	}
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			try
			{
			    Font introfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\Minecraftia.ttf")).deriveFont(12f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    ge.registerFont(introfont);
			    g.setFont(introfont);
			    Font currentFont = g.getFont();
			    Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
			    g.setFont(newFont);
			}
			catch (IOException e) 
			{
			    e.printStackTrace();
			} 
			catch(FontFormatException e)
			{
			    e.printStackTrace();
			}
		    g.drawString("Rules of the Duel", 25, 75);
		    Font currentFont = g.getFont();
		    Font newFont = currentFont.deriveFont(currentFont.getSize() * .7F);
		    g.setFont(newFont);
		    g.drawString("Click ", 25, 110);
		    BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
            g.drawImage(clicktoshowcards, 100, 85, 50, 50, null);
            g.drawString("to display your cards", 150, 110);
		}
		catch (IOException e)
		{
			out.println(e);
		}
	}
	public static void main (String args[])
	{
		RulesWindow rules = new RulesWindow();
	}
}