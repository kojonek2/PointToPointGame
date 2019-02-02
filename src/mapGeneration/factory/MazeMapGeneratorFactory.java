package mapGeneration.factory;

import enums.Difficulty;
import mapGeneration.IMapGenerationStrategy;
import mapGeneration.MazeMapGenerator;

public class MazeMapGeneratorFactory implements IMapGeneratorFactory {

	@Override
	public IMapGenerationStrategy getGenerator(Difficulty diffulty) {
		return new MazeMapGenerator(diffulty);
	}

	@Override
	public String toString() {
		return "Maze Map";
	}
	
}
