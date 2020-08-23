package ai.movegen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.piece.Bishop;
import ai.representation.piece.King;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;

public class MoveGeneratorTest {

	MoveGenerator generator;
	
	Board testBoard;
	
	@BeforeEach
	public void init() {
		generator = new MoveGenerator();
		testBoard = new Board();
		testBoard.initEmptyBoard();
	}
	
	@Test
	public void testKnight5() {
		testBoard.place(new Knight(Color.LIGHT),5);
		List<Integer> expected = Arrays.asList(20, 22, 15, 11);
		List<Integer> actual = generator.generateMoves(5, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight20() {
		testBoard.place(new Knight(Color.LIGHT),20);
		List<Integer> expected = Arrays.asList(35, 37, 30, 14, 5, 3 ,10, 26);
		List<Integer> actual = generator.generateMoves(20, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight31() {
		testBoard.place(new Knight(Color.LIGHT),31);
		List<Integer> expected = Arrays.asList(46, 14, 21, 37);
		List<Integer> actual = generator.generateMoves(31, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight7() {
		testBoard.place(new Knight(Color.LIGHT),7);
		List<Integer> expected = Arrays.asList(22, 13);
		List<Integer> actual = generator.generateMoves(7, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight63() {
		testBoard.place(new Knight(Color.LIGHT),63);
		List<Integer> expected = Arrays.asList(46, 53);
		List<Integer> actual = generator.generateMoves(63, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop63() {
		testBoard.place(new Bishop(Color.LIGHT),63);
		List<Integer> expected = Arrays.asList(54, 45, 36, 27, 18, 9, 0);
		List<Integer> actual = generator.generateMoves(63, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop63FriendlyPieceOn36() {
		testBoard.place(new Bishop(Color.LIGHT),63);
		testBoard.place(new Pawn(Color.LIGHT), 36);
		List<Integer> expected = Arrays.asList(54, 45);
		List<Integer> actual = generator.generateMoves(63, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop63EnemyPieceOn36() {
		testBoard.place(new Bishop(Color.LIGHT),63);
		testBoard.place(new Pawn(Color.DARK), 36);
		List<Integer> expected = Arrays.asList(54, 45, 36);
		List<Integer> actual = generator.generateMoves(63, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop36() {
		testBoard.place(new Bishop(Color.DARK),36);
		List<Integer> expected = Arrays.asList(43,50,57,45,54,63,29,22,15,27,18,9,0);
		List<Integer> actual = generator.generateMoves(36, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook36() {
		testBoard.place(new Rook(Color.LIGHT),36);
		List<Integer> expected = Arrays.asList(44,52,60,37,38,39,28,20,12,4,35,34,33,32);
		List<Integer> actual = generator.generateMoves(36, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook56() {
		testBoard.place(new Rook(Color.LIGHT),56);
		List<Integer> expected = Arrays.asList(57,58,59,60,61,62,63,48,40,32,24,16,8,0);
		List<Integer> actual = generator.generateMoves(56, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook7Enemy23Friend5Enemy39() {
		int testedPiecePosition = 7;
		testBoard.place(new Rook(Color.DARK),testedPiecePosition);
		testBoard.place(new Rook(Color.DARK),5);
		testBoard.place(new Rook(Color.LIGHT),23);
		List<Integer> expected = Arrays.asList(15,23,6);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testQueen12() {
		int testedPiecePosition = 12;
		testBoard.place(new Queen(Color.DARK),testedPiecePosition);
		List<Integer> expected = Arrays.asList(19,26,33,40,21,30,39,5,3,20,28,36,44,52,60,13,14,15,4,11,10,9,8);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testQueen12Friend33Enemy36Enemy39Friend14() {
		int testedPiecePosition = 12;
		testBoard.place(new Queen(Color.DARK),testedPiecePosition);
		testBoard.place(new Rook(Color.DARK),33);
		testBoard.place(new Rook(Color.LIGHT),36);
		testBoard.place(new Rook(Color.LIGHT),39);
		testBoard.place(new Rook(Color.DARK),14);
		List<Integer> expected = Arrays.asList(19,26,21,30,39,5,3,20,28,36,13,4,11,10,9,8);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn8() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		List<Integer> expected = Arrays.asList(16,24);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn8Enemy17() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),17);
		List<Integer> expected = Arrays.asList(16,24,17);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn8Enemy17Enemy15() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),17);
		testBoard.place(new Pawn(Color.DARK),15);
		List<Integer> expected = Arrays.asList(16,24,17);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy18Enemy20() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),18);
		testBoard.place(new Pawn(Color.DARK),20);
		List<Integer> expected = Arrays.asList(19,27,18,20);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy19Enemy18Enemy20() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),19);
		testBoard.place(new Pawn(Color.DARK),18);
		testBoard.place(new Pawn(Color.DARK),20);
		List<Integer> expected = Arrays.asList(18,20);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy19() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),19);
		List<Integer> expected = Arrays.asList();
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Friend19() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),19);
		List<Integer> expected = Arrays.asList();
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Friend27() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),27);
		List<Integer> expected = Arrays.asList(19);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy27() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),27);
		List<Integer> expected = Arrays.asList(19);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void IsKingInCheckTestBishop() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Bishop(Color.DARK),25);
		Boolean actual = generator.IsKingInCheck(Color.LIGHT, testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void IsKingInCheckTestKnight() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Knight(Color.DARK),5);
		Boolean actual = generator.IsKingInCheck(Color.LIGHT, testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void IsKingInCheckTest2() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Bishop(Color.LIGHT),25);
		Boolean actual = generator.IsKingInCheck(Color.LIGHT, testBoard);
		assertEquals(false, actual);
	}
}
