package cs518.a3.distributedchat.aspectj;



import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import cs518.a3.distributedchat.core.ChatClientApp;
import cs518.a3.distributedchat.core.RemoteChatClient;
import cs518.a3.distributedchat.core.Setting;
import cs518.a3.distributedchat.core.ChatServer;
import cs518.a3.distributedchat.core.Remote;
import cs518.a3.distributedchat.handler.MessageHandler;
import cs518.a3.distributedchat.util.LogFormatter;
import cs518.a3.distributedchat.wireformates.ClientInfo;
import cs518.a3.distributedchat.wireformates.RegisterRequest;

public aspect LoggingAspect extends BaseAspect{
	private Logger clientLogger;
	private Logger serverLogger;
	
	private Logger createLogger(String fileName) throws SecurityException, IOException{
		Logger logger = Logger.getLogger(fileName);
		logger.setUseParentHandlers(false);
		FileHandler handler = new FileHandler(fileName);
		handler.setFormatter(new LogFormatter());
		logger.addHandler(handler);
		return logger;
	}	
		
	pointcut joiningRequestReceived(RegisterRequest registerRequest): 
		call(void MessageHandler.handleRegisterRequest(..)) && args(*,registerRequest) ; 
	
	pointcut messageSent(RemoteChatClient receiver, ClientInfo sender, String data):
		call(void Remote.sendData(..)) && args(sender,data) && target(receiver) ; 
	
	after(ChatServer server) returning: chatServerRunning (server) {
		try {
			serverLogger = createLogger(Setting.SERVER_LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	after(ChatClientApp client) returning: chatClientRunning (client) {
		try {
			clientLogger = createLogger(Setting.CLIENT_LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

	after(RegisterRequest registerRequest):joiningRequestReceived(registerRequest){
		serverLogger.log(Level.INFO,"Joining reqsuest has been received from "+registerRequest.getClientInfo().getClientID());
	}
	
	after(RemoteChatClient receiver, ClientInfo sender, String data):messageSent(receiver, sender, data){
		clientLogger.log(Level.INFO,data + " is sent from "+sender.getClientID()+" to "+receiver.getNodeID());
	}

}
