package com.github.coughingDragon.kritzel;

import java.util.concurrent.Callable;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class ScreenBuilder implements Builder<Region> {
	
	private Model model;
	private Callable<String> openAction;
	private Runnable saveAction;
	private final VBox checkBoxContainer = new VBox();
	private final TextArea textArea = new TextArea();

	
	public ScreenBuilder(Model model, Callable<String> openAction, Runnable saveAction) {
		this.model = model;
		this.openAction = openAction;
		this.saveAction = saveAction;
	}
	
	@Override
	public Region build() {
		BorderPane root = new BorderPane();
		root.setTop(createMenuBar());
		root.setCenter(createTabPane());
		root.setBottom(new HBox());
		return root;
	}
	
	private Node createMenuBar() {
		MenuBar results = new MenuBar();
		results.getMenus().add(Widgets.createMenu("Datei", new MenuItem[]{menuItemOpen(), menuItemSave(), menuItemClose()}));
		results.getMenus().add(Widgets.createMenu("Bearbeiten", new MenuItem[]{menuItemUndo(), menuItemDelete()}));
		results.getMenus().add(Widgets.createMenu("Hilfe", new MenuItem[]{menuItemNews()}));
		return results;
	}
	
	private MenuItem menuItemOpen() {
		MenuItem results = new MenuItem();
		results.setText("Öffnen");
		results.disableProperty().bind(model.textEditorTabSelectionProperty());
		results.setOnAction(evt -> {try {
			model.setFileContent(openAction.call());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		return results;
	}
	
	private MenuItem menuItemSave() {
		MenuItem results = Widgets.createMenuItem("Speichern");
		results.setText("Speichern");
		results.disableProperty().bind(model.textEditorTabSelectionProperty());
		results.setOnAction(evt -> saveAction.run());
		return results;
	}
	
	private MenuItem menuItemClose() {
		MenuItem results = Widgets.createMenuItem("Schließen");
		results.disableProperty().bind(model.textEditorTabSelectionProperty());
		results.setOnAction(evt -> {textArea.clear();});
		return results;
	}
	
	private MenuItem menuItemUndo() {
		MenuItem results = Widgets.createMenuItem("Rückgängig");
		results.disableProperty().bind(model.textEditorTabSelectionProperty());
		results.setOnAction(evt -> {textArea.undo();});
		return results;
	}
	
	private MenuItem menuItemDelete() {
		MenuItem results = Widgets.createMenuItem("Löschen");
		results.disableProperty().bind(model.drawingTabSelectionProperty());
		results.setOnAction(evt -> {
			model.getGraphicsContext().clearRect(0, 0, Widgets.SCREEN_WIDTH, Widgets.SCREEN_HEIGHT);
		});
		return results;
	}
	
	private MenuItem menuItemNews() {
		MenuItem results = Widgets.createMenuItem("Änderungen");
		results.setOnAction(evt -> {
			new Main().getHostServices().showDocument("https://github.com/coughingDragon/kritzel/blob/master/src/main/resources/changelog.txt");
		});
		return results;
	}
	
	private Node createTabPane() {
		TabPane results = new TabPane();
		results.getTabs().add(textEditorTab());
		results.getTabs().add(drawingTab());
		results.getTabs().add(listTab()); 
		return results;
	}
	

	private Tab textEditorTab() {
		Tab results = Widgets.createTab("Notizblock", createTextEditorContainer());
		results.setOnSelectionChanged(evt -> {
			model.setTextEditorTabSelection(!model.getTextEditorTabSelection());
		});
		return results;
	}
	
	private Tab drawingTab() {
		Tab results = Widgets.createTab("Kritzelblock", createDrawingContainer());
		results.setOnSelectionChanged(evt -> {
			model.setDrawingTabSelection(!model.getDrawingTabSelection());
		});
		return results;
	}
	
	private Tab listTab() {
		Tab results = Widgets.createTab("To-do-Liste", createListContainer());
		return results;
	}
	
	private Node createTextEditorContainer() {
		VBox results = new VBox();
		results.getChildren().add(createEditorToolBar());
		results.getChildren().add(createTextArea(model.fileContentProperty()));
		VBox.setVgrow(results.getChildren().get(1), Priority.ALWAYS);
		return results;
	}
	
	private Node createEditorToolBar() {
		HBox results = new HBox();
		results.getChildren().add(createFontSizeChooser(new String[]{"12","14","18","22","28","34","72"}));
		results.getStyleClass().add("tool-bar");
		return results;
	}
	
	private Node createFontSizeChooser(String... items) {
		ComboBox<String> results = new ComboBox<>();
		results.getItems().addAll(FXCollections.observableArrayList(items));
		results.setValue("12");
		results.setEditable(true);
		results.setOnAction(evt -> {
			if (results.getValue().matches("-?([1-9][0-9]*)?") && !results.getValue().isEmpty()) {
				if (Integer.parseInt(results.getValue()) >= 8 && Integer.parseInt(results.getValue()) <= 100) {
					textArea.setStyle("-fx-font-size: " + results.getValue() + "px;");
				}
			}
		});
		return results;
	}
	
	private Node createTextArea(StringProperty boundProperty) {
		textArea.textProperty().bindBidirectional(boundProperty);
		textArea.setStyle("-fx-font-size: 12px;");
		return textArea;
	}
	
	private Node createDrawingContainer() {
		VBox results = new VBox();
		results.getChildren().add(createDrawingToolBar());
		results.getChildren().add(createCanvasContainer());
		VBox.setVgrow(results.getChildren().get(1), Priority.ALWAYS);
		return results;
	}
	
	private Node createDrawingToolBar() {
		HBox results = new HBox();
		results.getChildren().add(createToggleButtons());
		results.getChildren().add(createColorPicker());
		results.getChildren().add(createLineWidthChooser(new Integer[]{1,2,3,4,5,6,7,8,9,10}));
		results.getStyleClass().add("tool-bar");
		return results;
	}
	
	private Node createToggleButtons() {
		HBox results = new HBox();
		ToggleGroup group = new ToggleGroup();
		ToggleButton pencil = new ToggleButton("Stift");
		ToggleButton rubber = new ToggleButton("Radiergummi");
		pencil.selectedProperty().bindBidirectional(model.pencilSelectedProperty());
		rubber.selectedProperty().bindBidirectional(model.rubberSelectedProperty());
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
		results.setOnAction(evt -> {
			model.getGraphicsContext().setStroke(results.getValue());
		});
		return results;
	}
	
	private Node createLineWidthChooser(Integer... items) {
		ComboBox<Integer> results = new ComboBox<>();
		results.getItems().addAll(FXCollections.observableArrayList(items));
		results.setValue(1);
		results.setOnAction(evt -> 
			model.getGraphicsContext().setLineWidth(results.getValue())
		);
		return results;
	}
	
	private Region createCanvasContainer() {
		StackPane results = new StackPane();
		results.getChildren().add(createCanvas());
		return results;
	}
	
	private Canvas createCanvas() {
		Canvas results = new Canvas(Widgets.SCREEN_WIDTH, Widgets.SCREEN_HEIGHT);
		GraphicsContext gc = configureGraphicsContext(results.getGraphicsContext2D());
		results.getStyleClass().add("canvas");
		results.setOnMousePressed(evt -> {
			if (model.getPencilSelected()) {
				gc.beginPath(); gc.lineTo(evt.getX(), evt.getY());
			} else if (model.getRubberSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		results.setOnMouseDragged(evt -> {
			if (model.getPencilSelected()) {
				gc.lineTo(evt.getX(), evt.getY()); gc.stroke();
			} else if (model.getRubberSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		results.setOnMouseDragReleased(evt -> {
			if (model.getPencilSelected()) {
				gc.lineTo(evt.getX(), evt.getY()); gc.stroke(); gc.closePath();
			} else if (model.getRubberSelected()) {
                double lineWidth = gc.getLineWidth();
                gc.clearRect(evt.getX() - lineWidth / 2, evt.getY() - lineWidth / 2, lineWidth, lineWidth);
			}
		});
		return results;
	}
	
	private GraphicsContext configureGraphicsContext(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		model.setGraphicsContext(gc);
		return gc;
	}
	
	private Node createListContainer() {
		VBox results = new VBox();
		results.getChildren().add(createTextEntryField());
		results.getChildren().add(checkBoxContainer);
		return results;
	}
	
	private Node createTextEntryField() {
		HBox results = new HBox();
		TextField textField = new TextField();
		textField.textProperty().bindBidirectional(model.textInputFieldProperty());
		Button submitButton = new Button();
		submitButton.setText("Hinzufügen");
		submitButton.setOnAction(evt -> {
			if (!model.getTextInputField().equals("")) {
				checkBoxContainer.getChildren().add(createCheckBox());
			}
		});
		textField.setOnKeyReleased(evt -> {
			if (evt.getCode() == KeyCode.ENTER && !model.getTextInputField().equals("")) {
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
		results.setText(model.getTextInputField());
		return results;
	}
	

}
