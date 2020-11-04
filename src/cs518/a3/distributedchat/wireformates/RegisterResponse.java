package cs518.a3.distributedchat.wireformates;

import java.net.Socket;

import cs518.a3.distributedchat.handler.MessageHandler;

public class RegisterResponse 	extends Response{
	
	public RegisterResponse() {
		super(REGISTER_RESPONSE);
		setAdditionalInfo("");
	}

	public String getMessageType() {
		return "REGISTER_RESPONSE";
	}

	@Override
	public  void   handle(Socket link,MessageHandler handler){
		handler.handleRegisterResponse(link,this);
	}

}
