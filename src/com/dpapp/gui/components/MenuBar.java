package com.dpapp.gui.components;

import java.awt.event.ActionListener;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	public MenuBar(String[] headers, String[][] items, String[][] keystrokes, ActionListener listener) {
		for (int i = 0; i < headers.length; i++) {
	        add(new Menu(headers[i], items[i], keystrokes[i], listener));
		}
	}
}
