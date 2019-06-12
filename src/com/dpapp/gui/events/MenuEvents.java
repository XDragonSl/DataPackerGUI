package com.dpapp.gui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.dpapp.gui.Main;

public class MenuEvents implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "New":
			Main.create();
			break;
		case "Open":
			Main.open();
			break;
		case "Exit":
			System.exit(0);
			break;
		case "Help Contents":
			Main.help();
			break;
		case "About Data Packer":
			Main.about();
			break;
		}
	}
	
}
