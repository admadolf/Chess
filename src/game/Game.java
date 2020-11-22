package game;

import java.util.Arrays;
import java.util.LinkedList;

import ai.eval.ComplexMiniMaxEvaluateImpl;
import ai.eval.Evaluate;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Castle;
import ai.representation.Color;
import ai.representation.Node;
import ai.representation.PieceType;
import ai.representation.piece.ColoredPiece;
import ai.search.MiniMaxSearch;

/**
 *  instantiate a game of chess, encapsulating ai setup,save information about game - castle rule, en passant, history of the PVNodes / board states / move states / check for mate etc.
 *  this class should know the whole state space of the game, should take care of both human and ai players
 * @author konek
 *
 */
public class Game {

	boolean onTurn;
	
	public boolean hasLightCastledYet;
	public boolean hasDarkCastledYet;
	
	private Integer enPassantSquare = null;
	
	private Board[] state = new Board[400];

	private MoveGenerator generator = new MoveGenerator();
	
	private Board board = new Board();
	
	Evaluate lightEvaluator = new ComplexMiniMaxEvaluateImpl();
	
	MiniMaxSearch search = new MiniMaxSearch();
	
	Evaluate darkEvaluator = new ComplexMiniMaxEvaluateImpl();
	
	public Game() {
		super();
		board.initBoard();
		state[0] = board;
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
	
	/*	public Game(MoveGenerator generator, Board board, Evaluate evaluator, MiniMaxSearch search) {
			super();
			this.generator = generator;
			this.board = board;
			this.evaluator = evaluator;
			this.search = search;
		}*/

	public LinkedList<Node> moveLight(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.maxi(lookAheadDepth, this.lightEvaluator, board, pvTree, this.generator);
		//System.out.println("best move: " + pvTree.getPvNodes().peekLast().getMoveStringWithRating());
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public LinkedList<Node> moveDark(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.mini(lookAheadDepth, this.darkEvaluator, board, pvTree, this.generator);
		//System.out.println("best move: " + pvTree.getPvNodes().peekLast().getMoveStringWithRating());
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public void setGenerator(MoveGenerator generator) {
		this.generator = generator;
	}

	public void setBoard(Board board) {
		this.board = board;
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
	
	public boolean calculateCanCastle(Color color, Castle castle) {
		boolean result = true;
		boolean lightSide = color == Color.LIGHT;
		if(castleBlocked(color, castle)) {
			return false;
		}
		boolean hasCastledYet = lightSide ? hasLightCastledYet : hasDarkCastledYet;
		int i = lightSide ? 1 : 2;
		int initialKingSq = lightSide ? 4 : 60;
		
		int initialRookSq = -1;
		if(lightSide) {
			initialRookSq = castle == Castle.SHORT ? 7 : 0; 
		} else {
			initialRookSq = castle == Castle.SHORT ? 63 : 56;
		}
		if(!hasCastledYet) {
			while (state[i] != null) {
				boolean hasKingMoved = state[i].getTransitionMoveFromPreviousBoard().getFrom() == initialKingSq
						&& new ColoredPiece(PieceType.KING, color).equals(state[i].getTransitionMoveFromPreviousBoard().getFromPiece());
				
				boolean hasRookMoved = state[i].getTransitionMoveFromPreviousBoard().getFrom() == initialRookSq
						&& new ColoredPiece(PieceType.ROOK, color).equals(state[i].getTransitionMoveFromPreviousBoard().getFromPiece());
				
				if(hasKingMoved || hasRookMoved) {
					result = false;
					break;
				}
				i+=2;
			}
		}
		return result;
	}
	
	private boolean castleBlocked(Color color, Castle castle) {
		boolean isCastlingBlocked = false;
		Board latestBoard = getLatestBoard(); //maybe move this 
		//to argument and move this to king or some better place? cuz this has nothing to do with
		//the game itself?
		switch (color) {
		case LIGHT:
			switch(castle) {
			case LONG:
				if(!Board.batchIsEmptySquare(latestBoard, 1,2,3)) {
					isCastlingBlocked = true;
				}
				break;
			case SHORT:
				if(!Board.batchIsEmptySquare(latestBoard, 5,6)) {
					isCastlingBlocked = true;
				}
				break;
			}
			break;
		case DARK:
			switch(castle) {
			case LONG:
				if(!Board.batchIsEmptySquare(latestBoard, 57,58,59)) {
					isCastlingBlocked = true;
				}
				break;
			case SHORT:
				if(!Board.batchIsEmptySquare(latestBoard, 61,62)) {
					isCastlingBlocked = true;
				}
				break;
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
	
	
	
}
