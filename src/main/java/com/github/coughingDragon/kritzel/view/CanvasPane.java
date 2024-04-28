package com.github.coughingDragon.kritzel.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class CanvasPane extends Pane {

	private final Canvas canvas;
	
	public CanvasPane(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
		this.canvas = new Canvas(width, height);
		this.getChildren().add(canvas);
		
		canvas.widthProperty().bind(this.widthProperty());
		canvas.heightProperty().bind(this.heightProperty());
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
