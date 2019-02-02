package controllers;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import config.Config;
import listeners.IGameStateChangedListener;
import mapGeneration.IMapGenerationStrategy;
import model.GameMap;
import model.GameModel;
import model.GameState;
import views.GameMapFileChooser;
import views.GameView;
import views.mainFrame.MainFrame;

public class GameController implements IGameStateChangedListener {

	private MainFrame mainFrame;
	private GameModel gameModel;
	private KeyInputController keyInputController;
	
	public GameController() {
		this(null, null);
	}
	
	public GameController(MainFrame mainFrame) {
		this(mainFrame, null);
	}
	
	public GameController(MainFrame mainFrame, GameMap map) {
		GameView view = new GameView();
		this.mainFrame = new MainFrame(mainFrame, view);
		this.gameModel = new GameModel();
		
		if (map != null) {
			this.gameModel.setMap(map);
		}
		
		this.keyInputController = new KeyInputController();
		this.keyInputController.registerAsEventListener(this.mainFrame);
		InitializeKeyInputControllerListeners();
		
		InitializeActionListeners(view);
		gameModel.addGameStateChangedListener(this);
		
	}
	
	@Override
	public void gameStateChanged(GameState gameState) {
		if (mainFrame.getCurrentView() instanceof GameView) {
			((GameView)mainFrame.getCurrentView()).drawGameState(gameState);
			
			((GameView)mainFrame.getCurrentView()).setGamePhaseLabelText(gameState.getGamePhase().toString());
			
			if (gameState.getShouldMouseBeMovedToStart())
				ResetMousePostionToStart();
				
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private void InitializeKeyInputControllerListeners() {
		keyInputController.addOnKeyPressedAction(KeyEvent.VK_R, () -> {
			gameModel.startGame();
		});
	}
	
	private void ResetMousePostionToStart() {
		try {
			Point p = ((GameView)mainFrame.getCurrentView()).getGameBoardPanelScreenLocation();
			p.translate(Config.currentConfig.getStartingLocationSizeX() / 2, Config.currentConfig.getStartingLocationSizeY() / 2);
			
			new Robot().mouseMove(p.x, p.y);
		} catch (AWTException e) {
		}
	}
	
	private void InitializeActionListeners(GameView view) {
		view.addGamePanelMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				gameModel.setMousePositionX(e.getX(), e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) { }
		});
		view.addGamePanelMouseListener(new MouseListener() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				gameModel.leftGameboard();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mousePressed(MouseEvent e) { }
		
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		view.addGenerateMapButtonListener((e) -> {
			generateMap(view);
		});
		
		view.addLoadMapButtonListener((e) -> loadMap());
		view.addSaveMapButtonListener((e) -> saveMap());
		view.addChangeConfigButtonListener((e) -> new ConfigController(mainFrame));
	}
	
	private void loadMap() {
		GameMapFileChooser fileChooser = new GameMapFileChooser(mainFrame);
		File file = fileChooser.getFileToLoad();
		
		if (file == null)
			return;
		
		try (ObjectInputStream inputStram = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			GameMap map = (GameMap) inputStram.readObject();
			
			if (map.getUsedConfig().equals(Config.currentConfig))
				gameModel.setMap(map);
			else {
				int answer = JOptionPane.showConfirmDialog(mainFrame, "Map saved on different config. Would you like to change it?");
				if (answer != JOptionPane.YES_OPTION)
					return;
				
				Config.currentConfig = map.getUsedConfig();
				new GameController(mainFrame, map);
			}
				
		} catch (ClassNotFoundException | IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(mainFrame, "Error occured during loading", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void generateMap(GameView view) {
		mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
		IMapGenerationStrategy mapGenerator = view.getSelectedMapGeneratorFactory().getGenerator(view.getSelectedMapDifficulty());
		GameMap map = mapGenerator.generateMap();
		
		mainFrame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		gameModel.setMap(map);
	}
	
	private void saveMap() {
		GameMapFileChooser fileChooser = new GameMapFileChooser(mainFrame);
		File file = fileChooser.getFileToSave();
		
		if (file == null)
			return;
		
		try (ObjectOutputStream inputStram = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			inputStram.writeObject(gameModel.getCurrentMap());
		} catch (IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(mainFrame, "Error occured during loading", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
