package com.github.coughingDragon.kritzel;

import java.util.Objects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model model = new Model();
		Region sceneRoot = new ScreenBuilder(model, 
				new Tasks.OpenFile(primaryStage),
				new Tasks.SaveFile(primaryStage, model)
		).build();
		Scene scene = new Scene(sceneRoot, 1800, 1000);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(Main.class.getResource("/css/styles.css").toExternalForm());
		primaryStage.setTitle("Kritzel");
		setIcons(primaryStage);
		primaryStage.show();
	}
	
	private void setIcons(Stage stage) {
		stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/pencil.png"))));
	}
	
}
