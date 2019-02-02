package model;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import Utils.Utils;
import config.Config;
import enums.GamePhase;
import listeners.IGameStateChangedListener;

public class GameModel {

	private Timer gameLoopTimer;
	
	private GameMap currentMap = new GameMap(new ArrayList<>());
	
	private List<IGameStateChangedListener> gameStateChangeListeners = new ArrayList<>();
	
	private int mousePositionX;
	private int mousePositionY;
	
	private long gameTimer;
	
	private long lastFrameTimeStamp;
	
	private GamePhase gamePhase = GamePhase.COLLIDED;
	
	public GameModel() {
		gameLoopTimer = new Timer(16, (e) -> gameLoop());
	}
	
	public void setMousePositionX(int mousePositionX, int mousePositionY) {
		this.mousePositionX = mousePositionX;
		this.mousePositionY = mousePositionY;
	}
	
	public void setMap(GameMap map) {
		if (map == null)
			throw new IllegalArgumentException("Map can't be null");
		
		this.currentMap = map;
		
		gamePhase = GamePhase.COLLIDED;
		notifyGameStateChanged();
	}
	
	public void startGame() {
		gamePhase = GamePhase.STARTING;
		gameTimer = 0;
		lastFrameTimeStamp = System.currentTimeMillis();
		
		gameLoopTimer.restart();
		notifyGameStateChanged(true);
	}
	
	//runs at least 60 times per second plus every time mouse position changes
	private void gameLoop() {
		if (gameTimer == 0 && IsMouseInStartingBox()) {
			//player is in starting position
			lastFrameTimeStamp = System.currentTimeMillis();
			return;
		}
		
		gamePhase = GamePhase.PLAYING;
		notifyGameStateChanged();
		
		gameTimer += System.currentTimeMillis() - lastFrameTimeStamp;
		lastFrameTimeStamp = System.currentTimeMillis();
		
		if (HasCollided()) {
			gamePhase = GamePhase.COLLIDED;
			stopGame();
		}
		
		if (IsMouseInEndingBox()) {
			if (gameTimer < currentMap.getHighScore() || currentMap.getHighScore() < 0)
				currentMap.setHighScore(gameTimer);
			
			gamePhase = GamePhase.WON;
			stopGame();
		}
	}
	
	public void leftGameboard() {
		if (!gameLoopTimer.isRunning())
			return;
			
		gamePhase = GamePhase.COLLIDED;
		stopGame();
	}
	
	public void stopGame() {
		gameLoopTimer.stop();
		
		notifyGameStateChanged();
	}
	
	private boolean IsMouseInStartingBox() {
		Rectangle2D startingBox = new Rectangle2D.Double(0,
				0,
				Config.currentConfig.getStartingLocationSizeX() + 1,
				Config.currentConfig.getStartingLocationSizeY() + 1);
		// added one to size due to contains specific method
		
		for (Rectangle2D mouseCollider : getMouseColliders()) {
			if (!startingBox.contains(mouseCollider))
				return false;
		}
		return true;
	}
	
	private boolean IsMouseInEndingBox() {
		Rectangle2D edningBox = new Rectangle2D.Double(Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getEndingLocationSizeX() - 1,
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getEndingLocationSizeY() - 1,
				Config.currentConfig.getEndingLocationSizeX() + 20,
				Config.currentConfig.getEndingLocationSizeY() + 20);
		// Subtracted one to size due to contains specific method. box is wider and higher because you can pass 
		// overshoot ending box at bottom and right (just to be sure)
		
		for (Rectangle2D mouseCollider : getMouseColliders()) {
			if (!edningBox.contains(mouseCollider))
				return false;
		}
		return true;
	}
	
	private boolean HasCollided() {
		for (Rectangle2D mouseCollider : getMouseColliders()) {
			for (Polygon polygon : currentMap.getPolygons()) {
				if(polygon.intersects(mouseCollider))
					return true;
			}
				
			for (Rectangle2D borderCollider : Utils.getBorderColliders()) {
				if (mouseCollider.intersects(borderCollider))
					return true;
			}
		}
			
		
		return false;
	}
	
	private Rectangle2D[] getMouseColliders() {
		Rectangle2D mouseCollider1 = new Rectangle2D.Double(mousePositionX - 13, mousePositionY - 2, 25, 3);
		Rectangle2D mouseCollider2 = new Rectangle2D.Double(mousePositionX - 2, mousePositionY - 13, 3, 25);
		
		return new Rectangle2D[] { mouseCollider1, mouseCollider2 };
	}
	
	public void addGameStateChangedListener(IGameStateChangedListener listener) {
		gameStateChangeListeners.add(listener);
		notifyGameStateChanged();
	}
	
	public void notifyGameStateChanged() {
		notifyGameStateChanged(false);
	}
	
	public GameMap getCurrentMap() {
		return currentMap;
	}
	
	public void notifyGameStateChanged(boolean shouldMouseBeMovedToStart) {
		GameState gameState = new GameState(currentMap, gameTimer, gamePhase);
		gameState.setShouldMouseBeMovedToStart(shouldMouseBeMovedToStart);
		
		for (IGameStateChangedListener listener : gameStateChangeListeners)
			listener.gameStateChanged(gameState);
	}
	
}
