package io.krisrice.slackmessagebuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class SlackObject {

	private String _json;

	private SlackObject(String json) {
		_json = json;
	}
	public String getSlackObject() {
		return _json;
	}
	
	@Override
	public String toString() {
		return _json;
	}
	
	

	public static final class TextBuilder {
		private String text;
		private boolean md;
		private String emoji;

		public SlackObject build() throws IOException {
			StringWriter writer = new StringWriter();
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			json.writeStartObject();
			json.writeStringField("type",  md?"mrkdown":"plain_text" );
			json.writeStringField("text",this.text );
			if ( Objects.nonNull(this.emoji) ) json.writeStringField("emoji",this.emoji);
			json.writeEndObject();
			json.flush();
			writer.flush();
			return new SlackObject(writer.toString());

		}

		public static TextBuilder builder() {
			return new TextBuilder();
		}

		public TextBuilder emoji(String emoji) {
			this.emoji = emoji;
			return this;
		}
		public TextBuilder text(String text) {
			this.text = text;
			return this;
		}
		public TextBuilder mardown(boolean md) {
			this.md = md;
			return this;
		}
		
	}
}
