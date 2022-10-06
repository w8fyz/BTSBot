package fr.fyz.bts.utils.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.Gson;

public class DataManager {

	private static String base = "/data/";
	
	public static boolean exist(DataType type, String getter) {
		File f = new File(base+type.getSrc() + "/" + getter);
		return f.exists();
	}

	public static File getFile(DataType type, String getter) {
		File f = new File(base+type.getSrc() + "/" + getter);
		if (f.exists()) {
			return f;
		}
		return null;
	}
	
	public static Object getObject(DataType type, String str) {
		return new Gson().fromJson(str, type.getToken());
	}

	public static File updateOrCreateFile(DataType type, String name, String value) {
		File f = new File(base+type.getSrc() + "/" + name);
		if (!f.exists()) {
			try {
				Files.createDirectories(f.getParentFile().toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter w = new FileWriter(f);
			w.append(value);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

}
