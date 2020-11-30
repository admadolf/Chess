package ai.movegen;

import ai.representation.MoveType;
import ai.representation.piece.ColoredPiece;

public class Move{

	private int from;
	
	private int to;
	
	private ColoredPiece fromPiece;
	
	private ColoredPiece toPiece;
	
	private MoveType moveType;
	
	private int castleKingFrom;
	
	private int castleRookFrom;
	
	private int castleKingTo;
	
	private int castleRookTo;
	
	private Integer enPassantCapturer1;
	
	private Integer enPassantCapturer2;
	
	private Integer enPassantArrivalSquareAfterTake;
	
	private Integer enPassantToBeCaptured;
	
	public Move(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public Move(MoveType moveType, int from, int to) {
		super();
		this.moveType = moveType;
		this.from = from;
		this.to = to;
	}
	
	public Move(int from, int to, ColoredPiece fromPiece, ColoredPiece toPiece) {
		super();
		this.from = from;
		this.to = to;
		this.fromPiece = fromPiece;
		this.toPiece = toPiece;
	}

	public Move(MoveType castleType, int castleKingFrom, int castleRookFrom, int castleKingTo, int castleRookTo) {
		super();
		this.moveType = castleType;
		this.castleKingFrom = castleKingFrom;
		this.castleRookFrom = castleRookFrom;
		this.castleKingTo = castleKingTo;
		this.castleRookTo = castleRookTo;
	}



	@Override
	public String toString() {
		if(moveType != null) {
			if(moveType == MoveType.CASTLELONG || moveType == MoveType.CASTLESHORT) {
				return "Move [MoveType: " + moveType  +" kingFrom=" + castleKingFrom + ", kingTo=" + castleKingTo + ", rookFrom=" + castleRookFrom+ ", rookTo=" + castleRookTo  +  "]"; 
			} else if(moveType == MoveType.ENPASSANTFLAG) {
				return "Move [MoveType: " + moveType  +" enPassantCandidate1=" + enPassantCapturer1 + ", enPassantCandidate2=" + enPassantCapturer2 + ", enPassantArrivalSquareAfterTake=" + enPassantArrivalSquareAfterTake +  "]";
			} else if(moveType == MoveType.ENPASSANT) {
				return "Move [MoveType: " + moveType  + " from=" + from + ", to=" + to + ", fromPiece=" + fromPiece + ", toPiece=" + toPiece  +  "]";
			} else {
				return "unknown moveType in Move toString()";
			}
		} else {
			return "Move [from=" + from + ", to=" + to + ", fromPiece=" + fromPiece + ", toPiece=" + toPiece  +  "]";
		}
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

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public int getCastleKingFrom() {
		return castleKingFrom;
	}

	public void setCastleKingFrom(int castleKingFrom) {
		this.castleKingFrom = castleKingFrom;
	}

	public int getCastleRookFrom() {
		return castleRookFrom;
	}

	public void setCastleRookFrom(int castleRookFrom) {
		this.castleRookFrom = castleRookFrom;
	}

	public int getCastleKingTo() {
		return castleKingTo;
	}

	public void setCastleKingTo(int castleKingTo) {
		this.castleKingTo = castleKingTo;
	}

	public int getCastleRookTo() {
		return castleRookTo;
	}

	public void setCastleRookTo(int castleRookTo) {
		this.castleRookTo = castleRookTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + castleKingFrom;
		result = prime * result + castleKingTo;
		result = prime * result + castleRookFrom;
		result = prime * result + castleRookTo;
		result = prime * result
				+ ((enPassantArrivalSquareAfterTake == null) ? 0 : enPassantArrivalSquareAfterTake.hashCode());
		result = prime * result + ((enPassantCapturer1 == null) ? 0 : enPassantCapturer1.hashCode());
		result = prime * result + ((enPassantCapturer2 == null) ? 0 : enPassantCapturer2.hashCode());
		result = prime * result + ((enPassantToBeCaptured == null) ? 0 : enPassantToBeCaptured.hashCode());
		result = prime * result + from;
		result = prime * result + ((fromPiece == null) ? 0 : fromPiece.hashCode());
		result = prime * result + ((moveType == null) ? 0 : moveType.hashCode());
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
		if (castleKingFrom != other.castleKingFrom)
			return false;
		if (castleKingTo != other.castleKingTo)
			return false;
		if (castleRookFrom != other.castleRookFrom)
			return false;
		if (castleRookTo != other.castleRookTo)
			return false;
		if (enPassantArrivalSquareAfterTake == null) {
			if (other.enPassantArrivalSquareAfterTake != null)
				return false;
		} else if (!enPassantArrivalSquareAfterTake.equals(other.enPassantArrivalSquareAfterTake))
			return false;
		if (enPassantCapturer1 == null) {
			if (other.enPassantCapturer1 != null)
				return false;
		} else if (!enPassantCapturer1.equals(other.enPassantCapturer1))
			return false;
		if (enPassantCapturer2 == null) {
			if (other.enPassantCapturer2 != null)
				return false;
		} else if (!enPassantCapturer2.equals(other.enPassantCapturer2))
			return false;
		if (enPassantToBeCaptured == null) {
			if (other.enPassantToBeCaptured != null)
				return false;
		} else if (!enPassantToBeCaptured.equals(other.enPassantToBeCaptured))
			return false;
		if (from != other.from)
			return false;
		if (fromPiece == null) {
			if (other.fromPiece != null)
				return false;
		} else if (!fromPiece.equals(other.fromPiece))
			return false;
		if (moveType != other.moveType)
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

	public Integer getEnPassantCapturer1() {
		return enPassantCapturer1;
	}

	public void setEnPassantCapturer1(Integer enPassantCapturer1) {
		this.enPassantCapturer1 = enPassantCapturer1;
	}

	public Integer getEnPassantCapturer2() {
		return enPassantCapturer2;
	}

	public void setEnPassantCapturer2(Integer enPassantCapturer2) {
		this.enPassantCapturer2 = enPassantCapturer2;
	}

	public Integer getEnPassantArrivalSquareAfterTake() {
		return enPassantArrivalSquareAfterTake;
	}

	public void setEnPassantArrivalSquareAfterTake(Integer enPassantArrivalSquareAfterTake) {
		this.enPassantArrivalSquareAfterTake = enPassantArrivalSquareAfterTake;
	}

	public Integer getEnPassantToBeCaptured() {
		return enPassantToBeCaptured;
	}

	public void setEnPassantToBeCaptured(Integer enPassantToBeCaptured) {
		this.enPassantToBeCaptured = enPassantToBeCaptured;
	}

	
	
}
