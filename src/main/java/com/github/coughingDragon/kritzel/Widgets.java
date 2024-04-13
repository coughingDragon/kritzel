package com.github.coughingDragon.kritzel;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class Widgets {

	static final int SCREEN_WIDTH = 1920;
	
	static final int SCREEN_HEIGHT = 1080;
	
	static final int SCREEN_SIZE = SCREEN_WIDTH * SCREEN_HEIGHT;
	
	static Menu createMenu(String name, MenuItem... items) {
		Menu results = new Menu();
		results.getStyleClass().add("menu-item");
		results.setText(name);
		results.getItems().addAll(items);
		return results;
	}
	
	static MenuItem createMenuItem(String name) {
		MenuItem results = new MenuItem();
		results.setText(name);
		return results;
	}
	
	static Tab createTab(String name, Node content) {
		Tab results = new Tab();
		results.setText(name);
		results.setClosable(false);
		results.setContent(content);
		return results;
	}
	
}
