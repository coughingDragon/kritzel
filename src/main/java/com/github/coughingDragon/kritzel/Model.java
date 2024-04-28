package com.github.coughingDragon.kritzel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	
	private final StringProperty fileContent = new SimpleStringProperty("");
	
	
	public String getFileContent() {
		return fileContent.get();
	}
	
	public void setFileContent(String content) {
		fileContent.set(content);	
	}
	
	public StringProperty fileContentProperty() {
		return fileContent;
	}
		
}
