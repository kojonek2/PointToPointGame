package views.mainFrame;

import java.awt.Cursor;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private IView currentView;
	
	public MainFrame(IView view) {
		this(null, view);
	}

	public MainFrame(MainFrame mainFrame, IView view) {
		super();
		
		if (mainFrame != null) {
			setLocation(mainFrame.getLocation());
			mainFrame.dispose();
		}
		
		setTitle("Point to Point");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		currentView = view;
		setContentPane(view.getContentPanel());
		
		pack();
		setVisible(true);
	}
	
	
	public IView getCurrentView() {
		return currentView;
	}
	
}
