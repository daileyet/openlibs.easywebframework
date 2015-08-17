package openthinks.easyweb.utils.export.bean;

import java.beans.Beans;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BeanUtils {

	@SuppressWarnings("unchecked")
	public static <T, E> T wrapper(E originalBean, Class<T> wrapperClass) {
		Class<E> originalClass = (Class<E>) originalBean.getClass();
		if (!originalClass.isAssignableFrom(wrapperClass)) {
			throw new RuntimeException(wrapperClass + " need extends from "
					+ originalClass);
		}
		T wrappered = null;
		try {
			wrappered = (T) Beans.instantiate(wrapperClass.getClassLoader(),
					wrapperClass.getName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Field[] fields = originalClass.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers()))
				continue;
			field.setAccessible(true);
			Object fieldValue;
			try {
				fieldValue = field.get(originalBean);
				field.set(wrappered, fieldValue);
			} catch (Exception e) {
				// throw new RuntimeException(e);
				continue;
			}
		}
		return wrappered;
	}
}
