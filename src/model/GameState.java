package model;

import enums.GamePhase;

public class GameState {

	private GameMap currentMap;
	private long timeSinceStart;
	private GamePhase gamePhase;
	
	private boolean shouldMouseBeMovedToStart;
	
	public GameState(GameMap currentMap, long timeSinceStart, GamePhase gamePhase) {
		this.currentMap = currentMap;
		this.timeSinceStart = timeSinceStart;
		this.gamePhase = gamePhase;
	}
	
	public GameMap getCurrentMap() {
		return currentMap;
	}
	
	public long getTimeSinceStart() {
		return timeSinceStart;
	}
	
	public void setShouldMouseBeMovedToStart(boolean shouldMouseBeMovedToStart) {
		this.shouldMouseBeMovedToStart = shouldMouseBeMovedToStart;
	}
	
	public boolean getShouldMouseBeMovedToStart() {
		return shouldMouseBeMovedToStart;
	}
	
	public GamePhase getGamePhase() {
		return gamePhase;
	}
}
