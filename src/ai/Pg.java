package ai;

import ai.eval.ComplexMiniMaxEvaluateImpl;
import ai.eval.Evaluate;
import ai.eval.MaterialMiniMaxEvaluateImpl;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.PVNode;
import ai.representation.piece.EmptyPiece;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;
import ai.search.MiniMaxSearch;

public class Pg {

	
	
	public static void main(String[] args) {
		Board board =  new Board();
		firstExample(board);
		
		
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
		miniMax.setDepth(0);
		PVNode pvTree = new PVNode();
		pvTree.setDepth(0);
		pvTree.setParent(null);
		pvTree.setValue(board);
		miniMax.setPvTree(pvTree);
		int best = miniMax.maxi(1, evaluator, board, pvTree); //todo revisit this
		
		System.out.println("----------");
		System.out.println(miniMax.getPvTree().toString());
		
		System.out.println("best move: " + miniMax.getPvTree().getBestMove());
		System.out.println("best position :\n " + miniMax.getPvTree().getPrincipalVariationFinalPosition());
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
		miniMax.setDepth(0);
		PVNode pvTree = new PVNode();
		pvTree.setDepth(0);
		pvTree.setParent(null);
		pvTree.setValue(board);
		miniMax.setPvTree(pvTree);
		int best = miniMax.mini(3, complexEval, board, pvTree);
		System.out.println("----------");
		System.out.println(miniMax.getPvTree().toString());
		
		System.out.println("best move: " + miniMax.getPvTree().getBestMove());
		System.out.println("best position :\n " + miniMax.getPvTree().getPrincipalVariationFinalPosition());
		
	}
	

	
}
