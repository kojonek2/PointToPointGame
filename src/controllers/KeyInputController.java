package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class KeyInputController implements KeyListener {
	
	public Map<Integer, List<Runnable>> keyToActionsMap = new HashMap<>();

	public void registerAsEventListener(JFrame jFrame) {
		jFrame.addKeyListener(this);
	}
	
	/**
	 * Registers runnable to be executed on key pressed event
	 * @param keyCode KeyEvent static final int identifying key
	 * @param runnable runnable that shall be executed
	 */
	public void addOnKeyPressedAction(int keyCode, Runnable runnable) {
		if (!keyToActionsMap.containsKey(keyCode)) {
			keyToActionsMap.put(keyCode, new ArrayList<Runnable>());
		}
		
		keyToActionsMap.get(keyCode).add(runnable);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (keyToActionsMap.containsKey(e.getKeyCode())) {
			
			List<Runnable> runnables = keyToActionsMap.get(e.getKeyCode());
			for (Runnable runnable : runnables) {
				runnable.run();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
}
