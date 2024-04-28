package com.github.coughingDragon.kritzel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.function.Consumer;

import org.kordamp.ikonli.javafx.FontIcon;

import com.github.coughingDragon.kritzel.view.DrawingTool;
import com.github.coughingDragon.kritzel.view.ListCreator;
import com.github.coughingDragon.kritzel.view.TabContent;
import com.github.coughingDragon.kritzel.view.TextEditor;
import com.gluonhq.richtextarea.RichTextArea;
import com.gluonhq.richtextarea.model.Document;

public class ViewBuilder implements Builder<Region> {
	
	private Model model;
	private Runnable openAction;
	private Runnable saveAction;
	private Consumer<Tab> createTabAction;
	private final ObjectProperty<Tab> selectedTabProperty = new SimpleObjectProperty<>();
	
	public ViewBuilder(Model model, Runnable openAction, Runnable saveAction) {
		this.model = model;
		this.openAction = openAction;
		this.saveAction = saveAction;
	}
	
	@Override
	public Region build() {
		BorderPane root = new BorderPane();
		root.setCenter(createTabPane());
		root.setTop(createMenuBar());
		return root;
	}
	
	private Node createMenuBar() {
		MenuBar results = new MenuBar();
		results.getMenus().addAll(createFileMenu(), createEditMenu(), createHelpMenu());
		return results;
	}
	
	private Menu createFileMenu() {
		Menu results = new Menu("Datei");
		
		results.getItems().addAll(
				Widgets.makeMenuItem("Öffnen", openAction), 
				Widgets.makeMenuItem("Speichern", saveAction), 
				Widgets.makeMenuItem("Schließen", () -> getTabContent().clear()));
		return results;
		
	}
	
	private Menu createEditMenu() {
		Menu results = new Menu("Bearbeiten");
		results.getItems().addAll(
				Widgets.makeMenuItem("Rückgängig", () -> getTabContent().undo()), 
				Widgets.makeMenuItem("Löschen", () -> getTabContent().delete()),
				Widgets.makeMenuItem("Titel ändern", this::changeTabTitle));
		return results;
		
	}
	
	private Menu createHelpMenu() {
		Menu results = new Menu("Hilfe");
		results.getItems().add(Widgets.makeMenuItem("Änderungen", () -> {}));
		return results;
		
	}
	
	private Node createTabPane() {
		TabPane results = new TabPane();
		results.getTabs().addAll(configureTextEditorTab(new Tab()), configureDrawingTab(new Tab()), configureListTab(new Tab()), addNewTabButton()); 
		selectedTabProperty.bind(results.getSelectionModel().selectedItemProperty());
		selectedTabProperty.addListener( (ob, oldValue, newValue) -> {
			if (oldValue.getContent() != null &&  (oldValue.getContent().getClass().equals(TextEditor.class))) {
					((TextEditor) oldValue.getContent()).getTextProperty().unbindBidirectional(model.fileContentProperty());
					model.setFileContent("");
			}
			if (newValue.getContent() != null &&  (newValue.getContent().getClass().equals(TextEditor.class))) {
				model.fileContentProperty().bindBidirectional(((TextEditor) newValue.getContent()).getTextProperty());
			}
		});
		this.createTabAction = tab -> {
			results.getTabs().add(results.getTabs().size() - 1, tab);
			results.getSelectionModel().select(results.getTabs().size() - 2);
		};
		return results;
	}
	
	private Node createMenuButton() {
		MenuButton results = new MenuButton("Neuen Inhalt wählen");
		results.getItems().addAll(
				Widgets.makeMenuItem("Notizblock", () -> configureTextEditorTab(selectedTabProperty.get())),
				Widgets.makeMenuItem("Kritzelblock", () -> configureDrawingTab(selectedTabProperty.get())),
				Widgets.makeMenuItem("To-do-Liste", () -> configureListTab(selectedTabProperty.get()))
		);
		return results;
	}
	
	private Tab createNewTab() {
		Tab results = new Tab("Neuer Tab");
		results.setContent(createMenuButton());
		results.setStyle("-fx-align: center;");
		return results;
	}
	
	private Tab addNewTabButton() {
		Tab results = new Tab();
		results.setGraphic(new FontIcon("fa-plus"));
		results.setOnSelectionChanged(evt -> this.createTabAction.accept(createNewTab()));
		return results;
	}
	
	private Tab configureTextEditorTab(Tab tab) {
		TextEditor editor = new TextEditor();
		model.fileContentProperty().bindBidirectional(editor.getTextProperty());
		tab.setText("Notizblock");
		tab.setGraphic(new FontIcon("fa-pencil"));
		tab.setContent(editor);
		return tab;
	}
	
	private Tab configureDrawingTab(Tab tab) {
		tab.setText("Kritzelblock");
		tab.setContent(new DrawingTool());
		tab.setGraphic(new FontIcon("fa-photo"));
		return tab;
	}
	
	private Tab configureListTab(Tab tab) {
		tab.setText("To-do-Liste");
		tab.setContent(new ListCreator());
		tab.setGraphic(new FontIcon("fa-check-square-o"));
		return tab;
	}
	
	private void testTab() {
		Document doc = new Document();
		RichTextArea editor = new RichTextArea();
        editor.setDocument(doc);
        editor.setPromptText("Type something!");
        editor.setPadding(new Insets(20));
        BorderPane root = new BorderPane(editor);
        
	}
	
	private TabContent getTabContent() {
		return (TabContent) selectedTabProperty.get().getContent();
	}
	
	private void changeTabTitle() {
		TextInputDialog dialog = new TextInputDialog(selectedTabProperty.get().getText());
		dialog.setHeaderText("Eingabe benötigt");
		dialog.setContentText("Neuen Titel wählen: ");
		dialog.showAndWait().ifPresentOrElse(in -> selectedTabProperty.get().setText(in), () -> {}); 
	}

}
