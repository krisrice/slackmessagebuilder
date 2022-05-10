package io.krisrice.slackmessagebuilder;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

public class SlackMessage {

	public static void main(String[] args) throws IOException {
		
				
		SlackMessage msg = SlackMessage.Builder.builder()
	    .username("me")
		.text("Hello")
		.channel("#dbtools-community-syn")
		.emoji(":facepalm:")
		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 1").build()).build())
		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 2").build()).field(SlackObject.TextBuilder.builder().text("hi3").build()).field(SlackObject.TextBuilder.builder().text("hi4").build()).build())
		.block(SlackBlock.DividerBuilder.builder().build())
		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 3").build()).build())
		.block(SlackBlock.ImageBuilder.builder().title(SlackObject.TextBuilder.builder().text("SQL Developer ").build()).text("sqldev").url("https://www.oracle.com/technetwork/developer-tools/sql-developer/sqldev-64-2015-2530893.png").build())
		.build();

		System.out.println(msg.getMessage());
		
	}


	private String _msg;
	private SlackMessage(Builder b) throws IOException {
		StringWriter writer = new StringWriter();

		JsonGenerator json = new JsonFactory().createGenerator(writer);
		json.setPrettyPrinter(new DefaultPrettyPrinter());

		json.writeStartObject();
		// configure, if necessary:
		if ( Objects.nonNull(b.username) ) 	json.writeStringField("username",b.username);
		if ( Objects.nonNull(b.emoji) ) 	json.writeStringField("icon_emoji",b.emoji);
		if ( Objects.nonNull(b.channel) ) 	json.writeStringField("channel",b.channel);
		if ( Objects.nonNull(b.text) ) 		json.writeStringField("text",b.text);
		if ( Objects.nonNull(b.markdown) ) 	json.writeBooleanField("mrkdwn",b.markdown);
//
		if ( Objects.nonNull(b.blocks) ) {
				json.writeArrayFieldStart("blocks");
				for(SlackBlock block:b.blocks) {
					Utils.writeJsonObject(json,block.getBlock());
				}
				
				json.writeEndArray();				
				json.writeEndObject();
		}
		
		json.flush();
		
		writer.flush();
		_msg = writer.toString();

	}
	
	public String getMessage() {
		return _msg;
	}
	
	public static final class Builder{
		private String username;
		private String emoji;
		private String channel;
		private String text;
		private List<SlackBlock> blocks;
		private String[] attachments;
		private String thread;
		private boolean markdown;
		
		public SlackMessage build() throws IOException {
			return new SlackMessage(this);
		}
		public static Builder builder() {
			return new Builder();
		}
		public Builder username(String username) {
			this.username=username;
			return this;
		}
		public Builder emoji(String emoji) {
			this.emoji=emoji;
			return this;
		}		
		public Builder text(String text) {
			this.text=text;
			return this;
		}
		public Builder block(SlackBlock block) {
			if ( this.blocks== null ) {
				this.blocks = new ArrayList<SlackBlock>();
			}
			this.blocks.add(block);
			return this;
		}
		public Builder attachments(String[] attachments) {
			this.attachments=attachments;
			return this;
		}
		public Builder channel(String channel) {
			this.channel=channel;
			return this;
		}		
		public Builder thread(String thread) {
			this.thread=thread;
			return this;
		}

		public Builder markdown(boolean markdown) {
			this.markdown=markdown;
			return this;
		}

	}
	

}
