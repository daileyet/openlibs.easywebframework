package com.openthinks.easyweb.context;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;

/**
 * Shared web context, used {@link ObjectPool} as object shared pool
 * @author dailey.yet@outlook.com
 *
 */
public abstract class SharedContext implements WebContext {

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	private final ObjectPool objectPool = new ObjectPool();
	private final Lock lock = new ReentrantLock();

	// ////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * lookup a object by its type, if not find firstly, try to instance by
	 * instanceType and constructor parameters args
	 * 
	 * @param <T> lookup object type
	 * @param type
	 *            Class lookup key type
	 * @param args
	 *            Object[] instance constructor parameters
	 * @return T lookup object
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
	 * @param <E> lookup object type
	 * @param searchType
	 *            Class lookup key type
	 * @param instancewrapper
	 *            Class instance type when not lookup the key
	 * @param args
	 *            Object[] instance constructor parameters
	 * @return T lookup object
	 */
	public <T, E extends T> T lookup(final Class<T> searchType, InstanceWrapper<E> instancewrapper, Object... args) {
		T object = objectPool.get(searchType);
		lock.lock();
		try {
			object = objectPool.get(searchType);
			if (object == null) {
				object = InstanceUtilities.create(searchType, instancewrapper, args);
				register(object);
			}
		} finally {
			lock.unlock();
		}
		return object;
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

	/**
	 * look up object by its bean name
	 * @param <T> lookup object type
	 * @param beanName String lookup object mapping name
	 * @return T lookup object
	 */
	public <T> T lookup(String beanName) {
		return objectPool.get(beanName);
	}

	/**
	 * register object and mapping it to given bean name
	 * @param <T> register object type
	 * @param beanName String bean name
	 * @param object register object
	 */
	public <T> void register(String beanName, T object) {
		if (object != null) {
			objectPool.put(beanName, object);
		}
	}

}