package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import config.Config;
import enums.GamePhase;
import model.GameState;

@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {

	//used to cache polygons to use them in paint function
	private List<Polygon> polygons = new ArrayList<>();
	private GamePhase gamePhase;
	
	public GameBoardPanel() {
		super();
		setPreferredSize(new Dimension(Config.currentConfig.getGameBoardSizeX(), Config.currentConfig.getGameBoardSizeY()));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, Config.currentConfig.getGameBoardBorderThickness()));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//starting location
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, Config.currentConfig.getStartingLocationSizeX(), Config.currentConfig.getStartingLocationSizeY());
		g.fillRect(0, 0, Config.currentConfig.getStartingLocationSizeX(), Config.currentConfig.getStartingLocationSizeY());
		
		//ending location
		g.setColor(Color.GREEN);
		g.drawRect(Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getEndingLocationSizeX(),
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getEndingLocationSizeY(),
				Config.currentConfig.getEndingLocationSizeX(),
				Config.currentConfig.getEndingLocationSizeY());
		g.fillRect(Config.currentConfig.getGameBoardSizeX() - Config.currentConfig.getEndingLocationSizeX(),
				Config.currentConfig.getGameBoardSizeY() - Config.currentConfig.getEndingLocationSizeY(),
				Config.currentConfig.getEndingLocationSizeX(),
				Config.currentConfig.getEndingLocationSizeY());
		
		g.setColor(Color.BLACK);
		for (Polygon polygon : polygons) {
			g.drawPolygon(polygon);
			g.fillPolygon(polygon);
		}
		
		
		if (gamePhase == GamePhase.WON) {
			g.setColor(new Color(0, 255, 0, 50));
			g.drawRect(0, 0, Config.currentConfig.getGameBoardSizeX(), Config.currentConfig.getGameBoardSizeY());
			g.fillRect(0, 0, Config.currentConfig.getGameBoardSizeX(), Config.currentConfig.getGameBoardSizeY());
		} else if (gamePhase == GamePhase.COLLIDED) {
			g.setColor(new Color(255, 0, 0, 50));
			g.drawRect(0, 0, Config.currentConfig.getGameBoardSizeX(), Config.currentConfig.getGameBoardSizeY());
			g.fillRect(0, 0, Config.currentConfig.getGameBoardSizeX(), Config.currentConfig.getGameBoardSizeY());
		}
			
	}
	
	public void drawGameState(GameState gameState) {
		this.polygons = gameState.getCurrentMap().getPolygons();
		this.gamePhase = gameState.getGamePhase();
		repaint();
	}
	
}
