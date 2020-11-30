package ai.representation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Node implements Comparable<Node>{

	public static int nodeCounter = 0;
	
	private Integer depth = null;
	
	private Board position = null;
	
	private Node parent = null;
	
	private LinkedList<Node> children = new LinkedList<Node>();
	
	private LinkedList<Node> pvNodes = new LinkedList<Node>();
	
	private Integer score = null;
	
	private String miniOrMaxi;
	
	
	public Node() {
		super();
		nodeCounter++;
	}

	public boolean isRootNode() {
		if (parent == null) {
			return true;
		}
		return false;
	}
	
	public void addChild(Node pvNode) {
		this.children.add(pvNode);
	}
	
	public LinkedList<Node> getChildren() {
		return children;
	}

	public Board getPosition() {
		return position;
	}

	public void setPosition(Board value) {
		this.position = value;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("level " + depth ).append(System.lineSeparator());
		builder.append("move from parent ").append(position.getTransitionMoveFromPreviousBoard()).append(System.lineSeparator());
		builder.append("score ").append(score).append(System.lineSeparator());
		builder.append(miniOrMaxi).append(System.lineSeparator());
		builder.append(position.toString());
		if (!children.isEmpty()) {
			for (Node child : children) {
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
		return this.pvNodes.peekLast().getPosition();
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Integer getDepth() {
		return depth;
	}

	public String getMiniOrMaxi() {
		return miniOrMaxi;
	}

	public void setMiniOrMaxi(String miniOrMaxi) {
		this.miniOrMaxi = miniOrMaxi;
	}
	
	public void addToPvNodes(Node pvNode) {
		pvNodes.add(pvNode);
	}

	public LinkedList<Node> getPvNodes() {
		return pvNodes;
	}

	public static Node createNode(int currDepth, Node parent, Board position, String miniOrMaxi) {
		Node node = new Node();
		node.setDepth(currDepth);
		node.setParent(parent);
		node.setPosition(position);
		node.setMiniOrMaxi(miniOrMaxi);
		return node;
	}
	
	@Override
	public int compareTo(Node o) {
		return this.score > o.score ? 1 : this.score < o.score ? -1 : 0;
	}
	
	public String getMoveStringWithRating() {
		return position.getTransitionMoveFromPreviousBoard().toString() + ", moveRating = " +  score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depth == null) ? 0 : depth.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (depth == null) {
			if (other.depth != null)
				return false;
		} else if (!depth.equals(other.depth))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	
}
