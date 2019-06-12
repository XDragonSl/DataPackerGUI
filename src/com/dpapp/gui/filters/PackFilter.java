package com.dpapp.gui.filters;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PackFilter extends FileFilter {
	
	private static final String EXTENSION = ".dpack";

	@Override
	public boolean accept(File f) {
		if (f.getName().toLowerCase().endsWith(EXTENSION) || f.isDirectory()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Data Packs (*.dpack)";
	}

}
