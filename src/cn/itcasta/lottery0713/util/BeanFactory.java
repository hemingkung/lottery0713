package cn.itcasta.lottery0713.util;

import java.io.IOException;
import java.util.Properties;

public class BeanFactory {
	protected static Properties properties;
	static {
		properties = new Properties();
		try {
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}

	public static <T> T getImpl(Class<T> clazz) {
		// clazz UserService.class

		String simpleName = clazz.getSimpleName();

		String property = properties.getProperty(simpleName);
		T t;
		try {
			t = (T) Class.forName(property).newInstance();
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
