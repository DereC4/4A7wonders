import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.io.IOException;

public class WarWindow extends JFrame {
	private JLabel mongoltage;
	private URL mon;
	public static final int LENGTH = 1600;
	public static final int HEIGHT = 1000;
	Board board;

	public WarWindow(Board b) {
		super("WAR TIME");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setBounds(150, 25, LENGTH, HEIGHT);
		Mongol();
		board = b;
	}

	public void Mongol() {
		try {
			mon = new URL("https://thumbs.gfycat.com/AllBothBlackfish-size_restricted.gif");
			Icon mongol = new ImageIcon(mon);
			mongoltage = new JLabel(mongol);
			setLayout(new FlowLayout());

			add(mongoltage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		Board b = new Board();
		WarWindow mongoltage = new WarWindow(b);
	}
}