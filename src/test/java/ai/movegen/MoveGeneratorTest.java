package ai.movegen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.PieceType;
import ai.representation.piece.Bishop;
import ai.representation.piece.ColoredPiece;
import ai.representation.piece.King;
import ai.representation.piece.Knight;
import ai.representation.piece.Pawn;
import ai.representation.piece.Queen;
import ai.representation.piece.Rook;
import game.Game;

public class MoveGeneratorTest {

	MoveGenerator generator;
	
	Board testBoard;
	
	Game castleTestGame;
	
	Game enPassantGame;
	
	Board castleTestBoard;
	
	@BeforeEach
	public void init() {
		generator = new MoveGenerator();
		testBoard = new Board();
		testBoard.initEmptyBoard();
		castleTestGame = returnGameWithBoardToCastleTestBackRankNonBlock();
		castleTestBoard = castleTestGame.getState()[0];
		enPassantGame = new Game();
	}
	
	@Test
	public void testKnight10() {
		testBoard.place(new Knight(Color.LIGHT),10);
		Set<Integer> expected = Stream.of(25, 27, 4, 20, 0, 16).collect(Collectors.toSet());
		Set<Integer> actual = new HashSet<Move>(generator.generateMoves(10, testBoard, new Game()))
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight5() {
		testBoard.place(new Knight(Color.LIGHT),5);
		Set<Integer> expected = Stream.of(20, 22, 15, 11).collect(Collectors.toSet());
		Set<Integer> actual = generator.generateMoves(5, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight20() {
		testBoard.place(new Knight(Color.LIGHT),20);
		Set<Integer> expected = Set.of(35, 37, 30, 14, 5, 3 ,10, 26);
		Set<Integer> actual = generator.generateMoves(20, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight31() {
		testBoard.place(new Knight(Color.LIGHT),31);
		Set<Integer> expected = Set.of(46, 14, 21, 37);
		Set<Integer> actual = generator.generateMoves(31, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight7() {
		testBoard.place(new Knight(Color.LIGHT),7);
		Set<Integer> expected = Set.of(22, 13);
		Set<Integer> actual = generator.generateMoves(7, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKnight63() {
		testBoard.place(new Knight(Color.LIGHT),63);
		Set<Integer> expected = Set.of(46, 53);
		Set<Integer> actual = generator.generateMoves(63, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop63() {
		testBoard.place(new Bishop(Color.LIGHT),63);
		Set<Integer> expected = Set.of(54, 45, 36, 27, 18, 9, 0);
		Set<Integer> actual = generator.generateMoves(63, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop27Friend54() {
		testBoard.place(new Bishop(Color.LIGHT),27);
		testBoard.place(new Bishop(Color.LIGHT),54);
		Set<Integer> expected = new HashSet<Integer>(Set.of(36, 45, 34, 41, 48, 20, 13, 6, 18, 9, 0));
		Set<Integer> actual = generator.generateMoves(27, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop27FriendlyPieceOn36And0() {
		testBoard.place(new Bishop(Color.LIGHT),27);
		testBoard.place(new Pawn(Color.LIGHT), 36);
		testBoard.place(new Pawn(Color.LIGHT), 0);
		Set<Integer> expected = new HashSet<Integer>(( Set.of(20, 13, 6, 18, 9 ,34, 41 ,48)));
		Set<Integer> actual = generator.generateMoves(27, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKing27FriendlyPieceOn36() {
		testBoard.place(new King(Color.LIGHT),27);
		testBoard.place(new Pawn(Color.LIGHT), 36);
		Set<Integer> expected = new HashSet<Integer>((Set.of(35, 28, 20, 19, 18, 26, 34)));
		Set<Integer> actual = generator.generateMoves(27, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		//Function<? super T, ? extends Stream<? extends R>> mapper
		//BiConsumer<T, U>
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop63EnemyPieceOn36() {
		testBoard.place(new Bishop(Color.LIGHT),63);
		testBoard.place(new Pawn(Color.DARK), 36);
		Set<Integer> expected = Set.of(54, 45, 36);
		Set<Integer> actual = generator.generateMoves(63, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop36() {
		testBoard.place(new Bishop(Color.DARK),36);
		Set<Integer> expected = Set.of(43,50,57,45,54,63,29,22,15,27,18,9,0);
		Set<Integer> actual = generator.generateMoves(36, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook36() {
		testBoard.place(new Rook(Color.LIGHT),36);
		Set<Integer> expected = Set.of(44,52,60,37,38,39,28,20,12,4,35,34,33,32);
		Set<Integer> actual = generator.generateMoves(36, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook56() {
		testBoard.place(new Rook(Color.LIGHT),56);
		Set<Integer> expected = Set.of(57,58,59,60,61,62,63,48,40,32,24,16,8,0);
		Set<Integer> actual = generator.generateMoves(56, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRook7Enemy23Friend5Enemy39() {
		int testedPiecePosition = 7;
		testBoard.place(new Rook(Color.DARK),testedPiecePosition);
		testBoard.place(new Rook(Color.DARK),5);
		testBoard.place(new Rook(Color.LIGHT),23);
		Set<Integer> expected = Set.of(15,23,6);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testQueen12() {
		int testedPiecePosition = 12;
		testBoard.place(new Queen(Color.DARK),testedPiecePosition);
		Set<Integer> expected = Set.of(19,26,33,40,21,30,39,5,3,20,28,36,44,52,60,13,14,15,4,11,10,9,8);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
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
		Set<Integer> expected = Set.of(19,26,21,30,39,5,3,20,28,36,13,4,11,10,9,8);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn8() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		Set<Integer> expected = Set.of(16,24);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn8Enemy17() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),17);
		Set<Integer> expected = Set.of(16,24,17);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void testPawn8Enemy17Enemy15() {
		int testedPiecePosition = 8;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),17);
		testBoard.place(new Pawn(Color.DARK),15);
		Set<Integer> expected = Set.of(16,24,17);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy19Enemy18Enemy20() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),19);
		testBoard.place(new Pawn(Color.DARK),18);
		testBoard.place(new Pawn(Color.DARK),20);
		Set<Integer> expected = Set.of(18,20);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy19() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),19);
		Set<Integer> expected = Set.of();
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Friend19() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),19);
		Set<Integer> expected = Set.of();
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Friend27() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),27);
		Set<Integer> expected = Set.of(19);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy27() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),27);
		Set<Integer> expected = Set.of(19);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	//darkpawn
	@Test
	public void testDarkPawn50() {
		int testedPiecePosition = 50;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		Set<Integer> expected = Set.of(42,34);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn48Enemy41() {
		int testedPiecePosition = 48;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Knight(Color.LIGHT),41);
		Set<Integer> expected = Set.of(40,32,41);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn51Enemy42Enemy44() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),42);
		testBoard.place(new Pawn(Color.LIGHT),44);
		Set<Integer> expected = Set.of(43,35,42,44);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn48Enemy41Enemy55() {
		int testedPiecePosition = 48;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),41);
		testBoard.place(new Pawn(Color.LIGHT),55);
		Set<Integer> expected = Set.of(40,32,41);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn11Enemy18Enemy20() {
		int testedPiecePosition = 11;
		testBoard.place(new Pawn(Color.LIGHT),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),18);
		testBoard.place(new Pawn(Color.DARK),20);
		Set<Integer> expected = Set.of(19,27,18,20);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn51Enemy43Enemy42Enemy44() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		testBoard.place(new Pawn(Color.LIGHT),42);
		testBoard.place(new Pawn(Color.LIGHT),44);
		Set<Integer> expected = Set.of(42,44);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Enemy43() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		Set<Integer> expected = Set.of();
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Friend43() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		Set<Integer> expected = Set.of();
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn51Friend35() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),35);
		Set<Integer> expected = Set.of(43);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Enemy35() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),35);
		Set<Integer> expected = Set.of(43);
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void IsKingInCheckTestBishop() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Bishop(Color.DARK),25);
		Boolean actual = generator.isKingInCheck(Color.LIGHT, testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void IsKingInCheckTestKnight() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Knight(Color.DARK),5);
		Boolean actual = generator.isKingInCheck(Color.LIGHT, testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void IsKingInCheckTest2() {
		int kingPosition = 11;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Bishop(Color.LIGHT),25);
		Boolean actual = generator.isKingInCheck(Color.LIGHT, testBoard, new Game());
		assertEquals(false, actual);
	}
	
	@Test
	public void isAttackedByKnight() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Knight(Color.DARK), 27);
		boolean actual = generator.isAttackedBy(10, new Knight(Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByBishop() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Rook(Color.DARK), 58);
		boolean actual = generator.isAttackedBy(10, new ColoredPiece(PieceType.ROOK,Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByRook() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Rook(Color.DARK), 58);
		boolean actual = generator.isAttackedBy(10, new Rook(Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByPawn() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Pawn(Color.DARK), 17);
		boolean actual = generator.isAttackedBy(10, new Pawn(Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByQueen() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Queen(Color.DARK), 46);
		boolean actual = generator.isAttackedBy(10, new Queen(Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByKing() { //TODO king logic to prevent them from getting close
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new King(Color.DARK), 2);
		boolean actual = generator.isAttackedBy(10, new King(Color.DARK), testBoard, new Game());
		assertEquals(true, actual);
	}
	
	@Test
	public void testKing10() {
		int testedPiecePosition = 10;
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT),testedPiecePosition);
		Set<Integer> expected = new HashSet<Integer>(Set.of(17, 18, 19, 11, 9, 1, 2, 3));
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKing10Friend9Enemy19() {
		int testedPiecePosition = 10;
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT),testedPiecePosition);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT),9);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK),19);
		Set<Integer> expected = new HashSet<Integer>(Set.of(17, 18, 19, 11, 1, 3));
		Set<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	@Test
	public void isKingNearby() {
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KING, Color.DARK), 25);
		boolean actual = generator.isKingNearby(10, 17, testBoard, Color.LIGHT);
		assertEquals(true, actual);
	}
		
	@Test
	public void testKing10EnemyKing26() {
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KING, Color.DARK), 26);
		Set<Integer> expected = new HashSet<Integer>(Set.of(11, 1, 2, 9, 3));
		Set<Integer> actual = generator.generateMoves(10, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void kingCantGenerateMoveIntoChess() {
		int kingPosition = 14;
		testBoard.place(new King(Color.LIGHT),kingPosition);
		testBoard.place(new Rook(Color.DARK),63);
		Set<Integer> actual = generator.generateMoves(kingPosition, testBoard, new Game())
				.stream().map(Move::getTo).collect(Collectors.toSet());
		assertFalse(actual.contains(15) && actual.contains(7) && actual.contains(23));
	}
	
	@Test
	public void castleTest() {
		int lightKingPosition = 4;
		int darkKingPosition = 60;
		Set<Move> lightActual = generator.generateMoves(lightKingPosition, castleTestBoard, castleTestGame)
				.stream().collect(Collectors.toSet());
		Set<Move> darkActual = generator.generateMoves(darkKingPosition, castleTestBoard, castleTestGame)
				.stream().collect(Collectors.toSet());
		assertTrue(lightActual.size()==4);
		assertTrue(darkActual.size()==4);
	}
	
	@Test
	public void enPassantTest() {
		generator.generateMoves(12, enPassantGame.getLatestBoard(), enPassantGame);
		enPassantGame.addPositionToGame(Board.transposePositionToNewBoardInstance(enPassantGame.getLatestBoard(), new Move(12,28)));
		enPassantGame.addPositionToGame(Board.transposePositionToNewBoardInstance(enPassantGame.getLatestBoard(), new Move(48,40)));
		enPassantGame.addPositionToGame(Board.transposePositionToNewBoardInstance(enPassantGame.getLatestBoard(), new Move(28,36)));
		Set<Move> moves = generator.generateMoves(51, enPassantGame.getLatestBoard(), enPassantGame)
				.stream().collect(Collectors.toSet());
		Move moveThatTriggersEnPassantFlag = null;
		for (Move move : moves) {
			if(move.getMoveType() == MoveType.ENPASSANTFLAG) {
				moveThatTriggersEnPassantFlag = move;
			}
		}
		enPassantGame.addPositionToGame(Board.transposePositionToNewBoardInstance(enPassantGame.getLatestBoard(), moveThatTriggersEnPassantFlag));
		Set<Move> moves2 = generator.generateMoves(36, enPassantGame.getLatestBoard(), enPassantGame)
				.stream().collect(Collectors.toSet());
		Move enPassantMove = null;
		for (Move move : moves2) {
			if(move.getMoveType() == MoveType.ENPASSANT) {
				enPassantMove = move;
			}
		}
		
	}
	
	@Test
	public void canCastleIfOtherCastled() {
		Game game = returnProperlySetUpGameWithBoardToCastleTestBackRankNonBlock();
		Move darkCastle = null;
		//pick a castle move
		for(Move move : generator.generateMoves(60, game.getLatestBoard(), game)) {
			if(move.getMoveType() == MoveType.CASTLESHORT) {
				darkCastle = move;
			}
		}
		//game.addPositionToGame(Board.transposePositionToNewBoardInstance(game.getLatestBoard(), new Move(48, 40)));
		game.addPositionToGame(Board.transposePositionToNewBoardInstance(game.getLatestBoard(), darkCastle));
		List<Move> generatedMoves = generator.generateMoves(4, game.getLatestBoard(), game);
		//TODO test other color
		assertTrue(generatedMoves.size()==4);
	}
	
	private Game returnGameWithBoardToCastleTestBackRankNonBlock() {
		Game castleTestBackRankNonBlock = new Game();
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(1);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(2);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(3);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(5);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(6);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(57);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(58);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(59);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(61);
		castleTestBackRankNonBlock.getState()[0].grabPieceAndCleanFrom(62);
		//castleTestBackRankNonBlock.addPositionToGame();
		return castleTestBackRankNonBlock;
	}
	
	private Game returnProperlySetUpGameWithBoardToCastleTestBackRankNonBlock() {
		Game castleTestTwoPlayers = new Game();
		Board nextBoard = Board.deepCopy(castleTestTwoPlayers.getLatestBoard());
		nextBoard.grabPieceAndCleanFrom(1);
		nextBoard.grabPieceAndCleanFrom(2);
		nextBoard.grabPieceAndCleanFrom(3);
		nextBoard.grabPieceAndCleanFrom(5);
		nextBoard.grabPieceAndCleanFrom(6);
		nextBoard.grabPieceAndCleanFrom(57);
		nextBoard.grabPieceAndCleanFrom(58);
		nextBoard.grabPieceAndCleanFrom(59);
		nextBoard.grabPieceAndCleanFrom(61);
		nextBoard.grabPieceAndCleanFrom(62);
		nextBoard.setTransitionMoveFromPreviousBoard(new Move(8, 16));
		ColoredPiece p = nextBoard.grabPieceAndCleanFrom(8);
		nextBoard.place(new ColoredPiece(PieceType.PAWN, Color.LIGHT), 16);
		castleTestTwoPlayers.addPositionToGame(nextBoard);
		return castleTestTwoPlayers;
	}
	
}
