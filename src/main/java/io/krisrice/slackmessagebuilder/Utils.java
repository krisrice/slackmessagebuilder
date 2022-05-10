package io.krisrice.slackmessagebuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class Utils {

	public static void writeNamedArray(JsonGenerator gen, String name, List<?> list) throws IOException {
		if (Objects.nonNull(list) && list.size() > 0) {
			gen.writeArrayFieldStart(name);
			for (Object item : list) {
				gen.writeString(item.toString());
			}
			;
			gen.writeEndArray();
		}
	}

	public static void writeNamedObjectArray(JsonGenerator gen, String name, List<?> list) throws IOException {
		if (Objects.nonNull(list) && list.size() > 0) {
			gen.writeArrayFieldStart(name);
			for (Object item : list) {
				gen.writeStartObject();
				writeJsonObject(gen, item.toString());
				gen.writeEndObject();
			}
			;
			gen.writeEndArray();
		}
	}

	public static void writeJsonObject(JsonGenerator gen, String s) throws JsonParseException, IOException {
		JsonParser parser = new JsonFactory().createParser(s);
		JsonToken t = parser.nextToken();
		gen.copyCurrentStructure(parser); 

	}

}
