package views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import views.mainFrame.IView;

public class ConfigView implements IView {

	private JPanel contentPanel;
	
	private JSpinner gameBoardSizeXTextField;
	private JSpinner gameBoardSizeYTextField;
	
	private JSpinner startingLocationSizeXTextField;
	private JSpinner startingLocationSizeYTextField;
	
	private JSpinner endingLocationSizeXTextField;
	private JSpinner endingLocationSizeYTextField;
	
	private JSpinner gameBoardBorderThicknessTextField;
	private JSpinner humanImperfectionFactorTextField;
	
	private JButton confirmChangesButton;
	
	public ConfigView() {
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(300, 300));
		contentPanel.setLayout(new GridBagLayout());
		
		InitializeView();
	}
	
	private void InitializeView() {
		////////////////////////////////////////////////////////////////////////////////
				
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Game board size x:"), c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		gameBoardSizeXTextField = new JSpinner(new SpinnerNumberModel(500, 300, 1000, 10));
		gameBoardSizeXTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(gameBoardSizeXTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Game board size y:"), c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		NumberFormatter nf2 = new NumberFormatter();
		nf2.setMaximum(1000);
		nf2.setMinimum(300);
		gameBoardSizeYTextField = new JSpinner(new SpinnerNumberModel(500, 300, 800, 10));
		gameBoardSizeYTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(gameBoardSizeYTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.insets = new Insets(10, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Starting location size x:"), c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		startingLocationSizeXTextField = new JSpinner(new SpinnerNumberModel(32, 32, 64, 2));
		startingLocationSizeXTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(startingLocationSizeXTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.insets = new Insets(2, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Starting location size y:"), c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		startingLocationSizeYTextField = new JSpinner(new SpinnerNumberModel(32, 32, 64, 2));
		startingLocationSizeYTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(startingLocationSizeYTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.insets = new Insets(10, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Ending location size x:"), c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		endingLocationSizeXTextField = new JSpinner(new SpinnerNumberModel(32, 32, 64, 2));
		endingLocationSizeXTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(endingLocationSizeXTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.insets = new Insets(2, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Ending location size y:"), c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		endingLocationSizeYTextField = new JSpinner(new SpinnerNumberModel(32, 32, 64, 2));
		endingLocationSizeYTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(endingLocationSizeYTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.insets = new Insets(10, 0, 0, 3);
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Game board border thickness:"), c);
		
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.LINE_START;
		gameBoardBorderThicknessTextField = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
		gameBoardBorderThicknessTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(gameBoardBorderThicknessTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.LINE_END;
		contentPanel.add(new JLabel("Human imperfection factor:"), c);
		
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.LINE_START;
		humanImperfectionFactorTextField = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));
		humanImperfectionFactorTextField.setPreferredSize(new Dimension(50, 20));
		contentPanel.add(humanImperfectionFactorTextField, c);
		
		////////////////////////////////////////////////////////////////////////////////
		
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		confirmChangesButton = new JButton("Confirm config changes");
		contentPanel.add(confirmChangesButton, c);
	}
	
	public int getGameBoardSizeXTextFieldValue() {
		return ((Number)gameBoardSizeXTextField.getValue()).intValue();
	}
	
	public void setGameBoardSizeXTextFieldValue(int value) {
		gameBoardSizeXTextField.setValue(value);
	}
	
	public int getGameBoardSizeYTextFieldValue() {
		return ((Number)gameBoardSizeYTextField.getValue()).intValue();
	}
	
	public void setGameBoardSizeYTextFieldValue(int value) {
		gameBoardSizeYTextField.setValue(value);
	}
	
	public int getStartingLocationSizeXTextFieldValue() {
		return ((Number)startingLocationSizeXTextField.getValue()).intValue();
	}
	
	public void setStartingLocationSizeXTextFieldValue(int value) {
		startingLocationSizeXTextField.setValue(value);
	}
	
	public int getStartingLocationSizeYTextFieldValue() {
		return ((Number)startingLocationSizeYTextField.getValue()).intValue();
	}
	
	public void setStartingLocationSizeYTextFieldValue(int value) {
		startingLocationSizeYTextField.setValue(value);
	}
	
	public int getEndingLocationSizeXTextFieldValue() {
		return ((Number)endingLocationSizeXTextField.getValue()).intValue();
	}
	
	public void setEndingLocationSizeXTextFieldValue(int value) {
		endingLocationSizeXTextField.setValue(value);
	}
	
	public int getEndingLocationSizeYTextFieldValue() {
		return ((Number)endingLocationSizeYTextField.getValue()).intValue();
	}
	
	public void setEndingLocationSizeYTextFieldValue(int value) {
		endingLocationSizeYTextField.setValue(value);
	}
	
	public int getGameBoardBorderThicknessTextFieldValue() {
		return ((Number)gameBoardBorderThicknessTextField.getValue()).intValue();
	}
	
	public void setGameBoardBorderThicknessTextFieldValue(int value) {
		gameBoardBorderThicknessTextField.setValue(value);
	}
	
	public int getHumanImperfectionFactorTextFieldValue() {
		return ((Number)humanImperfectionFactorTextField.getValue()).intValue();
	}
	
	public void setHumanImperfectionFactorTextFieldValue(int value) {
		humanImperfectionFactorTextField.setValue(value);
	}
	
	public void addConfirmChangesButtonListener(ActionListener listener) {
		confirmChangesButton.addActionListener(listener);
	}
	
	@Override
	public JPanel getContentPanel() {
		return contentPanel;
	}
	
}
