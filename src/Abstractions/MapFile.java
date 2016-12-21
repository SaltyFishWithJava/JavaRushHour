package Abstractions;

import java.io.*;

public class MapFile {

	private File file;
	public String name;
	
	public MapFile(File f) {
		file = f;
		String[] nameParts = f.getName().split("-");
		name = nameParts[0] + " " +
				Integer.parseInt(nameParts[1].substring(0,2));
	}
	
	public File file() {
		return file;
	}
	
	public String toString() {
		return name;
	}
	
	public String getMapName(){
		return file.getName();
	}
}
