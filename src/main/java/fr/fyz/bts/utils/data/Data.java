package fr.fyz.bts.utils.data;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Data {
	
	private Class<?> clazz;
	
	public Data(Class<?> clazz) {
		this.clazz = clazz;
	} 
	
	public void del(String getter) {
		DataType type = DataType.getType(clazz);
		if(DataManager.exist(type, getter)) {
			File f = DataManager.getFile(type, getter);
			if(f != null) {
				f.delete();
			}
		}
	}
	
	public void send(String name, String content) {
		DataType type = DataType.getType(clazz);
		DataManager.updateOrCreateFile(type, name, content);
	}
	

	@SuppressWarnings("deprecation")
	public Object get(String getter) {
		DataType type = DataType.getType(clazz);
		if(DataManager.exist(type, getter)) {
			File f = DataManager.getFile(type, getter);
			try {
				return DataManager.getObject(type, Files.toString(f, Charsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
