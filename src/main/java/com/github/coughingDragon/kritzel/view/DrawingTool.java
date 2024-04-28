package com.github.coughingDragon.kritzel.view;

import com.github.coughingDragon.kritzel.Widgets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DrawingTool extends TabContent {
	
	private GraphicsContext gc;
	private final Region container;
	private final BooleanProperty pencilSelectedProperty = new SimpleBooleanProperty();
	private final BooleanProperty rubberSelectedProperty = new SimpleBooleanProperty();
	private final StringProperty canvasCoordinatesProperty = new SimpleStringProperty();
	
	public DrawingTool() {
		this.container = createDrawingContainer();
		this.getChildren().add(container);
	}
	
	private Region createDrawingContainer() {
		BorderPane results = new BorderPane();
		results.setTop(createDrawingToolBar());
		results.setCenter(createCanvas());
		results.setBottom(createBottomBar());
		return results;
	}
	
	private Node createDrawingToolBar() {
		HBox results = new HBox();
		results.getChildren().add(createToggleButtons());
		results.getChildren().add(createColorPicker());
		results.getChildren().add(createLineWidthChooser(1,2,3,4,5,6,7,8,9,10));
		results.getStyleClass().add("tool-bar");
		return results;
	}
	
	private Node createToggleButtons() {
		HBox results = new HBox();
		ToggleGroup group = new ToggleGroup();
		ToggleButton pencil = new ToggleButton("Stift");
		ToggleButton rubber = new ToggleButton("Radiergummi");
		pencil.selectedProperty().bindBidirectional(pencilSelectedProperty);
		rubber.selectedProperty().bindBidirectional(rubberSelectedProperty);
		group.getToggles().add(pencil);
		group.getToggles().add(rubber);
		group.selectToggle(pencil);
		results.getChildren().add(pencil);
		results.getChildren().add(rubber);
		return results;
		
	}
	
	private Node createColorPicker() {
		ColorPicker results = new ColorPicker();
		results.setValue(Color.BLACK);
		results.setOnAction(evt -> gc.setStroke(results.getValue()));
		return results;
	}
	
	private Node createLineWidthChooser(Integer... items) {
		ComboBox<Integer> results = new ComboBox<>();
		results.getItems().addAll(FXCollections.observableArrayList(items));
		results.setValue(1);
		results.setOnAction(evt -> gc.setLineWidth(results.getValue()));
		return results;
	}
	
	private CanvasPane createCanvas() {
		CanvasPane results = new CanvasPane(Widgets.SCREEN_WIDTH, Widgets.SCREEN_HEIGHT - 200);
		Canvas canvas = results.getCanvas();
		gc = canvas.getGraphicsContext2D();
		configureGraphicsContext();
		canvas.getStyleClass().add("canvas");
		canvas.setOnMousePressed(evt -> {
			if (pencilSelectedProperty.get()) {
				gc.beginPath(); gc.lineTo(evt.getX(), evt.getY());
			} else if (rubberSelectedProperty.get()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		canvas.setOnMouseDragged(evt -> {
			if (pencilSelectedProperty.get()) {
				gc.lineTo(evt.getX(), evt.getY()); gc.stroke();
			} else if (rubberSelectedProperty.get()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		canvas.setOnMouseDragReleased(evt -> {
			if (pencilSelectedProperty.get()) {
				gc.lineTo(evt.getX(), evt.getY()); gc.stroke(); gc.closePath();
			} else if (rubberSelectedProperty.get()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		canvas.setOnMouseMoved(evt -> canvasCoordinatesProperty.set(evt.getX() + " : " + evt.getY()));
		return results;
	}
	
	private Node createBottomBar() {
		HBox results = new HBox();
		results.setMaxHeight(20);
		Label coordinateLabel = new Label();
		coordinateLabel.textProperty().bind(canvasCoordinatesProperty);
		results.getChildren().add(coordinateLabel);
		results.getStyleClass().add("bottom-bar");
		return results;
	}
	
	private void configureGraphicsContext() {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
   // TODO document why this method is empty
 }

	@Override
	public void delete() {
		gc.clearRect(0, 0, Widgets.SCREEN_WIDTH - 40, Widgets.SCREEN_HEIGHT - 40);
	}
	
	
	

}
