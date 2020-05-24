package ai.eval;

import java.util.HashMap;
import java.util.Map;

import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.piece.Piece;

public class PieceSquareTable {

	private Map<String,Map<Integer,Integer>> pieceSquareTable = new HashMap<>();

	public PieceSquareTable() {
		super();
		initPawnTable();
		
		
		
		Map<Integer,Integer> knightTable = new HashMap<>();
		
		Map<Integer,Integer> bishopTable = new HashMap<>();
		
		Map<Integer,Integer> queenTable = new HashMap<>();
	}
	
	void initPawnTable() {
		Map<Integer,Integer> whitePawnTable = new HashMap<>();
		whitePawnTable.put(11, -30);
		whitePawnTable.put(12, -30);
		whitePawnTable.put(19, 3);
		whitePawnTable.put(20, 3);
		whitePawnTable.put(27, 6);
		whitePawnTable.put(28, 6);
		pieceSquareTable.put(Color.LIGHT+""+PieceType.PAWN, whitePawnTable);
		Map<Integer,Integer> darkPawnTable = new HashMap<>();
		darkPawnTable.put(51, -30);
		darkPawnTable.put(52, -30);
		darkPawnTable.put(43, 3);
		darkPawnTable.put(44, 3);
		darkPawnTable.put(35, 6);
		darkPawnTable.put(36, 6);
		pieceSquareTable.put(Color.DARK+""+PieceType.PAWN, darkPawnTable);
	}

	public Map<Integer, Integer> get(Piece piece) {
		String key = piece.getColor()+""+piece.getPieceType();
		Map<Integer,Integer> copy = new HashMap<>();
		if(pieceSquareTable.get(key) != null) {
			copy = new HashMap<>(pieceSquareTable.get(key));
		}
		return copy;
	} 

	public Integer getPSTableValue(Piece piece,Integer coordinate) {
		Integer value = this.get(piece).get(coordinate);
		return value != null ? value : 0;
	}
}
