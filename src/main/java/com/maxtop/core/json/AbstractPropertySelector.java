
package com.maxtop.core.json;

import java.util.Map;
import java.util.Set;

abstract public class AbstractPropertySelector {
	
	final private static ThreadLocal<Map<Class<?>, AbstractPropertySelector>> context = new ThreadLocal<Map<Class<?>, AbstractPropertySelector>>();
	
	abstract public Class<?> getType();
	
	public Set<String> include(Object o) {
		return null;
	}
	
	public Set<String> exclude(Object o) {
		return null;
	}
	
	final public static ThreadLocal<Map<Class<?>, AbstractPropertySelector>> getPropertySelectorContext() {
		return context;
	}
}
