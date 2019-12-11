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
	public static final int LENGTH = 1300;
    public static final int HEIGHT = 792;
    private Board board;
	
	public VictoryWindow(Board b)
	{
		super("Congrats on Finishing!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        board=b;
	}
	@SuppressWarnings("unchecked")
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
			
			ArrayList<Player> players = new ArrayList<Player>();
			for (Player p : board.getPlayerList())
				players.add(p);
			Collections.sort(players);
			
			g.drawString("Player " + (players.get(2).getIndex()+1) +"'s VP: "+ players.get(2).getVp(), 300, 250);
//			g.drawString(""+score.get(1), 300, 250);
			g.drawString("Player " + (players.get(1).getIndex()+1) +"'s VP: "+ players.get(1).getVp(), 600, 100);
			g.drawString("Player " + (players.get(0).getIndex()+1) +"'s VP: "+ players.get(0).getVp(), 900, 300);
		}
		catch (IOException e)
		{
			out.println(e);
		}
	}
	/*public static void main (String args[])
	{
		VictoryWindow x = new VictoryWindow(3, 2, 1, board );
	}*/
}