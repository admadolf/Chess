package ai.representation.piece;

import java.util.ArrayList;
import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class King extends Piece {

	static List<Integer> moveVectors = new ArrayList<>();
	
	
	static {
		moveVectors.addAll(Bishop.moveVectors);
		moveVectors.addAll(Rook.moveVectors);
	}
	
	public King(Color color) {
		super(PieceType.KING, color);
	}

	public static List<Integer> getMoveVectors() {
		return moveVectors;
	}
	
}
