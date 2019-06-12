package com.dpapp.gui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.dpapp.gui.Main;

public class ButtonEvents implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "Create pack":
			Main.pack();
			break;
		case "Clear choice":
			Main.clear();
			break;
		case "Extract all":
			Main.extract();
			break;
		case "Close pack":
			Main.clear();
			break;
		}
	}
}
