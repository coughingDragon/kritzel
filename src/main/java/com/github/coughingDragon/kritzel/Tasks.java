package com.github.coughingDragon.kritzel;

import java.util.concurrent.Callable;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Tasks {
	
	static class OpenFile implements Callable<String> {
		
		private final Stage stage;
		
		public OpenFile(Stage stage) {
			this.stage = stage;
		}

		@Override
		public String call() throws Exception {
			FileChooser fc = Helper.createFileChooser();
			return Helper.openFile(fc.showOpenDialog(stage), 
					fc.getSelectedExtensionFilter().getExtensions().get(0));
		}
		
	}
	
	static class SaveFile implements Runnable {
		
		private final Stage stage;
		private final Model model;
		
		public SaveFile(Stage stage, Model model) {
			this.stage = stage;
			this.model = model;
		}

		@Override
		public void run() {
			FileChooser fc = Helper.createFileChooser();
			Helper.saveTextToFile(model.getFileContent(), fc.showSaveDialog(stage), 
					fc.getSelectedExtensionFilter().getExtensions().get(0));
		}
		
	}
	

}
