package cs518.a3.distributedchat.wireformates;

import java.util.HashMap;
import java.util.Map;

// this classes uses reflection to load  particular class to create the required instance
public class MessageFactory {
	
	private static MessageFactory instance;
	
	private Map<Integer,Class<Message>> classList = new HashMap<Integer,Class<Message>>();  
	
	private void mapMessages() throws ClassNotFoundException{
		MessageType.ClassName[] classIDs = MessageType.ClassName.values();
		for (int i=0;i<classIDs.length;++i){
			
			@SuppressWarnings("unchecked")
			Class<Message> messageClass = (Class<Message>) Class.forName(this.getClass().getCanonicalName().replace("MessageFactory", classIDs[i].toString()) );
			classList.put(new Integer(i), messageClass);
		}
	}
	
	private MessageFactory() throws ClassNotFoundException{	
		mapMessages();
	}

	public  static  MessageFactory getInstance() throws ClassNotFoundException{
		if (instance == null)
			instance = new MessageFactory();
	    return instance;
	}
	
	public Message createMessageObject(int messageID) throws InstantiationException, IllegalAccessException{
		Class<Message> messageClass = classList.get(new Integer(messageID));
		return (Message) messageClass.newInstance();
	}

	public Message createMessage(byte[]  byteStream) throws InstantiationException, IllegalAccessException{
		int messageID = Message.unpackMessageID(byteStream);
		Message msg = createMessageObject(messageID);
		msg.initiate(byteStream);
		return msg;
	}
}
