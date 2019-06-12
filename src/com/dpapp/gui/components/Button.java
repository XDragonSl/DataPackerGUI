package com.dpapp.gui.components;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	public Button(Dimension size, ActionListener listener) {
		setPreferredSize(size);
		setEnabled(false);
		addActionListener(listener);
	}
}
