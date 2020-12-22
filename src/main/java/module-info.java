module chess {
	exports ai.eval;
	exports ai.movegen;
	exports ai.representation.piece;
	exports ai;
	exports ai.search;
	exports ai.representation;
	exports game;
	requires javafx.graphics;
	requires javafx.fxml;
	
    requires javafx.controls;
    requires javafx.media;
    opens ui to javafx.fxml;
    exports ui;


}