package cs518.a3.distributedchat.aspectj;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cs518.a3.distributedchat.communication.ConnectionManager;
import cs518.a3.distributedchat.communication.ReceivingTask;
import cs518.a3.distributedchat.core.ChatClientApp;
import cs518.a3.distributedchat.core.ChatServer;
import cs518.a3.distributedchat.util.Encryption;

public aspect EncryptingAspect extends BaseAspect{
	private Encryption encryptor; 
	
	private void createEncryptor(){
		try {
			encryptor = new Encryption();
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException
				| InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	// After creation of an instance of ChatClientApp create new instance(encryptor) of Encryption
	after(ChatClientApp client) returning: chatClientRunning(client) {
		createEncryptor();
	}
	
	after(ChatServer server) returning: chatServerRunning (server) {
		createEncryptor();
	}	

	pointcut sendingData(OutputStream outStream, byte[] dataToBeSent): 
		call(static void ConnectionManager.sendByteData(..)) && args(outStream,dataToBeSent) ; 
	
	pointcut receivingData(InputStream inStream): 
		call(static byte[]  ReceivingTask.receiveMessageFrom(..)) && args(inStream) ; 

	void around (OutputStream outStream,byte[] dataToBeSent) :sendingData(outStream,dataToBeSent){
		 try {
			 byte[] cryptedData = encryptor.encrypt(dataToBeSent);		
			 proceed(outStream,cryptedData);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	byte[] around (InputStream inStream) :receivingData(inStream){
		 try {
			 byte[] receivedData = proceed(inStream);
			 return receivedData==null? null:encryptor.decrypt(receivedData);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}


}
