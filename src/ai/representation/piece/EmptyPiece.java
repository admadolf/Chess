package ai.representation.piece;

import ai.representation.Color;
import ai.representation.PieceType;

public class EmptyPiece extends Piece {

	public EmptyPiece() {
		super(PieceType.EMPTY, Color.EMPTY);
	}
	
	public EmptyPiece(PieceType pieceType, Color color) {
		super(pieceType, color);
	}

}
