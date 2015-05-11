package mvcController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MvcUtil {

	@SuppressWarnings("rawtypes")
	private static boolean checkInterface(Class clazz, String interfaceName) {
		boolean found = false;
		Class[] interfaces = clazz.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if (interfaces[i].getName().equals(interfaceName)) {
				found = true;
				break;
			}
		}
		return found;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map buildHandlers(String propsFile) throws MvcException {
		Map handlers = new HashMap();
		Properties props = new Properties();
		FileInputStream proStr = null;
		try {
			proStr = new FileInputStream(propsFile);
			props.load(proStr);
			Enumeration enKeys = props.propertyNames();
			while (enKeys.hasMoreElements()) {
				String key = (String) enKeys.nextElement();
				System.out.println("key" + key);
				String clazz = props.getProperty(key);
				System.out.println(clazz);
				Class handClazz = Class.forName(clazz);
				// Check for interface
				if (checkInterface(handClazz,
						"mvcController.HttpRequestHandler")) {
					Object handler = handClazz.newInstance();
					handlers.put(key, handler);
					System.out.println(key + " " + handler);
				} else {
					throw new MvcException(
							"Class does not  implement com.caritor.mvc.HttpRequestHandlerinterface");
				}
			}
		} catch (FileNotFoundException e) {
			throw new MvcException("");
		} catch (IOException e) {
			throw new MvcException("");

		} catch (ClassNotFoundException e) {
			throw new MvcException("Mvc ClassNotFoundException " + e);
		} catch (InstantiationException e) {
			throw new MvcException("Mvc InstantiationException " + e);
		} catch (IllegalAccessException e) {
			throw new MvcException("Mvc IllegalAccessException" + e);
		} finally {
			try {
				proStr.close();
			} catch (IOException e) {
				throw new MvcException("Mvc IOException" + e);
			}
		}
		return handlers;
	}
}