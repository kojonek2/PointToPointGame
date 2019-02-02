package controllers;

import config.Config;
import views.ConfigView;
import views.mainFrame.MainFrame;

public class ConfigController {

	private MainFrame mainFrame;
	
	public ConfigController() {
		this(null);
	}
	
	public ConfigController(MainFrame mainFrame) {
		ConfigView view = new ConfigView();
		this.mainFrame = new MainFrame(mainFrame, view);
		
		InitializeViewValues(view);
		InitializeListener(view);
	}
	
	private void InitializeViewValues(ConfigView view) {
		view.setGameBoardSizeXTextFieldValue(Config.currentConfig.getGameBoardSizeX());
		view.setGameBoardSizeYTextFieldValue(Config.currentConfig.getGameBoardSizeY());
	
		view.setStartingLocationSizeXTextFieldValue(Config.currentConfig.getStartingLocationSizeX());
		view.setStartingLocationSizeYTextFieldValue(Config.currentConfig.getStartingLocationSizeY());
		
		view.setEndingLocationSizeXTextFieldValue(Config.currentConfig.getEndingLocationSizeX());
		view.setEndingLocationSizeYTextFieldValue(Config.currentConfig.getEndingLocationSizeY());
		
		view.setGameBoardBorderThicknessTextFieldValue(Config.currentConfig.getGameBoardBorderThickness());
		
		view.setHumanImperfectionFactorTextFieldValue(Config.currentConfig.getHumanImperfectionFactor());
	}
	
	private void InitializeListener(ConfigView view) {
		view.addConfirmChangesButtonListener((e) -> {
			Config newConfig = new Config();
			
			newConfig.setGameBoardSizeX(view.getGameBoardSizeXTextFieldValue());
			newConfig.setGameBoardSizeY(view.getGameBoardSizeYTextFieldValue());
			
			newConfig.setStartingLocationSizeX(view.getStartingLocationSizeXTextFieldValue());
			newConfig.setStartingLocationSizeY(view.getStartingLocationSizeYTextFieldValue());
			
			newConfig.setEndingLocationSizeX(view.getEndingLocationSizeXTextFieldValue());
			newConfig.setEndingLocationSizeY(view.getEndingLocationSizeYTextFieldValue());
			
			newConfig.setGameBoardBorderThickness(view.getGameBoardBorderThicknessTextFieldValue());
			
			newConfig.setHumanImperfectionFactor(view.getHumanImperfectionFactorTextFieldValue());
			
			Config.currentConfig = newConfig;
			
			new GameController(mainFrame);
		});
	}
}
