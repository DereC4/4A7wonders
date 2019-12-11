import static java.lang.System.out;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.Color;
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
	public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
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
			BufferedImage stand = ImageIO.read(new File("images\\assets\\clipart.png"));
			BufferedImage background = ImageIO.read(new File("images\\assets\\confetti.jpeg"));
			g.drawImage(background, 0, 0, LENGTH, HEIGHT, null);
			g.drawImage(stand, 550, 50, 533, 325, null);
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
			{
				players.add(p);
				/*
				int totalVp = 0;
				for (String type : p.getVpSources().keySet())
				{
					totalVp += p.getVpSources().get(type);
				}
				out.println("Player " + p.getIndex() + "'s VP: " + totalVp);
				*/
			}
			Collections.sort(players);
			Player p3 = players.get(2); //most VP
			Player p2 = players.get(1);
			Player p1 = players.get(0);
			TreeMap<String, Integer> vpSources;
			
			g.drawString("Player " + (p3.getIndex()+1), 780, 100);
//			g.drawString(""+score.get(1), 300, 250);
			g.drawString("Player " + (p2.getIndex()+1), 640, 160);
			g.drawString("Player " + (p1.getIndex()+1), 920, 175);
			
			//1st place
			vpSources = p3.getVpSources();
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString("Player " + (p3.getIndex()+1), 25, 450);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Total VP: " + p3.getVp(), 25, 500);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.setColor(new Color(208, 25, 30));
			g.drawString("VP from War: " + vpSources.get("War"), 25, 550);
			g.setColor(new Color(243, 131, 31));
			g.drawString("VP from Coins: " + vpSources.get("Coins"), 25, 600);
			g.setColor(Color.black);
			g.drawString("VP from Wonder: " + vpSources.get("Wonder"), 25, 650);
			g.setColor(new Color(29, 51, 134));
			g.drawString("VP from Blue Cards: " + vpSources.get("BlueCards"), 25, 700);
			g.setColor(new Color(2, 125, 59));
			g.drawString("VP from Sciences: " + vpSources.get("Science"), 25, 750);
			g.setColor(new Color(245, 119, 21));
			g.drawString("VP from Yellow Cards: " + vpSources.get("YellowCards"), 25, 800);
			g.setColor(new Color(76, 5, 96));
			g.drawString("VP from Guilds: " + vpSources.get("GuildCards"), 25, 850);
			
			//2nd place
			vpSources = p2.getVpSources();
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString("Player " + (p2.getIndex()+1), 600, 450);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Total VP: " + p2.getVp(), 600, 500);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.setColor(new Color(208, 25, 30));
			g.drawString("VP from War: " + vpSources.get("War"), 600, 550);
			g.setColor(new Color(243, 131, 31));
			g.drawString("VP from Coins: " + vpSources.get("Coins"),600, 600);
			g.setColor(Color.black);
			g.drawString("VP from Wonder: " + vpSources.get("Wonder"),600, 650);
			g.setColor(new Color(29, 51, 134));
			g.drawString("VP from Blue Cards: " + vpSources.get("BlueCards"),600, 700);
			g.setColor(new Color(2, 125, 59));
			g.drawString("VP from Sciences: " + vpSources.get("Science"),600, 750);
			g.setColor(new Color(245, 119, 21));
			g.drawString("VP from Yellow Cards: " + vpSources.get("YellowCards"),600, 800);
			g.setColor(new Color(76, 5, 96));
			g.drawString("VP from Guilds: " + vpSources.get("GuildCards"),600, 850);
			
			//3rd place
			vpSources = p1.getVpSources();
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.setColor(Color.black);
			g.drawString("Player " + (p1.getIndex()+1), 1225, 450);
			g.setFont(new Font("Arial", Font.BOLD, 25));
			g.drawString("Total VP: " + p1.getVp(), 1225, 500);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.setColor(new Color(208, 25, 30));
			g.drawString("VP from War: " + vpSources.get("War"), 1225, 550);
			g.setColor(new Color(243, 131, 31));
			g.drawString("VP from Coins: " + vpSources.get("Coins"), 1225, 600);
			g.setColor(Color.black);
			g.drawString("VP from Wonder: " + vpSources.get("Wonder"), 1225, 650);
			g.setColor(new Color(29, 51, 134));
			g.drawString("VP from Blue Cards: " + vpSources.get("BlueCards"), 1225, 700);
			g.setColor(new Color(2, 125, 59));
			g.drawString("VP from Sciences: " + vpSources.get("Science"), 1225, 750);
			g.setColor(new Color(245, 119, 21));
			g.drawString("VP from Yellow Cards: " + vpSources.get("YellowCards"),1225, 800);
			g.setColor(new Color(76, 5, 96));
			g.drawString("VP from Guilds: " + vpSources.get("GuildCards"),1225, 850);
		
		}
		catch (IOException e)
		{
			out.println(e);
		}
	}
	public static void main (String args[]) throws IOException
	{
		Board board = new Board();
		VictoryWindow x = new VictoryWindow(board);
	}
}