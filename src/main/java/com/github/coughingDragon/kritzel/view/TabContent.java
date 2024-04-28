package com.github.coughingDragon.kritzel.view;

import javafx.scene.layout.Region;

public abstract class TabContent extends Region {
	public abstract void clear();
	public abstract void undo();
	public abstract void delete();
}
