package ai.eval;

import ai.movegen.MoveGenerator;
import ai.representation.Board;
import ai.representation.Color;
import ai.representation.MoveType;
import ai.representation.PieceType;
import ai.representation.piece.ColoredPiece;

public class AttackDefenseMap {

	
	private static MoveGenerator moveGen = new MoveGenerator();
	private final Board board;
	private ColoredPiece[][] attackDefenseMap = new ColoredPiece[64][64];
	Color color;

	
	
	public AttackDefenseMap(Board board, Color color, MoveType moveType) {
		super();
		this.color = color;
		this.board = board;
		for (int i : moveGen.getAll(color, board)) {
			this.attackDefenseMap[i] = moveGen.generateMap(i, board, moveType)[i];
		}
		
	}


	public ColoredPiece[][] get() {
		return attackDefenseMap;
	}

	
	public ColoredPiece[][] getAttackDefenseMap() {
		return attackDefenseMap;
	}

	public void setAttackDefenseMap(ColoredPiece[][] attackDefenseMap) {
		this.attackDefenseMap = attackDefenseMap;
	}



	public ColoredPiece[] attackedBySquare(int coord) {
		return attackDefenseMap[coord];
	}
	
	//todo defend oldalat megcsinalni es 2 bol mar SEE ugyanazon a fielden megnezem mindket oldalon ki tamad oda, ki ved oda, sorrend meg akinek a kore az van tempoba
	public int countPieces() {
		int counter = 0;
		for (int i = 0; i < attackDefenseMap.length; i++) {
			ColoredPiece attacker = board.get(i);
			if(attacker.pieceType != PieceType.EMPTY && attacker.color == this.color) {
				for (int j = 0; j < attackDefenseMap.length; j++) {
					ColoredPiece attacked = attackDefenseMap[i][j];
					if(attacked != null) {
						counter++;
					}
						//todo mar itt valami callback h mit csinaljon egyes esetekben? pl. egy lo egy pawnot csak akkor usson le ha nem vedi mas? kell egyaltalan
					//ha van material heuristic - SEE -ben hogy legyen gondolkozni egymas utani exchangeket... lehet azt kene elobb h eszembe jusson vmi ertelmes fv ennek az osztalynak
					
				}
			}
		}
		return counter;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 64; i++) {
			ColoredPiece attacker = board.get(i);
				for (int j = 0; j < 64; j++) {
					ColoredPiece attacked = attackDefenseMap[i][j];
					if(attacked == null)
						sb.append(i + " " + j + " " + PieceType.EMPTY + " " + Color.EMPTY);
					else
						sb.append(i + " " + j + " " + attacked.getPieceType() + " " + attacked.getColor());
					/*if(attacked == null) {
						sb.append(PieceType.EMPTY);
					} else {
						sb.append(attacked.getPieceType().toString());
					}*/
				
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

	/*public getAllAttackersOn(int coord) {
		
	}
	*/
	
	
}
