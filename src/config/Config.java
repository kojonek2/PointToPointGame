package config;

import java.io.Serializable;

import mapGeneration.factory.IMapGeneratorFactory;
import mapGeneration.factory.MazeMapGeneratorFactory;
import mapGeneration.factory.RandomMapGeneratorFactory;

public class Config implements Serializable {
	
	private static final long serialVersionUID = 5818800071494352508L;

	public static Config currentConfig = new Config(); 

	public static IMapGeneratorFactory[] availableMapGeneratorFactories = new IMapGeneratorFactory[] {
			new MazeMapGeneratorFactory(),
			new RandomMapGeneratorFactory()
	};
	
	private int gameBoardSizeX = 500;
	private int gameBoardSizeY = 500;
	
	private int startingLocationSizeX = 32;
	private int startingLocationSizeY = 32;
	
	private int endingLocationSizeX = 32;
	private int endingLocationSizeY = 32;
	
	private int gameBoardBorderThickness = 2;
	
	private int humanImperfectionFactor = 2;

	public int getGameBoardSizeX() {
		return gameBoardSizeX;
	}

	public void setGameBoardSizeX(int gameBoardSizeX) {
		this.gameBoardSizeX = gameBoardSizeX;
	}

	public int getGameBoardSizeY() {
		return gameBoardSizeY;
	}

	public void setGameBoardSizeY(int gameBoardSizeY) {
		this.gameBoardSizeY = gameBoardSizeY;
	}

	public int getStartingLocationSizeX() {
		return startingLocationSizeX;
	}

	public void setStartingLocationSizeX(int startingLocationSizeX) {
		this.startingLocationSizeX = startingLocationSizeX;
	}

	public int getStartingLocationSizeY() {
		return startingLocationSizeY;
	}

	public void setStartingLocationSizeY(int startingLocationSizeY) {
		this.startingLocationSizeY = startingLocationSizeY;
	}

	public int getEndingLocationSizeX() {
		return endingLocationSizeX;
	}

	public void setEndingLocationSizeX(int endingLocationSizeX) {
		this.endingLocationSizeX = endingLocationSizeX;
	}

	public int getEndingLocationSizeY() {
		return endingLocationSizeY;
	}

	public void setEndingLocationSizeY(int endingLocationSizeY) {
		this.endingLocationSizeY = endingLocationSizeY;
	}

	public int getGameBoardBorderThickness() {
		return gameBoardBorderThickness;
	}

	public void setGameBoardBorderThickness(int gameBoardBorderThickness) {
		this.gameBoardBorderThickness = gameBoardBorderThickness;
	}

	public int getHumanImperfectionFactor() {
		return humanImperfectionFactor;
	}

	public void setHumanImperfectionFactor(int humanImperfectionFactor) {
		this.humanImperfectionFactor = humanImperfectionFactor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endingLocationSizeX;
		result = prime * result + endingLocationSizeY;
		result = prime * result + gameBoardBorderThickness;
		result = prime * result + gameBoardSizeX;
		result = prime * result + gameBoardSizeY;
		result = prime * result + humanImperfectionFactor;
		result = prime * result + startingLocationSizeX;
		result = prime * result + startingLocationSizeY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Config other = (Config) obj;
		if (endingLocationSizeX != other.endingLocationSizeX)
			return false;
		if (endingLocationSizeY != other.endingLocationSizeY)
			return false;
		if (gameBoardBorderThickness != other.gameBoardBorderThickness)
			return false;
		if (gameBoardSizeX != other.gameBoardSizeX)
			return false;
		if (gameBoardSizeY != other.gameBoardSizeY)
			return false;
		if (humanImperfectionFactor != other.humanImperfectionFactor)
			return false;
		if (startingLocationSizeX != other.startingLocationSizeX)
			return false;
		if (startingLocationSizeY != other.startingLocationSizeY)
			return false;
		return true;
	}
	
}
