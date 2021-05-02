package ai.eval;

import representation.ColoredPiece;

public class MaterialValue {

	private static final int pawnVal = 100;
	private static final int rookVal = 500;
	private static final int knightVal = 350;
	private static final int bishopVal = 350;
	private static final int queenVal = 1000;
	private static final int kingVal = 100000;
	
	public static int getValue(ColoredPiece piece) {
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
