package com.github.coughingDragon.kritzel;

class Interactor {

	Model model;
	
	Interactor(Model model) {
		this.model = model;
		
	}
	
	public void openDataFromFile() {
		String data = Helper.openFile();
		if (data != null) model.setFileContent(data);
	}
	
	public void saveDataToFile() {
		Helper.saveTextToFile(model.getFileContent());
	}
	
}
