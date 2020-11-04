package cs518.a3.distributedchat.core;

import java.io.IOException;

import cs518.a3.distributedchat.threadpool.Task;

// This is a task class whose method execute is called periodically based on configurable  MEMBERSCHECKER_TIMEINTERVAL
// Its job is to call removeFailedMembers() method in chatGroupsManager to remove all dead chat clients
public class MembersCheckerTask extends Task {
	private ChatGroupsManager chatGroupsManager;
	public  MembersCheckerTask(ChatGroupsManager chatGroupsManager) {
		this.chatGroupsManager	= chatGroupsManager;
	}
	
	public void execute() throws IOException, InterruptedException {
		 while (true) {
			Thread.sleep(Setting.MEMBERSCHECKER_TIMEINTERVAL);
			chatGroupsManager.removeFailedMembers();
		 } 
	}
}
