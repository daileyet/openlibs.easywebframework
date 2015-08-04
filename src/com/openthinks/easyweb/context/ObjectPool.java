/**
 * 
 */
package com.openthinks.easyweb.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author minjdai
 * 
 */
public class ObjectPool {
	// store the shared objects
	// private final ObjectList objects = new ObjectList();
	private final FirstLastObjectNameLookUp pool = new FirstLastObjectNameLookUp();
	// map to shared objects by alias
	private final Map<String, Ends> aliasMap = new ConcurrentHashMap<String, Ends>();

	// map to shared objects by class type
	private final Map<Class<?>, Ends> typeMap = new ConcurrentHashMap<Class<?>, Ends>();

	public ObjectPool() {
	}

	void cleanUp() {
		pool.clear();
	}

	public void put(Class<?> type, Object object) {
		if (type == null)
			return;
		Ends ends = typeMap.get(type);
		if (ends != null && ends.isAssign()) {
			pool.replace(ends.getIndex(), object);
		} else {
			ends = pool.put(object);
			if (ends.isAssign())
				typeMap.put(type, ends);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> type) {
		T retVal = null;
		if (type != null) {
			Ends ends = typeMap.get(type);
			retVal = (T) pool.get(ends);
		}
		return retVal;
	}

	public void put(String alias, Object object) {
		if (alias == null)
			return;
		Ends ends = aliasMap.get(alias);
		if (ends != null && ends.isAssign()) {
			pool.replace(ends.getIndex(), object);
		} else {
			ends = pool.put(object);
			if (ends.isAssign())
				aliasMap.put(alias, ends);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String alias) {
		T retVal = null;
		if (alias != null) {
			Ends ends = aliasMap.get(alias);
			retVal = (T) pool.get(ends);
		}
		return retVal;
	}

	public void asAlias(Object sharedObject, String alias) {
		if (alias == null)
			return;
		Ends ends = pool.findEnds(sharedObject);
		if (!ends.equals(aliasMap.get(alias))) {
			aliasMap.put(alias, ends);
		}
	}

}

// two-level stored data structure
class FirstLastObjectNameLookUp {

	// entry map
	Map<Character, Map<Character, ObjectList>> entryMap = new ConcurrentHashMap<Character, Map<Character, ObjectList>>();

	// combine entry map
	Map<String, ObjectList> combineMap = new ConcurrentHashMap<String, ObjectList>();

	Ends findEnds(Object sharedObject) {
		Ends ends = Ends.build(sharedObject);
		ObjectList objList = getObjectList(ends);
		if (objList != null) {
			ends.setIndex(objList.find(sharedObject));
		}
		return ends;
	}

	private ObjectList getObjectList(Ends ends) {
		// TODO check ends not null
		ObjectList objList = combineMap.get(ends.combine());
		if (objList == null) {
			Map<Character, ObjectList> firstEntry = entryMap.get(entryMap
					.get(ends.first()));// TODO exist issue???
			if (firstEntry != null) {
				objList = firstEntry.get(ends.last());
			}
		}
		return objList;
	}

	Ends put(final Object object) {
		Ends ends = null;
		ends = Ends.build(object);
		Map<Character, ObjectList> firstEntry = entryMap.get(ends.first());
		if (firstEntry == null) {
			firstEntry = new ConcurrentHashMap<Character, ObjectList>();
			entryMap.put(ends.first(), firstEntry);
		}
		ObjectList secondEntry = firstEntry.get(ends.last());
		if (secondEntry == null) {
			secondEntry = new ObjectList();
			firstEntry.put(ends.last(), secondEntry);
		}
		int index = secondEntry.add(object);
		ends.setIndex(index);
		if (ends.isAssign()) {
			combineMap.put(ends.combine(), secondEntry);
		}
		return ends;
	}

	void replace(Integer index, Object object) {
		Ends ends = Ends.build(object);
		ObjectList objList = getObjectList(ends);
		if (objList != null) {
			objList.replace(index, object);
		}

	}

	Object get(Ends ends) {
		Object retVal = null;
		if (ends == null || !ends.isAssign())
			return retVal;
		ObjectList objList = getObjectList(ends);
		if (objList != null)
			retVal = objList.get(ends.getIndex());
		return retVal;
	}

	void clear() {
		combineMap.clear();
		for (Map.Entry<Character, Map<Character, ObjectList>> entry : entryMap
				.entrySet()) {
			Map<Character, ObjectList> value = entry.getValue();
			if (value != null) {
				value.clear();
				value = null;
			}
		}
		entryMap.clear();
	}

	private class ObjectList {
		private final List<Object> _objects = new ArrayList<Object>();
		private final Lock lock = new ReentrantLock();

		void replace(int index, Object newObject) {
			_objects.set(index, newObject);
		}

		int find(Object sharedObject) {
			int index = _objects.indexOf(sharedObject);
			return index;
		}

		int add(Object value) {
			int retVal = -1;
			lock.lock();
			try {
				boolean isSuccess = _objects.add(value);
				if (isSuccess)
					retVal = _objects.size() - 1;
			} finally {
				lock.unlock();
			}
			return retVal;
		}

		Object get(int index) {
			if (index > -1 && index < _objects.size())
				return _objects.get(index);
			return null;
		}
	}
}

/**
 * First and Last(end to end) character of the object class name
 * 
 * @author minjdai
 * 
 */
class Ends {
	public static Ends build(Object object) {
		// TODO check object not null
		return new Ends(object.getClass().getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ends == null) ? 0 : ends.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ends other = (Ends) obj;
		if (ends == null) {
			if (other.ends != null)
				return false;
		} else if (!ends.equals(other.ends))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	private final String ends;
	private Integer index;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Ends(final String className) {
		ends = className.substring(0, 1)
				+ className.substring(className.length() - 1,
						className.length());
	}

	public Character first() {
		return ends.charAt(0);
	}

	public Character last() {
		return ends.charAt(1);
	}

	public String combine() {
		return ends;
	}

	public boolean isAssign() {
		return getIndex() != null || getIndex() < 0;
	}
}
/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */
