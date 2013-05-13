package Reika.DragonAPI;

import java.io.*;

public final class ReikaCSVReader {
	
	private ReikaCSVReader() {throw new RuntimeException("The class "+this.getClass()+" cannot be instantiated!");}
	
	private final BufferedReader bf;
	
	public ReikaCSVReader(Class root, String path) {
		InputStream input = root.getResourceAsStream(path);
		FileReader fr = null;
		if (input == null) {
			bf = null;
			return;
		}
		try {
			fr = new FileReader(path);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			bf = null;
			return;
		}
		bf = new BufferedReader(fr);
	}
	
}