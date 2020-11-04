package cs518.a3.distributedchat.core;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import cs518.a3.distributedchat.handler.ChatServerHandler;
import cs518.a3.distributedchat.wireformates.Data;

public class ChatServer extends Node{
	private ChatGroupsManager chatGroupsManager;
	
	public ChatServer(int port) throws UnknownHostException{
		super("server",port);
		chatGroupsManager = new ChatGroupsManager();
	}
	
	public void broadCastData(Data data)  {
		try {
			chatGroupsManager.broadCastData(data);
		} catch (IOException e) {}
	}
	
	public int addMember(RemoteChatClient memebr){
		ChatGroup group= chatGroupsManager.addMember(memebr);
		if (group == null)
			return -1;
		return group.getGroupID();
	}
	
	public ArrayList<RemoteChatClient> getGroupMembers(String memberID){
		return chatGroupsManager.getGroupMembers(memberID);
	}

	public void run(int threadpoolSize) throws InstantiationException, IllegalAccessException, IOException, InterruptedException{
		setHandler(new ChatServerHandler(this));
		if (!startListening(threadpoolSize))
			return;
		getThreadPoolManager().addTask(new MembersCheckerTask(chatGroupsManager));
	}

	public static void main(String args[]) throws InstantiationException, IllegalAccessException, IOException, InterruptedException {
		ChatServer chatServer;
		      
		if (args.length < 1) {
			System.err.println("Discovery Node:  Usage:");
			System.err.println("         java  cs518.a1.distributedchat.core.ChatServer PORT-NUM");
		    return;
		}
		int port = Integer.parseInt(args[0]);
		
		chatServer = new ChatServer(port);
		chatServer.run(Setting.SERVER_THREADPOOL_SIZE);
	}

}
