package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import ai.movegen.Move;
import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.PieceType;
import ai.representation.piece.ColoredPiece;
import game.Game;
import game.GameWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PrimaryController implements Initializable{

	@FXML
	GridPane chessBoard;
	
	private List<Node> nodes;
	
	private Game game;
	
	Color playerColor;
	
	private MoveGenerator moveGenerator;
	
	private List<Button> possibleMoveTargetTiles;
	
	private List<Move> possibleMoves;
	
	private boolean inMovement = false;
	
	private Button moveStartTile;
	
	private boolean isPlayerMove = true;
	
	private GameWorker gameWorker;
	
    @FXML
    private void switchToSecondary() throws IOException {
    	for (Node node : chessBoard.getChildren()) {
			if(node instanceof Button) {
				Button b = (Button) node;
				//b.getId()
				System.out.println("index in grindPane: " + chessBoard.getChildren().get(idToInt(b)) + "button id: " + b.getId() + "node id" + node.getId() );
			}else {
				System.out.println("node which is not button in : " + node + "node id: " + node.getId() + "class of non button node: " + node.getClass() );
			}
			
		}
    	redrawBoard(this.game);
		for (Node node : chessBoard.getChildren()) {
			if(node instanceof Button) {
				Button b = (Button) node;
				//b.getId()
				System.out.println("index in grindPane: " + chessBoard.getChildren().get(idToInt(b)) + "button id: " + b.getId() + "node id" + node.getId() );
			}else {
				System.out.println("node which is not button in : " + node + "node id: " + node.getId() + "class of non button node: " + node.getClass() );
			}
			
		}
    }
    
    @FXML
    private void newGame() throws IOException {
    	game = new Game();
    	possibleMoveTargetTiles = new ArrayList<Button>();
    	moveGenerator = new MoveGenerator();
    	possibleMoves = new ArrayList<Move>();
    	gameWorker = new GameWorker();
    	gameWorker.setController(this);
    	playerColor = Color.LIGHT;
    	this.nodes = chessBoard.getChildren().stream().filter(n -> n instanceof Button).collect(Collectors.toList());
    	for (Node node : this.nodes) {
				Button button = (Button) node;
				Integer squareId = idToInt(button);
				button.setFont(new Font("", 50));
				if(playerColor == Color.LIGHT) {
					button.setText(game.getLatestBoard().get(squareId).toUnicodeChessSymbol());
				} else {
					button.setText(game.getLatestBoard().get(generateFlippedId(squareId)).toUnicodeChessSymbol());
				}
				
			}
    }
    
    @FXML
    private void onClick(ActionEvent event) {
    	if(moveGenerator == null) {
    		return;
    	}
    	if(playerColor == Color.LIGHT) {
    		
    	}
    	if(!inMovement) {
    		if(moveGenerator.isKingInCheck(Color.LIGHT, game.getLatestBoard(), game)) {
    			
    		}
    		
    		Button clickedButton = (Button) event.getSource();
    		List<Move> moves;
			moves = moveGenerator.generateMoves(idToInt(clickedButton), game.getLatestBoard(), game);
        	possibleMoves.addAll(moves);
        	hightlightMoveList(moves);
        	inMovement = true;
        	moveStartTile = clickedButton;
    	} else {
    		dehightlightMoveList();
    		Button moveEndTile = (Button) event.getSource();
    		Integer moveEndTileId = idToInt(moveEndTile);
    		Move selectedMove = null;
    		Move castleMove = null;
    		for (Move move : possibleMoves) {
    			boolean castlingMove = move.getMoveType() == MoveType.CASTLELONG || move.getMoveType() == MoveType.CASTLESHORT;
    			boolean nonCastleMove = Integer.valueOf(move.getFrom()).equals(idToInt(moveStartTile)) && (Integer.valueOf(move.getTo()).equals(moveEndTileId));
    			if(castlingMove) {
    				//if clicked on king
    				if(game.getLatestBoard().get(idToInt(moveStartTile)).getPieceType() == PieceType.KING && game.getLatestBoard().get(idToInt(moveEndTile)).getPieceType() == PieceType.ROOK) {
    					castleMove = move;
    				}
    			}else if(nonCastleMove){
    				selectedMove = move;
    			}
			}
    		//if nonsensical move is selected / or possible movelist is empty
    		if(selectedMove == null && castleMove == null && (!possibleMoves.contains(selectedMove) || !possibleMoves.contains(castleMove))) {
    			dehightlightMoveList();
    			isPlayerMove = true;
    		}else {
    			if(castleMove != null) {
    				Board nextBoard = Board.transposePositionToNewBoardInstance(game.getLatestBoard(), castleMove);
    				game.addPositionToGame(nextBoard);
    				System.out.println("player moved: " + castleMove);
    				System.out.println(game.getLatestBoard());
    				isPlayerMove = false;
    				redrawBoard(game);
    			} else {
	    			Board nextBoard = Board.transposePositionToNewBoardInstance(game.getLatestBoard(), selectedMove);
	    			game.addPositionToGame(nextBoard);
    				System.out.println("player moved: " + selectedMove);
    				System.out.println(game.getLatestBoard());
	    			redrawBoard(game);
	    			isPlayerMove = false;
    			}
    		}
    		inMovement = false;
    		if(playerColor == Color.LIGHT) {
    			gameWorker.run(Color.DARK);
    		} else {
    			gameWorker.run(Color.LIGHT);
    		}
    	}
    }
    
    private Integer idToInt(Button button) {
    	return Integer.valueOf(button.getId());
    }
    
    private void hightlightMoveList(List<Move> moves) {
    	for (Move move : moves) {
    		boolean castlingMove = move.getMoveType() == MoveType.CASTLELONG || move.getMoveType() == MoveType.CASTLESHORT;
    		for (Node node : this.nodes) {
    			if(node instanceof Button) {
    				Button b = (Button) node;
    				if(castlingMove) {
    					if(move.getMoveType() == MoveType.ENPASSANT && b.getId().equals(String.valueOf(move.getEnPassantArrivalSquareAfterTake()))) {
        					possibleMoveTargetTiles.add(b);
        				}else if((move.getMoveType() == MoveType.CASTLESHORT || move.getMoveType() == MoveType.CASTLELONG ) && b.getId().equals(String.valueOf(move.getCastleRookFrom()))) {
        					possibleMoveTargetTiles.add(b);
            			}
    				} else if(move.getMoveType() == MoveType.ENPASSANT && b.getId().equals(String.valueOf(move.getTo())) ) {
    					possibleMoveTargetTiles.add(b);
    				} else if(b.getId().equals(String.valueOf(move.getTo()))){
        				possibleMoveTargetTiles.add(b);
        			}
    			}
    		}
		}
    	for (Button button : possibleMoveTargetTiles) {
    		button.getStyleClass().clear();
    		button.getStyleClass().add("tile-red");
		}
    }
    
    private void dehightlightMoveList() {
    	for (Node button : this.nodes) {
    		if(button instanceof Button) {
	    		Integer tileId = Integer.valueOf(button.getId());
	    		if(moveGenerator.row(tileId) % 2 == 0) {
	    			if(tileId % 2 == 0) {
	    				button.getStyleClass().clear();
	        			button.getStyleClass().add("tile-dark");
	        		} else {
	        			button.getStyleClass().clear();
	        			button.getStyleClass().add("tile-light");
	        		}
	    		}else {
	    			if(tileId % 2 == 0) {
	    				button.getStyleClass().clear();
	    				button.getStyleClass().add("tile-light");
	        		} else {
	        			button.getStyleClass().clear();
	        			button.getStyleClass().add("tile-dark");
	        		}
	    		}
    		}
    	}
    	inMovement = false;
    	possibleMoveTargetTiles = new ArrayList<Button>();
    }
    
    public static void main(String[] args) {
		System.out.println("lala");
		MoveGenerator mg = new MoveGenerator();
		System.out.println(mg.row(56));
		System.out.println(mg.row(48));
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isPlayerMove() {
		return isPlayerMove;
	}

	public void setPlayerMove(boolean isPlayerMove) {
		this.isPlayerMove = isPlayerMove;
	}

	public void setGameWorker(GameWorker gameWorker) {
		this.gameWorker = gameWorker;
	}
 
	public void register(GameWorker worker) {
		worker.eventHappened();
	}
	
	public void doAiMoveonUI(Move move) {
		this.setPlayerMove(true);
		Board nextBoard = Board.transposePositionToNewBoardInstance(game.getLatestBoard(), move);
		System.out.println("AI moved: " + nextBoard.getTransitionMoveFromPreviousBoard());
		System.out.println(nextBoard);
		boolean castlingMove = move.getMoveType() == MoveType.CASTLELONG || move.getMoveType() == MoveType.CASTLESHORT;
		if(!castlingMove) {
			getButton(move.getFrom()).getStyleClass().clear();
			getButton(move.getFrom()).getStyleClass().add("shiny-orange");
			getButton(move.getTo()).getStyleClass().clear();
			getButton(move.getTo()).getStyleClass().add("shiny-orange");
		} else {
			getButton(move.getCastleKingTo()).getStyleClass().clear();
			getButton(move.getCastleKingTo()).getStyleClass().add("shiny-orange");
			getButton(move.getCastleRookTo()).getStyleClass().clear();
			getButton(move.getCastleRookTo()).getStyleClass().add("shiny-orange");
		}
		game.addPositionToGame(nextBoard);
		redrawBoard(game);
	}
	
	public void redrawBoard(Game game) {
		Map<Integer, ColoredPiece> board = game.getLatestBoard().getBoardMapReference();
		for(Integer key  : board.keySet()) {
			Button button = null;
			String pieceSymbol = null;
			if(playerColor == Color.LIGHT) {
				button = (Button) this.nodes.get(generateFlippedId(key));
				pieceSymbol = board.get(key).toUnicodeChessSymbol();
			} else {
				button = (Button) this.nodes.get(key);
				System.out.println("button id: " + button.getId());
				pieceSymbol = board.get(key).toUnicodeChessSymbol();
				System.out.println("board square: " + key);
			}
			
			System.out.println();
			
			button.setText(pieceSymbol);
			button.setAlignment(Pos.BASELINE_CENTER);
		}
	}
	

	private Button getButton(Integer modelIndex) {
		Button button = (Button) chessBoard.getChildren().get(((7-moveGenerator.row(modelIndex)) * 8) + moveGenerator.column(modelIndex));
		return button;
	}
	
	private int generateFlippedId(int id) {
		return ((7-moveGenerator.row(id)) * 8) + moveGenerator.column(id);
	}

	public void showMatePopup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("adsasd");
	}
}
