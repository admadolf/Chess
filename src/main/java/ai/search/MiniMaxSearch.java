package ai.search;

import java.util.List;

import ai.eval.Evaluate;
import ai.movegen.Move;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.Node;
import game.Game;

public class MiniMaxSearch {

	
	public MiniMaxSearch(Board position) {
		super();
	}

	public MiniMaxSearch() {
		
	}

	public int maxi(int depthLimit,Evaluate evaluator, Board position,Node parent, MoveGenerator movegen, Game game) {
		int prevDepth = parent.getDepth();
		if(prevDepth == depthLimit) {
			int result = evaluator.evaluate(position);
			return result;
		}
		int max = -100001;
		List<Board> positions = movegen.generatePositions(position,Color.LIGHT, game);
		if (positions.isEmpty()) {
			return 1000002;
		}
		
		MiniMaxSearch mini = new MiniMaxSearch();
		int currDepth = ++prevDepth;
		for (Board nextPosition : positions)  {
			Node node = Node.createNode(currDepth, parent, nextPosition, "mini");
			boolean isKingInCheck = movegen.isKingInCheck(Color.LIGHT, nextPosition, game);
			if(isKingInCheck) {
				continue;
			}
			int score = mini.mini(depthLimit, evaluator, nextPosition, node, movegen, game);
			node.setScore(score);
			if(score > max) { //TODO might set this to >= to rise the number
				max = score;
				//bubbling things up to root
				parent.addToPvNodes(node);
				parent.setScore(score);
			}
			parent.addChild(node);
		}
		return max;
	}
	
	public int mini(int depthLimit,Evaluate evaluator, Board position,Node parent, MoveGenerator movegen, Game game) {
		int prevDepth = parent.getDepth();
		if(prevDepth == depthLimit) {
			int result = evaluator.evaluate(position);
			return result;
		}
		
		
		int min = 100002;
		List<Board> positions = movegen.generatePositions(position,Color.DARK, game);
		if (positions.isEmpty()) {
			return -1000002; //evaluator.evaluateMiniMax(position)
		}
		MiniMaxSearch maxi = new MiniMaxSearch();
		int currDepth = ++prevDepth;
		for (Board nextPosition : positions)  {
			Node node = Node.createNode(currDepth, parent, nextPosition, "maxi");
			boolean isKingInCheck = movegen.isKingInCheck(Color.DARK, nextPosition, game);
			if(isKingInCheck) {
				continue;
			}
			int score = maxi.maxi(depthLimit, evaluator, nextPosition, node, movegen, game);
			node.setScore(score);
			if(score < min) { //TODO might set this to >= to rise the number
				min = score;
				//bubbling things up to root
				parent.addToPvNodes(node);
				parent.setScore(score);
			}
			parent.addChild(node);
		}
		return min;
	}

}
