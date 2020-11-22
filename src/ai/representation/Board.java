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
import ai.representation.piece.ColoredPiece;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;

public class Board {

	private Move transitionMoveFromPreviousBoard;
	
	private static String rankHeader = "  A  B  C  D  E  F  G  H  ";
	private static String rankHeaderDelim ="  -- -- -- -- -- -- -- --  ";
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
	
	private Map<Integer,ColoredPiece> board = new TreeMap<>();

	public Board() {
		super();
		System.err.println("no arg board constr");
	}
	
	public Board(Map<Integer,ColoredPiece> boardMap) {
		super();
		this.board = new TreeMap<>(boardMap);
	}
	
	public void initBoard() {
		board.put(56, new ColoredPiece(PieceType.ROOK,Color.DARK));
		board.put(57, new ColoredPiece(PieceType.KNIGHT,Color.DARK));
		board.put(58, new ColoredPiece(PieceType.BISHOP,Color.DARK));
		board.put(59, new ColoredPiece(PieceType.QUEEN,Color.DARK));
		board.put(60, new ColoredPiece(PieceType.KING,Color.DARK));
		board.put(61, new ColoredPiece(PieceType.BISHOP,Color.DARK));
		board.put(62, new ColoredPiece(PieceType.KNIGHT,Color.DARK));
		board.put(63, new ColoredPiece(PieceType.ROOK,Color.DARK));
		IntStream.range(48, 56).forEach((i) -> board.put(i, new ColoredPiece(PieceType.PAWN,Color.DARK)));
		IntStream.range(8, 48).forEach((i) -> board.put(i, new ColoredPiece(PieceType.EMPTY, Color.EMPTY)));
		IntStream.range(8, 16).forEach((i) -> board.put(i, new ColoredPiece(PieceType.PAWN,Color.LIGHT)));
		board.put(0, new ColoredPiece(PieceType.ROOK,Color.LIGHT));
		board.put(1, new ColoredPiece(PieceType.KNIGHT,Color.LIGHT));
		board.put(2, new ColoredPiece(PieceType.BISHOP,Color.LIGHT));
		board.put(3, new ColoredPiece(PieceType.QUEEN,Color.LIGHT));
		board.put(4, new ColoredPiece(PieceType.KING,Color.LIGHT));
		board.put(5, new ColoredPiece(PieceType.BISHOP,Color.LIGHT));
		board.put(6, new ColoredPiece(PieceType.KNIGHT,Color.LIGHT));
		board.put(7, new ColoredPiece(PieceType.ROOK,Color.LIGHT));
	}
	
	public void initEmptyBoard() {
		IntStream.range(0, 64).forEach((i) -> board.put(i, new EmptyPiece()));
	}
	
	
	public Map<Integer, ColoredPiece> getBoardMapReference() {
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
	
	public String toStringFlipped() {
		StringBuilder builder = new StringBuilder("");
		int rowStart = 56;
		int rowEnd = 64; //end is exclusive
		int rowOffset = rowStart-rowEnd;
		builder.append(rankHeader).reverse().append(System.lineSeparator());
		builder.append(rankHeaderDelim).append(System.lineSeparator());
		for (; rowStart>=0; rowStart+=rowOffset,rowEnd+=rowOffset) {
			builder.append(Math.abs((rowStart-64)/8)).append(fileHeaderDelim);
			IntStream.range(rowStart, rowEnd).forEach((i) -> builder.append(board.get(Math.abs(i-63)).toString()));
			builder.append(fileHeaderDelim).append(Math.abs((rowStart-64)/8));
			builder.append(System.lineSeparator());
		}
		builder.append(rankHeaderDelim).append(System.lineSeparator());
		builder.append(rankHeader).append(System.lineSeparator());
		return builder.toString();
	}
	
	public static boolean isEmptySquare(int square, Board board) {
		return board.board.get(square).getPieceType() == PieceType.EMPTY;
	}
	
	public static boolean batchIsEmptySquare(Board board, int... squares) {
		boolean result = true;
		for (int sq : squares) {
			if(!isEmptySquare(sq, board)) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	public static Color getColorOf(int square, Board board) {
		return board.board.get(square).getColor();
	}
	
	public ColoredPiece grabPieceAndCleanFrom(int from) {
		return this.board.put(from, new EmptyPiece());
	}
	
	public void place(ColoredPiece piece , int to) {
		this.board.put(to, piece);
	}

	public ColoredPiece get(Integer key) {
		return this.board.get(key);
	}
	
	public Move getTransitionMoveFromPreviousBoard() {
		return transitionMoveFromPreviousBoard;
	}
	
	public void setTransitionMoveFromPreviousBoard(Move move) {
		this.transitionMoveFromPreviousBoard = move;
	}

	public static Board transposePosition(Board board, int from, int to) {
		Board nextBoard = Board.deepCopy(board);
		ColoredPiece tmpToPiece = board.getBoardMapReference().get(to);
		ColoredPiece piece = nextBoard.grabPieceAndCleanFrom(from);
		nextBoard.place(piece, to);
		nextBoard.setTransitionMoveFromPreviousBoard(
				new Move(from, to, piece, tmpToPiece));
		return nextBoard;
	}
	
	public static Board transposePositionNab(Board board, int from, int to) {
		ColoredPiece tmpToPiece = board.getBoardMapReference().get(to);
		ColoredPiece piece = board.grabPieceAndCleanFrom(from);
		board.place(piece, to);
		board.setTransitionMoveFromPreviousBoard(
				new Move(from, to, piece, tmpToPiece));
		return board;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result
				+ ((transitionMoveFromPreviousBoard == null) ? 0 : transitionMoveFromPreviousBoard.hashCode());
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
		Board other = (Board) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (transitionMoveFromPreviousBoard == null) {
			if (other.transitionMoveFromPreviousBoard != null)
				return false;
		} else if (!transitionMoveFromPreviousBoard.equals(other.transitionMoveFromPreviousBoard))
			return false;
		return true;
	}
	
	
}
