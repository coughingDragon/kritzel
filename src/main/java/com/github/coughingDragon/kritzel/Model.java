package com.github.coughingDragon.kritzel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;

public class Model {
	
	private final StringProperty fileContent = new SimpleStringProperty("");
	
	private final BooleanProperty textEditorTabSelection = new SimpleBooleanProperty(true);
	
	private final BooleanProperty drawingTabSelection = new SimpleBooleanProperty(true);
	
	private final BooleanProperty listTabSelection = new SimpleBooleanProperty(true);
	
	private final ObjectProperty<GraphicsContext> graphicsContext = new SimpleObjectProperty<GraphicsContext>();
	
	private final StringProperty textInputField = new SimpleStringProperty("");
	
	private final BooleanProperty pencilSelected = new SimpleBooleanProperty();
	
	private final BooleanProperty rubberSelected = new SimpleBooleanProperty();
	
	
	public String getFileContent() {
		return fileContent.get();
	}
	
	public void setFileContent(String content) {
		fileContent.set(content);	
	}
	
	public StringProperty fileContentProperty() {
		return fileContent;
	}
	
	public boolean getTextEditorTabSelection() {
		return textEditorTabSelection.get();
	}
	
	public void setTextEditorTabSelection(boolean flag) {
		textEditorTabSelection.set(flag);
	}
	
	public BooleanProperty textEditorTabSelectionProperty() {
		return textEditorTabSelection;
	}
	
	public boolean getDrawingTabSelection() {
		return drawingTabSelection.get();
	}
	
	public void setDrawingTabSelection(boolean flag) {
		drawingTabSelection.set(flag);
	}
	
	public BooleanProperty drawingTabSelectionProperty() {
		return drawingTabSelection;
	}
	
	public boolean getListTabSelection() {
		return listTabSelection.get();
	}
	
	public void setListTabSelection(boolean flag) {
		listTabSelection.set(flag);
	}
	
	public BooleanProperty listTabSelectionProperty() {
		return listTabSelection;
	}
	
	public GraphicsContext getGraphicsContext() {
		return graphicsContext.get();
	}
	
	public void setGraphicsContext(GraphicsContext gc) {
		graphicsContext.set(gc);
	}
	
	public ObjectProperty<GraphicsContext> graphicsContextProperty() {
		return graphicsContext;
	}
	
	public String getTextInputField() {
		return textInputField.get();
	}
	
	public void setTextInputField(String text) {
		textInputField.set(text);
	}
	
	public StringProperty textInputFieldProperty() {
		return textInputField;
	}
	
	public boolean getPencilSelected() {
		return pencilSelected.get();
	}
	
	public void setPencilSelected(boolean selected) {
		pencilSelected.set(selected);
	}
	
	public BooleanProperty pencilSelectedProperty() {
		return pencilSelected;
	}
	
	public boolean getRubberSelected() {
		return rubberSelected.get();
	}
	
	public void setRubberSelected(boolean selected) {
		rubberSelected.set(selected);
	}
	
	public BooleanProperty rubberSelectedProperty() {
		return rubberSelected;
	}
		
}
