import static java.lang.System.out;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VictoryWindow extends JFrame
{
	private TreeMap<String, ArrayList<Card>> playedCards;
	public static final int LENGTH = 650;
    public static final int HEIGHT = 396;
	
	public VictoryWindow(Player p)
	{
		super("Congrats on Finishing!");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        playedCards = p.getPlayedCards();
        //out.println(playedCards);
	}
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage victoryscreen = ImageIO.read(new File("images\\assets\\clipart.jpg"));
			g.drawImage(victoryscreen, 0, 0, LENGTH, HEIGHT, null);
			
		}
		catch (IOException e)
		{
			//out.println(e);
		}
	}
	
}
