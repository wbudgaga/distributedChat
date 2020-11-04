package cs518.a3.distributedchat.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import cs518.a3.distributedchat.communication.ConnectionManager;
import cs518.a3.distributedchat.handler.MessageHandler;
import cs518.a3.distributedchat.threadpool.ThreadPoolManager;


public class Node {
	private 	String 				nodeID;
	private 	String 				host;
	private 	int 				portNum;
	protected 	ConnectionManager 	connectionManager;
	protected 	MessageHandler 		handler;
	protected 	ThreadPoolManager	threadPoolManager;

	public Node(String nodeID, int port) throws UnknownHostException{
		this.nodeID = nodeID;
		host = InetAddress.getLocalHost().getHostName();
		this.portNum = port;
	}

	public ThreadPoolManager getThreadPoolManager(){
		return threadPoolManager;
	}

	protected void setHandler(MessageHandler 	handler){
		this.handler = handler;
	}
	
	protected boolean startListening(int threadPoolSize) throws IOException{
		threadPoolManager = new ThreadPoolManager(threadPoolSize);
		threadPoolManager.start();
		connectionManager = new ConnectionManager(handler,threadPoolManager);
		connectionManager.startListening(portNum);
		System.out.println("Listening on port "+ portNum);
		return true;
	}
	
	protected void stopListening(){
		connectionManager.stopListening();
		threadPoolManager.stop();
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPortNum() {
		return portNum;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

}
