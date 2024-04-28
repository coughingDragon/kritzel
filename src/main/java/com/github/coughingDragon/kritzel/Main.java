package com.github.coughingDragon.kritzel;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	private URL resource;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(new Controller().getView(), Widgets.SCREEN_WIDTH, Widgets.SCREEN_HEIGHT - 80);
		scene.getStylesheets().add(getResource("css/styles.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Kritzel");
		primaryStage.getIcons().add(new Image(getResource("images/pencil.png").toExternalForm()));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	private void loadResource(String resourceName) {
		URL tmp = this.getClass().getResource(resourceName);
		if (tmp != null) {
			this.resource = tmp;
		} else {
			throw new BadResourceException("Resource not found at given location.");
		}
	}
	
	public URL getResource(String resourceName) {
		loadResource(resourceName);
		return this.resource;
	}
	
	class BadResourceException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public BadResourceException(String errorMessage) {
			super(errorMessage);
		}
	}
	
}
