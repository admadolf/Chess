package ai.eval;

import java.util.List;

import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;

public class MaterialHeuristic {

	
	MaterialValue materialValue = new MaterialValue();
	
	//https://www.chessprogramming.org/Evaluation#Linear_vs._Nonlinear
	
	public int evaluateMiniMax(Board position, MoveGenerator movegen) {
		Board board = Board.getBoardCloneNew(position);
		List<Integer> side = movegen.getAll(Color.LIGHT, board);
		List<Integer> oppositeSide = movegen.getAll(Color.DARK, board);
		int valueSum = 0;
		int oppositeValueSum = 0;
		for (Integer piece : side) {
			valueSum += materialValue.getValue(board.getBoardReference().get(piece));
		}
		for (Integer piece : oppositeSide) {
			oppositeValueSum += materialValue.getValue(board.getBoardReference().get(piece));
		}
		return (valueSum-oppositeValueSum);
	}
	
	
	
}
