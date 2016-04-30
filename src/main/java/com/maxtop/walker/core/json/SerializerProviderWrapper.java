package com.maxtop.walker.core.json;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class SerializerProviderWrapper extends DefaultSerializerProvider{

	  private static final long serialVersionUID = 1L;

      public SerializerProviderWrapper() { 
    	  super(); 
      }
      
      public SerializerProviderWrapper(SerializerProviderWrapper src) { 
    	  super(src); 
      }

      protected SerializerProviderWrapper(SerializerProvider src, SerializationConfig config,SerializerFactory f) {
          super(src, config, f);
      }

      @Override
      public DefaultSerializerProvider copy(){
          if (getClass() != SerializerProviderWrapper.class) {
              return super.copy();
          }
          return new SerializerProviderWrapper(this);
      }
      
	@Override
	public SerializerProviderWrapper createInstance(SerializationConfig config,
			SerializerFactory jsf) {
		return new SerializerProviderWrapper(this, config, jsf);
	}
	
	@Override
	public JsonSerializer<Object> findTypedValueSerializer(Class<?> valueType,
            boolean cache, BeanProperty property)
        throws JsonMappingException{
		return super.findTypedValueSerializer(valueType,false,property);
	}
	
	protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> type)
	        throws JsonMappingException{       
        JsonSerializer<Object> ser;
        try {
            ser = _createUntypedSerializer(_config.constructType(type));
        } catch (IllegalArgumentException iae) {
            /* We better only expose checked exceptions, since those
             * are what caller is expected to handle
             */
            throw new JsonMappingException(iae.getMessage(), null, iae);
        }
        return ser;
	    }

}
