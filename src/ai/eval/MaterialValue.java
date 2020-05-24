package ai.eval;

import ai.representation.piece.Piece;

public class MaterialValue {

	private final int pawnVal = 100;
	private final int rookVal = 500;
	private final int knightVal = 350;
	private final int bishopVal = 350;
	private final int queenVal = 1000;
	private final int kingVal = 100000;
	
	int getValue(Piece piece) {
		switch (piece.getPieceType()) {
			case PAWN:
				return pawnVal;
			case KNIGHT:
				return knightVal;
			case BISHOP:
				return bishopVal;
			case ROOK:
				return rookVal;
			case QUEEN:
				return queenVal;
			case KING:
				return kingVal;
			default:
				return 0;
			}
	}
}
