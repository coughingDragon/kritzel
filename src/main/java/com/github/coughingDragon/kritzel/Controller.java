package com.github.coughingDragon.kritzel;

import javafx.scene.layout.Region;

public class Controller {
	
	private final ViewBuilder viewBuilder;
	
	public Controller() {
		Model model = new Model();
		Interactor interactor = new Interactor(model);
		this.viewBuilder = new ViewBuilder(model, 
				interactor::openDataFromFile, 
				interactor::saveDataToFile);
	}
	
	public Region getView() {
		return viewBuilder.build();
	}
}
