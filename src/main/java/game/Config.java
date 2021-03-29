package game;

public class Config {
	
	private static Config instance = null;

	private Integer lookAheadDepth = 3;
	
	private Config() {}

	public Integer getLookAheadDepth() {
		return lookAheadDepth;
	}

	public void setLookAheadDepth(Integer lookAheadDepth) {
		this.lookAheadDepth = lookAheadDepth;
	}
	
	public static Config getInstance() {
		if(instance == null) {
			instance = new Config();
		}
		return instance;
	}
}
