import static java.lang.System.out;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VictoryWindow extends JFrame
{
	private TreeMap<String, ArrayList<Card>> playedCards;
	public static final int LENGTH = 1300;
    public static final int HEIGHT = 792;
    private int player1vp, player2vp, player3vp;
	
	public VictoryWindow(int player1vp, int player2vp, int player3vp)
	{
		super("Congrats on Finishing!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        this.player1vp = player1vp;
        this.player2vp = player2vp;
        this.player3vp = player3vp;
        //out.println(playedCards);
	}
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage victoryscreen = ImageIO.read(new File("images\\assets\\clipart.png"));
			g.drawImage(victoryscreen, 0, 0, LENGTH, HEIGHT, null);
//			String fName = "fonts\\Minecraftia.ttf";
//			InputStream x = VictoryWindow.class.getResourceAsStream(fName);
			try
			{
			    Font victoryfont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\Minecraftia.ttf")).deriveFont(12f);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    ge.registerFont(victoryfont);
			    g.setFont(victoryfont);
			}
			catch (IOException e) 
			{
			    e.printStackTrace();
			} 
			catch(FontFormatException e)
			{
			    e.printStackTrace();
			}
			//maybe add more game stats later
			ArrayList <Integer> score = new ArrayList<Integer>();
			score.add(player1vp);
			score.add(player2vp);
			score.add(player3vp);
			Collections.sort(score);
			System.out.println(score);
			g.drawString("Player 1's VP", 300, 250);
			g.drawString("Player 2's VP", 600, 100);
			g.drawString("Player 3's VP", 900, 300);
		}
		catch (IOException e)
		{
			out.println(e);
		}
	}
	public static void main (String args[])
	{
		VictoryWindow x = new VictoryWindow(3, 2, 1);
	}
}