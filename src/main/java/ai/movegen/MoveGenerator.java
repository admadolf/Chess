package ai.movegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import representation.Board;
import representation.Color;
import representation.ColoredPiece;
import representation.Game;
import representation.MoveType;
import representation.PieceType;
import representation.Vector;

/**
 * This
 * 
 * @author adam adolf
 */
public class MoveGenerator {

	/**
	 * 
	 * @param from
	 * @param currentBoard
	 * @return possible moves for the piece on a given position
	 */
	public List<Move> generateMoves(Integer from, Board currentBoard, Game game) {
		List<Move> moveList = new ArrayList<Move>();
		ColoredPiece piece = currentBoard.get(from);
		if(piece == null) {
			throw new RuntimeException("Trying to allocate piece from non existing square with coordinate: " + from);
		}
		Color sideColor = piece.getColor();
		switch (piece.getPieceType()) {
		case PAWN:
			moveList.addAll(generatePawnMoves(from, currentBoard, game, sideColor));
			break;
		case KNIGHT:
			moveList.addAll(generateKnightMoves(from, currentBoard, sideColor));
			break;
		case BISHOP:
			moveList.addAll(generateSlidingPieceMoves(from, currentBoard, sideColor));
			break;
		case QUEEN:
			moveList.addAll(generateSlidingPieceMoves(from, currentBoard, sideColor));
			break;
		case KING:
			moveList.addAll(generateKingMoves(from, currentBoard, game, sideColor));
			break;
		case ROOK:
			moveList.addAll(generateSlidingPieceMoves(from, currentBoard, sideColor));
			break;
		default:
			break;
		}
		return moveList;
	}


	private List<Move> generateKingMoves(Integer from, Board currentBoardState, Game game, Color sideColor) {
		List<Move> moveList = new ArrayList<Move>();
		switch(sideColor) {
		case LIGHT:
			Move lightShortCastleMove = new Move(MoveType.CASTLESHORT, 4, 7 , 6, 5);
			Move lightLongCastleMove = new Move(MoveType.CASTLELONG, 4, 0 , 2, 3);
			Board maybeLightShortCastledBoard = generateCastlePreconditionChecked(currentBoardState, game, sideColor, lightShortCastleMove);
			Board maybeLightLongCastledBoard = generateCastlePreconditionChecked(currentBoardState, game, sideColor, lightLongCastleMove);
			if(maybeLightShortCastledBoard != null && !isKingInCheck(sideColor, maybeLightShortCastledBoard, game)) {
				moveList.add(lightShortCastleMove);
			}
			if(maybeLightLongCastledBoard != null && !isKingInCheck(sideColor, maybeLightLongCastledBoard, game)) {
				moveList.add(lightLongCastleMove);
			}
			break;
		case DARK:
			Move darkShortCastleMove = new Move(MoveType.CASTLESHORT, 60, 63 , 62, 61);
			Move darkLongCastleMove = new Move(MoveType.CASTLELONG, 60, 56 , 58, 59);
			Board maybeDarkShortCastledBoard = generateCastlePreconditionChecked(currentBoardState, game, sideColor, darkShortCastleMove);
			Board maybeDarkLongCastledBoard = generateCastlePreconditionChecked(currentBoardState, game, sideColor, darkLongCastleMove);
			if(maybeDarkShortCastledBoard != null && !isKingInCheck(sideColor, maybeDarkShortCastledBoard, game)) {
				moveList.add(darkShortCastleMove);
			}
			if(maybeDarkLongCastledBoard != null && !isKingInCheck(sideColor, maybeDarkLongCastledBoard, game)) {
				moveList.add(darkLongCastleMove);
			}
			break;
			default:
			System.err.println("MoveGenerator calling with empty color is not legit");
		}
		for (int vector : getMoveVectors(PieceType.KING, sideColor)) {
			int tmpFrom = from;
			int to = tmpFrom + vector;
			Move move = new Move(from, to, currentBoardState.get(from), currentBoardState.get(to));
			if (isValidSlidingPieceMove(tmpFrom, to, currentBoardState, sideColor) && !isKingNearby(to, currentBoardState, sideColor) 
					&& !isKingInCheck(sideColor, Board.transposePositionToNewBoardInstance(currentBoardState, move), game)) {
				moveList.add(move);
			}
		}
		return moveList;
	}


	private List<Move> generateSlidingPieceMoves(Integer from, Board currentBoardState, Color sideColor) {
		List<Move> moveList = new ArrayList<Move>();
		for (Integer vector : getMoveVectors(currentBoardState.get(from))) {
			Integer tmpFrom = from;
			Integer to = tmpFrom + vector;
			while (isValidSlidingPieceMove(tmpFrom, to, currentBoardState, sideColor)) {
				moveList.add(new Move(from, to, currentBoardState.get(from), currentBoardState.get(to)));
				if (Board.getColorOf(to, currentBoardState) == sideColor.opposite()) {
					break;
				}
				tmpFrom = to;
				to += vector;
			}
		}
		return moveList;
	}


	private List<Move> generatePawnMoves(Integer from, Board currentBoardState, Game game, Color sideColor) {
		List<Move> moveList = new ArrayList<Move>();
		//if the previous move has en passant flag true, generate the en passant captures 
		Move previousMove = game.getLatestBoard().getTransitionMoveFromPreviousBoard();
		if(previousMove != null && previousMove.getMoveType() == MoveType.ENPASSANTFLAG){
			if(previousMove.getEnPassantCapturer1() != null) {
				Move enPassantMove1 = new Move(MoveType.ENPASSANT, previousMove.getEnPassantCapturer1(), previousMove.getEnPassantArrivalSquareAfterTake());
				enPassantMove1.setEnPassantToBeCaptured(previousMove.getEnPassantToBeCaptured());
				moveList.add(enPassantMove1);
			}
			if(previousMove.getEnPassantCapturer2() != null) {
				Move enPassantMove2 = new Move(MoveType.ENPASSANT, previousMove.getEnPassantCapturer2(), previousMove.getEnPassantArrivalSquareAfterTake());
				enPassantMove2.setEnPassantToBeCaptured(previousMove.getEnPassantToBeCaptured());
				moveList.add(enPassantMove2);
			}
		}
		for (Integer vector : getMoveVectors(currentBoardState.get(from))) {
			Integer to = from + vector;
			if (isValidPawnMove(from, to, currentBoardState, sideColor)) {
				Move move;
				if(isPawnOnPromotionSquare(to, sideColor)) {
					move = new Move(MoveType.PROMOTION, from, to);
				} else {
					move = new Move(from, to, currentBoardState.get(from), currentBoardState.get(to));
				}
				moveList.add(move);
			}
			// if on init square check and add double forward move too
			if (isPawnOnInitialSquare(from, sideColor) && isValidPawnMove(from, to, currentBoardState, sideColor)
					&& isValidPawnMove(from, to + vector, currentBoardState, sideColor)) {
				Move doublePawnMove = new Move(from, to + vector, currentBoardState.get(from), currentBoardState.get(to + vector));
				moveList.add(doublePawnMove);
				//en passant opportunity can only happen after at least 3 moves
				if(game.getIndex() > 2) {
					setupEnPassant(sideColor, doublePawnMove, currentBoardState);
				}
			}
		}
		return moveList;
	}


	private List<Move> generateKnightMoves(Integer from, Board currentBoardState, Color sideColor) {
		List<Move> moveList = new ArrayList<Move>();
		for (Integer vector : getMoveVectors(currentBoardState.get(from))) {
			Integer to = from + vector;
			if (!offTheGrid(to) && currentBoardState.get(to).getColor() != sideColor && rowDistance(from, to) < 3
					&& columnDistance(from, to) < 3) {
				moveList.add(new Move(from, to, currentBoardState.get(from), currentBoardState.get(to)));
			}
		}
		return moveList;
	}

	private Board generateCastlePreconditionChecked(Board currentBoardState, Game game, Color sideColor, Move castleMove) {
		boolean castlePrecondition = !isKingInCheck(sideColor, currentBoardState, game) && 
				game.calculateCanCastle(sideColor, castleMove.getMoveType());
		if(castlePrecondition) {
			return Board.transposePositionToNewBoardInstance(currentBoardState, castleMove);
		}
		return null;
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
		Stream<Entry<Integer, ColoredPiece>> boardStream = board
				.getBoardMapReference()
				.entrySet()
				.stream();
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
	public List<Board> generatePositions(Board board, Color color, Game game) {
		List<Board> positions = new ArrayList<>();
		for (Integer from : getAll(color, board)) {
			for (Move move : generateMoves(from, board, game)) {
				positions.add(Board.transposePositionToNewBoardInstance(board, move));
			}
		}
		return positions;
	}

	public List<Board> generatePositions(Board board, int from, Game game) {
		List<Board> positions = new ArrayList<>();
		for (Move move : generateMoves(from, board, game)) {
			positions.add(Board.transposePositionToNewBoardInstance(board, move));
		}
		return positions;
	}

	public boolean isKingInCheck(final Color color,final Board temporaryBoard, Game game) {
		List<Integer> pieceList = getAll(color, temporaryBoard);
		Integer kingSquare = null;
		for (Integer key : pieceList) {
			if (PieceType.KING == temporaryBoard.getBoardMapReference().get(key).getPieceType()) {
				kingSquare = key;
				break;
			}
		}
		ColoredPiece[] pieces = new ColoredPiece[] { new ColoredPiece(PieceType.KNIGHT, color.opposite()),
				new ColoredPiece(PieceType.BISHOP, color.opposite()),
				new ColoredPiece(PieceType.ROOK, color.opposite()), new ColoredPiece(PieceType.QUEEN, color.opposite()),
				new ColoredPiece(PieceType.PAWN, color.opposite()) };
		for (ColoredPiece piece : pieces) {
			if (isAttackedBy(kingSquare, piece, temporaryBoard, game)) {
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
	 * @param square The Square we want to know whether is being attacked by piece
	 * @param piece  Type of the attacker we're looking for
	 * @param board  Current board we're examining
	 * @return
	 */
	public boolean isAttackedBy(final Integer square, final ColoredPiece piece, final Board board, final Game game) {
		if(square == null) {
			return false;
		}
		Board boardClone = Board.deepCopy(board);
		ColoredPiece helper = piece.opposite();
		boardClone.place(helper, square);
		List<Move> possibleAttackerSquares = generateMoves(square, boardClone, game); // TODO pawn moves should be adjusted
																					// here
		for (Move move : possibleAttackerSquares) {
			ColoredPiece pieceOnAttackerSquare = boardClone.getBoardMapReference().get(move.getTo());
			if (helper.getColor() == pieceOnAttackerSquare.getColor().opposite()
					&& pieceOnAttackerSquare.getPieceType() == helper.getPieceType()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the coordinate inside the coordinate frame
	 * 
	 * @param key
	 * @return
	 */
	private boolean offTheGrid(int key) {
		return !(0 <= key && key <= 63);
	}

	public int row(int position) {
		return position / 8;
	}

	public int column(int position) {
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
		if (sameColumn(from, to)) {
			return !offTheGrid(to) && Board.isEmptySquare(to, board);
		} else if (columnDistance(from, to) < 2) {
			return !offTheGrid(to) && Board.getColorOf(to, board) == sideColor.opposite();
		}
		return false;
	}
	
	private boolean isValidSlidingPieceMove(final int from, final int to, final Board board, final Color sideColor) {
		return !offTheGrid(to) && differentColor(Board.getColorOf(to, board), sideColor) && rowDistance(from, to) < 2
				&& columnDistance(from, to) < 2;
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
	
	private boolean isPawnOnPromotionSquare(int position, Color sideColor) {
		if (sideColor == Color.LIGHT) {
			return (row(position) == 7);
		} else if (sideColor == Color.DARK) {
			return (row(position) == 0);
		}
		return false;
	}
	
	public boolean isKingNearby(Integer to,Board board, Color sideColor) {
			List<Integer> nearbySquares = new ArrayList<Integer>();
			for (int kingVec : getMoveVectors(PieceType.KING, sideColor)) {
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
	
	
	/**
	 * Fill up helper fields in generated move after a double pawn move noting which pawns are able to capture en passant and
	 * the arrival square of those pawns after capture, the previous moves "to" square will be the square to remove the pawn from
	 * The rest of en passant will be handled in transposePosition
	 * move candidate squares will be null if neighbour after double move is not a pawn
	 * @param sideColor
	 * @param move
	 * @param board
	 */
	private void setupEnPassant(Color sideColor, Move move, Board board) {
		int doubleMoveArrivalSquare = move.getTo();
		int neighbourSquareOffset = 1;
		Integer enPassantCandidate1Square = doubleMoveArrivalSquare-neighbourSquareOffset;
		Integer enPassantCandidate2Square = doubleMoveArrivalSquare+neighbourSquareOffset;
		setupEnPassantCapturer(sideColor, move, board, doubleMoveArrivalSquare, enPassantCandidate1Square);
		setupEnPassantCapturer(sideColor, move, board, doubleMoveArrivalSquare, enPassantCandidate2Square);
	}


	private void setupEnPassantCapturer(Color sideColor, Move move, Board board, int doubleMoveArrivalSquare,
			Integer enPassantCandidateSquare) {
		int enPassantOffset = 8;
		ColoredPiece candidateToCapturePiece = board.get(enPassantCandidateSquare);
		if(candidateToCapturePiece.getPieceType() == PieceType.PAWN && candidateToCapturePiece.getColor().opposite() == sideColor && isValidPawnMove(move.getFrom(), enPassantCandidateSquare, board, sideColor)) {
			move.setEnPassantCapturer1(enPassantCandidateSquare);
			move.setEnPassantArrivalSquareAfterTake(sideColor == Color.LIGHT ? doubleMoveArrivalSquare - enPassantOffset : doubleMoveArrivalSquare + enPassantOffset);
			move.setEnPassantToBeCaptured(doubleMoveArrivalSquare);
			move.setMoveType(MoveType.ENPASSANTFLAG);
		}
	}
	
	private List<Integer> getMoveVectors(PieceType pieceType, Color color) {
		switch (pieceType) {
		case PAWN:
			switch(color) {
			case DARK:
				return Arrays.asList(new Integer[] {Vector.SOUTH,Vector.SW,Vector.SE});
			case LIGHT:
				return Arrays.asList(new Integer[] {Vector.NORTH,Vector.NW,Vector.NE});
			default:
				break;
			}
		case KNIGHT:
			return Arrays.asList(new Integer[] {
					Vector.NORTH+Vector.NW, 
					Vector.NORTH+Vector.NE,
					Vector.EAST+Vector.NE,
					Vector.EAST+Vector.SE,
					Vector.SOUTH+Vector.SE,
					Vector.SOUTH+Vector.SW,
					Vector.WEST+Vector.SW,
					Vector.WEST+Vector.NW});
		case BISHOP:
			return Arrays.asList(new Integer[] {Vector.NW,Vector.NE,Vector.SE,Vector.SW});
		case QUEEN:
			return Arrays.asList(
					new Integer[] {
							Vector.NW,Vector.NE,Vector.SE,Vector.SW,
							Vector.NORTH,Vector.EAST,Vector.SOUTH,Vector.WEST
					});
		case KING:
			return Arrays.asList(new Integer[] {
					Vector.NW,Vector.NE,Vector.SE,Vector.SW,
					Vector.NORTH,Vector.EAST,Vector.SOUTH,Vector.WEST
			});
		case ROOK:
			return Arrays.asList(new Integer[] {
					Vector.NORTH,
					Vector.EAST,
					Vector.SOUTH,
					Vector.WEST});
		default:
			return Arrays.asList(new Integer[] {});
		}
	}
	
	private List<Integer> getMoveVectors(ColoredPiece coloredPiece) {
		return getMoveVectors(coloredPiece.getPieceType(), coloredPiece.getColor());
	}

}
