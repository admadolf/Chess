package ai.representation.piece;

import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class Bishop extends ColoredPiece {

	public Bishop(Color color) {
		super(PieceType.BISHOP, color);
	}

	public static List<Integer> getMoveVectors() {
		return PieceType.BISHOP.getMoveVectors();
	}
	
}
