package ai.machine;

import java.util.LinkedList;

import ai.eval.Evaluate;
import ai.eval.MiniMaxEvaluateImpl;
import ai.movegen.MoveGenerator;
import ai.search.MiniMaxSearch;
import ai.search.Node;
import representation.Board;
import representation.Game;

public class Machine {

	private Game game;
	
	private MoveGenerator generator = new MoveGenerator();
	
	private Evaluate lightEvaluator = new MiniMaxEvaluateImpl();
	
	private MiniMaxSearch search = new MiniMaxSearch();
	
	private Evaluate darkEvaluator = new MiniMaxEvaluateImpl();
	
	public Machine(Game game) {
		super();
		this.game = game;
	}

	public LinkedList<Node> moveLight(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.maxi(lookAheadDepth, this.lightEvaluator, board, pvTree, this.generator, game);
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public LinkedList<Node> moveDark(int lookAheadDepth, Board board) {
		Node pvTree = new Node();
		pvTree.setParent(null);
		pvTree.setPosition(board);
		pvTree.setDepth(0);
		
		search.mini(lookAheadDepth, this.darkEvaluator, board, pvTree, this.generator, game);
		return pvTree.getPvNodes().isEmpty() ? pvTree.getChildren() : pvTree.getPvNodes();
	}
	
	public void setGenerator(MoveGenerator generator) {
		this.generator = generator;
	}

	public Evaluate getLightEvaluator() {
		return lightEvaluator;
	}

	public void setLightEvaluator(Evaluate lightEvaluator) {
		this.lightEvaluator = lightEvaluator;
	}

	public Evaluate getDarkEvaluator() {
		return darkEvaluator;
	}

	public void setDarkEvaluator(Evaluate darkEvaluator) {
		this.darkEvaluator = darkEvaluator;
	}
}
