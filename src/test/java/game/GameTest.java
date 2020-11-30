package game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ai.movegen.Move;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.Node;
import ai.representation.PieceType;
import ai.representation.piece.EmptyPiece;

public class GameTest {

	
	Game game;
	
	Game castleTestBackRankBlock;
	
	Game castleTestBackRankNonBlock;
	
	Game castleTestBackRankNonBlockKingMoved;
	
	Game castleTestBackRankNonBlockQueenRookMoved;
	
	Game castleTestBackRankNonBlockKingRookMoved;
	
	@BeforeEach
	public void init() {
		game = new Game();
		//remove that pawn so other pieces have to block 
		castleTestBackRankBlock = new Game();
		castleTestBackRankNonBlock = new Game();
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
		
		castleTestBackRankNonBlockKingMoved = new Game();
		Board boardState1KingsMoved = Board.deepCopy(castleTestBackRankNonBlock.getState()[0]);
		castleTestBackRankNonBlockKingMoved.getState()[0] = boardState1KingsMoved;
		Board lightKingMoved = Board.transposePositionToNewBoardInstance(boardState1KingsMoved, new Move(4,5));
		castleTestBackRankNonBlockKingMoved.getState()[1] = lightKingMoved;
		Board darkKingMoved = Board.transposePositionToNewBoardInstance(boardState1KingsMoved, new Move(60, 59));
		castleTestBackRankNonBlockKingMoved.getState()[2] = darkKingMoved;
		
		castleTestBackRankNonBlockQueenRookMoved = new Game();
		Board boardStateQueenRooksMoved = Board.deepCopy(castleTestBackRankNonBlock.getState()[0]);
		castleTestBackRankNonBlockQueenRookMoved.getState()[0] = boardState1KingsMoved;
		Board lightQueenSideRookMoved = Board.transposePositionToNewBoardInstance(boardStateQueenRooksMoved, new Move(0, 3));
		castleTestBackRankNonBlockQueenRookMoved.getState()[1] = lightQueenSideRookMoved;
		Board darkQueenSideRookMoved = Board.transposePositionToNewBoardInstance(boardStateQueenRooksMoved, new Move(56, 57));
		castleTestBackRankNonBlockQueenRookMoved.getState()[2] = darkQueenSideRookMoved;
		
		castleTestBackRankNonBlockKingRookMoved = new Game();
		Board boardStateKingRooksMoved = Board.deepCopy(castleTestBackRankNonBlock.getState()[0]);
		Board lightKingSideRookMoved = Board.transposePositionToNewBoardInstance(boardStateKingRooksMoved, new Move(7, 6));
		castleTestBackRankNonBlockKingRookMoved.getState()[1] = lightKingSideRookMoved;
		Board darkKingSideRookMoved = Board.transposePositionToNewBoardInstance(boardStateQueenRooksMoved, new Move(63, 61));
		castleTestBackRankNonBlockKingRookMoved.getState()[2] = darkKingSideRookMoved;
	}
	
	@Test
	public void a() {
		
		/*for (Node node : moveList) {
			System.out.println(node.getPosition());
			System.out.println(node.getPosition().getTransitionMoveFromPreviousBoard());
		}*/
		int i = 1;
		System.out.println("initial position:\n" + game.getState()[i-1]);
		for (;i<20;i++) {
			Board board = game.getState()[i-1];
			if(i%2 == 1) {
				System.out.println("LIGHT MOVE");
				LinkedList<Node> moveList = game.moveLight(3, board);
				//Collections.shuffle(moveList);
				game.getState()[i] = moveList.peekLast().getPosition();
				System.out.println("NEXT BOARD");
				System.out.println(game.getState()[i]);
				//System.out.println("SCORE: " + game.getDarkEvaluator().evaluateMiniMax(game.getState()[i]));
			}else {
				System.out.println("DARK MOVE:");

				if(i==12) {
					System.out.println(i);
				}
				LinkedList<Node> moveList = game.moveDark(3, board);
				//Collections.shuffle(moveList);
				//System.out.println("moveList: " + moveList);
				//100000 a king erteke  + az allas erteke sumja mar nagyobb es a minimaxsearchben levo min value , csinalni vmit ezzel
				//lehet ilyen esetekre kene vmi random lepest generalni vagy megirni vegre h a kinget ki kelljen vinni sakkbol
				game.getState()[i] = moveList.peekLast().getPosition();
				System.out.println("NEXT BOARD");
				System.out.println("score: " + moveList.peekLast().getScore());
				System.out.println(game.getState()[i]);
				//System.out.println("SCORE: " + game.getDarkEvaluator().evaluateMiniMax(game.getState()[i]));
			}
			
		}
		
		int j = 0;
		
		System.out.println(game.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT));
		System.out.println(game.calculateCanCastle(Color.DARK, MoveType.CASTLELONG));
		System.out.println(game.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT));
		//game.canCastle(Color.LIGHT);
		/*while (game.getState()[j] != null) {
			System.out.println("NEXT BOARD");
			System.out.println(game.getState()[j]);
			System.out.println("SCORE: " + game.getDarkEvaluator().evaluateMiniMax(game.getState()[j]));
			++j;
		}*/
		
		//game.getState();
		//Set<Integer> expected =;
		//Set<Integer> actual = new HashSet<Integer>(generator.generateMoves(10, testBoard));
		//assertEquals(expected, actual);
	}
	
	@Test
	public void testCalculateCanNotCastleWhenPiecesPopulateBackRank() {
		boolean LightLongSide = castleTestBackRankBlock.calculateCanCastle(Color.LIGHT, MoveType.CASTLELONG);
		boolean lightShortSide = castleTestBackRankBlock.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT);
		boolean darkLongSide = castleTestBackRankBlock.calculateCanCastle(Color.DARK, MoveType.CASTLELONG);
		boolean darkShortSide = castleTestBackRankBlock.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT);
		assertFalse(LightLongSide);
		assertFalse(lightShortSide);
		assertFalse(darkLongSide);
		assertFalse(darkShortSide);
	}
	
	@Test
	public void testCalculateCanCastleWhenPiecesDontPopulateBackRank() {
		boolean LightLongSide = castleTestBackRankNonBlock.calculateCanCastle(Color.LIGHT, MoveType.CASTLELONG);
		boolean lightShortSide = castleTestBackRankNonBlock.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT);
		boolean darkLongSide = castleTestBackRankNonBlock.calculateCanCastle(Color.DARK, MoveType.CASTLELONG);
		boolean darkShortSide = castleTestBackRankNonBlock.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT);
		assertTrue(LightLongSide);
		assertTrue(lightShortSide);
		assertTrue(darkLongSide);
		assertTrue(darkShortSide);
	}
	
	@Test
	public void testCalculateCanCastleWhenPiecesDontPopulateBackRankAndKingMoved() {
		boolean LightLongSide = castleTestBackRankNonBlockKingMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLELONG);
		boolean lightShortSide = castleTestBackRankNonBlockKingMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT);
		boolean darkLongSide = castleTestBackRankNonBlockKingMoved.calculateCanCastle(Color.DARK, MoveType.CASTLELONG);
		boolean darkShortSide = castleTestBackRankNonBlockKingMoved.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT);
		assertFalse(LightLongSide);
		assertFalse(lightShortSide);
		assertFalse(darkLongSide);
		assertFalse(darkShortSide);
	}
	
	@Test
	public void testCalculateCanCastleKingSideCantCastleQueenSideWhenQueensideRookMoved() {
		boolean LightLongSide = castleTestBackRankNonBlockQueenRookMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLELONG);
		boolean lightShortSide = castleTestBackRankNonBlockQueenRookMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT);
		boolean darkLongSide = castleTestBackRankNonBlockQueenRookMoved.calculateCanCastle(Color.DARK, MoveType.CASTLELONG);
		boolean darkShortSide = castleTestBackRankNonBlockQueenRookMoved.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT);
		assertFalse(LightLongSide);
		assertTrue(lightShortSide);
		assertFalse(darkLongSide);
		assertTrue(darkShortSide);
	}
	
	@Test
	public void testCalculateCanCastleQueenSideCantCastleKingSideWhenKingRookMoved() {
		boolean LightLongSide = castleTestBackRankNonBlockKingRookMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLELONG);
		boolean lightShortSide = castleTestBackRankNonBlockKingRookMoved.calculateCanCastle(Color.LIGHT, MoveType.CASTLESHORT);
		boolean darkLongSide = castleTestBackRankNonBlockKingRookMoved.calculateCanCastle(Color.DARK, MoveType.CASTLELONG);
		boolean darkShortSide = castleTestBackRankNonBlockKingRookMoved.calculateCanCastle(Color.DARK, MoveType.CASTLESHORT);
		assertTrue(LightLongSide);
		assertFalse(lightShortSide);
		assertTrue(darkLongSide);
		assertFalse(darkShortSide);
	}
	
	@Test
	public void testAddPositionToGame() {
		Game game = new Game();
		game.addPositionToGame(Board.transposePositionToNewBoardInstance(game.getLatestBoard(), new Move(12,28)));
		System.out.println(game.getLatestBoard());
		System.out.println(game.getIndex());
	}
	
}
