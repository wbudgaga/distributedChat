# define a makefile variable for the java compiler
#
JCC = javac

#source & output directory
#
OUT_DIR=bin/
SRC_DIR=src/
LIB=aspectjrt.jar/

#packages
#
LIST = \
	cs518/a3/distributedchat/handler \
	cs518/a3/distributedchat/core \
	cs518/a3/distributedchat/wireformates \
	cs518/a3/distributedchat/threadpool \
	cs518/a3/distributedchat/communication \
	cs518/a3/distributedchat/util \
	cs518/a3/distributedchat/test \
	cs518/a3/distributedchat/gui

# define a makefile variable for compilation flags
#
JFLAGS = -g -classpath $(SRC_DIR) -d $(OUT_DIR)


all:
	$(foreach var,$(LIST),$(JCC) $(JFLAGS) $(SRC_DIR)$(var)/*.java;)
	
#
# RM is a predefined macro in make (RM = rm -f)
#
clean:
	$(foreach var,$(LIST),$(RM) $(OUT_DIR)$(var)/*.class;)

