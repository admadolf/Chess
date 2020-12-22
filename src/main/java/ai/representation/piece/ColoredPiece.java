package ai.representation.piece;

import ai.representation.Color;
import ai.representation.PieceType;

public class ColoredPiece {

	public final PieceType pieceType;
		
	public final Color color;
	
	public ColoredPiece(PieceType pieceType,Color color) {
		this.pieceType = pieceType;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return color.toString()+pieceType.toString()+" ";
	}
	
	public String toUnicodeChessSymbol() {
		switch(color) {
			case DARK:
				switch(pieceType) {
				case BISHOP:
					return "♝";
				case EMPTY:
					return "";
				case KING:
					return "♚";
				case KNIGHT:
					return "♞";
				case PAWN:
					return "♟";
				case QUEEN:
					return "♛";
				case ROOK:
					return "♜";
				default:
					break;
				
				}
			break;
			case LIGHT:
				switch(pieceType) {
				case BISHOP:
					return "♗";
				case EMPTY:
					return "";
				case KING:
					return "♔";
				case KNIGHT:
					return "♘";
				case PAWN:
					return "♙";
				case QUEEN:
					return "♕";
				case ROOK:
					return "♖";
				default:
					break;
				
				}
			break;
			default:
				return "";
		}
		return color.toString()+pieceType.toString()+" ";
	}
	
	public Color getColor() {
		return color;
	}
	
	public PieceType getPieceType() {
		return pieceType;
	}
	
	public ColoredPiece opposite() {
		return new ColoredPiece(this.pieceType, this.color.opposite());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((pieceType == null) ? 0 : pieceType.hashCode());
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
		ColoredPiece other = (ColoredPiece) obj;
		if (color != other.color)
			return false;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}

	
	
}
