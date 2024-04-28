package com.github.coughingDragon.kritzel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javafx.stage.FileChooser;

public class Helper {
	
	private static final String PREFIX = "*.";
	private static final String TXT_EXTENSION = "txt";
	private static final String DOCX_EXTENSION = "docx";
	
	private static final FileChooser chooser = getFileChooser();
	
	
	private Helper() {}
	
	private static FileChooser getFileChooser() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
			     new FileChooser.ExtensionFilter("Text Dateien", PREFIX + TXT_EXTENSION),
			     new FileChooser.ExtensionFilter("Word Dateien", PREFIX + DOCX_EXTENSION));
		return fc;
	}
	
	private static String getFileExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}
	
	static String openFile() {
		File file = chooser.showOpenDialog(null);
		String data = "";
		if (file == null) return null;
		try {
			FileInputStream fileStream = new FileInputStream(file);
			switch(getFileExtension(file.getName())) {
				case TXT_EXTENSION -> data = readTextFile(fileStream);
				case DOCX_EXTENSION -> data = readDocXFile(fileStream);
				default -> data = null;
			}
			chooser.setInitialFileName(file.getName());
			chooser.setInitialDirectory(file.getParentFile());
			fileStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	private static String readTextFile(FileInputStream fis) {
		StringBuilder builder = new StringBuilder();
		try {
			Reader r = new InputStreamReader(fis, StandardCharsets.UTF_8);
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
	
	private static String readDocXFile(FileInputStream fis) {
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
	
	
	public static void saveTextToFile(String text) {
		File file = chooser.showSaveDialog(null);
		if (file == null) return;
		try {
			FileOutputStream fileStream = new FileOutputStream(file);
			switch(getFileExtension(file.getName())) {
				case TXT_EXTENSION -> writeTextFile(fileStream, text);
				case DOCX_EXTENSION -> writeDocXFile(fileStream, text);
				default -> {}
			}
			fileStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void writeTextFile(FileOutputStream fos, String text) {
		try {
			Writer w = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			char[] ch = text.toCharArray();
			
			for (int i = 0; i < text.length(); i++) {
				w.write(ch[i]);
			}
			w.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeDocXFile(FileOutputStream fos, String text) {
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
