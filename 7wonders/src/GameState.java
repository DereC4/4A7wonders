import java.io.IOException;

public class GameState {
	private Board boardCopy;
	public GameState() throws IOException {
		boardCopy=new Board();
	}
	public GameState(Board b) throws IOException{
		boardCopy=b;
	}
	public void setGameState(Board ib) {
		boardCopy=ib;
	}
}
