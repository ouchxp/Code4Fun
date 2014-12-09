import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class ActorInJava {
	public static final ActorSystem system = ActorSystem.create("MySystem");
	public static final String GREETER_PATH;
	static {
		final ActorRef greeter = system.actorOf(Props.create(Receiver.class), "greeter");
		GREETER_PATH = greeter.path().toString();
	}
	
	public static void main(String[] args) throws InterruptedException {
		sayHello();
		system.actorSelection(GREETER_PATH).tell(new ObjectMessage("Tom"), ActorRef.noSender());
		System.out.println("main finished");
	}
	
	private static void sayHello() {
		final Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
					ActorSelection as = system.actorSelection(GREETER_PATH);
					as.tell(3000, ActorRef.noSender());
					System.out.println("please say it");
					as.tell("Hello", ActorRef.noSender());
					Thread.sleep(1000);
					as.tell("kill", ActorRef.noSender());
				} catch (InterruptedException e) {
				}
				
			}
		});
		
		t.start();
	}
	
	// Make these inner classes, so it won't conflict with other classes
	static class ObjectMessage implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		public final String fromWho;
		
		public ObjectMessage(String fromWho) {
			this.fromWho = fromWho;
		}
	}
	
	static class Receiver extends UntypedActor {
		
		public void onReceive(Object message) throws Exception {
			if (message instanceof ObjectMessage)
				System.out.println(("Hello " + ((ObjectMessage) message).fromWho));
			if (message instanceof String) {
				System.out.println(message);
				if ("kill".equals(message)) {
					System.out.println("killing...");
					context().system().shutdown();
					System.out.println("killed!");
					return;
				}
				System.out.println(message);
			}
			if (message instanceof Integer) {
				Thread.sleep((Integer) message);
			}
		}
	}
}
