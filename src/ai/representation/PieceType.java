package ai.representation;

public enum PieceType {
	ROOK("R"), KNIGHT("N"), BISHOP("B"), QUEEN("Q"), KING("K"), PAWN("P"), EMPTY("E");

	public final String text;

	public String toString() {
		return text;
	}

	private PieceType(String text) {
		this.text = text;
	}
	
}
