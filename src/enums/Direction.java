package enums;

public enum Direction {
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);
	
	private int x, y;
	
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static Direction getDirection(int x, int y) {
		for (Direction direction : values()) {
			if (direction.x == x && direction.y == y)
				return direction;
		}
		
		return null;
	}
}
