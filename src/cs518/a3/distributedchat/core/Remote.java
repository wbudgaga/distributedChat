package cs518.a3.distributedchat.core;

import java.io.IOException;

import cs518.a3.distributedchat.communication.ConnectionManager;
import cs518.a3.distributedchat.wireformates.ClientInfo;
import cs518.a3.distributedchat.wireformates.Data;
import cs518.a3.distributedchat.wireformates.Message;

public class Remote {
	protected 	ConnectionManager 	connectionManager;
	private 	String 				host;
	private 	int 				port;

	public Remote(String 	host, int 	port){
		setHost(host);
		setPort(port);
		connectionManager = new ConnectionManager(null,null);
	}
	
	public void sendMessage(Message msg) throws IOException{
		connectionManager.sendMessage(msg,getHost(), getPort());
	}

	public void sendData(ClientInfo sender, String data) throws IOException{
		Data dataMSG = new Data();
		dataMSG.setText(data);
		dataMSG.setSender(sender);
		sendMessage(dataMSG);
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
