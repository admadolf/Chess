module chess {
	exports controller;
	exports ai.eval;
	exports ai.movegen;
	exports application;
	exports ai.representation.piece;
	exports ai;
	exports model;
	exports ai.search;
	exports ai.representation;
	exports game;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
}