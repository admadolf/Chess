package ai.movegen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.eval.AttackDefenseMap;
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
	public void testKnight10() {
		testBoard.place(new Knight(Color.LIGHT),10);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(25, 27, 4, 20, 0, 16));
		Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(10, testBoard));
		assertEquals(expected, actual);
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
	public void testBishop27Friend54() {
		testBoard.place(new Bishop(Color.LIGHT),27);
		testBoard.place(new Bishop(Color.LIGHT),54);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(36, 45, 34, 41, 48, 20, 13, 6, 18, 9, 0));
		Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(27, testBoard));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBishop27FriendlyPieceOn36And0() {
		testBoard.place(new Bishop(Color.LIGHT),27);
		testBoard.place(new Pawn(Color.LIGHT), 36);
		testBoard.place(new Pawn(Color.LIGHT), 0);
		Set<Integer> expected = new HashSet<Integer>(( Arrays.asList(20, 13, 6, 18, 9 ,34, 41 ,48)));
		Set<Integer> actual = new HashSet<Integer>((generator.generateMoves(27, testBoard)));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKing27FriendlyPieceOn36() {
		testBoard.place(new King(Color.LIGHT),27);
		testBoard.place(new Pawn(Color.LIGHT), 36);
		Set<Integer> expected = new HashSet<Integer>((Arrays.asList(35, 28, 20, 19, 18, 26, 34)));
		Set<Integer> actual = new HashSet<Integer>((generator.generateMoves(27, testBoard)));
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
		System.out.println(testBoard);
		System.out.println(expected);
		System.out.println(actual);
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
	
	//darkpawn
	@Test
	public void testDarkPawn50() {
		int testedPiecePosition = 50;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		List<Integer> expected = Arrays.asList(42,34);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn48Enemy41() {
		int testedPiecePosition = 48;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Knight(Color.LIGHT),41);
		List<Integer> expected = Arrays.asList(40,32,41);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn51Enemy42Enemy44() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),42);
		testBoard.place(new Pawn(Color.LIGHT),44);
		List<Integer> expected = Arrays.asList(43,35,42,44);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn48Enemy41Enemy55() {
		int testedPiecePosition = 48;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),41);
		testBoard.place(new Pawn(Color.LIGHT),55);
		List<Integer> expected = Arrays.asList(40,32,41);
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
	public void testDarkPawn51Enemy43Enemy42Enemy44() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		testBoard.place(new Pawn(Color.LIGHT),42);
		testBoard.place(new Pawn(Color.LIGHT),44);
		List<Integer> expected = Arrays.asList(42,44);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Enemy43() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		List<Integer> expected = Arrays.asList();
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Friend43() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),43);
		List<Integer> expected = Arrays.asList();
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDarkPawn51Friend35() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.DARK),35);
		List<Integer> expected = Arrays.asList(43);
		List<Integer> actual = generator.generateMoves(testedPiecePosition, testBoard);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPawn51Enemy35() {
		int testedPiecePosition = 51;
		testBoard.place(new Pawn(Color.DARK),testedPiecePosition);
		testBoard.place(new Pawn(Color.LIGHT),35);
		List<Integer> expected = Arrays.asList(43);
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
	
	@Test
	public void isAttackedByKnight() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Knight(Color.DARK), 27);
		System.out.println(testBoard);
		boolean actual = generator.isAttackedBy(10, new Knight(Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByBishop() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Rook(Color.DARK), 58);
		System.out.println(testBoard);
		boolean actual = generator.isAttackedBy(10, new ColoredPiece(PieceType.ROOK,Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByRook() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Rook(Color.DARK), 58);
		boolean actual = generator.isAttackedBy(10, new Rook(Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByPawn() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Pawn(Color.DARK), 17);
		boolean actual = generator.isAttackedBy(10, new Pawn(Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByQueen() {
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new Queen(Color.DARK), 46);
		boolean actual = generator.isAttackedBy(10, new Queen(Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void isAttackedByKing() { //TODO king logic to prevent them from getting close
		testBoard.place(new Bishop(Color.LIGHT), 10);
		testBoard.place(new King(Color.DARK), 2);
		System.out.println(testBoard);
		boolean actual = generator.isAttackedBy(10, new King(Color.DARK), testBoard);
		assertEquals(true, actual);
	}
	
	@Test
	public void testKing10() {
		int testedPiecePosition = 10;
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT),testedPiecePosition);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(17, 18, 19, 11, 9, 1, 2, 3));
		Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(testedPiecePosition, testBoard));
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKing10Friend9Enemy19() {
		int testedPiecePosition = 10;
		testBoard.place(new ColoredPiece(PieceType.KING, Color.LIGHT),testedPiecePosition);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT),9);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK),19);
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(17, 18, 19, 11, 1, 2, 3));
		Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(testedPiecePosition, testBoard));
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
		Set<Integer> expected = new HashSet<Integer>(Arrays.asList(11, 1, 2, 9, 3));
		Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(10, testBoard));
		assertEquals(expected, actual);
	}
	
	@Test
	public void attackDefenseMapTest() {
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 25);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 27);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.DARK), 4);
		ColoredPiece[][] arr = generator.generateMap(10, testBoard, MoveType.ATTACK);
		List<Integer> moveList = generator.generateMoves(10, testBoard);
		//AttackDefenseMap admap = new AttackDefenseMap();
		//admap.setAttackDefenseMap(arr);
		System.out.println(moveList);
		System.out.println(Arrays.deepToString(arr));
		System.out.println(testBoard);
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				k++;
				if(arr[i][j]!=null) {
					System.out.println("map " + arr[i][j] );	
				}
				
			}
		}
		AttackDefenseMap adMap = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.ATTACK);
		AttackDefenseMap adMap1 = new AttackDefenseMap(testBoard, Color.DARK, MoveType.ATTACK);
		System.out.println(adMap.countPieces());
		System.out.println(adMap1.countPieces());
		//System.out.println(Arrays.deepToString(admap.attackedBySquare(10)));
		System.out.println("eoe " + k );
		
		//System.out.println(arr[10][25]);
		//System.out.println(arr[10][20]);
	}
	
	@Test
	public void attackDefenseMapAttackTest() {
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 37);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.ATTACK);
		System.out.println(map.getAttackDefenseMap()[10][37]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapBishopDefenseTestDark() {
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 37);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 28);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 24);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][37]);
		assertEquals(new ColoredPiece(PieceType.BISHOP, Color.DARK), map.getAttackDefenseMap()[10][24]);
		assertEquals(null, map.getAttackDefenseMap()[10][28]);
		//System.out.println(map.getAttackDefenseMap()[10][37]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapBishopDefenseTestLIGHT() {
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 37);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 28);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 24);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][37]);
		assertEquals(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), map.getAttackDefenseMap()[10][24]);
		assertEquals(null, map.getAttackDefenseMap()[10][28]);
		//System.out.println(map.getAttackDefenseMap()[10][37]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseRookDarkInbetweenEnemyTest() {
		testBoard.place(new ColoredPiece(PieceType.ROOK, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 26);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 42);
		System.out.println(testBoard);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][42]);
		assertEquals(null, map.getAttackDefenseMap()[10][26]);
		System.out.println(map.getAttackDefenseMap()[10][42]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseRookLightInbetweenEnemyTest() {
		testBoard.place(new ColoredPiece(PieceType.ROOK, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 26);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 42);
		System.out.println(testBoard);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][42]);
		assertEquals(null, map.getAttackDefenseMap()[10][26]);
		System.out.println(map.getAttackDefenseMap()[10][42]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseRookInbetweenFriendTest() {
		testBoard.place(new ColoredPiece(PieceType.ROOK, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 26);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 42);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][42]);
		assertEquals(new ColoredPiece(PieceType.ROOK, Color.DARK), map.getAttackDefenseMap()[10][26]);
		System.out.println(map.toString());
	}
	
	
	
	@Test
	public void attackDefenseMapAttackRookLightTest() {
		testBoard.place(new ColoredPiece(PieceType.ROOK, Color.LIGHT), 27);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 26);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 24);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 35);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 31);
		System.out.println(testBoard);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.ATTACK);
		assertEquals(null, map.getAttackDefenseMap()[27][26]);
		System.out.println(map.getAttackDefenseMap()[27][24]);
		assertEquals(new ColoredPiece(PieceType.ROOK, Color.LIGHT), map.getAttackDefenseMap()[27][35]);
		assertEquals(new ColoredPiece(PieceType.ROOK, Color.LIGHT), map.getAttackDefenseMap()[27][31]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseKnightDarkTest() {
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 20);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 4);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.DARK), 16);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][4]);
		assertEquals(new ColoredPiece(PieceType.KNIGHT, Color.DARK), map.getAttackDefenseMap()[10][20]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseKnightLightTest() {
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 20);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 4);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.LIGHT), 16);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][4]);
		assertEquals(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), map.getAttackDefenseMap()[10][20]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseQueentLightTest() {
		testBoard.place(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), 27);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 3);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 41);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 34);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 59);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 31);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 26);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 24);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 29);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 63);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 11);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		System.out.println(testBoard.toString());
		assertEquals(null, map.getAttackDefenseMap()[27][3]);
		assertEquals(null, map.getAttackDefenseMap()[27][41]);
		assertEquals(null, map.getAttackDefenseMap()[27][26]);
		assertEquals(null, map.getAttackDefenseMap()[27][31]);
		assertEquals(null, map.getAttackDefenseMap()[27][59]);
		assertEquals(null, map.getAttackDefenseMap()[27][24]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][63]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][34]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][29]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][11]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseQueentDarkTest() {
		testBoard.place(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), 27);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 3);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 41);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 34);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 59);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 31);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 26);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 24);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 29);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 63);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 11);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		System.out.println(testBoard.toString());
		assertEquals(null, map.getAttackDefenseMap()[27][3]);
		assertEquals(null, map.getAttackDefenseMap()[27][41]);
		assertEquals(null, map.getAttackDefenseMap()[27][26]);
		assertEquals(null, map.getAttackDefenseMap()[27][31]);
		assertEquals(null, map.getAttackDefenseMap()[27][59]);
		assertEquals(null, map.getAttackDefenseMap()[27][24]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][63]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][34]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][29]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][11]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapAttackQueentDarkTest() {
		testBoard.place(new ColoredPiece(PieceType.QUEEN, Color.DARK), 27);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 3);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 41);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 34);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 59);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 31);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 26);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 24);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 29);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 63);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 11);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 13);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 20);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.ATTACK);
		System.out.println(testBoard.toString());
		assertEquals(null, map.getAttackDefenseMap()[27][3]);
		assertEquals(null, map.getAttackDefenseMap()[27][41]);
		assertEquals(null, map.getAttackDefenseMap()[27][26]);
		assertEquals(null, map.getAttackDefenseMap()[27][31]);
		assertEquals(null, map.getAttackDefenseMap()[27][59]);
		assertEquals(null, map.getAttackDefenseMap()[27][24]);
		assertEquals(null, map.getAttackDefenseMap()[27][20]);
		assertEquals(null, map.getAttackDefenseMap()[27][13]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.DARK), map.getAttackDefenseMap()[27][63]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.DARK), map.getAttackDefenseMap()[27][34]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.DARK), map.getAttackDefenseMap()[27][29]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.DARK), map.getAttackDefenseMap()[27][11]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapAttackQueentLightTest() {
		testBoard.place(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), 27);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 3);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 41);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 34);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 59);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 31);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 26);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 24);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 29);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 63);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 11);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.DARK), 13);
		testBoard.place(new ColoredPiece(PieceType.BISHOP, Color.LIGHT), 20);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.ATTACK);
		System.out.println(testBoard.toString());
		assertEquals(null, map.getAttackDefenseMap()[27][3]);
		assertEquals(null, map.getAttackDefenseMap()[27][41]);
		assertEquals(null, map.getAttackDefenseMap()[27][26]);
		assertEquals(null, map.getAttackDefenseMap()[27][31]);
		assertEquals(null, map.getAttackDefenseMap()[27][59]);
		assertEquals(null, map.getAttackDefenseMap()[27][24]);
		assertEquals(null, map.getAttackDefenseMap()[27][20]);
		assertEquals(null, map.getAttackDefenseMap()[27][13]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][63]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][34]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][29]);
		assertEquals(new ColoredPiece(PieceType.QUEEN, Color.LIGHT), map.getAttackDefenseMap()[27][11]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseQueenDarkTest() {
		testBoard.place(new ColoredPiece(PieceType.QUEEN, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 20);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 4);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.DARK), 16);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][4]);
		assertEquals(new ColoredPiece(PieceType.KNIGHT, Color.DARK), map.getAttackDefenseMap()[10][20]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseKingLightTest() {
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 20);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 4);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.LIGHT), 16);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.LIGHT, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][4]);
		assertEquals(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), map.getAttackDefenseMap()[10][20]);
		System.out.println(map.toString());
	}
	
	@Test
	public void attackDefenseMapDefenseKingDarkTest() {
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 10);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.DARK), 20);
		testBoard.place(new ColoredPiece(PieceType.KNIGHT, Color.LIGHT), 4);
		testBoard.place(new ColoredPiece(PieceType.PAWN, Color.DARK), 16);
		AttackDefenseMap map = new AttackDefenseMap(testBoard, Color.DARK, MoveType.DEFENSE);
		assertEquals(null, map.getAttackDefenseMap()[10][4]);
		assertEquals(new ColoredPiece(PieceType.KNIGHT, Color.DARK), map.getAttackDefenseMap()[10][20]);
		System.out.println(map.toString());
	}
}
