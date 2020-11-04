package cs518.a3.distributedchat.aspectj;

import cs518.a3.distributedchat.core.ChatClientApp;
import cs518.a3.distributedchat.core.ChatServer;

public abstract aspect BaseAspect {
	pointcut chatServerRunning(ChatServer server): 
		execution(new(..))  && target(server) ;
	
	pointcut chatClientRunning(ChatClientApp client): 
		execution(new(..))  && target(client) ;

}
