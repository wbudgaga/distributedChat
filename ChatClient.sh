#!/bin/bash
if [ $# -ne 3 ] ; then
	echo "You have to enter ChatClient's listing-port,  and ChatServer's host name and port number"
	exit 3
fi

if [ ! -f chatApp.jar ];
then
    jar -cvfm chatApp.jar MANIFEST.MF -C bin/ .
fi

echo "Running a Chat Client on $(hostname)"
java -cp chatApp.jar cs518.a3.distributedchat.core.ChatClientApp "$1" $(hostname) "$2" "$3" 

