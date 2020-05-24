package ai.representation.piece;

import java.util.ArrayList;
import java.util.List;

import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.Vector;

public class Knight extends Piece {

	static List<Integer> moveVectors = new ArrayList<>();
	
	
	static {
		moveVectors.add(Vector.NORTH+Vector.NW); //felbal 15
		moveVectors.add(Vector.NORTH+Vector.NE); //feljobb 17
		moveVectors.add(Vector.EAST+Vector.NE); //jobbfel 10
		moveVectors.add(Vector.EAST+Vector.SE); //jobble -6
		moveVectors.add(Vector.SOUTH+Vector.SE); //lejobb -15
		moveVectors.add(Vector.SOUTH+Vector.SW); //lebal -17
		moveVectors.add(Vector.WEST+Vector.SW); //balle -10
		moveVectors.add(Vector.WEST+Vector.NW); //balfel 6 
	}
	
	public Knight(Color color) {
		super(PieceType.KNIGHT, color);
	}

	public static List<Integer> getMoveVectors() {
		return moveVectors;
	}
}
