package game;

public enum GameType {

	NO_LIMIT("Texas Hold'em No Limit"), LIMIT("Texas Hold'em Limit");

	private String name;

	GameType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
