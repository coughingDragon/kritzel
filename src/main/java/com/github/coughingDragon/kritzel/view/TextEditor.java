package com.github.coughingDragon.kritzel.view;

import com.github.coughingDragon.kritzel.Widgets;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TextEditor extends TabContent {
	
	private final Region container;
	private StringProperty textProperty;
	private final TextArea textArea = new TextArea();
	
	public TextEditor() {
		this.container = createTextEditorContainer();
		this.getChildren().add(container);
	}
	
	
	private Region createTextEditorContainer() {
		VBox results = new VBox();
		results.getChildren().add(createEditorToolBar());
		results.getChildren().add(createTextArea());
		return results;
	}
	
	private Node createEditorToolBar() {
		HBox results = new HBox();
		results.getChildren().add(createFontSizeChooser("12","14","18","22","28","34","72"));
		results.getStyleClass().add("tool-bar");
		return results;
	}
	
	private Node createFontSizeChooser(String... items) {
		ComboBox<String> results = new ComboBox<>();
		results.getItems().addAll(FXCollections.observableArrayList(items));
		results.setValue("12");
		results.setEditable(true);
		results.setOnAction(evt -> {
			if (results.getValue().matches("-?([1-9]\\d*)?") && !results.getValue().isEmpty() &&  
					(Integer.parseInt(results.getValue()) >= 8 && Integer.parseInt(results.getValue()) <= 100)) {
					textArea.setStyle("-fx-font-size: " + results.getValue() + "px;");
				
			}
		});
		return results;
	}
	
	private Node createTextArea() {
		this.textProperty = textArea.textProperty();
		textArea.setStyle("-fx-font-size: 12px;");
		textArea.setPrefWidth(Widgets.SCREEN_WIDTH);
		textArea.setPrefHeight(Widgets.SCREEN_HEIGHT - 200);
		return textArea;
	}
	
	public StringProperty getTextProperty() {
		return this.textProperty;
	}
	
	@Override
	public void clear() {
		textArea.clear();
	}
	
	@Override
	public void undo() {
		textArea.undo();
	}


	@Override
	public void delete() {
		System.out.println("Call from TextEditor::delete");
		// TODO Auto-generated method stub
		
	}
	
}
