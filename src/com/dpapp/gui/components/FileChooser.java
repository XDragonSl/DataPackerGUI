package com.dpapp.gui.components;

import java.io.File;
import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser {
	
	private static final long serialVersionUID = 1L;
	
	public FileChooser(String dir) {
		setCurrentDirectory(new File(dir));
	}
}
