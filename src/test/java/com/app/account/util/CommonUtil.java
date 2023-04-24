package com.app.account.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	/**
	 * Read json file and converts into object of given class type
	 * @param <T>
	 * @param fileName
	 * @param contentClass
	 * @return
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public static <T>  Object retrieveObject(String fileName, Class<T> contentClass)
			throws StreamReadException, DatabindException, IOException {
		File file = new File(CommonConstants.BASE_FILE_PATH + fileName);
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(file, contentClass);
		return object;

	}
	/**
	 * Read json file.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String readJSONFile(String filePath) throws IOException {
		File file = new File(filePath);
		String content = FileUtils.readFileToString(file, "UTF-8");
		return content;
	}
}
