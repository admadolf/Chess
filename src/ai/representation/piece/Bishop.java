package ai.representation.piece;

import java.util.ArrayList;
import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.Vector;

public class Bishop extends Piece {

	static List<Integer> moveVectors = new ArrayList<>();
	
	
	static {
		moveVectors.add(Vector.NW);
		moveVectors.add(Vector.NE);
		moveVectors.add(Vector.SE);
		moveVectors.add(Vector.SW);
	}
	
	public Bishop(Color color) {
		super(PieceType.BISHOP, color);
	}

	public static List<Integer> getMoveVectors() {
		return moveVectors;
	}
	
}
