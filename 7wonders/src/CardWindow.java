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
public class CardWindow extends JFrame
{
	private TreeMap<String, ArrayList<Card>> playedCards;
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
	
	public CardWindow(Player p)
	{
		super("Player " + incrementIndex(p.getIndex()) + "'s Cards");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        playedCards = p.getPlayedCards();
        //out.println(playedCards);
	}
	public static int incrementIndex(int i)
	{
		return ++i;
	}
	public void paint(Graphics g)
	{
		try
		{
			BufferedImage background = ImageIO.read(new File("images\\background.jpg"));
			BufferedImage sampleCard;
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			int numBrown = 0;
			int numGray = 0;
			int numYellow = 0;
			int numBlue = 0;
			int numGreen = 0;
			int numRed = 0;
			int numPurple = 0;
			
			for (String color : playedCards.keySet())
			{
				for (Card card : playedCards.get(color))
				{
					sampleCard = ImageIO.read(new File("images\\cards\\" + card.getName().toLowerCase() + ".png"));
					if (color.equalsIgnoreCase("Brown"))
					{
						g.drawImage(sampleCard, 50 + (numBrown * 25), 50 + (numBrown * 45), 125, 190, null);
						numBrown++;
					}
					else if (color.equalsIgnoreCase("Gray") || color.equalsIgnoreCase("Silver"))
					{
						g.drawImage(sampleCard, 300 + (numGray * 25), 50 + (numGray * 45), 125, 190, null);
						numGray++;
					}
					else if (color.equalsIgnoreCase("Yellow"))
					{
						g.drawImage(sampleCard, 550 + (numYellow * 25), 50 + (numYellow * 45), 125, 190, null);
						numYellow++;
					}
					else if (color.equalsIgnoreCase("Blue"))
					{
						g.drawImage(sampleCard, 800 + (numBlue * 25), 50 + (numBlue * 45), 125, 190, null);
						numBlue++;
					}
					else if (color.equalsIgnoreCase("Green"))
					{
						g.drawImage(sampleCard, 50 + (numGreen * 25), 500 + (numGreen * 45), 125, 190, null);
						numGreen++;
					}
					else if (color.equalsIgnoreCase("Red"))
					{
						g.drawImage(sampleCard, 300 + (numRed * 25), 500 + (numRed * 45), 125, 190, null);
						numRed++;
					}
					else if (color.equalsIgnoreCase("Purple"))
					{
						g.drawImage(sampleCard, 550 + (numPurple * 25), 500 + (numPurple * 45), 125, 190, null);
						numPurple++;
					}
				}
				//g.drawImage(sampleCard, 50 + (i * 1), 50, 125, 190, null);
			}
		}
		catch (IOException e)
		{
			//out.println(e);
		}
	}
	
}
