package game;

import java.util.ArrayList;
import java.util.List;

import ai.movegen.Move;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;

public class GameWorker{

	private MoveGenerator moveGenerator;
	
	private Game game;
	
	private Color playerColor;
	
	private Boolean isPlayerMove;

	private List<Move> possibleMoves;
	
	public GameWorker() {
    	this.game = new Game();
    	this.moveGenerator = new MoveGenerator();
    	this.possibleMoves = new ArrayList<Move>();
	}
	
	public GameWorker(Color playerColor) {
		this();
		this.playerColor = playerColor;
		
	}

	public void newGame() {
		if(this.playerColor == Color.LIGHT) {
			isPlayerMove = true;
		} else {
			isPlayerMove = false;
		}
	}
	
	public void generateMovesOnUserInput(Integer from){
		if(isPlayerMove) {
			List<Integer> playerPieceList = moveGenerator.getAll(playerColor, game.getLatestBoard());
			if(playerPieceList.contains(from)) {
				for (Move move : moveGenerator.generateMoves(from, game.getLatestBoard(), game)) {
					Board afterMoveBoard = Board.transposePositionToNewBoardInstance(game.getLatestBoard(), move);
					System.out.println("looping");
					if(!moveGenerator.isKingInCheck(playerColor, afterMoveBoard, game)) {
						System.out.println("move added: " + move);
						possibleMoves.add(move);
					}
				}
			}
		}
	}
	
	public boolean finalizeMoveIfValid(Integer from, Integer to) {
		System.out.println("finalizemove: " + from + " " + to);
		System.out.println(possibleMoves);
		for (Move move : possibleMoves) {
			System.out.println("finalizemove: " + move.getFrom() + " " + move.getTo());
			if( move.getFrom() == from && move.getTo() == to || move.getCastleKingFrom() == from && move.getCastleRookFrom() == to ) {
				game.addPositionToGame(Board.transposePositionToNewBoardInstance(game.getLatestBoard(), move));
				System.out.println("move chosen: " + move);
				return true;
			}
		}
		possibleMoves.clear();
		return false;
	}

	public Boolean getIsPlayerMove() {
		return isPlayerMove;
	}

	public void setIsPlayerMove(Boolean isPlayerMove) {
		this.isPlayerMove = isPlayerMove;
	}

	public List<Move> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(List<Move> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public MoveGenerator getMoveGenerator() {
		return moveGenerator;
	}

	public void setMoveGenerator(MoveGenerator moveGenerator) {
		this.moveGenerator = moveGenerator;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}
	
}
