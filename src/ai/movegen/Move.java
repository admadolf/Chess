package ai.movegen;

import ai.representation.piece.ColoredPiece;

public class Move{

	private int from;
	
	private int to;
	
	private ColoredPiece fromPiece;
	
	private ColoredPiece toPiece;
	
	public Move(int from, int to, ColoredPiece fromPiece, ColoredPiece toPiece) {
		super();
		this.from = from;
		this.to = to;
		this.fromPiece = fromPiece;
		this.toPiece = toPiece;
	}

	@Override
	public String toString() {
		return "Move [from=" + from + ", to=" + to + ", fromPiece=" + fromPiece + ", toPiece=" + toPiece  +  "]";
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

	public ColoredPiece getFromPiece() {
		return fromPiece;
	}

	public void setFromPiece(ColoredPiece fromPiece) {
		this.fromPiece = fromPiece;
	}

	public ColoredPiece getToPiece() {
		return toPiece;
	}

	public void setToPiece(ColoredPiece toPiece) {
		this.toPiece = toPiece;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + from;
		result = prime * result + ((fromPiece == null) ? 0 : fromPiece.hashCode());
		result = prime * result + to;
		result = prime * result + ((toPiece == null) ? 0 : toPiece.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (from != other.from)
			return false;
		if (fromPiece == null) {
			if (other.fromPiece != null)
				return false;
		} else if (!fromPiece.equals(other.fromPiece))
			return false;
		if (to != other.to)
			return false;
		if (toPiece == null) {
			if (other.toPiece != null)
				return false;
		} else if (!toPiece.equals(other.toPiece))
			return false;
		return true;
	}

	
	
}
