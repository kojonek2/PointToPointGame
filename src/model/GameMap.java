package model;

import java.awt.Polygon;
import java.io.Serializable;
import java.util.List;

import config.Config;

public class GameMap implements Serializable {

	private static final long serialVersionUID = 6695584687733498012L;

	private List<Polygon> polygons;
	
	private long highScore;
	
	private Config usedConfig;
	
	public GameMap(List<Polygon> polygons) {
		this.polygons = polygons;
		this.highScore = -1;
		this.usedConfig = Config.currentConfig;
	}
	
	public List<Polygon> getPolygons() {
		return polygons;
	}
	
	public long getHighScore() {
		return highScore;
	}
	
	public void setHighScore(long highScore) {
		this.highScore = highScore;
	}
	
	public Config getUsedConfig() {
		return usedConfig; //TODO USE IT WHEN LOADING SAVE
	}
}
