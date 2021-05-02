package controller;

import java.util.ArrayList;
import java.util.List;

import ai.machine.Machine;
import ai.movegen.Move;
import ai.movegen.MoveGenerator;
import representation.Board;
import representation.Color;
import representation.Game;
import representation.MoveType;

public class GameWorker{

	private MoveGenerator moveGenerator;
	
	private Game game;
	
	private Machine machine;
	
	private Color playerColor;
	
	private Boolean isPlayerMove;

	private List<Move> possibleMoves;
	
	public GameWorker() {
    	this.game = new Game();
    	this.moveGenerator = new MoveGenerator();
    	this.possibleMoves = new ArrayList<Move>();
    	this.machine = new Machine(game);
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
					if(!moveGenerator.isKingInCheck(playerColor, afterMoveBoard, game)) {
						possibleMoves.add(move);
					}
				}
			}
		}
	}
	
	public boolean finalizeMoveIfValid(Integer from, Integer to) {
		for (Move move : possibleMoves) {
			boolean nonCastleMove = from.equals(move.getFrom()) && to.equals(move.getTo());
			boolean castleMove = move.getMoveType() == MoveType.CASTLELONG || move.getMoveType() == MoveType.CASTLESHORT && move.getCastleKingFrom() == from && move.getCastleRookFrom() == to;
			boolean validMoveCond = nonCastleMove || castleMove ;
			if( validMoveCond) {
				game.addPositionToGame(Board.transposePositionToNewBoardInstance(game.getLatestBoard(), move));
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

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

}
