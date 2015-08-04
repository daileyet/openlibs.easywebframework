package openthinks.easyweb.annotation.process;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import openthinks.easyweb.WebUtils;
import openthinks.easyweb.annotation.Mapping;
import openthinks.easyweb.annotation.process.objects.WebContainer;
import openthinks.easyweb.annotation.process.objects.WebController;
import openthinks.easyweb.annotation.process.objects.WebMethod;
import openthinks.easyweb.annotation.process.objects.WebUnit;
import openthinks.easyweb.context.WebConfigure;
import openthinks.easyweb.context.WebContexts;

/**
 * The easyweb processor for annotation configure approach 
 * @author dailey.yet@outlook.com
 *
 */
public class WebProcesser {

	public static final String WEB_ROOT = "EasyWeb_Container";
	public static final String WEB_CONFIGURE = "EasyWeb_Configure";
	public static final String WEB_CONTROLLER = "EasyWeb_Controller";
	public static final String WEB_METHOD = "EasyWeb_Method";
	public static final String CONTROLLER_FILE_SUFFIX = "Controller.class";
	public static final String PATH_SPLITER = File.separator;
	public static final String WEB_CLASS_DIR = "easyweb-class-dir";
	private final ServletContext servletContext;

	public WebProcesser(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
	}

	public void process() {
		AbstractProcesser<?> processer = new WebContainerProcesser();
		processer.process();
	}

	abstract class AbstractProcesser<T extends WebUnit> implements Processer<T> {
		private final Map<String, Object> properties = new HashMap<String, Object>();

		public void setPropertie(String key, Object value) {
			properties.put(key, value);
		}

		@SuppressWarnings({ "unchecked" })
		public <U extends Object> U getPropertie(String key) {
			return (U) properties.get(key);
		}

		@Override
		public abstract T process();

	}

	/**
	 * Web container processor 
	 * @author dailey.yet@outlook.com
	 *
	 */
	class WebContainerProcesser extends AbstractProcesser<WebContainer> {

		@Override
		public WebContainer process() {
			WebContainer container = WebContexts.get().getWebContainer();
			WebConfigure configure = WebContexts.get().getWebConfigure();

			WebControllerProcesser processer = new WebControllerProcesser();
			for (String pack : configure.getScanPackages()) {
				String path = getPackPath(pack);
				List<Class<?>> controllerClasses = filterControllerClass(path);
				List<Object> controllers = getControllerInstances(controllerClasses);

				for (Object controller : controllers) {
					processer.setPropertie(WEB_CONTROLLER, controller);
					WebController child = processer.process();
					container.add(child);
				}
			}
			container.buildMapping();

			return container;
		}

		// TODO
		private List<Object> getControllerInstances(List<Class<?>> controllerClasses) {
			List<Object> controllerInstance = new ArrayList<Object>();
			for (Class<?> clazz : controllerClasses) {
				try {
					controllerInstance.add(clazz.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return controllerInstance;
		}

		private List<Class<?>> filterControllerClass(String packPath) {
			File dir = new File(packPath);
			List<Class<?>> controllerClasss = new ArrayList<Class<?>>();
			dir.listFiles(new ControllerFileFilterVistor(controllerClasss));
			return controllerClasss;
		}

		private String getPackPath(String pack) {
			// TODO check pack is not null
			int all_index = pack.indexOf("*");
			String tempPack = pack;
			if (all_index > 0) {// com.xxx.*
				tempPack = tempPack.substring(0, all_index);
			}
			tempPack = tempPack.replace(".", PATH_SPLITER);
			return WebUtils.contactFilePath(getWebClassDir(), tempPack);
		}
	}

	/**
	 * Web controller processor
	 * @author dailey.yet@outlook.com
	 *
	 */
	class WebControllerProcesser extends AbstractProcesser<WebController> {
		@Override
		public WebController process() {
			Object instance = this.getPropertie(WEB_CONTROLLER);
			WebController controller = new WebController(instance);
			if (!controller.isValid())
				return controller;
			WebMethodProcesser processer = new WebMethodProcesser();

			for (Method method : instance.getClass().getDeclaredMethods()) {
				if (method.getAnnotation(Mapping.class) == null)
					continue;
				processer.setPropertie(WEB_METHOD, method);
				WebMethod child = processer.process();
				controller.add(child);
			}
			return controller;
		}
	}

	/**
	 * Web method processor
	 * @author dailey.yet@outlook.com
	 *
	 */
	class WebMethodProcesser extends AbstractProcesser<WebMethod> {

		@Override
		public WebMethod process() {
			Method method = this.getPropertie(WEB_METHOD);
			WebMethod webMethod = new WebMethod(method);
			return webMethod;
		}

	}

	class ControllerFileFilterVistor implements FileFilter {
		List<Class<?>> controllerClasss;

		public ControllerFileFilterVistor(List<Class<?>> controllerClasss) {
			super();
			this.controllerClasss = controllerClasss;
		}

		private String getClassName(String classFileWholePath, String classPackRootDir) {
			String cfwp = classFileWholePath.toUpperCase();
			String cprd = classPackRootDir.toUpperCase();
			//fix file path not unified
			cprd = WebUtils.contactFilePath(cprd, "");
			int index = cfwp.indexOf(cprd);
			// TODO check param1 contain param2
			String className = classFileWholePath.substring(index + cprd.length());
			return className.replace(PATH_SPLITER, ".").replace(".class", "");
		}

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				file.listFiles(this);
			} else {
				if (file.getName().toUpperCase().endsWith(CONTROLLER_FILE_SUFFIX.toUpperCase())) {
					String className = getClassName(file.getAbsolutePath(), getWebClassDir());
					try {
						controllerClasss.add(Class.forName(className));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return true;
				}
			}
			return false;
		}
	}

	protected String getWebClassDir() {
		String web_class_dir = (String) this.servletContext.getAttribute(WEB_CLASS_DIR);
		if (web_class_dir == null) {
			web_class_dir = this.servletContext.getInitParameter(WEB_CLASS_DIR);
			if (web_class_dir == null)
				web_class_dir = servletContext.getRealPath("") + PATH_SPLITER + "WEB-INF" + PATH_SPLITER + "classes"
						+ PATH_SPLITER;
		}
		return web_class_dir;
	}
}
