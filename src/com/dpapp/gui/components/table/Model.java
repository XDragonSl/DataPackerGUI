package com.dpapp.gui.components.table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class Model extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Row> rows = new ArrayList<>();

	@Override
	public int getColumnCount() {
		return Row.getFieldCount();
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return rows.get(arg0).getField(arg1);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		for (int i = rows.size(); i < rowIndex + 1; i++) {
			rows.add(new Row());
		}
		rows.get(rowIndex).setField(columnIndex, aValue);
	}
	
	@Override
	public String getColumnName(int column) {
		return Row.getFieldName(column);
	}
	
	public void clear() {
		rows.clear();
	}
}
