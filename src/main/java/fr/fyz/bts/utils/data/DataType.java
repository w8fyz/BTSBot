package fr.fyz.bts.utils.data;

import java.lang.reflect.Type;
import java.util.Arrays;

import com.google.common.reflect.TypeToken;
@SuppressWarnings("serial")
public enum DataType {
	
	
	TEMP_PLAYER("pluralmodules", Object.class, new TypeToken<Object>() {}.getType());
	
	private String src;
	private Class<?> dataClass;
	private Type token;
	
	private DataType(String src, Class<?> dataClass, Type token) {
		this.src = src;
		this.token = token;
		this.dataClass = dataClass;
	}
	
	public Type getToken() {
		return token;
	}
	
	public Class<?> getDataClass() {
		return dataClass;
	}
	
	public String getSrc() {
		return src;
	}

	
	
	public static DataType getType(Class<?> c) {
		return Arrays.stream(values()).filter(e -> e.getDataClass().toString().equals(c.toString())).findAny().orElse(null);
	}

}
