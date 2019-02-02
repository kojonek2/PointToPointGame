package mapGeneration;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import Utils.Utils;
import config.Config;
import enums.Difficulty;
import enums.Direction;
import model.GameMap;

public class MazeMapGenerator implements IMapGenerationStrategy {
	
	private final int CORRIDOR_WIDTH;
	private final int WALL_THICKNESS;
	
	private Random random = new Random();
	
	private  int startingXCellsCount;
	private  int startingYCellsCount;
	private  int endingXCellsCount;
	private  int endingYCellsCount;
	
	private  int marginHorizontal;
	private  int marginVertical;
	
	private int columnsCount;
	private int rowsCount;

	public MazeMapGenerator() { 
		this.CORRIDOR_WIDTH = 32;
		this.WALL_THICKNESS = 5;
		
		InitializeVariables();
	}
	
	public MazeMapGenerator(Difficulty difficulty) {
		switch (difficulty) {
			case EASY:
				this.CORRIDOR_WIDTH = 40;
				this.WALL_THICKNESS = 5;
				break;
			case MEDIUM:
				this.CORRIDOR_WIDTH = 35;
				this.WALL_THICKNESS = 5;
				break;
			case HARD:
				this.CORRIDOR_WIDTH = 30;
				this.WALL_THICKNESS = 2;
				break;
			default:
				this.CORRIDOR_WIDTH = 32;
				this.WALL_THICKNESS = 5;
				break;
		}
		
		InitializeVariables();
	}
	
	private void InitializeVariables() {
		startingXCellsCount = (Config.currentConfig.getStartingLocationSizeX() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) + 1;
		startingYCellsCount = (Config.currentConfig.getStartingLocationSizeY() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) + 1;
		endingXCellsCount = (Config.currentConfig.getEndingLocationSizeX() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) + 1;
		endingYCellsCount = (Config.currentConfig.getEndingLocationSizeY() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) + 1;
		
		marginHorizontal = (Config.currentConfig.getGameBoardSizeX() % (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) / 2;
		marginVertical = (Config.currentConfig.getGameBoardSizeY() % (CORRIDOR_WIDTH + 2 * WALL_THICKNESS)) / 2;
		
		columnsCount = Config.currentConfig.getGameBoardSizeX() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS);
		rowsCount = Config.currentConfig.getGameBoardSizeY() / (CORRIDOR_WIDTH + 2 * WALL_THICKNESS);
	}
	
	@Override
	public GameMap generateMap() {
		List<Polygon> polygons = new ArrayList<>();
		
		Cell[][] cells = InitializeCellMatrix();
		
		//create entrance
		cells[0][startingYCellsCount].hasNorthWall = false;
		cells[0][startingYCellsCount - 1].hasSouthWall = false;
		
		//create exit
		cells[columnsCount - endingXCellsCount][rowsCount - endingYCellsCount].hasNorthWall = false;
		cells[columnsCount - endingXCellsCount][rowsCount - endingYCellsCount - 1].hasSouthWall = false;
		
		
		Cell currentCell = cells[0][startingYCellsCount]; //Initializing starting position
		
		Stack<Cell> visitedStack = new Stack<>();
		visitedStack.push(currentCell);
		
		//generate maze
		while (!visitedStack.isEmpty()) {
			List<Cell> neighbours = getNotVisitedNeighbours(currentCell, cells);
			
			if (neighbours.size() > 0) {
				int nextNeighbourIndex = random.nextInt(neighbours.size());
				currentCell.removeWallBetween(neighbours.get(nextNeighbourIndex));
				
				currentCell = neighbours.get(nextNeighbourIndex);
				currentCell.visited = true;
				visitedStack.push(currentCell);
			} else {
				currentCell = visitedStack.pop();//go back
			}
		}
		
		//create edges of maze so there is no free space between maze and edge of gameBoard
		polygons.addAll(generateBorders());
		
		//generate walls polygons
		for (int i = 0; i < columnsCount; i++) {
			for (int j = 0; j < rowsCount; j++) {
				Cell cell = cells[i][j];
				
				polygons.addAll(cell.getWallsPolygons());
			}
		}
		
		return new GameMap(polygons);
	}
	
	private Cell[][] InitializeCellMatrix() {
		
		
		Cell[][] cells = new Cell[columnsCount][rowsCount];
		for (int i = 0; i < columnsCount; i++) {
			for (int j = 0; j < rowsCount; j++)
				cells[i][j] = new Cell(i, j);
		}
		
		//setting to not generate maze on starting and ending positions
		for (int i = 0; i < startingXCellsCount; i++) {
			for (int j = 0; j < startingYCellsCount; j++) {
				cells[i][j].visited = true;
				cells[i][j].removeWalls(true, i != startingXCellsCount - 1, true, j != startingYCellsCount - 1);
			}
				
		}
		for (int i = cells.length - endingXCellsCount; i < columnsCount; i++) {
			for (int j = cells[0].length - endingYCellsCount; j < rowsCount; j++) {
				cells[i][j].visited = true;
				cells[i][j].removeWalls(i != columnsCount - endingXCellsCount,
						true,
						j != rowsCount - endingYCellsCount,
						true);
			}
		}
		
		return cells;
	}
	
	private List<Cell> getNotVisitedNeighbours(Cell currentCell, Cell[][] cells) {
		List<Cell> neighbours = new ArrayList<>();
		
		for (Direction direction : Direction.values()) {
			
			boolean isValid = currentCell.x + direction.getX() >= 0 && currentCell.y + direction.getY() >= 0;
			isValid &= currentCell.x + direction.getX() < cells.length && currentCell.y + direction.getY() < cells[0].length;
			
			if (isValid &&  !cells[currentCell.x + direction.getX()][currentCell.y + direction.getY()].visited)
				neighbours.add(cells[currentCell.x + direction.getX()][currentCell.y + direction.getY()]);
		}
		
		return neighbours;
	}
	
	private List<Polygon> generateBorders() {
		List<Polygon> polygons = new ArrayList<>();
		
		Rectangle2D westBorder = new Rectangle2D.Double(0,
				startingYCellsCount * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginVertical - WALL_THICKNESS,
				marginHorizontal,
				(rowsCount - startingYCellsCount) * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginVertical + WALL_THICKNESS);
		
		Rectangle2D eastBorder = new Rectangle2D.Double(columnsCount * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginHorizontal,
				marginVertical,
				marginHorizontal,
				(rowsCount - endingYCellsCount) * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + WALL_THICKNESS);
		
		Rectangle2D northBorder = new Rectangle2D.Double(startingXCellsCount * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginHorizontal - WALL_THICKNESS,
				0,
				(columnsCount - startingXCellsCount) * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginHorizontal + WALL_THICKNESS,
				marginVertical);
		
		Rectangle2D southBorder = new Rectangle2D.Double(0,
				rowsCount * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginVertical,
				(columnsCount - endingXCellsCount) * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginHorizontal + WALL_THICKNESS,
				marginVertical);
		
		polygons.add(Utils.Rectangle2DToPolygon(westBorder));
		polygons.add(Utils.Rectangle2DToPolygon(eastBorder));
		polygons.add(Utils.Rectangle2DToPolygon(northBorder));
		polygons.add(Utils.Rectangle2DToPolygon(southBorder));
		
		return polygons;
	}

	
	private class Cell {
		private int x, y;
		
		private boolean hasWestWall = true;
		private boolean hasEastWall = true;
		private boolean hasNorthWall = true;
		private boolean hasSouthWall = true;
		
		private boolean visited = false;
		
		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		private void removeWalls(boolean removeWestWall, boolean removeEastWall, boolean removeNorthWall, boolean removeSouthWall) {
			if (removeWestWall)
				hasWestWall = false;
			if (removeEastWall)
				hasEastWall = false;
			if (removeNorthWall)
				hasNorthWall = false;
			if (removeSouthWall)
				hasSouthWall = false;
		}
		
		private void removeWallBetween(Cell cell) {
			Direction directionToCell = Direction.getDirection(cell.x - x, cell.y - y);
			
			switch (directionToCell) {
				case RIGHT:
					this.hasEastWall = false;
					cell.hasWestWall = false;
					break;
				case LEFT:
					this.hasWestWall = false;
					cell.hasEastWall = false;
					break;
				case DOWN:
					this.hasSouthWall = false;
					cell.hasNorthWall = false;
					break;
				case UP:
					this.hasNorthWall = false;
					cell.hasSouthWall = false;
					break;
				default:
					throw new IllegalArgumentException("Cells are not neighbours");
			}
		}
		
		private List<Polygon> getWallsPolygons() {
			List<Polygon> result  = new ArrayList<>();
			
			if (hasWestWall) {
				int xPosition = x * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginHorizontal;
				int yPosition = y * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) - WALL_THICKNESS + marginVertical;
				int width = WALL_THICKNESS;
				int height = CORRIDOR_WIDTH + 4 * WALL_THICKNESS; // 4 times to create correct corners
				Rectangle2D rectangle = new Rectangle2D.Double(xPosition, yPosition, width, height);
				
				result.add(Utils.Rectangle2DToPolygon(rectangle));
			}
			
			if (hasEastWall) {
				int xPosition = x * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + CORRIDOR_WIDTH + WALL_THICKNESS + marginHorizontal;
				int yPosition = y * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) - WALL_THICKNESS + marginVertical;
				int width = WALL_THICKNESS;
				int height = CORRIDOR_WIDTH + 4 * WALL_THICKNESS; // 4 times to create correct corners
				Rectangle2D rectangle = new Rectangle2D.Double(xPosition, yPosition, width, height);
				
				result.add(Utils.Rectangle2DToPolygon(rectangle));
			}
			
			if (hasNorthWall) {
				int xPosition = x * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) - WALL_THICKNESS + marginHorizontal;
				int yPosition = y * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + marginVertical;
				int width = CORRIDOR_WIDTH + 4 * WALL_THICKNESS; // 4 times to create correct corners
				int height = WALL_THICKNESS;
				Rectangle2D rectangle = new Rectangle2D.Double(xPosition, yPosition, width, height);
				
				result.add(Utils.Rectangle2DToPolygon(rectangle));
			}
			
			if (hasSouthWall) {
				int xPosition = x * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) - WALL_THICKNESS + marginHorizontal;
				int yPosition = y * (CORRIDOR_WIDTH + 2 * WALL_THICKNESS) + CORRIDOR_WIDTH + WALL_THICKNESS + marginVertical;
				int width = CORRIDOR_WIDTH + 4 * WALL_THICKNESS; // 4 times to create correct corners
				int height = WALL_THICKNESS;
				Rectangle2D rectangle = new Rectangle2D.Double(xPosition, yPosition, width, height);
				
				result.add(Utils.Rectangle2DToPolygon(rectangle));
			}
			
			return result;
		}
	}
}
