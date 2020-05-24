package ai.eval;

import ai.movegen.MoveGenerator;
import ai.representation.Board;

public class MaterialMiniMaxEvaluateImpl implements Evaluate{

	MoveGenerator movegen = new MoveGenerator();
	
	MaterialHeuristic materialHeuristic = new MaterialHeuristic();
	PieceSquareTable psTable = new PieceSquareTable();
	
	@Override
	public int evaluateMiniMax(Board board) {
		return materialHeuristic.evaluateMiniMax(board,movegen);
	}

}
