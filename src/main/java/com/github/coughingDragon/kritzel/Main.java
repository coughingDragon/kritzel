package com.github.coughingDragon.kritzel;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Logger logger = LogManager.getLogger(Main.class);
	private static URL resource;
	
	
	public static void main(String[] args) {
		logger.info("Program start");
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
	
	private static void loadResource(String resourceName) {
		resource = Main.class.getResource(resourceName);
		if (resource != null) {
			logger.info("Resource {} succesfully loaded @{}", resourceName, resource);
		} else {
			BadResourceException exception = new BadResourceException("Resource not found at given location");
			logger.error("Resource {} failed loading with error @{} ", resourceName, exception );
			throw exception;
		}
	}
	
	public static URL getResource(String resourceName) {
		loadResource(resourceName);
		return resource;
	}
	
	static class BadResourceException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public BadResourceException(String errorMessage) {
			super(errorMessage);
		}
	}
	
}
