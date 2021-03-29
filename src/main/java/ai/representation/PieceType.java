package ai.representation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PieceType {
	ROOK("R", Arrays.asList(new Integer[] {
			Vector.NORTH,
			Vector.EAST,
			Vector.SOUTH,
			Vector.WEST})),
	KNIGHT("N", Arrays.asList(new Integer[] {
			Vector.NORTH+Vector.NW, 
			Vector.NORTH+Vector.NE,
			Vector.EAST+Vector.NE,
			Vector.EAST+Vector.SE,
			Vector.SOUTH+Vector.SE,
			Vector.SOUTH+Vector.SW,
			Vector.WEST+Vector.SW,
			Vector.WEST+Vector.NW})),
	BISHOP("B", Arrays.asList(new Integer[] {Vector.NW,Vector.NE,Vector.SE,Vector.SW})),
	QUEEN("Q", Arrays.asList(
			new Integer[] {
					Vector.NW,Vector.NE,Vector.SE,Vector.SW,
					Vector.NORTH,Vector.EAST,Vector.SOUTH,Vector.WEST
			})),
	KING("K", Arrays.asList(new Integer[] {
			Vector.NW,Vector.NE,Vector.SE,Vector.SW,
			Vector.NORTH,Vector.EAST,Vector.SOUTH,Vector.WEST
	})), //TODO movegen doesnt use these but separately rook and bishop ones still they're noted here
	PAWN("P", Arrays.asList(new Integer[] {
			Vector.NORTH,
			Vector.NW,
			Vector.NE,
			Vector.SOUTH,
			Vector.SW,
			Vector.SE
			})),
	EMPTY("E", Arrays.asList(new Integer[] {}));

	private final String text;

	private List<Integer> moveVectors = new ArrayList<>();
	
	public String toString() {
		return text;
	}

	private PieceType(String text, List<Integer> moveVectors) {
		this.text = text;
		this.moveVectors = moveVectors;
	}

	public List<Integer> getMoveVectors() {
		return moveVectors;
	}

	public void setMoveVectors(List<Integer> moveVectors) {
		this.moveVectors = moveVectors;
	}
	
}
