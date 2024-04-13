package com.github.coughingDragon.kritzel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.stage.FileChooser;

public class Helper {

	public static FileChooser createFileChooser() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Text Dateien", "*.txt"),
			     new FileChooser.ExtensionFilter("Word Dateien", "*.docx"));
		return fc;
	}
	
	public static String openFile(File file, String extension) {
		String data = "";
		if (file == null) return data;
		try {
			FileInputStream fileStream = new FileInputStream(file);
			switch(extension) {
				case "*.txt" -> data = readTextFile(fileStream);
				case "*.docx" -> data = readDocXFile(fileStream);
				default -> data = "";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String readTextFile(FileInputStream fis) {
		StringBuilder builder = new StringBuilder();
		try {
			Reader r = new InputStreamReader(fis, "UTF-8");
			int ch = r.read();
			while (ch >= 0) {
				builder.append((char)ch);
				ch = r.read();
			}
			r.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static String readDocXFile(FileInputStream fis) {
		StringBuilder builder = new StringBuilder();
		try {
			XWPFDocument document = new XWPFDocument(fis);
			List<XWPFParagraph> data = document.getParagraphs();
			
			for (XWPFParagraph p : data) {
				builder.append(p.getText() + "\n");
			}
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	
	public static void saveTextToFile(String text, File file, String extension) {
		if (file == null) return;
		try {
			FileOutputStream fileStream = new FileOutputStream(file);
			switch(extension) {
				case "*.txt" -> writeTextFile(fileStream, text);
				case "*.docx" -> writeDocXFile(fileStream, text);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void writeTextFile(FileOutputStream fos, String text) {
		try {
			Writer w = new OutputStreamWriter(fos, "UTF-8");
			char ch[] = text.toCharArray();
			
			for (int i = 0; i < text.length(); i++) {
				w.write(ch[i]);
			}
			w.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeDocXFile(FileOutputStream fos, String text) {
		try {
			XWPFDocument document = new XWPFDocument();
			String[] data = text.split("\n");
			
			for (String str : data) {
				XWPFParagraph p = document.createParagraph();
				XWPFRun r = p.createRun();
				r.setText(str);
			}
			document.write(fos);
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
