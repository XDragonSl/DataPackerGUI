package com.dpapp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.dp.DataInfo;
import com.dp.DataReader;
import com.dp.DataWriter;
import com.dpapp.gui.components.Button;
import com.dpapp.gui.components.FileChooser;
import com.dpapp.gui.components.Frame;
import com.dpapp.gui.components.Text;
import com.dpapp.gui.components.Menu;
import com.dpapp.gui.components.MenuBar;
import com.dpapp.gui.components.table.Table;
import com.dpapp.gui.events.ButtonEvents;
import com.dpapp.gui.events.MenuEvents;
import com.dpapp.gui.filters.PackFilter;

public class Main {

	private static final String DIRECTORY = "user.dir";
	private static final String EXTENSION = ".dpack";
	private static final String EXTENSION_TMP = ".dpacktmp";
	private static final String CREATING_PROGRESS = "Creating of pack... Please, wait!";
	private static final String EXTRACTING_PROGRESS = "Extracting of files... Please, wait!";
	private static final String OVERWRITE = " is already exists! Overwrite it?";
	private static final String SAVE = "Save";
	private static final String READY = "Ready!";
	private static final String SUCCESS = "Success";
	private static final String ERROR = "Error";
	private static final String NEW_ERROR = "Cannot create the pack!";
	private static final String OPEN_ERROR = "Cannot open the pack!";
	private static final String HELP_FILE = "help/help.html";
	private static final String ABOUT_FILE = "help/about.html";
	private static final String CREATING_HEADER = " - Creating of pack";
	private static final String EXTRACTING_HEADER = " - Extracting of files";
	private static final String HELP_HEADER = " - Help Contents";
	private static final String ABOUT_HEADER = " - About Data Packer";
	private static final String HELP_ERROR = "Sorry, help not found!";
	private static final String[] NEW_BUTTONS = {"Create pack", "Clear choice"};
	private static final String[] OPEN_BUTTONS = {"Extract all", "Close pack"};
	private static final String[] HEADERS = {"File", "Help"};
	private static final String[][] ITEMS = {{"New", "Open", Menu.SEPARATOR, "Exit"}, {"Help Contents", "About Data Packer"}};
	private static final String[][] KEY_STROKES = {{"ctrl N", "ctrl O", null, "ctrl E"}, {"ctrl H", "ctrl A"}};
	private static final int HELP_FONT_SIZE = 12;
	private static final int ABOUT_FONT_SIZE = 16;
	private static final int PROGRESS_FONT_SIZE = 14;
	private static final Dimension MAIN_DIM = new Dimension(720, 400);
	private static final Dimension TABLE_DIM = new Dimension(500, 300);
	private static final Dimension BUTTON_SIZE = new Dimension(250, 20);
	private static final Dimension BUTTONS_DIM = new Dimension(610, 40);
	private static final Dimension HELP_DIM = new Dimension(830, 300);
	private static final Dimension ABOUT_DIM = new Dimension(360, 200);
	private static final Dimension PROGRESS_DIM = new Dimension(360, 100);
	
	private static Frame mainFrame;
	private static Table table;
	private static JButton[] buttons;
	private static File[] files;
	private static File pack;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mainFrame = new Frame(MAIN_DIM, false);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setJMenuBar(new MenuBar(HEADERS, ITEMS, KEY_STROKES, new MenuEvents()));
				table = new Table();
				JScrollPane scroll = new JScrollPane(table);
				scroll.setPreferredSize(TABLE_DIM);
				JPanel tablePanel = new JPanel();
				tablePanel.add(scroll);
				mainFrame.add(tablePanel);
				buttons = new JButton[NEW_BUTTONS.length];
				JPanel buttonsPanel = new JPanel();
				for (int i = 0; i < buttons.length; i++) {
					buttons[i] = new Button(BUTTON_SIZE, new ButtonEvents());
					buttonsPanel.add(buttons[i]);
				}
				buttonsPanel.setPreferredSize(BUTTONS_DIM);
				mainFrame.add(buttonsPanel, BorderLayout.SOUTH);
				mainFrame.setVisible(true);
			}
		});
	}
	
	public static void create() {
        FileChooser open = new FileChooser(System.getProperty(DIRECTORY));
        open.setMultiSelectionEnabled(true);
        if (open.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
        	System.setProperty(DIRECTORY, open.getSelectedFile().getParent());
    		files = open.getSelectedFiles();
    		String[] names = new String[files.length];
    		long[] sizes = new long[files.length];
        	for (int i = 0; i < files.length; i++) {
        		names[i] = new String(files[i].getName());
	    	    sizes[i] = files[i].length();
			}
        	clear();
        	for (int i = 0; i < names.length; i++) {
				table.setValueAt(names[i], i, 0);
				table.setValueAt(sizes[i], i, 1);
			}
        	table.revalidate();
        	enable(NEW_BUTTONS);
        } else {
        	clear();
        }
	}
	
	public static void open() {
        FileChooser open = new FileChooser(System.getProperty(DIRECTORY));
        open.setFileFilter(new PackFilter());
        if (open.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
        	try {
        		pack = open.getSelectedFile();
            	System.setProperty(DIRECTORY, pack.getParent());
	        	String[] names = DataInfo.getList(pack.getAbsolutePath());
	        	long[] sizes = DataInfo.getSizes(pack.getAbsolutePath());
	        	clear();
	        	for (int i = 0; i < names.length; i++) {
					table.setValueAt(names[i], i, 0);
					table.setValueAt(sizes[i], i, 1);
				}
	        	table.revalidate();
	        	enable(OPEN_BUTTONS);
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(mainFrame, OPEN_ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
        	}
        } else {
        	clear();
        }
	}
	
	public static void help() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Frame frame = new Frame(HELP_DIM, true);
				frame.setTitle(frame.getTitle() + HELP_HEADER);
				Text text = new Text(HELP_FONT_SIZE);
				try {
					text.setText(Tools.loadHTML(HELP_FILE));
				} catch (Exception e) {
					text.setText(HELP_ERROR);
				}
				frame.add(text);
				frame.setVisible(true);
			}
		});
	}
	
	public static void about() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Frame frame = new Frame(ABOUT_DIM, true);
				frame.setTitle(frame.getTitle() + ABOUT_HEADER);
				Text text = new Text(ABOUT_FONT_SIZE);
				try {
					text.setText(Tools.loadHTML(ABOUT_FILE));
				} catch (Exception e) {
					text.setText(HELP_ERROR);
				}
				frame.add(text);
				frame.setVisible(true);
			}
		});
	}
	
	public static void pack() {
		FileChooser save = new FileChooser(System.getProperty(DIRECTORY));
		save.setFileFilter(new PackFilter());
		if (save.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			if (!new File(save.getSelectedFile().getAbsolutePath() + EXTENSION).exists() || JOptionPane.showConfirmDialog(mainFrame, save.getSelectedFile().getName() + EXTENSION + OVERWRITE, SAVE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Frame frame = new Frame(PROGRESS_DIM, true);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						frame.setTitle(mainFrame.getTitle() + CREATING_HEADER);
						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						Text text = new Text(PROGRESS_FONT_SIZE);
						text.setText(CREATING_PROGRESS);
						frame.add(text);
						frame.setVisible(true);
					}
				});
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							mainFrame.setEnabled(false);
							String[] filenames = new String[table.getRowCount()];
							for (int i = 0; i < filenames.length; i++) {
								filenames[i] = (String)table.getValueAt(i, 0);
							}
							ArrayList<InputStream> list = new ArrayList<>();
							for (int i = 0; i < files.length; i++) {
								list.add(new FileInputStream(files[i]));
							}
							String packname = save.getSelectedFile().getAbsolutePath() + EXTENSION;
							DataWriter.writeFile(list, filenames, packname);
							frame.dispose();
							JOptionPane.showMessageDialog(mainFrame, READY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
							mainFrame.setEnabled(true);
							mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						} catch (Exception e) {
							frame.dispose();
							JOptionPane.showMessageDialog(mainFrame, NEW_ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
							mainFrame.setEnabled(true);
							mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						}
					}
				}).start();
			}
		}
	}
	
	public static void extract() {
		FileChooser save = new FileChooser(System.getProperty(DIRECTORY));
		save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (save.showSaveDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
			System.setProperty(DIRECTORY, save.getSelectedFile().getAbsolutePath());
			System.out.println(System.getProperty(DIRECTORY));
			Frame frame = new Frame(PROGRESS_DIM, true);
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					frame.setTitle(mainFrame.getTitle() + EXTRACTING_HEADER);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					Text text = new Text(PROGRESS_FONT_SIZE);
					text.setText(EXTRACTING_PROGRESS);
					frame.add(text);
					frame.setVisible(true);
				}
			});
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						mainFrame.setEnabled(false);
						String[] filenames = new String[table.getRowCount()];
						for (int i = 0; i < filenames.length; i++) {
							filenames[i] = (String)table.getValueAt(i, 0);
						}
						String packname = pack.getAbsolutePath();
						for (int i = 0; i < filenames.length; i++) {
							InputStream is = DataReader.readFile(filenames[i], packname);
							if (new File(filenames[i] + EXTENSION_TMP).exists()) {
								new File(filenames[i] + EXTENSION_TMP).renameTo(new File(System.getProperty(DIRECTORY) + "\\" + filenames[i]));
							} else {
								PrintStream ps = new PrintStream(System.getProperty(DIRECTORY) + "\\" + filenames[i]);
								byte file[] = new byte[is.available()];
								is.read(file);
								ps.write(file);
								ps.close();
							}
							is.close();
						}
						frame.dispose();
						JOptionPane.showMessageDialog(mainFrame, READY, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
						mainFrame.setEnabled(true);
						mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					} catch (Exception e) {
						frame.dispose();
						JOptionPane.showMessageDialog(mainFrame, NEW_ERROR, ERROR, JOptionPane.ERROR_MESSAGE);
						mainFrame.setEnabled(true);
						mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					}
				}
			}).start();
		}
	}
	
	public static void clear() {
    	table.clear();
    	table.revalidate();
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText(null);
			buttons[i].setEnabled(false);
		}
	}
	
	private static void enable(String[] names) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText(names[i]);
			buttons[i].setEnabled(true);
		}
	}
}
