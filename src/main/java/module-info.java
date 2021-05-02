module chess {
	exports ai.eval;
	exports ai.movegen;
	exports ai.search;
	exports ai.machine;
	exports controller;
	requires javafx.graphics;
	requires javafx.fxml;
	
    requires transitive javafx.controls;
    exports representation;
    opens controller to javafx.fxml;
    exports ui;


}