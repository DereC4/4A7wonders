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
    private int player1vp, player2vp, player3vp;
    private Board board;
	
	public VictoryWindow(int player1vp, int player2vp, int player3vp,Board b)
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
        board=b;
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
			TreeMap<Integer,Player>list=new TreeMap<Integer, Player>();
			list.put(player1vp,board.getPlayerList().get(0));
			list.put(player2vp,board.getPlayerList().get(1));
			list.put(player3vp,board.getPlayerList().get(2));
			
			ArrayList <Integer> score = new ArrayList<Integer>();
			score.add(player1vp);
			score.add(player2vp);
			score.add(player3vp);
			Collections.sort(score);
			
			//System.out.println(score);
			
			
			g.drawString("Player "+(list.get(score.get(1)).getIndex()+1)+"'s VP: "+score.get(1), 300, 250);
//			g.drawString(""+score.get(1), 300, 250);
			g.drawString("Player "+(list.get(score.get(2)).getIndex()+1)+"'s VP: "+score.get(2), 600, 100);
			g.drawString("Player "+(list.get(score.get(0)).getIndex()+1)+"'s VP: "+score.get(0), 900, 300);
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