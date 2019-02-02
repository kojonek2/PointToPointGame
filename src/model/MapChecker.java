package model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Utils.Utils;
import config.Config;

public class MapChecker {

	private List<Polygon> polygons;
	
	public MapChecker(List<Polygon> polygons) {
		this.polygons = polygons;
	}
	
	public boolean canMapBePassed() {
		long timeStamp = System.currentTimeMillis();
		// Using modified A* to check if player can get to end point. Wikipedia implementation.
		Set<Point> closedSet = new HashSet<>();
		Set<Point> openSet = new HashSet<>();
		Map<Point, Integer> gScore = new HashMap<>();
		Map<Point, Integer> fScore = new HashMap<>();
		Set<Point> validPositions = new HashSet<>();
		
		Point startingPoint = new Point(Config.currentConfig.getStartingLocationSizeX() / 2, Config.currentConfig.getStartingLocationSizeY() / 2);
		openSet.add(startingPoint);
		fScore.put(startingPoint, 0); //no matter what value we put there. Starting point will be removed from openSet in first step 
		
		Point targetPoint = new Point(Config.currentConfig.getGameBoardSizeX() - (Config.currentConfig.getEndingLocationSizeX() / 2),
				Config.currentConfig.getGameBoardSizeY() - (Config.currentConfig.getEndingLocationSizeY() / 2));
		gScore.put(startingPoint, 0);
		
		//generate valid positions
		//moving 2 pixels at a time to reduce computation time
		for (int i = 0; i <= Config.currentConfig.getGameBoardSizeX(); i += 2) {
			for (int j = 0; j<= Config.currentConfig.getGameBoardSizeY(); j += 2) {
				if (isValidPosition(i, j))
					validPositions.add(new Point(i,j));
			}
		}
		
		
		while (!openSet.isEmpty()) {
			Point currentPoint = getPointWithLowestFScore(openSet, fScore);
			
			if (currentPoint.equals(targetPoint))
				return true; //Target reached so player can pass map
			
			openSet.remove(currentPoint);
			closedSet.add(currentPoint);
			
			for (Point neighbour : getNeighbours(currentPoint, validPositions)) {
				if (closedSet.contains(neighbour))
					continue;
				
				int tentativeGScore = gScore.get(currentPoint) + 2; //assuming that distance to neighbors is always equal to 2
				boolean tentativeIsBetter = false;
				
				if (!openSet.contains(neighbour)) {
					openSet.add(neighbour);
					tentativeIsBetter = true;
				} else if (tentativeGScore < gScore.get(neighbour))
					tentativeIsBetter = true;
				
				if (tentativeIsBetter) {
					int heuristicDistance =  targetPoint.x - neighbour.x + targetPoint.y - neighbour.y; 
					gScore.put(neighbour, gScore.get(currentPoint) + 1); 
					fScore.put(neighbour, gScore.get(neighbour) + heuristicDistance);
				}
				
				
			}
		}
		
		System.out.println(System.currentTimeMillis() - timeStamp);
		return false; // did not reach target
	}
	
	private Point getPointWithLowestFScore(Set<Point> openSet, Map<Point, Integer> fScore) {
		Point resultPoint = null;
		int resultScore = Integer.MAX_VALUE;
		
		for (Point point : openSet) {
			if (fScore.containsKey(point) && fScore.get(point) < resultScore) {
				resultPoint = point;
				resultScore = fScore.get(point);
			}
		}
		
		return resultPoint;
	}
	
	private Set<Point> getNeighbours(Point point, Set<Point> validPositions) {
		Set<Point> neighbours = new HashSet<>();
		Point potentionNeighbour;
		
		//moving 2 pixels at a time to reduce computation time
		for (int i = -2; i <= 2; i += 2) {
			for (int j = -2; j <= 2; j += 2) {
				if (i == 0 && j == 0) // this is passed point
					continue;
				
				potentionNeighbour = new Point(point.x + i, point.y + j);
				if (validPositions.contains(potentionNeighbour))
					neighbours.add(potentionNeighbour);
			}
		}
		
		return neighbours;
	}
	
	private boolean isValidPosition(int xPostiion, int yPosition) {
		if (xPostiion < 0 || yPosition < 0)
			return false;
		if (xPostiion > Config.currentConfig.getGameBoardSizeX() || yPosition > Config.currentConfig.getGameBoardSizeY())
			return false;
		
		//colliders are bigger than cursor to compensate for human imperfection
		Rectangle2D mockMouseCollider1 = new Rectangle2D.Double(xPostiion - 13 - Config.currentConfig.getHumanImperfectionFactor(),
				yPosition - 2,
				25 + 2 * Config.currentConfig.getHumanImperfectionFactor(),
				3);
		Rectangle2D mockMouseCollider2 = new Rectangle2D.Double(xPostiion - 2,
				yPosition - 13 - Config.currentConfig.getHumanImperfectionFactor(),
				3,
				25 + 2 * Config.currentConfig.getHumanImperfectionFactor());
		
		for (Rectangle2D borderCollider : Utils.getBorderColliders()) {
			if (mockMouseCollider1.intersects(borderCollider) || mockMouseCollider2.intersects(borderCollider))
				return false;
		}
		
		for (Polygon polygon : polygons) {
			if (polygon.intersects(mockMouseCollider1) || polygon.intersects(mockMouseCollider2))
				return false;
		}
		
		return true;
	}
}
