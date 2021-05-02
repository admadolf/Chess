package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import ui.App;

public class SecondaryController implements Initializable{

	@FXML
	private ChoiceBox<Integer> choiceBox;
	
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
        Integer choosenDepth = choiceBox.getValue();
        Config.getInstance().setLookAheadDepth(choosenDepth == null ? 3 : choosenDepth);
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 choiceBox.getItems().add(1);
	     choiceBox.getItems().add(2);
	     choiceBox.getItems().add(3);
	}
}