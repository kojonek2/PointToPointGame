package Utils;

import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import config.Config;

public class Utils {

	public static Rectangle2D[] getBorderColliders() {
		Rectangle2D borderCollider1 = new Rectangle2D.Double(0,
				0 + Config.currentConfig.getStartingLocationSizeY() + 1,
				Config.currentConfig.getGameBoardBorderThickness() - 1,
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getStartingLocationSizeY());
		
		Rectangle2D borderCollider2 = new Rectangle2D.Double(0,
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getGameBoardBorderThickness(),
				Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getEndingLocationSizeX() - 1,
				Config.currentConfig.getGameBoardBorderThickness());
		
		Rectangle2D borderCollider3 = new Rectangle2D.Double(Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getGameBoardBorderThickness(),
				0,
				Config.currentConfig.getGameBoardBorderThickness() - 1,
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getEndingLocationSizeY() - 1);
		
		Rectangle2D borderCollider4 = new Rectangle2D.Double(0 + Config.currentConfig.getStartingLocationSizeX() + 1,
				0,
				Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getStartingLocationSizeX(),
				Config.currentConfig.getGameBoardBorderThickness() - 1);
		
		
		return new Rectangle2D[] { borderCollider1, borderCollider2, borderCollider3, borderCollider4 };
	}
	
	public static Polygon Rectangle2DToPolygon(Rectangle2D rectangle) {
		int[] xCords = new int[4];
		int[] yCords = new int[4];
		
		xCords[0] = (int)rectangle.getX();
		yCords[0] = (int)rectangle.getY();

		xCords[1] = (int)rectangle.getMaxX();
		yCords[1] = (int)rectangle.getY();

		xCords[2] = (int)rectangle.getMaxX();
		yCords[2] = (int)rectangle.getMaxY();
		
		xCords[3] = (int)rectangle.getX();
		yCords[3] = (int)rectangle.getMaxY();
	
		return new Polygon(xCords, yCords, 4);
	}
	
}
