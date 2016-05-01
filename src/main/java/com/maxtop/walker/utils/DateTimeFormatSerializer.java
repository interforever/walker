
package com.maxtop.walker.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeFormatSerializer extends JsonSerializer<Date> {
	
	@Override
	public void serialize(Date dateTime, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		String formatTimeString = null;
		if (dateTime != null) {
			formatTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime);
			jgen.writeObject(formatTimeString);
		}
	}
	
}
