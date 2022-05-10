package io.krisrice.slackmessagebuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import io.krisrice.slackmessagebuilder.SlackObject.TextBuilder;


public class SlackBlock {
	
	private String _block;
	
	protected SlackBlock(String block) {
		_block = block;
	}
	
	public String getBlock() {
		return _block;
	}


	
	public static final class ImageBuilder{
		private String id;
		private String url;
		private SlackObject title;
		private String text;
		
		public SlackBlock build() throws IOException {
			StringWriter writer = new StringWriter();
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			json.writeStartObject();
			json.writeStringField("type","image");
			if ( Objects.nonNull(this.id) ) json.writeStringField("block_id",this.id);
			json.writeStringField("image_url",this.url);
			json.writeStringField("alt_text",this.text);
			if ( Objects.nonNull(this.title) ) {
				json.writeFieldName("title");
				Utils.writeJsonObject(json, this.title.getSlackObject());
			}
			json.writeEndObject();
			json.flush();
			writer.flush();			
			return new SlackBlock(writer.toString());
			
		}

		public static ImageBuilder builder() {
			return new ImageBuilder();
		}
		public ImageBuilder id(String id) {
			this.id=id;
			return this;
		}
		public ImageBuilder url(String url) {
			this.url=url;
			return this;
		}
		public ImageBuilder title(SlackObject title) {
			this.title=title;
			return this;
		}
		public ImageBuilder text(String text) {
			this.text=text;
			return this;
		}
	}	
	
	public static final class DividerBuilder{
		private String id;
		public SlackBlock build() throws IOException {
			StringWriter writer = new StringWriter();
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			json.writeStartObject();
			json.writeStringField("type","divider");
			if ( Objects.nonNull(this.id) ) json.writeStringField("block_id",this.id);
			json.writeEndObject();
			json.flush();
			writer.flush();			
			return new SlackBlock(writer.toString());
			
		}

		public static DividerBuilder builder() {
			return new DividerBuilder();
		}
		public DividerBuilder id(String id) {
			this.id=id;
			return this;
		}
				
	}
	
	public static final class SectionBuilder{
		
		private SlackObject text;
		private String id;
		private List<SlackObject> fields = new ArrayList<SlackObject>();

		public SlackBlock build() throws IOException {
			StringWriter writer = new StringWriter();
			JsonGenerator json = new JsonFactory().createGenerator(writer);
			json.writeStartObject();
			json.writeStringField("type","section");
			if ( Objects.nonNull(this.text) ) {
				json.writeFieldName("text");
				Utils.writeJsonObject(json, this.text.getSlackObject());
			}
			if ( Objects.nonNull(this.id) ) json.writeStringField("block_id",this.id);
			if ( Objects.nonNull(fields) && fields.size()> 0 ) {
				
				json.writeArrayFieldStart("fields");
				for(SlackObject f:fields) {
					Utils.writeJsonObject(json, f.getSlackObject());
				}
				json.writeEndArray();
			}
			json.writeEndObject();
			json.flush();
			writer.flush();			
			return new SlackBlock(writer.toString());
			
		}
		public static SectionBuilder builder() {
			return new SectionBuilder();
		}
		
		public SectionBuilder id(String id) {
			this.id=id;
			return this;
		}
		
		public SectionBuilder text(SlackObject text) {
			this.text=text;
			return this;
		}
		public SectionBuilder field(SlackObject field) {
			this.fields.add(field);
			return this;
		}
		
	}

}
