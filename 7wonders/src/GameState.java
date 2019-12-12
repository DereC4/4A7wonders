import java.io.IOException;
import java.util.ArrayList;

public class GameState 
{
	private Board boardCopy;
	public GameState() throws IOException 
	{
		boardCopy=new Board();
	}
	public GameState(Board newboard) throws IOException
	{
		boardCopy=newboard;
	}
	public void setGameState(Board newboard)
	{
		boardCopy=newboard;
	}
	public void addtonextplayer(Card c)
	{
		boardCopy.getCurrentPlayer().addToHand(c);
	}
	public ArrayList <Card> getcurrentplayerhand()
	{
		return boardCopy.getCurrentPlayer().getHand();
	}
	public Player getboardplayer()
	{
		return boardCopy.getCurrentPlayer();
	}
	public int getcurrentplayerindex()
	{
		return boardCopy.getPlayerList().indexOf(boardCopy.getCurrentTurn());
	}
}