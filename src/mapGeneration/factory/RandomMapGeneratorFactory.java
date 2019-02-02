package mapGeneration.factory;

import enums.Difficulty;
import mapGeneration.IMapGenerationStrategy;
import mapGeneration.RandomMapGenerator;

public class RandomMapGeneratorFactory implements IMapGeneratorFactory {

	@Override
	public IMapGenerationStrategy getGenerator(Difficulty difficulty) {
		return new RandomMapGenerator(difficulty);
	}

	@Override
	public String toString() {
		return "Random Map";
	}
	
}
