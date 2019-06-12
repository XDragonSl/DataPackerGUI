package com.dpapp.gui.components.table;

public class Row {
	
	private static final String HEADERS[] = {"Name", "Size"};
	
	private String name;
	private String size;
	
	public static int getFieldCount() {
		return HEADERS.length;
	}
	
	public static String getFieldName(int index) {
		return HEADERS[index];
	}
	
	public void setField(int index, Object value) {
		switch (index) {
		case 0:
			name = (String)value;
			break;
		case 1:
			size = value + " b";
			break;
		}
	}
	
	public Object getField(int index) {
		Object field = new Object();
		switch (index) {
		case 0:
			field = name;
			break;
		case 1:
			field = size;
			break;
		}
		return field;
	}
}
