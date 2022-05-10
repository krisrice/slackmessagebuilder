package io.krisrice.slack.webhook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import io.krisrice.slackmessagebuilder.SlackBlock;
import io.krisrice.slackmessagebuilder.SlackMessage;
import io.krisrice.slackmessagebuilder.SlackObject;

public class SlackWebHook {
	public static void main(String[] args) throws IOException {
		
		
        
        SlackMessage msg = SlackMessage.Builder.builder()
        	    .username("Kris")
        		.text("Hello")
        		.channel("#dbtools-slack")
        		.emoji(":facepalm:")
        		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 1").build()).build())
        		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 2").build()).field(SlackObject.TextBuilder.builder().text("hi3").build()).field(SlackObject.TextBuilder.builder().text("hi4").build()).build())
        		.block(SlackBlock.DividerBuilder.builder().build())
        		.block(SlackBlock.SectionBuilder.builder().text(SlackObject.TextBuilder.builder().text("Section 3").build()).build())
        		.block(SlackBlock.ImageBuilder.builder().title(SlackObject.TextBuilder.builder().text("SQL Developer ").build()).text("sqldev").url("https://www.oracle.com/technetwork/developer-tools/sql-developer/sqldev-64-2015-2530893.png").build())
        		.build();
        
        
        System.out.println(msg.getMessage());
        
      // Web Hook URL from Slack
			String url = "https://hooks.slack.com/services/...";
		
		
		   	HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		   	conn.setRequestMethod("POST");
		    conn.setDoOutput(true);

		    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		    writer.write(msg.getMessage());
		    writer.flush();
		    String line;

		    int responseCode = conn.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);
			
		    if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("POST request not worked");
			}

		    writer.close();		    

////

	}

	public static void sendMessage(String s) {
		  
        
        
				try {
          // slack web hook
					String url = "https://hooks.slack.com/services/....";
					
					
					   HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					   conn.setRequestMethod("POST");
					    conn.setDoOutput(true);

					    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

					    writer.write(s);
					    writer.flush();
					    String line;
					    BufferedReader reader = new BufferedReader(new 
					                                     InputStreamReader(conn.getInputStream()));
					    while ((line = reader.readLine()) != null) {
					      System.out.println(line);
					    }
					    writer.close();
					    reader.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
				    
		
	}
 
}
