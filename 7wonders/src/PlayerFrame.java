import static java.lang.System.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
@SuppressWarnings("serial")
public class PlayerFrame extends JFrame implements MouseListener
{
    public static final int LENGTH = 1600;
    public static final int HEIGHT = 1000;
    private static Board board;
    Player player;
    private boolean isMain;
    public PlayerFrame(Player player, Board b) throws IOException
    {
        super("Seven Wonders");
        board = b;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150, 25, LENGTH, HEIGHT);
        addMouseListener(this);
        this.player = player;
        setMain(false);
    }
    public PlayerFrame() throws IOException
    {
        super("Seven Wonders");
        board = new Board();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setBounds(150, 25, LENGTH, HEIGHT);
        addMouseListener(this);
        this.player = board.getCurrentPlayer();
        setMain(true);
    }
    public void setBoard(Board board2)
    {
        board = board2;
    }
    public void paint(Graphics g)
    {
        ArrayList < Card > hand = board.getCurrentPlayer().getHand();
        if (!board.gameFinished())
        {
            try
            {
                if (isMain()) 
                	setPlayer(board.getCurrentPlayer());
                BufferedImage wonderbackground = ImageIO.read(new File("images\\wonderbackgrounds\\"+player.getWonder().getName()+"bk.jpg"));
                BufferedImage sampleWonder = ImageIO.read(new File("images\\wonders\\" + player.getWonder().getName() + ".png"));
                BufferedImage currentage = ImageIO.read(new File("images\\assets\\age" + board.getCurrentAge() + ".png"));
                g.drawImage(wonderbackground, 0, 0, LENGTH, HEIGHT, null);
                g.drawImage(sampleWonder, 250, 250, 1100, 342, null);
                g.drawImage(currentage, 750, 100, 100, 100, null);
                g.setFont(new Font("Arial", Font.PLAIN, 50));
                int currentPlayer = player.getIndex() + 1;
                g.drawString("Player " + currentPlayer, 350, 175);
                //Derek's space. Click to open new window
                BufferedImage clicktoshowcards = ImageIO.read(new File("images\\assets\\card.png"));
                g.drawImage(clicktoshowcards, 1000, 100, 100, 100, null);
                g.setColor(new Color(0, 102, 225));
                //Area to click; shows cards that player has played
                g.drawRect(1000, 100, 100, 100);
                Player p = player;
                g.setColor(Color.black);
                //g.drawRect(250, 250, 1100, 342); //Current player's wonder
                //g.drawRect(750, 100, 100, 100); //current age
                if (p.getWonderStage() >= 1) g.setColor(Color.green);
                else g.setColor(Color.red);
                g.drawRect(325, 510, 280, 85); //Wonder stage 1
                if (p.getWonderStage() >= 2) g.setColor(Color.green);
                else g.setColor(Color.red);
                g.drawRect(655, 510, 280, 85); //Wonder stage 2
                if (p.getWonderStage() >= 3) g.setColor(Color.green);
                else g.setColor(Color.red);
                g.drawRect(980, 510, 280, 85); //Wonder stage 3
            }
            catch (IOException e)
            {
            }
        }
    }
    @SuppressWarnings("unused")
    public void mouseClicked(MouseEvent event)
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
    public void mouseReleased(MouseEvent event)
    {
        Player player = this.player;
        //1000, 100, 100, 100
        if (event.getX() < 1100 && event.getY() < 200 && event.getX() > 1000 && event.getY() > 100) //shows card window
        {
            //			out.println("Pressed!");
            CardWindow cards = new CardWindow(player);
        }
        repaint();
    }
    public Board getBoard()
    {
        return board;
    }
    public Player getPlayer()
    {
        return player;
    }
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    public boolean isMain()
    {
        return isMain;
    }
    public void setMain(boolean isMain)
    {
        this.isMain = isMain;
    }
}