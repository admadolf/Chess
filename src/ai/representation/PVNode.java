package ai.representation;

import java.util.ArrayList;
import java.util.List;

import ai.movegen.Move;

public class PVNode {

	public static int nodeCounter = 0;
	
	private Integer depth = null;
	
	private Board value = null;
	
	private PVNode parent = null;
	
	private List<PVNode> children = new ArrayList<PVNode>();
	
	private Board principalVariationFinalPosition = null; // store best move somehow prolly new movetype
	
	private Integer score = null;
	
	private Move move;
	
	private Move bestMove;
	
	private String miniOrMaxi;
	
	
	public PVNode() {
		super();
		nodeCounter++;
	}

	public boolean isRootNode() {
		if (parent == null) {
			return true;
		}
		return false;
	}
	
	public void addChild(PVNode pvNode) {
		this.children.add(pvNode);
	}
	
	
	public void setValue(Board value) {
		this.value = value;
	}

	public void setParent(PVNode parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("level " + depth ).append(System.lineSeparator());
		builder.append("move from parent ").append(move).append(System.lineSeparator());
		builder.append("score ").append(score).append(System.lineSeparator());
		builder.append(miniOrMaxi).append(System.lineSeparator());
		builder.append(value.toString());
		if (!children.isEmpty()) {
			for (PVNode child : children) {
				builder.append(child.toString());
			}
		}
		builder.append("node count: " + nodeCounter).append(System.lineSeparator());
		return builder.toString();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Board getPrincipalVariationFinalPosition() {
		return principalVariationFinalPosition;
	}

	public void setPrincipalVariationFinalPosition(Board principalVariationFinalPosition) {
		this.principalVariationFinalPosition = principalVariationFinalPosition;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Move getBestMove() {
		return bestMove;
	}

	public void setBestMove(Move bestMove) {
		this.bestMove = bestMove;
	}

	public String getMiniOrMaxi() {
		return miniOrMaxi;
	}

	public void setMiniOrMaxi(String miniOrMaxi) {
		this.miniOrMaxi = miniOrMaxi;
	}
	
	
}
