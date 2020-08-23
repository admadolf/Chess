package ai.representation;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import ai.movegen.Move;
import ai.representation.piece.Bishop;
import ai.representation.piece.EmptyPiece;
import ai.representation.piece.King;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Piece;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;

public class Board {

	private Move move;
	
	private boolean whiteQueenSideCastle;
	private boolean whiteKingSideCastle;
	private boolean darkQueenSideCastle;
	private boolean darkKingSideCastle;
	
	private static String rankHeader = "  A  B  C  D  E  F  G  H";
	private static String rankHeaderDelim ="  -- -- -- -- -- -- -- --";
	private static String fileHeaderDelim = "|";
	
	//ints from 0 to 63 representing board squares
	//https://www.chessprogramming.org/Square_Mapping_Considerations
	//Little-Endian Rank-File Mapping
	//We rely on the compass rose to identify ray-directions with following increments to neighbored squares. 
	//  noWe         nort         noEa
    //         +7    +8    +9
    //            \  |  /
	//west    -1 <-  0 -> +1    east
    //            /  |  \
    //        -9    -8    -7
//     soWe         sout         soEa
	
	private Map<Integer,Piece> board = new TreeMap<>();

	public Board() {
		super();
		System.err.println("no arg board constr");
	}
	
	public Board(Map<Integer,Piece> boardMap) {
		super();
		this.board = new TreeMap<>(boardMap);
	}
	
	public void initBoard() {
		board.put(56, new Rook(Color.DARK));
		board.put(57, new Knight(Color.DARK));
		board.put(58, new Bishop(Color.DARK));
		board.put(59, new Queen(Color.DARK));
		board.put(60, new King(Color.DARK));
		board.put(61, new Bishop(Color.DARK));
		board.put(62, new Knight(Color.DARK));
		board.put(63, new Rook(Color.DARK));
		IntStream.range(48, 56).forEach((i) -> board.put(i, new Pawn(Color.DARK)));
		IntStream.range(8, 48).forEach((i) -> board.put(i, new EmptyPiece()));
		IntStream.range(8, 16).forEach((i) -> board.put(i, new Pawn(Color.LIGHT)));
		board.put(0, new Rook(Color.LIGHT));
		board.put(1, new Knight(Color.LIGHT));
		board.put(2, new Bishop(Color.LIGHT));
		board.put(3, new Queen(Color.LIGHT));
		board.put(4, new King(Color.LIGHT));
		board.put(5, new Bishop(Color.LIGHT));
		board.put(6, new Knight(Color.LIGHT));
		board.put(7, new Rook(Color.LIGHT));
	}
	
	public void initEmptyBoard() {
		IntStream.range(0, 64).forEach((i) -> board.put(i, new EmptyPiece()));
	}
	
	
	public Map<Integer, Piece> getBoardMapReference() {
		return board;
	}
	
	public static Board deepCopy(Board board) {
		return new Board(board.getBoardMapReference());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("");
		int rowStart = 56;
		int rowEnd = 64; //end is exclusive
		int rowOffset = rowStart-rowEnd;
		builder.append(rankHeader).append(System.lineSeparator());
		builder.append(rankHeaderDelim).append(System.lineSeparator());
		for (; rowStart>=0; rowStart+=rowOffset,rowEnd+=rowOffset) {
			builder.append(rowStart/8+1).append(fileHeaderDelim);
			IntStream.range(rowStart, rowEnd).forEach((i) -> builder.append(board.get(i).toString()));
			builder.append(fileHeaderDelim).append(rowStart/8+1);
			builder.append(System.lineSeparator());
		}
		builder.append(rankHeaderDelim).append(System.lineSeparator());
		builder.append(rankHeader).append(System.lineSeparator());
		return builder.toString();
	}
	
	public static boolean isEmptySquare(int square, Board board) {
		return board.board.get(square).getPieceType() == PieceType.EMPTY;
	}
	
	public static Color getColorOf(int square, Board board) {
		return board.board.get(square).getColor();
	}
	
	@SuppressWarnings(value = { "This might change the board" })
	public Piece grabPieceAndCleanFrom(int from) {
		return this.board.put(from, new EmptyPiece());
	}
	
	@SuppressWarnings(value = { "This might change the board" })
	public void place(Piece piece , int to) {
		this.board.put(to, piece);
	}

	public Piece get(Integer key) {
		return this.board.get(key);
	}
	
	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
	
}
