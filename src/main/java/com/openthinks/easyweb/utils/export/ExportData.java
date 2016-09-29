package com.openthinks.easyweb.utils.export;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import com.openthinks.easyweb.utils.export.bean.BeanUtils;

public abstract class ExportData {
	public abstract <T> T getBeanData();

	/**
	 * return those not in bean data, stored by key-value
	 * 
	 * @return
	 */
	public abstract Map<String, Object> getReferenceData();

	/**
	 * make a default instance of {@link ExportData} use a normal bean object
	 * 
	 * @param bean
	 * @return {@link ExportData}
	 */
	public static <T> ExportData create(T bean) {
		DefaultExportData data = new DefaultExportData();
		data.bean = bean;
		try {
			data.load();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * make a default instance of {@link ExportData} use a wrapper bean object,
	 * which convert from a normal bean object to its subclass type
	 * 
	 * @param bean
	 *            T
	 * @param wapperClass
	 *            Class<E> Type E is subclass of type T
	 * @return {@link ExportData}
	 */
	public static <T, E> ExportData createWrapper(T bean, Class<E> wapperClass) {
		E warpperBean = BeanUtils.wrapper(bean, wapperClass);
		return create(warpperBean);
	}

	private static class DefaultExportData extends ExportData {

		private Object bean = new Object();
		private final Map<String, Object> reference = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getBeanData() {
			return ((T) bean);
		}

		@Override
		public Map<String, Object> getReferenceData() {
			return reference;
		}

		private void load() throws IntrospectionException {
			BeanInfo beanInfo = Introspector.getBeanInfo(getBeanData()
					.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String key = pd.getName();

				Object value = null;
				try {
					value = pd.getReadMethod().invoke(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				value = (value == null ? "" : value);
				// System.out.println(key + "=" + value);
				reference.put(key, value);
			}
		}
	}
}
