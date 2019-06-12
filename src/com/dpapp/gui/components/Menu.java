package com.dpapp.gui.components;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class Menu extends JMenu {
	
	private static final long serialVersionUID = 1L;
	
	public static final String SEPARATOR = "---";
	
	public Menu(String header, String[] items, String[] keystrokes, ActionListener listener) {
        super(header);
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(SEPARATOR)) {
				addSeparator();
			} else {
				JMenuItem item = new JMenuItem(items[i], keystrokes[i].charAt(keystrokes[i].length() - 1));
				item.setAccelerator(KeyStroke.getKeyStroke(keystrokes[i]));
				item.addActionListener(listener);
				add(item);
			}
		}
	}
}
