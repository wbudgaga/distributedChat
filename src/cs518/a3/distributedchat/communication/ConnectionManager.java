package cs518.a3.distributedchat.communication;

//Manages the connections. it hides all the connections details. All connection operation can be  only performed through this class.

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import cs518.a3.distributedchat.handler.MessageHandler;
import cs518.a3.distributedchat.threadpool.ThreadPoolManager;
import cs518.a3.distributedchat.wireformates.ByteStream;
import cs518.a3.distributedchat.wireformates.Message;
import cs518.a3.distributedchat.wireformates.MessageFactory;

public class ConnectionManager {
	private ListeningTask listeningTask ;
	private	MessageHandler	messageHandler;
	private	ThreadPoolManager threadPoolManager;
	
	public ConnectionManager(MessageHandler	messageHandler, ThreadPoolManager	threadPoolManager){
		this.messageHandler 		= messageHandler;
		this.threadPoolManager 		= threadPoolManager;
	}
	
	public void handleConnection(Socket connection) throws IOException{
		receiveData(connection);
	}
	
	public void receiveData(Socket connection) throws IOException{
		threadPoolManager.addTask(new ReceivingTask(this,connection));
	}
	
	public void handleMassage(Socket link, byte[] byteBuffer) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, NoSuchAlgorithmException{
		Message msg 			=  MessageFactory.getInstance().createMessage(byteBuffer);
		msg.handle(link, messageHandler);
	}
	
	public void startListening(int listeningPort) throws IOException{
		listeningTask 			= new ListeningTask(listeningPort,this);
		threadPoolManager.addTask(listeningTask);
	}

	public void stopListening(){
		if (listeningTask != null)
			listeningTask.stop();
	}
	
	protected static void sendByteData(OutputStream outStream, byte[] dataToBeSent) throws IOException {
		outStream.write(ByteStream.addPacketHeader(dataToBeSent)); // message header will be added by sending each message
	}

	public static void sendData(OutputStream outStream, Message msg) throws IOException{
		sendByteData(outStream,msg.packMessage());
	}
	
	public static boolean isAlive(String hostAddress, int port){
		try {
			Socket socket 	= new Socket(hostAddress, port);
			socket.close();
			return true;
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		return false;
	}
	
	public void sendMessage(Message msg, String hostAddress, int port){
		try{
			Socket socket 	= new Socket(hostAddress, port);
			sendByteData(socket.getOutputStream(),msg.packMessage());
		} catch (IOException e) {}
	}
	
	public Message sendReceiveMessage(Message msg, String hostAddress, int port) {
		try{
			Socket socket 	= new Socket(hostAddress, port);
			sendByteData(socket.getOutputStream(),msg.packMessage());
			byte[] byteBuffer = ReceivingTask.receiveMessageFrom(socket.getInputStream());
			socket.close();
			return MessageFactory.getInstance().createMessage(byteBuffer);
		} catch (IOException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {}
		return null;
	}

}
