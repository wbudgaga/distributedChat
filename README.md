# Chat Application
The chat application consists of chat clients and server(s) that are Java applications (not applets). The chat application supports that each client can talk to a particular member privately, a group of memebers, and all other members in the system.

The implementaion of this application supports the following features:
- Allowing the chat clients to join and leave the chat sessions at any time without causing any issue
- Creating chat groups and assigning chat members to different groups
- Detecting the leaving of nodes (machines) and rebalancing the chat groups if there is a need.
- Sending three types of massages:
  - Private messaging
  - Group messaging
  - Broadcasting
- Creating GUI to simplify the interactive with the application features
- Configuring the application at runtime
- Scalability
- Logging
- Encryption
- Synchronization
- Failure Handling


....


## Scalability
Unlike the naive approach where the central chat server receives a message from a client and broadcasts the message to all other clients, 
The developed application uses a chat server as a central server to bootstrap clients that want to join the chat session. All chat clients are aware of this central server. Every chat client is in a group (called peer group), which has a peer-group ID. The group members have their own member IDs. When a client joins a chat session, the central server informs the client about its peer group ID and group member IDs. Each client communicates with a small set of other clients (peers) and this set is determined by the central server.

When a chat client has a message that needs to be broadcast, it sends the message to all other members in its peer group and also to the central chat server. Then, the central server sends this message to one client in every group. Each of those clients forwards the message to the other members existing in its own group. This way, all chat clients eventually get the message and the task is distributed instead of relying on one client or on the central server.



## Package Structure
	The main folder contains:
	
- src: It contains java source files

- bin: It contains java classes files (should not be deleted manually)

- aspectjrt.jar: It is an aspectj runtime library needed by the application

- chatApp.jar: It is the runnable chat application

- makefile: It is used to compile java classes(make all) and remove classes (make clean)

- MANIFEST.MF: It is needed to create chatApp.jar

- ChatClient.sh: It is used to run chat application client and to create chatApp.jar if it does not exist

- ChatServer.sh: It is used to run chat application server and to create chatApp.jar if it does not exist
	
- specification.txt: It includes detailed information about the application requirements and features


