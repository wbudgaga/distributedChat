package cs518.a3.distributedchat.core;

public interface Setting {
	public final int 	GROUP_SIZE					= 3;
	public final int 	CLIENT_THREADPOOL_SIZE		= 10;
	public final int 	SERVER_THREADPOOL_SIZE		= 10;
	public final int 	MEMBERSCHECKER_TIMEINTERVAL	= 1000;  // millisecond; 1 second = 1000 ms
	public final String CLIENT_LOG_FILE_NAME 		= "/tmp/clientLogging";
	public final String SERVER_LOG_FILE_NAME 		= "/tmp/serverLogging";
	public final int 	TEST_MESSAGE_SIZE			= 1000;	// # of bytes the message has
	public final int 	TEST_MESSAGE_RATE			= 5000;	// millisecond; 1 second = 1000 ms
}
