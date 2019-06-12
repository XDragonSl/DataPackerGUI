package com.dpapp.gui;

import java.io.FileInputStream;
import java.io.InputStream;

public class Tools {
	
	public static String loadHTML(String filepath) throws Exception {
		InputStream helpfile = new FileInputStream(filepath);
		byte help[] = new byte[helpfile.available()];
		helpfile.read(help);
		helpfile.close();
		return new String(help);
	}
}
