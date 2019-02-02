package views;

import java.awt.Component;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameMapFileChooser extends JFileChooser {

	private static final long serialVersionUID = -5396067324206393729L;

	private Component parent;
	
	public GameMapFileChooser(Component parent) {
		super();
		
		this.parent = parent;
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GameMap files", "ptp");
		setFileFilter(filter);
		setAcceptAllFileFilterUsed(false);
	}
	
	public File getFileToLoad() {
		int answer = showOpenDialog(parent);
		if (answer == JFileChooser.APPROVE_OPTION) {
			return getSelectedFile();
		}
		
		return null;
	}
	
	public File getFileToSave() {
		int answer = showSaveDialog(parent);
		if (answer == JFileChooser.APPROVE_OPTION) {
			return getSelectedFile();
		}
		
		return null;
	}
	
	@Override
	public void approveSelection() {
		File file = getSelectedFile();
		
		if (file.exists() && getDialogType() == JFileChooser.SAVE_DIALOG) {
			
			int answer = JOptionPane.showConfirmDialog(this, "Would you like to override file?");
			if (answer == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (answer != JOptionPane.YES_OPTION) {
				setSelectedFile(null);
			}
			
		} else if (!file.exists() && getDialogType() == JFileChooser.OPEN_DIALOG) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "File does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		super.approveSelection();
	}
}
