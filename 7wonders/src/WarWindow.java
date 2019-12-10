import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WarWindow extends JFrame
{
	public static final int LENGTH = 1300;
    public static final int HEIGHT = 792;
	
	public WarWindow()
	{
		super("War Time");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150,25,LENGTH,HEIGHT);
        URL mon = getClass().getResource("images\\assets\\mongoltage.gif");
        Icon mongol = new ImageIcon(mon);
        JLabel mongoltage = new JLabel(mongol);
        super.add(mongoltage);
	}
	
	public static void main (String args[])
	{
		WarWindow mongoltage = new WarWindow();
	}
}