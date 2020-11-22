package ai.representation.piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ai.representation.Color;
import ai.representation.PieceType;
import ai.representation.Vector;

public class Pawn extends ColoredPiece {

	static Map<Color, List<Integer>> moveVectorsMap = new HashMap<>();

	static {
		List<Integer> darkMoveVectors = new ArrayList<>();
		List<Integer> lightMoveVectors = new ArrayList<>();
		lightMoveVectors.add(Vector.NORTH);
		lightMoveVectors.add(Vector.NW);
		lightMoveVectors.add(Vector.NE);
		darkMoveVectors.add(Vector.SOUTH);
		darkMoveVectors.add(Vector.SW);
		darkMoveVectors.add(Vector.SE);
		moveVectorsMap.put(Color.DARK, darkMoveVectors);
		moveVectorsMap.put(Color.LIGHT, lightMoveVectors);
	}

	public Pawn(Color color) {
		super(PieceType.PAWN, color);
	}

	public static List<Integer> getMoveVectors(Color color) {
		return color == Color.LIGHT
				? PieceType.PAWN.moveVectors.stream().filter(i -> i > 0).collect(Collectors.toList())
				: PieceType.PAWN.moveVectors.stream().filter(i -> i < 0).collect(Collectors.toList());
	}

	public static List<Integer> getAttackDefenseMoveVectors(Color color) {
		return color == Color.LIGHT
				? Arrays.asList(new Integer[] {Vector.NW,Vector.NE})
				: Arrays.asList(new Integer[] {Vector.SW,Vector.SE});
	}
}
