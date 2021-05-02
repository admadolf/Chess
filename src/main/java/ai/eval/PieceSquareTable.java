package ai.eval;

import java.util.HashMap;
import java.util.Map;

import representation.Color;
import representation.ColoredPiece;
import representation.PieceType;

public class PieceSquareTable {

	private Map<ColoredPiece,int[]> pieceSquareTable = new HashMap<>();
	
	public PieceSquareTable() {
		super();
		initPawnTables();
		initBishopTables();
		initKnightTables();
		initKingTables();
		initRookTables();
		initQueenTables();
	}
	
	private void initQueenTables() {
		int[] lightQueenTable = {
				-5,-5,-5,-5,-5,-5,-5,-5,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), lightQueenTable);
		int[] darkQueenTable = {
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				-5,-5,-5,-5,-5,-5,-5,-5,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.QUEEN, Color.DARK), darkQueenTable);
	}

	private void initRookTables() {
		int[] lightRookTable = {
				0,0,0,15,15,10,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.ROOK, Color.LIGHT), lightRookTable);
		int[] darkRookTable = {
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,15,15,10,0,0,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.ROOK, Color.DARK), darkRookTable);
	}

	private void initKingTables() {
		int[] lightKingTable = {
				0,20,15,0,10,0,20,0,
				0,0,-20,-20,-20,-20,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.KING, Color.LIGHT), lightKingTable);
		int[] darkKingTable = {
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,-20,-20,-20,-20,0,0,
				0,20,15,0,10,0,20,0,
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.KING, Color.DARK), darkKingTable);
	}

	private void initPawnTables() {
		int[] whitePawnTable = new int[64];
		whitePawnTable[11] = -40;
		whitePawnTable[12] = -40;
		whitePawnTable[19] = 3;
		whitePawnTable[20] = 3;
		whitePawnTable[27] = 6;
		whitePawnTable[28] = 6;
		pieceSquareTable.put(new ColoredPiece(PieceType.PAWN, Color.LIGHT), whitePawnTable);
		int[] darkPawnTable  = new int[64];
		darkPawnTable[51] = -40;
		darkPawnTable[52] = -40;
		darkPawnTable[43] = 3;
		darkPawnTable[44] = 3;
		darkPawnTable[35] = 6;
		darkPawnTable[36] = 6;
		pieceSquareTable.put(new ColoredPiece(PieceType.PAWN, Color.DARK), darkPawnTable);
	}
	
	private void initKnightTables() {
		int[] lightKnightTable = {
				-16,-12,-8,-8,-8,-8,-12,-16,
				-8,0,1,2,2,1,0,-8,
				-8,0,4,6,6,4,0,-8,
				-8,0,6,8,8,6,0,-8,
				-8,0,6,8,8,6,0,-8,
				-8,0,4,6,6,4,0,-8,
				-8,0,0,0,0,0,0,-8,
				-8,-8,-8,-8,-8,-8,-8,-8
				};
		pieceSquareTable.put(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), lightKnightTable);
		int[] darkKnightTable = {
				-8,-8,-8,-8,-8,-8,-8,-8,
				-8,0,0,0,0,0,0,-8,
				-8,0,14,6,6,14,0,-8,
				-8,0,6,8,8,6,0,-8,
				-8,0,6,8,8,6,0,-8,
				-8,0,4,6,6,4,0,-8,
				-8,0,1,2,2,1,0,-8,
				-16,-12,-8,-8,-8,-8,-12,-16,
				};
		pieceSquareTable.put(new ColoredPiece(PieceType.KNIGHT, Color.DARK), darkKnightTable);
	}
	
	private void initBishopTables() {
		int[] lightBishopTable = {
				-4,-4,-12,-4,-4,-12,-4,-4,
				-4,2,1,1,1,1,2,-4,
				-4,1,2,4,4,2,1,-4,
				-4,0,4,6,6,4,0,-4,
				-4,0,4,6,6,4,0,-4,
				-4,0,2,4,4,2,0,-4,
				-4,0,0,0,0,0,0,-4,
				-4,-4,-4,-4,-4,-4,-4,-4
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), lightBishopTable);
		int[] darkBishopTable = {
				-4,-4,-4,-4,-4,-4,-4,-4,
				-4,0,0,0,0,0,0,-4,
				-4,0,2,4,4,2,0,-4,
				-4,0,4,6,6,4,0,-4,
				-4,0,4,6,6,4,0,-4,
				-4,1,2,4,4,2,1,-4,
				-4,2,1,1,1,1,2,-4,
				-4,-4,-12,-4,-4,-12,-4,-4
		};
		pieceSquareTable.put(new ColoredPiece(PieceType.BISHOP, Color.DARK), darkBishopTable);
	}
	
	public Integer getPSTableValue(ColoredPiece piece,Integer coordinate) {
		if(this.pieceSquareTable.containsKey(piece)) {
			Integer value = this.pieceSquareTable.get(piece)[coordinate];
			return value != null ? value : 0;
		}else {
			return 0;
		}
	}
}
