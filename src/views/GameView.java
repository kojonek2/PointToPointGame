package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config;
import enums.Difficulty;
import mapGeneration.factory.IMapGeneratorFactory;
import model.GameState;
import views.mainFrame.IView;

public class GameView implements IView {

	private JPanel contentPane;
	private GameBoardPanel gameBoardPanel;
	
	private JLabel highScoreLabel;
	private JLabel timerLabel;
	private JLabel gamePhaseLabel;
	
	private JComboBox<IMapGeneratorFactory> mapGeneratorComboBox;
	private JComboBox<Difficulty> mapDifficultyComboBox;
	private JButton generateMapButton;
	
	private JButton loadMapButton;
	private JButton saveMapButton;
	
	private JButton changeConfigButton;
	
	public GameView() {
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		gameBoardPanel = new GameBoardPanel();
		contentPane.add(gameBoardPanel);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		controlPanel.setPreferredSize(new Dimension(150, Config.currentConfig.getGameBoardSizeY()));
		contentPane.add(controlPanel);
		
		GridBagConstraints c = new GridBagConstraints();
		
		highScoreLabel = new JLabel("HIGH SCORE: 000:00:000");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(2, 10, 0, 10);
		controlPanel.add(highScoreLabel, c);
		
		timerLabel = new JLabel("000:00:000");
		c.gridy = 1;
		c.insets = new Insets(2, 10, 0, 10);
		controlPanel.add(timerLabel, c);
		
		gamePhaseLabel = new JLabel("Collided");
		c.gridy = 2;
		controlPanel.add(gamePhaseLabel, c);
		
		c.gridy = 3;
		c.insets = new Insets(10, 10, 0, 10);
		controlPanel.add(new JLabel("Press R to Restart"), c);
		
		mapGeneratorComboBox = new JComboBox<>(Config.availableMapGeneratorFactories);
		mapGeneratorComboBox.setFocusable(false);
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		controlPanel.add(mapGeneratorComboBox, c);
		
		mapDifficultyComboBox = new JComboBox<>(Difficulty.values());
		mapDifficultyComboBox.setFocusable(false);
		c.insets = new Insets(2, 10, 0, 10);
		c.gridy = 5;
		controlPanel.add(mapDifficultyComboBox, c);
		
		generateMapButton = new JButton("Generate Map");
		generateMapButton.setFocusable(false);
		c.gridy = 6;
		controlPanel.add(generateMapButton, c);
		
		loadMapButton = new JButton("Load Map");
		loadMapButton.setFocusable(false);
		c.insets = new Insets(10, 10, 0, 10);
		c.gridy = 7;
		controlPanel.add(loadMapButton, c);
		
		saveMapButton = new JButton("Save Map");
		saveMapButton.setFocusable(false);
		c.insets = new Insets(2, 10, 0, 10);
		c.gridy = 8;
		controlPanel.add(saveMapButton, c);
		
		changeConfigButton = new JButton("Change Config");
		changeConfigButton.setFocusable(false);
		c.insets = new Insets(10, 10, 0, 10);
		c.gridy = 9;
		controlPanel.add(changeConfigButton, c);
	}
	
	@Override
	public JPanel getContentPanel() {
		return contentPane;
	}
	
	public void drawGameState(GameState gameState) {
		gameBoardPanel.drawGameState(gameState);
		
		changeTimerLabel(gameState);
		changeHighScore(gameState);
	}
	
	public void addGamePanelMouseMotionListener(MouseMotionListener listener) {
		gameBoardPanel.addMouseMotionListener(listener);
	}
	
	public void addGamePanelMouseListener(MouseListener listener) {
		gameBoardPanel.addMouseListener(listener);
	}
	
	public void addGenerateMapButtonListener(ActionListener listener) {
		generateMapButton.addActionListener(listener);
	}
	
	public void addLoadMapButtonListener(ActionListener listener) {
		loadMapButton.addActionListener(listener);
	}
	
	public void addSaveMapButtonListener(ActionListener listener) {
		saveMapButton.addActionListener(listener);
	}
	
	public void addChangeConfigButtonListener(ActionListener listener) {
		changeConfigButton.addActionListener(listener);
	}
	
	public IMapGeneratorFactory getSelectedMapGeneratorFactory() {
		return mapGeneratorComboBox.getItemAt(mapGeneratorComboBox.getSelectedIndex());
	}
	
	public Difficulty getSelectedMapDifficulty() {
		return mapDifficultyComboBox.getItemAt(mapDifficultyComboBox.getSelectedIndex());
	}
	
	public Point getGameBoardPanelScreenLocation() {
		return gameBoardPanel.getLocationOnScreen();
	}
	
	private void changeTimerLabel(GameState gameState) {
		timerLabel.setText(convertTimeToString(gameState.getTimeSinceStart()));
	}
	
	private void changeHighScore(GameState gameState) {
		long highScore = gameState.getCurrentMap().getHighScore();
		if (highScore < 0)
			highScoreLabel.setText("HIGH SCORE: none");
		else
			highScoreLabel.setText("HIGH SCORE: " + convertTimeToString(highScore));
	}
	
	private String convertTimeToString(long time) {
		long miliseconds = time % 1000;
		long seconds = (time / 1000) % 60;
		long minutes = (time / 60000) % 60;
		
		return String.format("%03d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", miliseconds);
	}

	public void setGamePhaseLabelText(String text) {
		gamePhaseLabel.setText(text);
	}
}
