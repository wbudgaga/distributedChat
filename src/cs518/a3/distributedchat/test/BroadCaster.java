package cs518.a3.distributedchat.test;

import java.io.IOException;

import cs518.a3.distributedchat.core.ChatClient;
import cs518.a3.distributedchat.core.Setting;
import cs518.a3.distributedchat.threadpool.Task;
import cs518.a3.distributedchat.util.Helper;
import cs518.a3.distributedchat.wireformates.ByteStream;

public class BroadCaster extends Task {
	private ChatClient 	chatClient;
	private int 		messageRate;
	
	public BroadCaster(ChatClient 	chatClient, int messageRate){
		this.messageRate = messageRate;
		this.chatClient = chatClient;
	}
	
	@Override
	public void execute() throws InterruptedException, IOException {
		 while (true) {
			Thread.sleep(messageRate);
			chatClient.broadcastData(ByteStream.byteArrayToString(Helper.getRandomData(Setting.TEST_MESSAGE_SIZE)));
		 } 
	}
	
	
}
