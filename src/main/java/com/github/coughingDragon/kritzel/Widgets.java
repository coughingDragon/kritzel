package com.github.coughingDragon.kritzel;

import java.awt.Dimension;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class Widgets {
	
	static final Dimension SCREEN_SIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

	public static final double SCREEN_WIDTH = SCREEN_SIZE.getWidth();
	
	public static final double SCREEN_HEIGHT = SCREEN_SIZE.getHeight();
	
	private Widgets() {}
	
	static Menu createMenu(String name, MenuItem... items) {
		Menu results = new Menu();
		results.setText(name);
		results.getItems().addAll(items);
		return results;
	}
	
	static Tab createTab(String name, Node content) {
		Tab results = new Tab();
		results.setText(name);
		results.setContent(content);
		results.setClosable(true);
		return results;
	}
	
	static MenuItem makeMenuItem(String name, Runnable action) {
		MenuItem results = new MenuItem();
		results.setText(name);
		results.setOnAction(evt -> action.run());
		return results;
	}
	
	static Node makeActionButton(String name, Runnable action) {
		Button results = new Button();
		results.setText(name);
		results.setOnAction(evt -> action.run());
		return results;
	}
	
}
