package ai.representation.piece;

import ai.representation.Color;
import ai.representation.PieceType;

public abstract class Piece {

	protected PieceType pieceType;
	
	protected Color color;
	
	private int position;

	public Piece(PieceType pieceType,Color color) {
		super();
		this.pieceType = pieceType;
		this.color = color;
	}

	@Override
	public String toString() {
		return color.toString()+pieceType.toString()+" ";
	}
	
	public Color getColor() {
		return color;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
