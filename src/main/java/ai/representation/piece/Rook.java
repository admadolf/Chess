package ai.representation.piece;

import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class Rook extends ColoredPiece {
	
	public Rook(Color color) {
		super(PieceType.ROOK, color);
	}

	public static List<Integer> getMoveVectors() {
		return PieceType.ROOK.getMoveVectors();
	}
	
}
