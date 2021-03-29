package ai.representation.piece;

import ai.representation.Color;
import ai.representation.PieceType;

public class EmptyPiece extends ColoredPiece {

	public EmptyPiece() {
		super(PieceType.EMPTY, Color.EMPTY);
	}

}
