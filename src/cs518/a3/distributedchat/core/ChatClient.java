package cs518.a3.distributedchat.core;

import java.io.IOException;
import java.util.ArrayList;

import cs518.a3.distributedchat.gui.MainGUI;
import cs518.a3.distributedchat.wireformates.ClientInfo;
import cs518.a3.distributedchat.wireformates.Message;


public interface ChatClient {
	public boolean 						setGroupMembers(ArrayList<RemoteChatClient> members) throws IOException;
	public void 						newMemberGroupNotification() throws IOException;
	public void 						newMemberNotification(RemoteChatClient member) throws IOException;
	public void 						leavingMemberNotification(ClientInfo departedMember) throws IOException;
	public void 						setGroupID(int groupID);
	public int 							getGroupID();
	public String 						getNodeID();
	public void 						setNodeID(String nodeID);
	public boolean 						run(int threadPoolSize) throws IOException;
	public boolean 						isAlive();
	public ArrayList<RemoteChatClient> 	getMembers();
	public void 						sendGroupData(String data) throws IOException;
	public void 						sendGroupMSG(Message data)throws IOException;
	public void 						broadcastData(String data);
	public void 						setMainGUI(MainGUI mainGUI);
	public void 						cleanGroup();
	public void 						refreshChatGroup();
	public void 						dataReceivedNotifying(String senderID, String data);
	public ClientInfo 					getClientInfo();
}
