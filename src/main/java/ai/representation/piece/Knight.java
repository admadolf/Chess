package ai.representation.piece;

import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;

public class Knight extends ColoredPiece {

	public Knight(Color color) {
		super(PieceType.KNIGHT, color);
	}

	public static List<Integer> getMoveVectors() {
		return PieceType.KNIGHT.moveVectors;
	}
}
