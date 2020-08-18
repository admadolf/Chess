package ai.eval;

import java.util.List;

import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.piece.Piece;

public class ComplexMiniMaxEvaluateImpl implements Evaluate {

	MaterialValue materialValue = new MaterialValue();
	MoveGenerator movegen = new MoveGenerator();
	PieceSquareTable psTable = new PieceSquareTable();
	
	@Override
	public int evaluateMiniMax(Board position) {
		Board board = Board.deepCopy(position);
		List<Integer> side = movegen.getAll(Color.LIGHT, board);
		List<Integer> oppositeSide = movegen.getAll(Color.DARK, board);
		int valueSum = 0;
		int oppositeValueSum = 0;
		for (Integer coordinate : side) {
			Piece piece = board.getBoardMapReference().get(coordinate);
			valueSum += materialValue.getValue(piece)
					+ psTable.getPSTableValue(piece, coordinate);
		}
		for (Integer coordinate : oppositeSide) {
			Piece piece = board.getBoardMapReference().get(coordinate);
			oppositeValueSum += materialValue.getValue(piece)
					+ psTable.getPSTableValue(piece, coordinate);
		}
		return (valueSum-oppositeValueSum);
	}

}
