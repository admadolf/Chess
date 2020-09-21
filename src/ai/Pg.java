package ai;

import ai.eval.ComplexMiniMaxEvaluateImpl;
import ai.eval.Evaluate;
import ai.eval.MaterialMiniMaxEvaluateImpl;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.Node;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;
import ai.search.MiniMaxSearch;

public class Pg {

	
	
	public static void main(String[] args) {
		
		Board board =  new Board();
		//firstExample(board);
		//board.initBoard();
		board.initEmptyBoard();
		MoveGenerator mg = new MoveGenerator();
		
		board.place(new Rook(Color.LIGHT), 10);
		board.place(new Knight(Color.DARK), 27);
		System.out.println(board.toString());
		System.out.println(mg.isAttackedBy(10, new Knight(Color.DARK), board));
	}
	
	public static void testBoardMethods() {
		Board board =  new Board();
		board.initBoard();
		
		Board newBoard = Board.deepCopy(board);
		
		newBoard.grabPieceAndCleanFrom(0);
		
		System.out.println(board.toString());
		System.out.println(newBoard.toString());
	}
	
	
	public static void firstExample(Board board) {
		board.initEmptyBoard();
		Evaluate evaluator = new MaterialMiniMaxEvaluateImpl();
		
		board.place(new Rook(Color.LIGHT), 9);
		board.place(new Rook(Color.DARK), 11);
		board.place(new Queen(Color.DARK), 33);
		board.place(new Queen(Color.DARK), 34);
		board.place(new Queen(Color.DARK), 63);
		board.place(new Queen(Color.LIGHT), 7);
		
		
		MiniMaxSearch miniMax = new MiniMaxSearch(board);
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		MoveGenerator movegen = new MoveGenerator();
		int best = miniMax.maxi(2, evaluator, board, pvTree, movegen); //todo revisit this
		
		System.out.println("----------");
		System.out.println(pvTree.toString());
		
		System.out.println("best move: " + pvTree.getPvNodes().peekLast().getMoveStringWithRating());
		System.out.println("best position :\n " + pvTree.getPvNodes().peekLast().getPosition());
		
		for (Node bm : pvTree.getPvNodes()) {
			//System.out.println(bm.getMoveStringWithRating());
			if(bm.getPosition().getTransitionMoveFromPreviousBoard().getFrom() == 7 && bm.getPosition().getTransitionMoveFromPreviousBoard().getTo() == 21) {
				System.out.println("random subtree check...");
				System.out.println(bm);
			}
			
		}
	}
	
	public static void secondExample(Board board) {
		board.initBoard();
//		Board.place(new EmptyPiece(), 6, board);
//		Board.place(new EmptyPiece(), 1, board);
//		Board.place(new EmptyPiece(), 61, board);
//		Board.place(new EmptyPiece(), 58, board);
//		Board.place(new Queen(Color.LIGHT), 32, board);
		Evaluate complexEval = new ComplexMiniMaxEvaluateImpl();
		MiniMaxSearch miniMax = new MiniMaxSearch(board);
		Node pvTree = new Node();
		pvTree.setDepth(0);
		pvTree.setParent(null);
		pvTree.setPosition(board);
		MoveGenerator movegen = new MoveGenerator();
		int best = miniMax.mini(3, complexEval, board, pvTree, movegen);
		System.out.println("----------");
		System.out.println(pvTree.toString());
		
		System.out.println("best move: " + pvTree.getPvNodes().peekLast().getMoveStringWithRating());
		System.out.println("best position :\n " + pvTree.getPvNodes().peekLast().getPosition());
		
	}
	

	
}
