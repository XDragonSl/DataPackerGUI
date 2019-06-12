package com.dpapp.gui.components;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Data Packer";
	
	public Frame(Dimension dim, boolean top) {
		super(TITLE);
		setAlwaysOnTop(top);
		setSize(dim);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
