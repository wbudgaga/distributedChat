package cs518.a3.distributedchat.threadpool;

import java.io.IOException;

public abstract class Task {
	public abstract void execute() throws IOException, InterruptedException;
}
