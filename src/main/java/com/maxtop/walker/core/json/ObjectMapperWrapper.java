package com.maxtop.walker.core.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperWrapper extends ObjectMapper {
	
	private static final long serialVersionUID = -7755932610450962546L;

	public ObjectMapperWrapper() {
		disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
}