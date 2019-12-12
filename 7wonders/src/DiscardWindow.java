import static java.lang.System.out;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
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
    
    public DiscardWindow(Board b, Player p)
    {
    	super("Graveyard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        addMouseListener(this);
        board = b;
        player = p;
        discard = board.getDeck().getDiscard();
        //discard = board.getDeck().getAgeTwo(); //temp
        coords = new HashMap<String, Integer>();
    }
    
    public void paint(Graphics g)
    {
    	try 
    	{
    		BufferedImage background = ImageIO.read(new File("images\\assets\\yourgrave.jpg"));
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
			g.setColor(new Color(26, 109, 176));
			g.fillRect(925, 750, 350, 125);
			g.setColor(Color.black);
			g.drawRect(925, 750, 350, 125);
			g.setFont(new Font("Arial", Font.BOLD, 45));
			g.drawString("Player " + (player.getIndex()+1) + "'s", 975, 800);
			g.drawString("Graveyard", 975, 850);
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
					player.setIgnoreCost(true);
					discard.remove(card);
					player.setDrawDiscard(false);
					board.setToDrawDiscard(null);
					dispose();
				}
		}
	}
	
	/*
	public static void main(String[] args) throws IOException
	{
		Board b= new Board();
		DiscardWindow hi = new DiscardWindow(b, b.getCurrentPlayer());
	}
	*/
}
