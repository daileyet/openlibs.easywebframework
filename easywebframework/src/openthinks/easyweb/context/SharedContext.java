package openthinks.easyweb.context;

import java.lang.reflect.Constructor;

import openthinks.libs.utilities.Checker;
import openthinks.libs.utilities.CommonUtilities;
import openthinks.libs.utilities.logger.ProcessLogger;

/**
 * Shared web context, used {@link ObjectPool} as object shared pool
 * @author dailey.yet@outlook.com
 *
 */
public abstract class SharedContext implements WebContext {

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	private final ObjectPool objectPool = new ObjectPool();

	// ////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * lookup a object by its type, if not find firstly, try to instance by
	 * instanceType and constructor parameters args
	 * 
	 * @param <T> lookup object type
	 * @param searchType
	 *            Class<T> lookup key type
	 * @param args
	 *            Object[] instance constructor parameters
	 * @return T
	 */
	public <T> T lookup(Class<T> type, Object... args) {
		return lookup(type, InstanceWrapper.build(type), args);
	}

	void cleanUp() {
		objectPool.cleanUp();
	}

	/**
	 * lookup a object by its type, if not find firstly, try to instance by
	 * instanceType and constructor parameters args
	 * 
	 * @param <T> lookup object type
	 * @param searchType
	 *            Class<T> lookup key type
	 * @param instanceType
	 *            Class<E> instance type when not lookup the key
	 * @param args
	 *            Object[] instance constructor parameters
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T, E extends T> T lookup(final Class<T> searchType, InstanceWrapper<E> instancewrapper, Object... args) {
		T object = null;
		if (searchType != null) {
			object = objectPool.get(searchType);
			if (object == null) {
				if (instancewrapper == null)
					instancewrapper = (InstanceWrapper<E>) InstanceWrapper.build(searchType);
				Constructor<E>[] constructors = (Constructor<E>[]) instancewrapper.instanceType.getConstructors();
				for (Constructor<E> c : constructors) {
					try {
						Class<?>[] paramTypes = c.getParameterTypes();
						if (instancewrapper.isMember) {
							if (paramTypes.length == 1) {
								if (args == null)
									args = new Object[0];
								Object[] tmp = new Object[args.length + 1];
								tmp[0] = instancewrapper.owner;
								System.arraycopy(args, 0, tmp, 1, args.length);
								args = tmp;
							}
						}
						Checker.require(paramTypes).sameLengthWith(args);
						Checker.require(paramTypes).sameTypeWith(args);
						c.setAccessible(true);
						object = c.newInstance(args);
						register(searchType, object);
						break;
					} catch (Exception e) {
						ProcessLogger.warn(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
						continue;
					}
				}
			}
		}
		return object;
	}

	protected static class InstanceWrapper<E> {
		Class<E> instanceType;
		boolean isMember;
		Object owner;

		/**
		 * 
		 * @param instanceType
		 *            Class<E> the class type of instancing object
		 * @param owner
		 *            Object the instance object class type define in owner, it
		 *            means the instance class is a member class.
		 */
		public InstanceWrapper(Class<E> instanceType, Object owner) {
			super();
			this.instanceType = instanceType;
			if (this.instanceType != null)
				this.isMember = this.instanceType.isMemberClass();
			this.owner = owner;
		}

		public static <T> InstanceWrapper<T> build(Class<T> instanceType, Object... objects) {
			Object owner = null;
			if (objects != null && objects.length > 0)
				owner = objects[0];
			return new InstanceWrapper<T>(instanceType, owner);
		}

	}

	public <T> void register(T object) {
		if (object != null) {
			objectPool.put(object.getClass(), object);
		}
	}

	public <T> void register(Class<T> classType, T object) {
		if (object != null) {
			objectPool.put(classType, object);
		}
	}

}