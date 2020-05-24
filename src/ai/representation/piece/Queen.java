package ai.representation.piece;

import java.util.ArrayList;
import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class Queen extends Piece {

	static List<Integer> moveVectors = new ArrayList<>();
	
	
	static {
		moveVectors.addAll(Bishop.moveVectors);
		moveVectors.addAll(Rook.moveVectors);
	}
	
	public Queen(Color color) {
		super(PieceType.QUEEN, color);
	}

	public static List<Integer> getMoveVectors() {
		return moveVectors;
	}

}
