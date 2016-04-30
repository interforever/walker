
package com.maxtop.walker.core.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class BeanSerializerFactoryWrapper extends BeanSerializerFactory {
	
	private static final long serialVersionUID = 5088543285702404167L;
	
	public BeanSerializerFactoryWrapper() {
		super(null);
	}
	
	protected BeanSerializerFactoryWrapper(SerializerFactoryConfig config) {
		super(config);
	}
	
	protected List<BeanPropertyWriter> findBeanProperties(SerializerProvider prov, BeanDescription beanDesc, BeanSerializerBuilder builder) throws JsonMappingException {
		List<BeanPropertyWriter> result = super.findBeanProperties(prov, beanDesc, builder);
		Map<Class<?>, AbstractPropertySelector> selectors = AbstractPropertySelector.getPropertySelectorContext().get();
		if (null != selectors) {
			Class<?> cls = beanDesc.getBeanClass();
			//if proxy delegate class
			//			if(HibernateProxy.class.isAssignableFrom(cls)){
			//				cls= cls.getSuperclass();
			//			}
			if (selectors.containsKey(cls)) {
				AbstractPropertySelector propertySelector = selectors.get(cls);
				Set<String> includes = propertySelector.include(null);
				if (includes != null && !includes.isEmpty()) {
					List<BeanPropertyWriter> tmp = new ArrayList<BeanPropertyWriter>();
					for (BeanPropertyWriter writer : result) {
						if (includes.contains(writer.getName())) {
							tmp.add(writer);
						}
					}
					return tmp;
				}
				Set<String> excludes = propertySelector.exclude(null);
				if (excludes != null && !excludes.isEmpty()) {
					List<BeanPropertyWriter> tmp = new ArrayList<BeanPropertyWriter>();
					for (BeanPropertyWriter writer : result) {
						if (!excludes.contains(writer.getName())) {
							tmp.add(writer);
						}
					}
					return tmp;
				}
			}
		}
		return result;
	}
	
}
