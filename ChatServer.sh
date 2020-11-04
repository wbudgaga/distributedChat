#!/bin/bash
if [ $# -ne 1 ] ; then
	echo "You have to enter the listing-port of the ChatServer"
	exit 1
fi

if [ ! -f chatApp.jar ];
then
    jar -cvfm chatApp.jar MANIFEST.MF -C bin/ .
fi

echo "Running the ChatServer on $(hostname)"
java -cp chatApp.jar cs518.a3.distributedchat.core.ChatServer "$1"

