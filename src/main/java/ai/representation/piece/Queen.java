package ai.representation.piece;

import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class Queen extends ColoredPiece {

	public Queen(Color color) {
		super(PieceType.QUEEN, color);
	}

	public static List<Integer> getMoveVectors() {
		return PieceType.QUEEN.moveVectors;
	}

}
