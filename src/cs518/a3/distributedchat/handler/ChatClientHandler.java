package cs518.a3.distributedchat.handler;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import cs518.a3.distributedchat.core.ChatClient;
import cs518.a3.distributedchat.core.RemoteChatClient;
import cs518.a3.distributedchat.util.Helper;
import cs518.a3.distributedchat.wireformates.ByteStream;
import cs518.a3.distributedchat.wireformates.ClientInfo;
import cs518.a3.distributedchat.wireformates.Data;
import cs518.a3.distributedchat.wireformates.DelMember;
import cs518.a3.distributedchat.wireformates.Forward;
import cs518.a3.distributedchat.wireformates.GroupMembers;
import cs518.a3.distributedchat.wireformates.NewMember;
import cs518.a3.distributedchat.wireformates.TestData;

// This class handles all messages that received at the chat client
public class ChatClientHandler extends MessageHandler{
	private ChatClient chatClient;
	
	public ChatClientHandler(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	private void addNewMember(ClientInfo  newMember) throws IOException{
		if (chatClient.getNodeID().compareTo(newMember.getClientID())!=0)
			chatClient.newMemberNotification(RemoteChatClient.getInstance(newMember));	
	}

	//It handles GroupMembers message received from chat server. After group members received and 
	// added, refreshChatGroup() method is called to update the current group list
	public void handleGroupMembers(Socket link, GroupMembers groupMembers) throws IOException {
		chatClient.cleanGroup();
		chatClient.setGroupID(groupMembers.getGroupID());
		for(int i=0; i<groupMembers.getNumberOfMembers();++i){
			addNewMember(groupMembers.getMember(i));
		}
		chatClient.newMemberGroupNotification();
		chatClient.refreshChatGroup();
	}
	
	//It handles the received text message(data) by calling dataReceivedNotifying() that shows the text in text area
	public void handleData(Socket link, Data data) {
		chatClient.dataReceivedNotifying(data.getSender().getClientID(), data.getText());
	}
		
	public void handleForwardData(Socket link, Forward forwardData) throws IOException {
		System.out.print("handleForwardData");
		Data dataMSG = forwardData.getData();
		handleData(link, dataMSG);
		chatClient.sendGroupMSG(dataMSG);
	}
	
	public void handleTestData(Socket link, TestData testData) throws NoSuchAlgorithmException, IOException {
		String hashCodeOfReceivedData 	= Helper.fromBytes(ByteStream.StringToByteArray(testData.getText()));
		chatClient.dataReceivedNotifying(testData.getSender().getClientID(),hashCodeOfReceivedData); 
		RemoteChatClient.getInstance(testData.getSender()).sendData(chatClient.getClientInfo(), hashCodeOfReceivedData);
	}

	//It handles NewMember message that includes data of new member joined to the group.
	//It adds the new member and calls refreshChatGroup() to reflect the new update on the GUI.
	public void handleNewMember(Socket link, NewMember newMember) throws IOException {
		addNewMember(newMember.getClientInfo());
		chatClient.refreshChatGroup();
	}
	
	//It handles DelMember message that includes ID of deleted member.
	//It removes that member and calls refreshChatGroup() to reflect the new update on the GUI.
	public void handleDelMember(Socket link, DelMember deletedMember) throws IOException {
		chatClient.leavingMemberNotification(deletedMember.getClientInfo());
		chatClient.refreshChatGroup();
	}
}
