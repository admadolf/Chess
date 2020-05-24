package ai.search;

import java.util.List;

import ai.eval.Evaluate;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.PVNode;

public class MiniMaxSearch {

	Integer depth = null;
	
	PVNode pvTree = null;
	
	MoveGenerator movegen = new MoveGenerator();
	
	public MiniMaxSearch(Board position) {
		super();
	}

	public MiniMaxSearch() {
		
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setPvTree(PVNode pvTree) {
		this.pvTree = pvTree;
	}

	public int maxi(int depthLimit,Evaluate evaluator, Board position,PVNode parent) {
		if(depth == depthLimit) {
			int result = evaluator.evaluateMiniMax(position);
			return result;
		}
		int max = -100001;
		List<Board> positions = movegen.generatePositions(position,Color.LIGHT);
		if (positions.isEmpty()) {
			return 1000002;
		}
		
		MiniMaxSearch mini = new MiniMaxSearch();
		int newDepth = depth+1;
		mini.setDepth(newDepth);
		for (Board nextPosition : positions)  {
			PVNode child = new PVNode();
			child.setDepth(mini.depth);
			child.setParent(parent);
			child.setValue(nextPosition);
			child.setMiniOrMaxi("mini");
			parent.addChild(child);
			int score = mini.mini(depthLimit, evaluator, nextPosition, child);
			child.setScore(score);
			
				
			
			child.setMove(nextPosition.getMove());
			//child.getMove().setMoveRating(score); //uj
			if(score > max) {
				max = score;
				child.setBestMove(nextPosition.getMove());
				parent.setBestMove(nextPosition.getMove());
				//parent.addMoveToSet(child.getMove()); //uj
				parent.setScore(score);
				parent.setPrincipalVariationFinalPosition(nextPosition);
			}
		}
		return max;
	}
	
	public int mini(int depthLimit,Evaluate evaluator, Board position,PVNode parent) {
		if(depth == depthLimit) {
			int result = evaluator.evaluateMiniMax(position);
			return result;
		}
		
		
		int min = 100002;
		List<Board> positions = movegen.generatePositions(position,Color.DARK);
		if (positions.isEmpty()) {
			return -1000002; //evaluator.evaluateMiniMax(position)
		}
		MiniMaxSearch maxi = new MiniMaxSearch();
		int newDepth = depth+1;
		maxi.setDepth(newDepth);
		for (Board nextPosition : positions)  {
			PVNode child = new PVNode();
			child.setDepth(maxi.depth);
			child.setParent(parent);
			child.setValue(nextPosition);
			child.setMiniOrMaxi("maxi");
			parent.addChild(child);
			int score = maxi.maxi(depthLimit, evaluator, nextPosition, child);
			child.setScore(score);
			
			
			
			child.setMove(nextPosition.getMove());
			//child.getMove().setMoveRating(score); //uj
			if(score < min) {
				min = score;
				child.setBestMove(nextPosition.getMove());
				parent.setBestMove(nextPosition.getMove());
				parent.setScore(score);
				//parent.addMoveToSet(child.getMove()); //uj
				parent.setPrincipalVariationFinalPosition(nextPosition);
			}
		}
		return min;
	}

	public PVNode getPvTree() {
		return pvTree;
	}

}
