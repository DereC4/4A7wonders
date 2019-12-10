import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WarWindow
{
	private JFrame frame;
	private JLabel mongoltage;
	private URL mon;
	public WarWindow()
	{
		frame = new JFrame("WAR TIME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mongol();
	}
	public void Mongol()
	{
		try 
		{
			//whap flashbacks
			mon = new URL("https://thumbs.gfycat.com/AllBothBlackfish-size_restricted.gif");
			Icon mongol = new ImageIcon(mon);
	        mongoltage = new JLabel(mongol);
	        
	        frame.getContentPane().add(mongoltage);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        frame.setResizable(false);
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
//	public static void main (String args[])
//	{
//		WarWindow mongoltage = new WarWindow();
//	}
}