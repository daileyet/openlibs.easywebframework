package com.openthinks.easyweb.annotation.process.filter;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.openthinks.easyweb.WebUtils;

/**
 * Easyweb component class file base visitor
 * @author dailey.yet@outlook.com
 *
 */
public abstract class FileFilterVisitor implements FileFilter {
	protected List<Class<?>> filterClasss;

	public FileFilterVisitor(List<Class<?>> filterClasss) {
		super();
		this.filterClasss = filterClasss;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			file.listFiles(this);
		} else {
			if (acceptClassName(file)) {
				String className = WebUtils.getClassName(file.getAbsolutePath(), WebUtils.getWebClassDir());
				try {
					Class<?> clazz = Class.forName(className);
					if (acceptClassType(clazz)) {
						filterClasss.add(clazz);
						doAddition(clazz);
					}
				} catch (ClassNotFoundException e) {

				}
				return true;
			}
		}
		return false;
	}

	/**
	 * do additional work after all accept
	 * @param clazz Class
	 */
	protected void doAddition(Class<?> clazz) {

	}

	/**
	 * accept which type of Class, check the Class
	 * @param clazz Class
	 * @return boolean
	 */
	public abstract boolean acceptClassType(Class<?> clazz);

	/**
	 * accept which type of Class file name, check the class file
	 * @param file File
	 * @return boolean
	 */
	public abstract boolean acceptClassName(File file);

}