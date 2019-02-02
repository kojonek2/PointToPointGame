package enums;

public enum GamePhase {
	STARTING("Starting"),
	PLAYING("Playing"),
	COLLIDED("Collided"),
	WON("Won");
	
	private String gamePhaseString;
	
	private GamePhase(String gamePthaseString) {
		this.gamePhaseString = gamePthaseString;
	}
	
	@Override
	public String toString() {
		return gamePhaseString;
	}
}
