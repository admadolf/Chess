package game;

import java.util.Arrays;
import java.util.LinkedList;

import ai.eval.MiniMaxEvaluateImpl;
import ai.eval.Evaluate;
import ai.movegen.Move;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.Node;
import ai.representation.PieceType;
import ai.representation.piece.ColoredPiece;
import ai.search.MiniMaxSearch;

/**
 *  instantiate a game of chess, encapsulating ai setup,save information about game - castle rule, en passant, history of the PVNodes / board states / move states /  etc.
 *  this class should know the whole state space of the game, should take care of both human and ai players
 * @author konek
 *
 */
public class Game {

	private int index = 0;
	
	private boolean hasLightCastledYet;
	
	private boolean hasDarkCastledYet;
	
	private Board[] state = new Board[400];

	private MoveGenerator generator = new MoveGenerator();
	
	private Evaluate lightEvaluator = new MiniMaxEvaluateImpl();
	
	private MiniMaxSearch search = new MiniMaxSearch();
	
	private Evaluate darkEvaluator = new MiniMaxEvaluateImpl();
	
	public Game() {
		super();
		Board.getASetuppedBoard();
		state[0] = Board.getASetuppedBoard();
	}

	public Board[] getState() {
		return state;
	}
	
	public Board getLatestBoard() {
		int i=0;
		while(state[i+1] != null) {
			++i;
		}
		 return state[i];
	}
	
	public LinkedList<Node> moveLight(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.maxi(lookAheadDepth, this.lightEvaluator, board, pvTree, this.generator, this);
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public LinkedList<Node> moveDark(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.mini(lookAheadDepth, this.darkEvaluator, board, pvTree, this.generator, this);
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public void setGenerator(MoveGenerator generator) {
		this.generator = generator;
	}

	public Evaluate getLightEvaluator() {
		return lightEvaluator;
	}

	public void setLightEvaluator(Evaluate lightEvaluator) {
		this.lightEvaluator = lightEvaluator;
	}

	public Evaluate getDarkEvaluator() {
		return darkEvaluator;
	}

	public void setDarkEvaluator(Evaluate darkEvaluator) {
		this.darkEvaluator = darkEvaluator;
	}
	
	public boolean calculateCanCastle(Color color, MoveType moveType) {
		boolean result = true;
		boolean lightSide = color == Color.LIGHT;
		
		boolean hasCastledYet = lightSide ? hasLightCastledYet : hasDarkCastledYet;
		
		if(castleBlocked(color, moveType) || hasCastledYet) {
			return false;
		}
		
		int i = lightSide ? 1 : 2;
		int initialKingSq = lightSide ? 4 : 60;
		
		int initialRookSq = -1;
		if(lightSide) {
			initialRookSq = moveType == MoveType.CASTLESHORT ? 7 : 0; 
		} else {
			initialRookSq = moveType == MoveType.CASTLESHORT ? 63 : 56;
		}
		if(!hasCastledYet) {
			while (state[i] != null) {
				Board board = state[i];
				Move historicalMove = board.getTransitionMoveFromPreviousBoard();
				//if king and rook castled this flag could still return false (they didnt move, because it only takes non castling moves into account) fixed?
				boolean hasKingMoved = (historicalMove.getFrom() == initialKingSq
						&& new ColoredPiece(PieceType.KING, color).equals(historicalMove.getFromPiece())) && (historicalMove.getMoveType() != MoveType.CASTLESHORT || historicalMove.getMoveType() != MoveType.CASTLELONG );
				
				boolean hasRookMoved = historicalMove.getFrom() == initialRookSq
						&& new ColoredPiece(PieceType.ROOK, color).equals(historicalMove.getFromPiece());
				if(hasKingMoved || hasRookMoved) {
					result = false;
					break;
				}
				i+=2;
			}
		}
		return result;
	}
	
	private boolean castleBlocked(Color color, MoveType moveType) {
		boolean isCastlingBlocked = false;
		Board latestBoard = getLatestBoard();
		switch (color) {
		case LIGHT:
			switch(moveType) {
			case CASTLELONG:
				if(!Board.batchIsEmptySquare(latestBoard, 1,2,3)) {
					isCastlingBlocked = true;
				}
				break;
			case CASTLESHORT:
				if(!Board.batchIsEmptySquare(latestBoard, 5,6)) {
					isCastlingBlocked = true;
				}
				break;
			default:
				throw new RuntimeException("Only CASTLESHORT and CASTLELONG moveType is supported when calling castling methods");
			}
			break;
		case DARK:
			switch(moveType) {
			case CASTLELONG:
				if(!Board.batchIsEmptySquare(latestBoard, 57,58,59)) {
					isCastlingBlocked = true;
				}
				break;
			case CASTLESHORT:
				if(!Board.batchIsEmptySquare(latestBoard, 61,62)) {
					isCastlingBlocked = true;
				}
				break;
			default:
				throw new RuntimeException("Only CASTLESHORT and CASTLELONG moveType is supported when calling castling methods");
			}
			break;
		default:
			throw new RuntimeException("Only DARK and LIGHT color supported when calling castling methods");
		}
		return isCastlingBlocked;
	}

	@Override
	public String toString() {
		return  Arrays.toString(getState());
	}

	public void addPositionToGame(Board board) {
		Move move = board.getTransitionMoveFromPreviousBoard();
		state[++index] = board;
		if(move.getMoveType() == MoveType.CASTLESHORT || move.getMoveType() == MoveType.CASTLELONG) {
			if(index % 2 == 0) {
				hasDarkCastledYet = true;
			} else {
				hasLightCastledYet = true;
			}
		}
	}

	public int getIndex() {
		return index;
	}

	
}
