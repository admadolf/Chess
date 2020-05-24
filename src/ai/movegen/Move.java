package ai.movegen;

import ai.representation.piece.Piece;

public class Move implements Comparable<Move>{

	private int from;
	
	private int to;
	
	private Piece fromPiece;
	
	private Piece toPiece;
	
	private int moveRating;
	
	public Move(int from, int to, Piece fromPiece, Piece toPiece) {
		super();
		this.from = from;
		this.to = to;
		this.fromPiece = fromPiece;
		this.toPiece = toPiece;
	}

	@Override
	public String toString() {
		return "Move [from=" + from + ", to=" + to + ", fromPiece=" + fromPiece + ", toPiece=" + toPiece + ", moveRating=" + moveRating +  "]";
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public Piece getFromPiece() {
		return fromPiece;
	}

	public void setFromPiece(Piece fromPiece) {
		this.fromPiece = fromPiece;
	}

	public Piece getToPiece() {
		return toPiece;
	}

	public void setToPiece(Piece toPiece) {
		this.toPiece = toPiece;
	}

	public int getMoveRating() {
		return moveRating;
	}

	public void setMoveRating(int moveScoreRating) {
		this.moveRating = moveScoreRating;
	}

	@Override
	public int compareTo(Move o) {
		return this.moveRating > o.moveRating ? 1 : this.moveRating < o.moveRating ? -1 : 0;
	}

}
