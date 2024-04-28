package com.github.coughingDragon.kritzel.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ListCreator extends TabContent {
	
	private final Region container;
	private final VBox checkBoxContainer = new VBox();
	private final StringProperty inputFieldProperty = new SimpleStringProperty();
	
	public ListCreator() {
		this.container = createListContainer();
		this.getChildren().add(container);
	}
	
	private Region createListContainer() {
		VBox results = new VBox();
		results.getChildren().add(createTextEntryField());
		results.getChildren().add(checkBoxContainer);
		return results;
	}
	
	private Node createTextEntryField() {
		HBox results = new HBox();
		TextField textField = new TextField();
		textField.textProperty().bindBidirectional(inputFieldProperty);
		Button submitButton = new Button();
		submitButton.setText("Hinzufügen");
		submitButton.setOnAction(evt -> {
			if (!inputFieldProperty.get().equals("")) {
				checkBoxContainer.getChildren().add(createCheckBox());
			}
		});
		textField.setOnKeyReleased(evt -> {
			if (evt.getCode() == KeyCode.ENTER && !inputFieldProperty.get().equals("")) {
				checkBoxContainer.getChildren().add(createCheckBox());
			}
		});
		results.getChildren().add(textField);
		results.getChildren().add(submitButton);
		results.getStyleClass().add("check-box");
		return results;
	}
	
	private Node createCheckBox() {
		CheckBox results = new CheckBox();
		results.setText(inputFieldProperty.get());
		return results;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
