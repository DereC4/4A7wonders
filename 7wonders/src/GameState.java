import java.io.IOException;
import java.util.ArrayList;

public class GameState {
	private Board boardCopy;

	public GameState() throws IOException {
		boardCopy = new Board();
	}

	public GameState(Board newboard) throws IOException {
		boardCopy = newboard;
	}

	public void setGameState(Board newboard) {
		boardCopy = newboard;
	}

	public void addtonextplayer(Card c) {
		boardCopy.getCurrentPlayer().addToHand(c);
	}

	public ArrayList<Card> getcurrentplayerhand() {
		return boardCopy.getCurrentPlayer().getHand();
	}

	public Player getboardplayer() {
		return boardCopy.getCurrentPlayer();
	}

	public int getcurrentplayerindex() {
		return boardCopy.getPlayerList().indexOf(boardCopy.getCurrentTurn());
	}

	public int getCurrentAge() {
		return boardCopy.getCurrentAge();
	}

	public void setCurrentAge(int currentAge) {
		boardCopy.setCurrentAge(currentAge);
	}

	public void setOnWards(boolean onWards) {
		boardCopy.setOnWards(onWards);
	}

	public Player getCurrentPlayer() {
		return boardCopy.getCurrentPlayer();
	}

	public int getCurrentTurn() {
		return boardCopy.getCurrentTurn();
	}

	public void setAge1CardQuantity(int age1CardQuantity) {
		boardCopy.setAge1CardQuantity(age1CardQuantity);
	}

	public void setAge2CardQuantity(int age2CardQuantity) {
		boardCopy.setAge2CardQuantity(age2CardQuantity);
	}

	public void setAge3CardQuantity(int age3CardQuantity) {
		boardCopy.setAge3CardQuantity(age3CardQuantity);
	}
}