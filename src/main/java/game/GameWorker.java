package game;

import java.util.LinkedList;

import ai.movegen.Move;
import ai.representation.Color;
import ai.representation.Node;
import javafx.application.Platform;
import ui.PrimaryController;

public class GameWorker implements ControllerEvent{

	PrimaryController controller;

	public void run(Color color) {
		if(!controller.isPlayerMove()) {
			Game game = controller.getGame();
			LinkedList<Node> moveList = game.moveDark(3, game.getLatestBoard());
			if(moveList.isEmpty()) {
					Platform.runLater(() -> controller.showMatePopup());
			}
			else {
				Move move = moveList.peekLast().getPosition().getTransitionMoveFromPreviousBoard();
				Platform.runLater(()-> controller.doAiMoveonUI(move));
			} 
		}
	}

	public PrimaryController getController() {
		return controller;
	}

	public void setController(PrimaryController controller) {
		this.controller = controller;
	}

	@Override
	public void eventHappened() {
		// TODO Auto-generated method stub
		
	}
	
}
