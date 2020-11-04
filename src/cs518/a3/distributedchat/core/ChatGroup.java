package cs518.a3.distributedchat.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import cs518.a3.distributedchat.wireformates.ClientInfo;
import cs518.a3.distributedchat.wireformates.Message;

public class ChatGroup{
	private int 				groupID;
	private ChatGroupsManager 	chatGroupsManager;
	private ConcurrentHashMap<String, RemoteChatClient> members = new ConcurrentHashMap<String, RemoteChatClient>();
	
	public ChatGroup(int groupID, ChatGroupsManager chatGroupsManager){
		this.groupID = groupID;
		this.chatGroupsManager = chatGroupsManager;
	}
	
	public synchronized boolean addMember(RemoteChatClient member){
		String memberID = member.getNodeID();
		if (isFull() || getMember(memberID)!=null)
			return false;
		members.put(memberID, member);
		member.setGroupID(getGroupID());
		return true;
	}
	
	public void removeAll(){
		members.clear();
	}
	
	public synchronized RemoteChatClient removeMember(String memberID){
		return members.remove(memberID);
	}
	
	public ArrayList<RemoteChatClient> getMembers(){
		ArrayList<RemoteChatClient> membersList = new ArrayList<RemoteChatClient>();
		for(RemoteChatClient member:members.values())
			membersList.add(member);
		return membersList;
	}
	
	public void sendDelMemberNotification(RemoteChatClient removedMember) throws IOException{
		 for(RemoteChatClient member:members.values())
			 member.leavingMemberNotification(removedMember.getClientInfo());
	}

	public void sendNewMemberNotification(RemoteChatClient newMember) throws IOException{
		 for(RemoteChatClient member:members.values()){
			 member.newMemberNotification(newMember);
		 }
	}
	
	public synchronized void sendMessageToOneMember(Message msg) throws IOException{			
		 for(RemoteChatClient member:members.values()){
			 member.sendMessage(msg);
			 return ;
		 }
	}


	public void sendMessage(Message msg) throws IOException{
		 for(RemoteChatClient member:members.values())
			 member.sendMessage(msg);
	}

	
	public void sendData(ClientInfo sender ,String data) throws IOException{
		 for(RemoteChatClient member:members.values())
			 member.sendData(sender, data);
	}

	public int getNumberOfMembers(){
		return members.size();
	}
	
	public boolean isFull(){
		return members.size()== Setting.GROUP_SIZE;
	}
	
	public RemoteChatClient getMember(String memberID){
		return members.get(memberID);
	}
	public int getGroupID() {
		return groupID;
	}

	protected void removeFailedMembers() throws IOException{
		for(RemoteChatClient member:members.values())
			if (!member.isAlive()){
				System.out.println("The member : "+member.getNodeID()+" has been removed!");
				sendDelMemberNotification(removeMember(member.getNodeID()));
				chatGroupsManager.removeMember(member.getNodeID());
			}
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
