package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controllers.GameController;

public class Main {

	public static void main(String[] args) {
		//To make sure that we interact with GUI on event dispatch thread.
		//In game there will be no long calculations or processing methods so it's ok
		SwingUtilities.invokeLater(() -> {
			Main main = new Main();
			main.run();
		});
	}

	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//ignore it and simply use cross platform look and feel
			e.printStackTrace();
		}
		
		new GameController();
	}
}
