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
    private TreeMap<String,Integer>player1tm, player2tm, player3tm;
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
	public VictoryWindow(TreeMap<String, Integer>player1vp, TreeMap<String, Integer>player2vp, TreeMap<String, Integer>player3vp,Board b)
	{
		super("Congrats on Finishing!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        player1tm = player1vp;
        player2tm = player2vp;
        player3tm = player3vp;
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
			out.println(board.getPlayerList());
			TreeMap<Integer,Integer> players =new TreeMap<Integer, Integer>();
			int p1 = board.getPlayerList().get(0).getIndex();
			int p2 = board.getPlayerList().get(1).getIndex();
			int p3 = board.getPlayerList().get(2).getIndex();
			players.put(p1,player1vp);
			players.put(p2,player2vp);
			players.put(p3,player3vp);
			
			ArrayList <Integer> score = new ArrayList<Integer>();
			score.add(players.get(p1));
			score.add(players.get(p2));
			score.add(players.get(p3));
			Collections.sort(score);
			
			//System.out.println(score);
			//out.println(players);
			int first = 0;
			int second = 0;
			int third = 0;
			
			for (int index : players.keySet())
			{
				if (players.get(index).equals(score.get(2)))
					first = index;
				else if (players.get(index).equals(score.get(1)))
					second = index;
				else
					third = index;
			}
			
			g.drawString("Player " + first +"'s VP: "+ score.get(2), 300, 250);
//			g.drawString(""+score.get(1), 300, 250);
			g.drawString("Player " + second +"'s VP: "+ score.get(1), 600, 100);
			g.drawString("Player " + third +"'s VP: "+ score.get(0), 900, 300);
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