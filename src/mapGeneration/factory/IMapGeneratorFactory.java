package mapGeneration.factory;

import enums.Difficulty;
import mapGeneration.IMapGenerationStrategy;

public interface IMapGeneratorFactory {

	public IMapGenerationStrategy getGenerator(Difficulty difficulty);
	
}
