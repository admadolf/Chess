package ai.representation.piece;

import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class King extends ColoredPiece {

	public King(Color color) {
		super(PieceType.KING, color);
	}

	public static List<Integer> getMoveVectors() {
		return PieceType.KING.getMoveVectors();
	}
	
}
