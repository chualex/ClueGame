package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color, boolean human) {
		super(playerName, row, column, color, human);
	}

	@Override
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	

}
