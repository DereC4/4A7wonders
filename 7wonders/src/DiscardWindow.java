import static java.lang.System.out;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DiscardWindow extends JFrame implements MouseListener
{
	private ArrayList<Card> discard;
	private HashMap<String, Integer> coords;
	private Player player;
	private Board board;
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
    
    public DiscardWindow(Board b)
    {
    	super("Graveyard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        addMouseListener(this);
        board = b;
        player = board.getCurrentPlayer();
        discard = board.getDeck().getDiscard();
        //discard = board.getDeck().getAgeTwo(); //temp
        coords = new HashMap<String, Integer>();
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
			int y = 100;
			int num = 0;
			for (int i = 0; i < discard.size(); i++)
			{
				sampleCard = ImageIO.read(new File("images\\cards\\" + discard.get(i).getName().toLowerCase() + ".png"));
				if (num * 160 >= 1400)
				{
					y += 239;
					num = 0;
				}
				int posX = 50 + (num * 160);
				int endX = posX + 150;
				int endY = y + 229;
				g.drawImage(sampleCard, 50 + (num++ * 160), y, 150, 229, null);
				coords.put(posX + "," + endX + "," + y + "," + endY, i);
			}
    	}
    	catch (IOException e)
    	{
    		out.println(e);
    	}
    }

	public void mouseClicked(MouseEvent arg0) 
	{
	}

	public void mouseEntered(MouseEvent arg0) 
	{
	}

	public void mouseExited(MouseEvent arg0) 
	{
	}

	public void mousePressed(MouseEvent arg0) 
	{
	}

	public void mouseReleased(MouseEvent e) 
	{
		//out.println("hey");
		for (String coord : coords.keySet()) 
		{
			String[] temp = coord.split(",");
			int startX = Integer.parseInt(temp[0]);
			int endX = Integer.parseInt(temp[1]);
			int startY = Integer.parseInt(temp[2]);
			int endY = Integer.parseInt(temp[3]);
			
			if (e.getX() > startX && e.getX() < endX)
				if (e.getY() > startY && e.getY() < endY)
				{
					//bypass playable
					//out.println(discard.get(coords.get(coord)));
					Card card = discard.get(coords.get(coord));
					player.addTempPlayedCard(card);
					discard.remove(card);
					board.setDrawDiscard(false);
					dispose();
				}
		}
	}
}
