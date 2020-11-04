package cs518.a3.distributedchat.core;

import cs518.a3.distributedchat.wireformates.Message;
import cs518.a3.distributedchat.wireformates.RegisterRequest;
import cs518.a3.distributedchat.wireformates.RegisterResponse;


public class RemoteChatServer extends Remote{

	public RemoteChatServer(String serverHost, int serverPort) {
		super(serverHost,serverPort);
	}

	private Message sendReceiveMessage(Message msg){
		return connectionManager.sendReceiveMessage(msg,getHost(), getPort());
	}
	
	public boolean join(ChatClientImp client) {
		RegisterRequest registerRequestMSG = new RegisterRequest();
		registerRequestMSG.setClientInfo(client.getClientInfo());
		
		RegisterResponse  registerResponse = (RegisterResponse) sendReceiveMessage(registerRequestMSG);	
		if(registerResponse == null){
			System.out.println("Connection with Chat Server could not be established!");
			return false;
		}
		if (registerResponse.getStatusCode() == Message.FAILURE){
			System.out.println("Joining denied because member with the same ID is already found!");
			return false;
		}
		System.out.println("Registeration accepted");
		return true;
	}
}
