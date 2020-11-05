Abstract:
The application is almost the same as the one in assignment 1. However, I have added new package called cs518.a3.distributedchat.aspectj.
Also, I have two classes into the package cs518.a3.distributedchat.util. the class Encryption is used to encrypt messages and the class LogFormatter is used by the logger

This package includes four aspects:
	- BaseAspect.aj: 		contains the methodes that are used by subaspects.
	- EncryptingAspect.aj: 		inherits BaseAspect and contains pointcuts, advices, and methods needed to deal with encryption things.
	- LoggingAspect.aj: 		inherits BaseAspect and contains pointcuts, advices, and methods needed to deal with logging things.
	- ExceptionHandlingAspect.aj: 	contains pointcut, advice needed to catch the thrown exceptions and prints appropriate messages.
==============================================
ASG3Budgaga.tar contains:
	The main folder, ASG3Budgaga,  contains :
	
	- src: java source files

	- bin: java classes files (should not be deleted manually)

	- aspectjrt.jar: aspectj runtime library needed by the application

	- chatApp.jar: Runnable chat application

	- makefile: used to compile java classes(make all) and remove classes (make clean)

	- MANIFEST.MF:  needed to create chatApp.jar.

	- ChatClient.sh: used to run chat application client and to create chatApp.jar if it does not exist

	- ChatServer.sh: used to run chat application server and to create chatApp.jar if it does not exist
	
	- README.TXT: readme file.
==============================================
Features were aspectized
	. For logging, 
		- I have intercepted the constructors of the server and client applictions to create only one the logger instance 
		  that is used to log the registration requests at the server-side and sending of messages at client-side.

		- I have intercepted the method joiningRequestReceivedthe() that is used by the server to receive registration requests.

		- I have intercepted the method sendData() when the caller is the client to to intercept only the messages sent by the clients.

	. For Encryption, 
		- I have intercepted the constructors of the server and client applictions to create only one the encryptor instance 
		  that is used to encrypt and decrypt messages sent between clients and server.

		- I have intercepted the method sendByteData() to encrypt messages before sending.

		- I have intercepted the method receiveMessageFrom() to decrypt all received messages.

	. For error handling, 
		- I have intercepted all thrown exceptions and printed appropriate messages.
==============================================
The challenges:
	The only chnages I did I had to refactor the application to do the error handling part. I have removed all try.. catch parts and inserted the throw part instead.
	Some try.. catch parts could not be removed in some methode because these methods are called by the system. Therefore, adding throw part is impossible.
==============================================
Execution:
	- to run the ChatServer you can use the script ChatServer.sh as following:
		
		ChatServer.sh LISTENING-PORT-NUM

	- to run the ChatClient you can use the script ChatClient.sh as following:
		
		ChatClient.sh LISTENING-PORT-NUM SERVER-HOST SERVER-PORT

	where the client ID will be the machine name where the script has been executed.
