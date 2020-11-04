package cs518.a3.distributedchat.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import cs518.a3.distributedchat.wireformates.Data;
import cs518.a3.distributedchat.wireformates.Forward;

public class ChatGroupsManager {
	private ConcurrentHashMap<String,Integer> groupMember 	= new ConcurrentHashMap<String,Integer>(); // <memberID,groupID>
	private ConcurrentHashMap<Integer,ChatGroup> groups 	= new ConcurrentHashMap<Integer,ChatGroup>(); // <groupID,group>
	
	public synchronized ChatGroup getMemberChatGroup(String memberID){
		Integer groupID = groupMember.get(memberID);
		if (groupID == null)
			return null;
		return groups.get(groupID);
	}
	
	public synchronized void broadCastData(Data data) throws IOException {
		Integer senderGroupID = groupMember.get(data.getSender().getClientID());
		data.getSender().setClientID("Group#"+senderGroupID+":"+data.getSender().getClientID());
		Forward fwd = data.getForward();
		for(ChatGroup group:groups.values())
			if (group.getGroupID() != senderGroupID)
				group.sendMessageToOneMember(fwd);
	}
	
	public synchronized ArrayList<RemoteChatClient> getGroupMembers(String memberID){
		ChatGroup chatGroup = getMemberChatGroup(memberID);
		if (chatGroup == null)
			return null;
		
		return chatGroup.getMembers();
	}

	public synchronized ChatGroup addMember(RemoteChatClient remoteChatClient){
		ChatGroup chatGroup = getMemberChatGroup(remoteChatClient.getNodeID());
		if (chatGroup != null)
			 return null;
		return addNewMember(remoteChatClient);
	}
	
	private ChatGroup addNewMember(RemoteChatClient remoteChatClient){
		ChatGroup chatGroup = getSmallestChatGroup();
		if (chatGroup == null || chatGroup.isFull()){
			chatGroup = createChatGroup();
			groups.put(chatGroup.getGroupID(), chatGroup);
		}
		return addNewMember(chatGroup, remoteChatClient);
	}

	private ChatGroup addNewMember(ChatGroup chatGroup, RemoteChatClient remoteChatClient){
		chatGroup.addMember(remoteChatClient);
		groupMember.put(remoteChatClient.getNodeID(),chatGroup.getGroupID());
		return chatGroup;
	}
	
	private int getNewGroupID(){
		for(int i = 1;;++i){
			if (!groups.containsKey(i))
				return i;
		}
	}
	
	private ChatGroup createChatGroup(){
		return new ChatGroup(getNewGroupID(),this);
	}
	
	private ChatGroup getSmallestChatGroup(){
		ChatGroup smallestGroup = null;
		for(ChatGroup group:groups.values())
			if (smallestGroup == null || smallestGroup.getNumberOfMembers() > group.getNumberOfMembers())
				smallestGroup = group;
		return smallestGroup;
	}
	
	public synchronized Integer removeMember(String memberID){
		return groupMember.remove(memberID);
	}

	protected synchronized void removeFailedMembers() throws IOException{
		for(ChatGroup group:groups.values()){
			group.removeFailedMembers();
			if (group.getNumberOfMembers()==0)
				groups.remove(group.getGroupID());
		}
	}

}
