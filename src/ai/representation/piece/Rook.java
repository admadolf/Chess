package ai.representation.piece;

import java.util.ArrayList;
import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.Vector;

public class Rook extends Piece {
	
	static List<Integer> moveVectors = new ArrayList<>();
	
	
	static {
		moveVectors.add(Vector.NORTH);
		moveVectors.add(Vector.EAST);
		moveVectors.add(Vector.SOUTH);
		moveVectors.add(Vector.WEST);
	}
	
	public Rook(Color color) {
		super(PieceType.ROOK, color);
	}

	public static List<Integer> getMoveVectors() {
		return moveVectors;
	}
	
}
