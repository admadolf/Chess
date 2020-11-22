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
import ai.representation.MoveType;
import ai.representation.PieceType;
import ai.representation.piece.Bishop;
import ai.representation.piece.ColoredPiece;
import ai.representation.piece.EmptyPiece;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Rook;
import game.Game;

/**
 * This
 * 
 * @author adam adolf
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
		ColoredPiece piece = boardMap.get(from);
		if(piece == null) {
			throw new RuntimeException("Trying to allocate piece from non existing square with coordinate: " + from);
		}
		Color sideColor = piece.getColor();
		switch (piece.getPieceType()) {
		case PAWN:
			for (Integer vector : Pawn.getMoveVectors(sideColor)) {
				Integer to = from + vector;
				if (isValidPawnMove(from, to, currentBoardState, sideColor)) {
					moveList.add(to);
				}
				// if on init square check and add double forward move too
				if (isPawnOnInitialSquare(from, sideColor) && isValidPawnMove(from, to, currentBoardState, sideColor)
						&& isValidPawnMove(from, to + vector, currentBoardState, sideColor)) {
					moveList.add(to + vector);
				}
			}
			break;
		case KNIGHT:
			for (Integer vector : Knight.getMoveVectors()) {
				Integer to = from + vector;
				if (!offTheGrid(to) && boardMap.get(to).getColor() != sideColor && rowDistance(from, to) < 3
						&& columnDistance(from, to) < 3) {
					moveList.add(to);
				}
			}
			break;
		case BISHOP:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
				while (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			break;
		case QUEEN:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
				while (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						// moveList.add(to); //TODO comment upper add and decomment this to filter out
						// non take moves (for search algorithm testing purposes, decreasing the move
						// numbers)
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				while (isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						// moveList.add(to); //TODO comment upper add and decomment this to filter out
						// non take moves (for search algorithm testing purposes, decreasing the move
						// numbers)
						break;
					}
					tmpFrom = to;
					to += vector;
				}
			}
			break;
		case KING:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
				if (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor) && !isKingNearby(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
				}
			}
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				if (isValidRookMove(tmpFrom, to, currentBoardState, sideColor) && !isKingNearby(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
				}
			}
			break;
		case ROOK:
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				while (isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
					moveList.add(to);
					if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
						// moveList.add(to); //TODO comment upper add and decomment this to filter out
						// non take moves (for search algorithm testing purposes, decreasing the move
						// numbers)
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
	 * 
	 * @param from
	 * @param currentBoardState
	 * @return possible moves for the piece on a given position
	 */
	public ColoredPiece[][] generateMap(Integer from, Board currentBoardState, MoveType moveType) {
		if(moveType == null)
			throw new RuntimeException("null as moveType not allowed");
		ColoredPiece[][] arr = new ColoredPiece[64][64];
		List<Integer> moveList = new ArrayList<Integer>();
		Board boardMap = currentBoardState;
		ColoredPiece piece = boardMap.get(from);
		Color sideColor = piece.getColor();
		switch (piece.getPieceType()) {
		case PAWN:
			for (Integer vector : Pawn.getAttackDefenseMoveVectors(sideColor)) {
				Integer to = from + vector;
				switch (moveType) {
				case DEFENSE: 
					if (isValidPawnDefenseMove(from, to, currentBoardState, sideColor)) {
						arr[from][to] = new ColoredPiece(PieceType.PAWN, sideColor);
					}
					break;
				case ATTACK:
					if (isValidPawnMove(from, to, currentBoardState, sideColor)) {
						arr[from][to] = new ColoredPiece(PieceType.PAWN, sideColor);
					}
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + moveType);
				}
			}
			break;
		case KNIGHT:
			for (Integer vector : Knight.getMoveVectors()) {
				Integer to = from + vector;
				switch(moveType){
				case ATTACK:
					if (!offTheGrid(to) && boardMap.get(to).getColor() == sideColor.opposite() && rowDistance(from, to) < 3
					&& columnDistance(from, to) < 3) {
						arr[from][to] = new ColoredPiece(PieceType.KNIGHT, sideColor);
					}
					break;
				case DEFENSE:
					if (!offTheGrid(to) && boardMap.get(to).getColor() == sideColor && rowDistance(from, to) < 3
					&& columnDistance(from, to) < 3) {
						arr[from][to] = new ColoredPiece(PieceType.KNIGHT, sideColor);
					}
					break;
				}
			}
			break;
		case BISHOP:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
					switch(moveType){
					case ATTACK:
						while (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
							if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
								arr[from][to] = new ColoredPiece(PieceType.BISHOP, sideColor);
								break;
							}
							tmpFrom = to;
							to += vector;
						}
					break;
					case DEFENSE:
						while (isValidBishopDefenseMove(tmpFrom, to, currentBoardState, sideColor)) {
							if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
								break;
							}
							if (Board.getColorOf(to, currentBoardState) == sideColor) {
								arr[from][to] = new ColoredPiece(PieceType.BISHOP, sideColor);
								break;
							}
							tmpFrom = to;
							to += vector;
						}
					break;
					}
			}
			break;
		case QUEEN:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
					switch(moveType){
					case ATTACK:
						while (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor)) {
							if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
								arr[from][to] = new ColoredPiece(PieceType.QUEEN, sideColor);
								break;
							}
							tmpFrom = to;
							to += vector;
						}
					break;
					case DEFENSE:
						while (isValidBishopDefenseMove(tmpFrom, to, currentBoardState, sideColor)) {
							if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
								break;
							}
							if (Board.getColorOf(to, currentBoardState) == sideColor) {
								arr[from][to] = new ColoredPiece(PieceType.QUEEN, sideColor);
								break;
							}
							tmpFrom = to;
							to += vector;
						}
					break;
					}
			}
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				switch(moveType){
				case ATTACK:
					while (isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							arr[from][to] = new ColoredPiece(PieceType.QUEEN, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				case DEFENSE:
					while (isValidRookDefenseMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							break;
						}
						if (Board.getColorOf(to, currentBoardState) == sideColor) {
							arr[from][to] = new ColoredPiece(PieceType.QUEEN, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				}
			}
			break;
		case KING:
			for (int vector : Bishop.getMoveVectors()) {
				int tmpFrom = from;
				int to = tmpFrom + vector;
				switch(moveType){
				case ATTACK:
					if (isValidBishopMove(tmpFrom, to, currentBoardState, sideColor) && !isKingNearby(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							arr[from][to] = new ColoredPiece(PieceType.KING, sideColor);
							break;
						}
					}
				break;
				case DEFENSE:
					if (isValidBishopDefenseMove(tmpFrom, to, currentBoardState, sideColor) && !isKingNearby(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor) {
							arr[from][to] = new ColoredPiece(PieceType.KING, sideColor);
							break;
						}
					}
				break;
				}
			}
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				switch(moveType){
				case ATTACK:
					while (isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							arr[from][to] = new ColoredPiece(PieceType.ROOK, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				case DEFENSE:
					while (isValidRookDefenseMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							break;
						}
						if (Board.getColorOf(to, currentBoardState) == sideColor) {
							arr[from][to] = new ColoredPiece(PieceType.ROOK, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				}
			}
			break;
		case ROOK:
			for (Integer vector : Rook.getMoveVectors()) {
				Integer tmpFrom = from;
				Integer to = tmpFrom + vector;
				switch(moveType){
				case ATTACK:
					while (isValidRookMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							arr[from][to] = new ColoredPiece(PieceType.ROOK, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				case DEFENSE:
					while (isValidRookDefenseMove(tmpFrom, to, currentBoardState, sideColor)) {
						if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
							break;
						}
						if (Board.getColorOf(to, currentBoardState) == sideColor) {
							arr[from][to] = new ColoredPiece(PieceType.ROOK, sideColor);
							break;
						}
						tmpFrom = to;
						to += vector;
					}
				break;
				}
			}
			break;
		default:
			break;
		}
		return arr;
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
		Stream<Entry<Integer, ColoredPiece>> boardStream = board.getBoardMapReference().entrySet().stream();
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
		Stream<Entry<Integer, ColoredPiece>> boardStream = board.getBoardMapReference().entrySet().stream();
		boardStream = boardStream.filter((e) -> e.getValue().getColor() == color);
		return boardStream.map(Entry::getKey).collect(Collectors.toList());
	}

	public List<Integer> getAll(int pov, Board board) {
		Color color = (pov == 1) ? Color.LIGHT : Color.DARK;
		Stream<Entry<Integer, ColoredPiece>> boardStream = board.getBoardMapReference().entrySet().stream();
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
			if (!generateMoves(position, board).isEmpty()) {
				movablePiecePositions.add(position);
			}
		}
		return movablePiecePositions;
	}

	public List<Board> generatePositions(Board board, Color color) {
		List<Board> positions = new ArrayList<>();
		for (Integer from : getAllMovable(color, board)) {
			for (Integer to : generateMoves(from, board)) {
				Board nextBoard = Board.deepCopy(board);
				ColoredPiece piece = nextBoard.grabPieceAndCleanFrom(from);
				nextBoard.place(piece, to);
				nextBoard.setTransitionMoveFromPreviousBoard(
						new Move(from, to, piece, board.getBoardMapReference().get(to)));
				positions.add(nextBoard);
			}
		}
		return positions;
	}

	public List<Board> generatePositions(Board board, int from) {
		List<Board> positions = new ArrayList<>();
		for (Integer to : generateMoves(from, board)) {
			Board nextBoard = Board.deepCopy(board);
			ColoredPiece piece = nextBoard.grabPieceAndCleanFrom(from);
			nextBoard.setTransitionMoveFromPreviousBoard(
					new Move(from, to, piece, board.getBoardMapReference().get(to)));
			nextBoard.place(piece, to);
			positions.add(nextBoard);
		}
		return positions;
	}

	// random move generator methods
	//
	//
	//
	//
	//
	//
	//

	/**
	 * Pick random piece of given color
	 * 
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

	public boolean IsKingInCheck(final Color color, final Board board) {
		List<Integer> pieceList = getAll(color, board);
		Integer kingSquare = -1;
		for (Integer key : pieceList) {
			if (PieceType.KING == board.getBoardMapReference().get(key).getPieceType()) {
				kingSquare = key;
				break;
			}
		}
		ColoredPiece[] pieces = new ColoredPiece[] { new ColoredPiece(PieceType.KNIGHT, color.opposite()),
				new ColoredPiece(PieceType.BISHOP, color.opposite()),
				new ColoredPiece(PieceType.ROOK, color.opposite()), new ColoredPiece(PieceType.QUEEN, color.opposite()),
				new ColoredPiece(PieceType.PAWN, color.opposite()) };
		for (ColoredPiece piece : pieces) {
			if (isAttackedBy(kingSquare, piece, board)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * !!hacky places piece on square generates moves with the piece, if the same
	 * color and same piece is found then the initial square is being attacked by
	 * piece
	 * 
	 * @param square Square we want to know is being attacked by piece
	 * @param piece  Type of the attacker we're looking for
	 * @param board  Current board we're examining
	 * @return
	 */
	public boolean isAttackedBy(final Integer square, final ColoredPiece piece, final Board board) {
		Board boardClone = Board.deepCopy(board);
		ColoredPiece attackedPiece = boardClone.get(square);
		ColoredPiece helper = piece.opposite();
		boardClone.place(helper, square);
		List<Integer> possibleAttackerSquares = generateMoves(square, boardClone); // TODO pawn moves should be adjusted
																					// here
		for (Integer sq : possibleAttackerSquares) {
			ColoredPiece pieceOnAttackerSquare = boardClone.getBoardMapReference().get(sq);
			if (helper.getColor() == pieceOnAttackerSquare.getColor().opposite()
					&& pieceOnAttackerSquare.getPieceType() == helper.getPieceType()) {
				//System.out.println(board);
				System.out.println("MoveGenerator [isAttackedBy]: " + attackedPiece + " is attacked by " + pieceOnAttackerSquare + piece + " on square coord: " + sq);
				return true;
			}
		}
		return false;
	}

	/*	public ColoredPiece[][] getAttackers(Color color, final Board board){
			
			
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
	// helper methods
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

	private boolean isValidPawnMove(final int from, final int to, final Board board, final Color sideColor) {
		// TODO en passant
		// TODO handle promotion
		if (sameColumn(from, to)) {
			return !offTheGrid(to) && Board.isEmptySquare(to, board);
		} else if (columnDistance(from, to) < 2) {
			return !offTheGrid(to) && Board.getColorOf(to, board) == sideColor.opposite();
		}
		return false;
	}
	
	private boolean isValidPawnDefenseMove(final int from, final int to, final Board board, final Color sideColor) {
		// TODO en passant
		// TODO handle promotion
		if (sameColumn(from, to)) {
			return !offTheGrid(to) && Board.isEmptySquare(to, board);
		} else if (columnDistance(from, to) < 2) {
			return !offTheGrid(to) && Board.getColorOf(to, board) == sideColor;
		}
		return false;
	}

	private boolean isValidBishopMove(final int from, final int to, final Board board, final Color sideColor) {
		return !offTheGrid(to) && differentColor(Board.getColorOf(to, board), sideColor) && rowDistance(from, to) < 2
				&& columnDistance(from, to) < 2;
	}
	
	private boolean isValidBishopDefenseMove(final int from, final int to, final Board board, final Color sideColor) {
		return !offTheGrid(to) && rowDistance(from, to) < 2
				&& columnDistance(from, to) < 2;
	}

	private boolean isValidRookMove(final int from, final int to, final Board board, final Color sideColor) {
		return !offTheGrid(to) && differentColor(Board.getColorOf(to, board), sideColor)
				&& (sameRow(from, to) || sameColumn(from, to));
	}
	
	private boolean isValidRookDefenseMove(final int from, final int to, final Board board, final Color sideColor) {
		return !offTheGrid(to) && (sameRow(from, to) || sameColumn(from, to));
	}

	private boolean differentColor(Color colorOne, Color colorTwo) {
		return colorOne != colorTwo;
	}

	private boolean isPawnOnInitialSquare(int position, Color sideColor) {
		if (sideColor == Color.LIGHT) {
			return (row(position) == 1);
		} else if (sideColor == Color.DARK) {
			return (row(position) == 6);
		}
		return false;
	}
	
	/*private void castle(Color color, Board board, Game game) {
		if(color == Color.DARK) {
			if(game.darkKingSideCastle) {
				board.getBoardMapReference()
			}
		} else if (color == Color.LIGHT) {
			
		}
	}*/
	
	public boolean isKingNearby(Integer tmpFrom,Integer to,Board board, Color sideColor) {
		//int actualVec = to - tmpFrom;
		List<Integer> nearbySquares = new ArrayList<Integer>();
		for (int kingVec : PieceType.KING.moveVectors) {
			int lookAheadPosition = to + kingVec;
			nearbySquares.add(lookAheadPosition);
		}
		
		for (Integer square : nearbySquares) {
			if(board.get(square) != null && board.get(square).equals(new ColoredPiece(PieceType.KING, sideColor.opposite()))) {
				return true;
			}
		}
		return false;
	}
	

}
