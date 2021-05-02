package ai.eval;

import java.util.List;

import ai.movegen.MoveGenerator;
import representation.Board;
import representation.Color;

public class MaterialHeuristic {

	
	MaterialValue materialValue = new MaterialValue();
	
	//https://www.chessprogramming.org/Evaluation#Linear_vs._Nonlinear
	
	public int evaluatePositionMaterial(Board position, MoveGenerator movegen) {
		Board board = Board.deepCopy(position);
		List<Integer> side = movegen.getAll(Color.LIGHT, board);
		List<Integer> oppositeSide = movegen.getAll(Color.DARK, board);
		int valueSum = 0;
		int oppositeValueSum = 0;
		for (Integer piece : side) {
			valueSum += MaterialValue.getValue(board.getBoardMapReference().get(piece));
		}
		for (Integer piece : oppositeSide) {
			oppositeValueSum += MaterialValue.getValue(board.getBoardMapReference().get(piece));
		}
		return (valueSum-oppositeValueSum);
	}
	
	
	
}
