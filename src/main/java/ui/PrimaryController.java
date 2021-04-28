package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ai.movegen.Move;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.piece.ColoredPiece;
import game.Config;
import game.Game;
import game.GameWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PrimaryController {

	@FXML
	private GridPane chessBoard;

	private List<Node> nodes;

	private List<Button> possibleMoveTargetTiles;

	private boolean inMovement = false;

	private Button moveStartTile;

	private GameWorker gameWorker;

	@FXML
	private void newGame() throws IOException {
		possibleMoveTargetTiles = new ArrayList<Button>();
		gameWorker = new GameWorker(Color.LIGHT);
		gameWorker.newGame();
		this.nodes = chessBoard.getChildren().stream().filter(n -> n instanceof Button).collect(Collectors.toList());
		for (Node node : this.nodes) {
			Button button = (Button) node;
			Integer squareId = idToInt(button);
			button.setFont(new Font("", 50));
			button.setText(gameWorker.getGame().getLatestBoard().get(squareId).toUnicodeChessSymbol());
			button.setAlignment(Pos.BASELINE_CENTER);
		}
		redrawColors();
	}

	@FXML
	private void showConfigPopup() throws IOException {
		App.setRoot("secondary");
	}

	@FXML
	private void onClick(ActionEvent event) {
		if (gameWorker == null) {
			return;
		}
		if (!inMovement) {
			Button clickedButton = (Button) event.getSource();
			moveStartTile = clickedButton;
			gameWorker.generateMovesOnUserInput(idToInt(clickedButton));
			hightlightMoveList(gameWorker.getPossibleMoves());
			inMovement = true;
		} else {
			Button moveEndTile = (Button) event.getSource();
			Integer moveEndTileId = idToInt(moveEndTile);
			boolean validMove = gameWorker.finalizeMoveIfValid(idToInt(moveStartTile), moveEndTileId);
			dehightlightMoveList();
			if (validMove) {
				redrawBoard(gameWorker.getGame());
				LinkedList<ai.representation.Node> possibleNodes = gameWorker.getGame()
						.moveDark(Config.getInstance().getLookAheadDepth(), gameWorker.getGame().getLatestBoard());
				Move computerMove = null;
				if (possibleNodes.isEmpty()) {
					showPopup(Color.DARK, gameWorker.getGame());
				} else {
					computerMove = possibleNodes.peekLast().getPosition().getTransitionMoveFromPreviousBoard();
					doAiMoveonUI(computerMove);
					// one lookAheadDepth is enough to detect if light has any valid moves..
					LinkedList<ai.representation.Node> possiblePlayerNodes = gameWorker.getGame().moveLight(1,
							gameWorker.getGame().getLatestBoard());
					if (possiblePlayerNodes.isEmpty()) {
						showPopup(Color.LIGHT, gameWorker.getGame());
					}
				}
			}
			inMovement = false;
		}
	}

	private Integer idToInt(Button button) {
		return Integer.valueOf(button.getId());
	}

	private void hightlightMoveList(List<Move> moves) {
		for (Move move : moves) {
			boolean castlingMove = move.getMoveType() == MoveType.CASTLELONG
					|| move.getMoveType() == MoveType.CASTLESHORT;
			for (Node node : this.nodes) {
				if (node instanceof Button) {
					Button b = (Button) node;
					if (castlingMove) {
						if (move.getMoveType() == MoveType.ENPASSANT
								&& b.getId().equals(String.valueOf(move.getEnPassantArrivalSquareAfterTake()))) {
							possibleMoveTargetTiles.add(b);
						} else if ((move.getMoveType() == MoveType.CASTLESHORT
								|| move.getMoveType() == MoveType.CASTLELONG)
								&& b.getId().equals(String.valueOf(move.getCastleRookFrom()))) {
							possibleMoveTargetTiles.add(b);
						}
					} else if (move.getMoveType() == MoveType.ENPASSANT
							&& b.getId().equals(String.valueOf(move.getTo()))) {
						possibleMoveTargetTiles.add(b);
					} else if (b.getId().equals(String.valueOf(move.getTo()))) {
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
		redrawColors();
		gameWorker.getPossibleMoves().clear();
		possibleMoveTargetTiles.clear();
	}

	private void redrawColors() {
		for (Node button : this.nodes) {
			if (button instanceof Button) {
				Integer tileId = Integer.valueOf(button.getId());
				if (gameWorker.getMoveGenerator().row(tileId) % 2 == 0) {
					if (tileId % 2 == 0) {
						button.getStyleClass().clear();
						button.getStyleClass().add("tile-dark");
					} else {
						button.getStyleClass().clear();
						button.getStyleClass().add("tile-light");
					}
				} else {
					if (tileId % 2 == 0) {
						button.getStyleClass().clear();
						button.getStyleClass().add("tile-light");
					} else {
						button.getStyleClass().clear();
						button.getStyleClass().add("tile-dark");
					}
				}
			}
		}
	}

	public void setGameWorker(GameWorker gameWorker) {
		this.gameWorker = gameWorker;
	}

	public void doAiMoveonUI(Move move) {
		Board nextBoard = Board.transposePositionToNewBoardInstance(gameWorker.getGame().getLatestBoard(), move);
		boolean castlingMove = move.getMoveType() == MoveType.CASTLELONG || move.getMoveType() == MoveType.CASTLESHORT;
		if (!castlingMove) {
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
		gameWorker.getGame().addPositionToGame(nextBoard);
		redrawBoard(gameWorker.getGame());
	}

	public void redrawBoard(Game game) {
		Map<Integer, ColoredPiece> board = game.getLatestBoard().getBoardMapReference();
		for (Integer key : board.keySet()) {
			Button button = null;
			String pieceSymbol = null;
			if (gameWorker.getPlayerColor() == Color.LIGHT) {
				button = (Button) this.nodes.get(generateFlippedId(key));
				pieceSymbol = board.get(key).toUnicodeChessSymbol();
			} else {
				button = (Button) this.nodes.get(key);
				pieceSymbol = board.get(key).toUnicodeChessSymbol();
			}

			button.setText(pieceSymbol);
			button.setAlignment(Pos.BASELINE_CENTER);
		}
	}

	private Button getButton(Integer modelIndex) {
		Button button = (Button) chessBoard.getChildren().get(((7 - gameWorker.getMoveGenerator().row(modelIndex)) * 8)
				+ gameWorker.getMoveGenerator().column(modelIndex));
		return button;
	}

	private int generateFlippedId(int id) {
		return ((7 - gameWorker.getMoveGenerator().row(id)) * 8) + gameWorker.getMoveGenerator().column(id);
	}

	private void showPopup(Color color, Game game) {
		Alert a = new Alert(AlertType.INFORMATION);
		boolean isKingInCheck = gameWorker.getMoveGenerator().isKingInCheck(color, game.getLatestBoard(), game);
		if (isKingInCheck) {
			a.setTitle("Check Mate");
			a.setContentText("Game ended");
			a.showAndWait();
		} else {
			a.setTitle("Stale Mate");
			a.setContentText("Game ended");
			a.showAndWait();
		}
		try {
			newGame();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
