package com.dpapp.gui.components;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Text extends JLabel {

	private static final long serialVersionUID = 1L;
	
	public Text(int fontsize) {
		setFont(new Font(null, Font.PLAIN, fontsize));
		setHorizontalAlignment(SwingConstants.CENTER);
	}
}
