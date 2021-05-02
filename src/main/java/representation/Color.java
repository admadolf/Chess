package representation;

public enum Color {
	LIGHT("L"), DARK("D"), EMPTY("E");

	public final String text;

	public String toString() {
		return text;
	}

	public Color opposite() {
		if(this == Color.LIGHT) {
			return Color.DARK;
		} else if (this == Color.DARK) {
			return Color.LIGHT;
		}
		return Color.EMPTY;
	}

	private Color(String text) {
		this.text = text;
	};

}
