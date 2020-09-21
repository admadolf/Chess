package ai.movegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.representation.Board;
import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.piece.Bishop;
import ai.representation.piece.EmptyPiece;
import ai.representation.piece.King;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Piece;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;

/**
 * This
 * 
 * @author adam adolf
 * shaazamkazni b3ta m$xbol
 * //43:25,2:34
 */
public class MoveGenerator {

	/**
	 * 
	 * @param from
	 * @param currentBoardState
	 * @return possible moves for the piece on a given position
	 */
	public List<Integer> generateMoves(Integer from, Board currentBoardState) {
		List<Integer> moveList = new ArrayList<Integer>();
		Board boardMap = currentBoardState;
		Piece piece = boardMap.get(from);
		Color sideColor = piece.getColor();
		switch (piece.getPieceType()) {
		case PAWN:
			for (Integer vector : Pawn.getMoveVectors(sideColor)) {
				Integer to = from + vector;
				if(isValidPawnMove(from, to, currentBoardState, sideColor)) {
					moveList.add(to);
				}
				//if on init square check and add double forward move too
				if(isPawnOnInitialSquare(from,sideColor) && isValidPawnMove(from, to, currentBoardState, sideColor) 
						&& isValidPawnMove(from, to + vector, currentBoardState, sideColor)) {
					moveList.add(to + vector);
				}
			}
			break;
		case KNIGHT:
			for (Integer vector : Knight.getMoveVectors()) {
				Integer to = from + vector;
				if(!offTheGrid(to) && boardMap.get(to).getColor() != sideColor &&
						rowDistance(from, to) < 3 && columnDistance(from, to) < 3) {
					moveList.add(to);
				}
			}
			break;
		case BISHOP:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from ;
				int to = tmpFrom + vector;
				while(isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if(Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			break;
		case QUEEN:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from ;
				int to = tmpFrom + vector;
				while(isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if(Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						//moveList.add(to); //TODO comment upper add and decomment this to filter out non take moves (for search algorithm testing purposes, decreasing the move numbers)
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				while(isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if(Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						//moveList.add(to); //TODO comment upper add and decomment this to filter out non take moves (for search algorithm testing purposes, decreasing the move numbers)
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			break;
		case KING:
			for (Integer vector : King.getMoveVectors()) {
				Integer to = from + vector;
				if(isValidBishopMove(from, to, currentBoardState, sideColor) && isValidRookMove(from, to, currentBoardState, sideColor)) {
					moveList.add(to);
				}
			}
			break;
		case ROOK:
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				while(isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if(Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						//moveList.add(to); //TODO comment upper add and decomment this to filter out non take moves (for search algorithm testing purposes, decreasing the move numbers)
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			break;
		default:
			break;
		}
		return moveList;
	}

	/**
	 * Return piece coordinates of given piece type and given color
	 * 
	 * @param color
	 * @param pieceType
	 * @param board
	 * @return all position of the given piece
	 */
	public List<Integer> getAll(Color color, PieceType pieceType, Board board) {
		Stream<Entry<Integer, Piece>> boardStream = board.getBoardMapReference().entrySet().stream();
		boardStream = boardStream.filter((e) -> e.getValue().getColor() == color);
		boardStream = boardStream.filter((e) -> e.getValue().getPieceType() == pieceType);
		return boardStream.map(Entry::getKey).collect(Collectors.toList());
	}
	
	/**
	 * Return piece coordinates of given color
	 * 
	 * @param color
	 * @param pieceType
	 * @param board
	 * @return all position of the given piece
	 */
	public List<Integer> getAll(Color color, Board board) {
		Stream<Entry<Integer, Piece>> boardStream = board.getBoardMapReference().entrySet().stream();
		boardStream = boardStream.filter((e) -> e.getValue().getColor() == color);
		return boardStream.map(Entry::getKey).collect(Collectors.toList());
	}
	
	public List<Integer> getAll(int pov, Board board) {
		Color color = (pov == 1) ? Color.LIGHT : Color.DARK ;
		Stream<Entry<Integer, Piece>> boardStream = board.getBoardMapReference().entrySet().stream();
		boardStream = boardStream.filter((e) -> e.getValue().getColor() == color);
		return boardStream.map(Entry::getKey).collect(Collectors.toList());
	}
	
	/**
	 * Return movable piece coordinates of given color
	 * 
	 * @param color
	 * @param pieceType
	 * @param board
	 * @return all position of the given piece
	 */
	public List<Integer> getAllMovable(Color color, Board board) {
		List<Integer> movablePiecePositions = new ArrayList<>(); 
		List<Integer> colorPositions = getAll(color, board);
		for (Integer position : colorPositions) {
			if(!generateMoves(position, board).isEmpty()) {
				movablePiecePositions.add(position);
			}
		}
		return movablePiecePositions;
	}
	
	/**
	 * Return movable piece coordinates
	 * 
	 * @param color
	 * @param pieceType
	 * @param board
	 * @return all position of the given piece
	 */
	public List<Integer> getAllMovable(Board board, Color color) {
		List<Integer> movablePiecePositions = new ArrayList<>(); 
		List<Integer> positions = getAll(color, board);
		for (Integer position : positions) {
			if(!generateMoves(position, board).isEmpty()) {
				movablePiecePositions.add(position);
			}
		}
		return movablePiecePositions;
	}
	
	public List<Board> generatePositions(Board board, Color color) {
		List<Board> positions = new ArrayList<>();
		for (Integer from : getAllMovable(board, color)) {
			for (Integer to : generateMoves(from, board)) {
				Board nextBoard = Board.deepCopy(board);
				Piece piece = nextBoard.grabPieceAndCleanFrom(from);
				nextBoard.place(piece, to);
				nextBoard.setTransitionMoveFromPreviousBoard(new Move(from, to, piece, board.getBoardMapReference().get(to)));
				positions.add(nextBoard);
			}
		}
		return positions;
	}
	
	public List<Board> generatePositions(Board board, int from) {
		List<Board> positions = new ArrayList<>();
			for (Integer to : generateMoves(from, board)) {
				Board nextBoard = Board.deepCopy(board);
				Piece piece = nextBoard.grabPieceAndCleanFrom(from);
				nextBoard.setTransitionMoveFromPreviousBoard(new Move(from, to, piece, board.getBoardMapReference().get(to)));
				nextBoard.place(piece, to);
				positions.add(nextBoard);
			}
		return positions;
	}
	
	//random move generator methods
	//
	//
	//
	//
	//
	//
	//
	
	/**
	 * Pick random piece of given color
	 * @param board
	 * @param color
	 * @return
	 */
	public int pickRandomMovablePiece(Board board, Color color) {
		LinkedList<Integer> pieceList = new LinkedList<>(getAllMovable(color, board));
		Collections.shuffle(pieceList);
		return !pieceList.isEmpty() ? pieceList.getFirst() : -1;
	}

	/**
	 * Generate a random move for a given coordinate
	 *  
	 * @param board
	 * @param color
	 * @return
	 */
	public int generateRandomMoveForPiece(Board board, int from) {
		LinkedList<Integer> moveList = new LinkedList<>(generateMoves(from, board));
		Collections.shuffle(moveList);
		return !moveList.isEmpty() ? moveList.getFirst() : -1;
	}
	
	public Board moveRandomPiece(int from, Board board) {
		Board result;
		int to = -1;
		Random random = new Random();
		boolean isEmpty = false;
		while (!isEmpty) {
			to = random.nextInt(64);
			isEmpty = Board.isEmptySquare(to, board);
		}
		result = Board.deepCopy(board);
		result.place(board.grabPieceAndCleanFrom(from), to);
		result.getBoardMapReference().put(from, new EmptyPiece());
		return result;
	}
	
	public boolean IsKingInCheck(final Color color,final Board board) {
		List<Integer> pieceList = getAll(color, board);
		Integer kingSquare = -1;
		for (Integer key : pieceList) {
			if(PieceType.KING == board.getBoardMapReference().get(key).getPieceType()) {
				kingSquare = key;
				break;
			}
		}
		Piece[] pieces = new Piece[] {new Knight(color),new Bishop(color),new Rook(color),new Queen(color),new Pawn(color)};
		for (Piece piece : pieces) {
			if(isAttackedBy(kingSquare, piece, board)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * !!hacky places piece on square generates moves with the piece, if 
	 * the opposite color but same piece is found the initial square is being attacked by that kind of opposite color piece
	 * @param square
	 * @param piece
	 * @param board
	 * @return
	 */
	public boolean isAttackedBy(final Integer square, final Piece piece, final Board board) {
		Board boardClone = Board.deepCopy(board);
		boardClone.place(piece, square);
		List<Integer> attackedList = generateMoves(square, boardClone); //TODO pawn moves should be adjusted here
		for (Integer attacked : attackedList) {
			Piece pieceOnAttackedSquare = boardClone.getBoardMapReference().get(attacked);
			Color colorOfAttacked = pieceOnAttackedSquare.getColor();
			if(colorOfAttacked.opposite() == piece.getColor() && pieceOnAttackedSquare.getPieceType() == piece.getPieceType()) {
				return true;
			}
		}
		return false;
	}
	
	/*	public List<Integer> getAttackers(final Integer square, final Piece piece, final Board board){
			List<Integer> list = new ArrayList<Integer>();
			Board boardClone = Board.deepCopy(board);
			boardClone.place(piece, square);
			List<Integer> attackedList = generateMoves(square, boardClone);
			for (Integer attacked : attackedList) {
				Piece pieceOnAttackedSquare = boardClone.getBoardMapReference().get(attacked);
				Color colorOfAttacked = pieceOnAttackedSquare.getColor();
				if(colorOfAttacked.opposite() == piece.getColor() && pieceOnAttackedSquare.getPieceType() == piece.getPieceType()) {
					return true;
				}
			}
			return false;
		}*/
	//helper methods
	//
	//
	//
	//
	//
	//
	//
	
	/**
	 * check if the coordinate inside the coordinate frame
	 * 
	 * @param key
	 * @return
	 */
	private static boolean offTheGrid(int key) {
		return !(0 <= key && key <= 63);
	}

	private int row(int position) {
		return position / 8;
	}
	
	private int column(int position) {
		return position % 8;
	}
	
	private boolean sameRow(int positionA, int positionB) {
		return row(positionA) == row(positionB);
	}
	
	private boolean sameColumn(int positionA, int positionB) {
		return column(positionA) == column(positionB);
	}
	
	private int columnDistance(int positionA, int positionB) {
		return Math.abs(column(positionA) - column(positionB));
	}
	
	private int rowDistance(int positionA, int positionB) {
		return Math.abs(row(positionA) - row(positionB));
	}
	
	private boolean isValidPawnMove(final int from, final int to,final Board board, final Color sideColor) {
		//TODO en passant
		//TODO handle promotion
		if(sameColumn(from, to)) {
			return !offTheGrid(to) && Board.isEmptySquare(to, board);
		} else if(columnDistance(from, to) < 2) {
			return !offTheGrid(to) && Board.getColorOf(to, board) == sideColor.opposite();
		}
		return false;
	}
	
	private boolean isValidBishopMove(final int from, final int to,final Board board, final Color sideColor) {
		return !offTheGrid(to) && differentColor(Board.getColorOf(to, board),sideColor) 
				&& rowDistance(from, to) < 2 && columnDistance(from, to) < 2;
	}
	
	private boolean isValidRookMove(final int from, final int to,final Board board, final Color sideColor) {
		return !offTheGrid(to) && differentColor(Board.getColorOf(to, board),sideColor) 
				&& (sameRow(from, to) || sameColumn(from, to));
	}
	
	private boolean differentColor(Color colorOne, Color colorTwo) {
		return colorOne != colorTwo;
	}
	
	private boolean isPawnOnInitialSquare(int position,Color sideColor) {
		if(sideColor == Color.LIGHT) {
			return (row(position) == 1);
		} else if(sideColor == Color.DARK){
			return (row(position) == 6);
		}
		return false;
	}
	
}
