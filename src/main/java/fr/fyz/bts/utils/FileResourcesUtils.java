package fr.fyz.bts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileResourcesUtils {

	public FileResourcesUtils() {
	}

	public InputStream getFileFromResourceAsStream(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}

	}
	
	public static String getValue(InputStream is) {

		String result = "";

		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {

			String line;
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

}
