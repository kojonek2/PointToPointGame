package mapGeneration;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Config;
import enums.Difficulty;
import model.GameMap;
import model.MapChecker;

public class RandomMapGenerator implements IMapGenerationStrategy {
	
	Random random = new Random();

	public static int test = 0;
	
	private final float DIFFICULTY_FACTOR;
	
	public RandomMapGenerator(Difficulty difficulty) {
		switch (difficulty) {
			case EASY:
				DIFFICULTY_FACTOR = 0.7f;
				break;
			case HARD:
				DIFFICULTY_FACTOR = 1.3f;
				break;
			case MEDIUM:
				DIFFICULTY_FACTOR = 1f;
				break;
			default:
				DIFFICULTY_FACTOR = 1f;
				break;
		}
	}
	
	@Override
	public GameMap generateMap() {
		List<Polygon> polygons;
		
		do {
			polygons = generatePotentialMap();
			System.out.println("WYGENEROWANO " + test++);
		} while (!(new MapChecker(polygons).canMapBePassed()));
		
		return new GameMap(polygons);
	}

	
	private List<Polygon> generatePotentialMap() {
		List<Polygon> result = new ArrayList<>(10);
		
		for (int i = 0; i < 100 * DIFFICULTY_FACTOR; i++) {
			Polygon polygon = new Polygon();

			int positionX = random.nextInt(Config.currentConfig.getGameBoardSizeX());
			int positionY = random.nextInt(Config.currentConfig.getGameBoardSizeY());
			
			
			for (int j = 0; j < 4; j++) {
				polygon.addPoint(random.nextInt(50), random.nextInt(50));
			}
			polygon.translate(positionX, positionY);
			
			result.add(polygon);
		}
		
		return result;
	}
}
