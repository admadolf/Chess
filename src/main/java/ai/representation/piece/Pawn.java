package ai.representation.piece;

import java.util.List;
import java.util.stream.Collectors;

import ai.representation.Color;
import ai.representation.PieceType;

public class Pawn extends ColoredPiece {

	public Pawn(Color color) {
		super(PieceType.PAWN, color);
	}

	public static List<Integer> getMoveVectors(Color color) {
		return color == Color.LIGHT
				? PieceType.PAWN.getMoveVectors().stream().filter(i -> i > 0).collect(Collectors.toList())
				: PieceType.PAWN.getMoveVectors().stream().filter(i -> i < 0).collect(Collectors.toList());
	}

}
