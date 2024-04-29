module com.github.coughingDragon.kritzel {
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires org.apache.poi.ooxml;
	requires java.desktop;
	requires com.gluonhq.richtextarea;
	requires org.apache.commons.io;
	requires org.kordamp.ikonli.javafx;
	requires org.apache.logging.log4j;
	
	exports com.github.coughingDragon.kritzel;
}