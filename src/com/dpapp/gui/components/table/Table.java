package com.dpapp.gui.components.table;

import javax.swing.JTable;

public class Table extends JTable {
	
	private static final long serialVersionUID = 1L;
	
	private static Model model = new Model();
	
	public Table() {
		super(model);
		setRowSelectionAllowed(false);
	}
	
	public void clear() {
		model.clear();
	}
}
