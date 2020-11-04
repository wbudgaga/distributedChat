package cs518.a3.distributedchat.aspectj;

import org.aspectj.lang.Signature;

public aspect ExceptionHandlingAspect{
	private ThreadLocal<Throwable> lastLoggedException	= new ThreadLocal<Throwable>();
	
	pointcut exceptionTraced(): execution(* *.*(..));
	
	after() throwing(Throwable ex) : exceptionTraced() {
		if (lastLoggedException.get() != ex) {
			lastLoggedException.set(ex);
			Signature sig = thisJoinPointStaticPart.getSignature();
			System.out.println("Using aspect exception catched at "+sig.toShortString()+". \nError Details:\n "+ex);
		}
	}
	

}
