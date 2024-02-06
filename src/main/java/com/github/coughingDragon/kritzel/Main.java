package com.github.coughingDragon.kritzel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContents());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Region createContents() {
		return new Label("These are the Bare-Bones of Kritzel!");
	}
}