
package com.maxtop.walker.utils;

import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderFactory {
	
	private static AtomicReference<Gson> reference = new AtomicReference<Gson>();
	
	private static Gson create() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Object.class, new GsonNaturalDeserializer());
		return gsonBuilder.serializeNulls().disableHtmlEscaping().create();
	}
	
	public static Gson getInstance() {
		Gson gson = reference.get();
		if (gson == null) {
			reference.compareAndSet(null, create());
		}
		return reference.get();
	}
	
}
