package ai.eval;

import java.util.List;

import ai.movegen.MoveGenerator;
import representation.Board;
import representation.Color;
import representation.ColoredPiece;

public class MiniMaxEvaluateImpl implements Evaluate {

	MaterialValue materialValue = new MaterialValue();
	MoveGenerator movegen = new MoveGenerator();
	PieceSquareTable psTable = new PieceSquareTable();
	
	@Override
	public int evaluate(Board position) {
		Board board = Board.deepCopy(position);
		List<Integer> side = movegen.getAll(Color.LIGHT, board);
		List<Integer> oppositeSide = movegen.getAll(Color.DARK, board);
		int valueSum = 0;
		int oppositeValueSum = 0;
		for (Integer coordinate : side) {
			ColoredPiece piece = board.getBoardMapReference().get(coordinate);
			valueSum += MaterialValue.getValue(piece) + psTable.getPSTableValue(piece, coordinate);
		}
		for (Integer coordinate : oppositeSide) {
			ColoredPiece piece = board.getBoardMapReference().get(coordinate);
			oppositeValueSum += MaterialValue.getValue(piece) + psTable.getPSTableValue(piece, coordinate);
		}
		return (valueSum-oppositeValueSum);
	}

}
