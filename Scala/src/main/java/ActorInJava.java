import akka.actor.*;

public class ActorInJava {
    private static final ActorSystem system = ActorSystem.create("MySystem");
    private static final String GREETER_PATH;

    static {
        final ActorRef greeter = system.actorOf(Props.create(Receiver.class), "greeter");
        GREETER_PATH = greeter.path().toString();
    }

    public static void main(String[] args) {
        sayHello();
        system.actorSelection(GREETER_PATH).tell(new ObjectMessage("Tom"), ActorRef.noSender());
        System.out.println("main finished");
    }

    private static void sayHello() {
        final Thread t = new Thread(() -> {
            try {
                Thread.sleep(4000);
                ActorSelection as = system.actorSelection(GREETER_PATH);
                as.tell(3000, ActorRef.noSender());
                System.out.println("please say it");
                as.tell("Hello", ActorRef.noSender());
                Thread.sleep(1000);
                as.tell("kill", ActorRef.noSender());
            } catch (InterruptedException ignored) {
            }
        });

        t.start();
    }

    // Make these inner classes, so it won't conflict with other classes
    static class ObjectMessage implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        final String fromWho;
        ObjectMessage(String fromWho) {
            this.fromWho = fromWho;
        }
    }

    static class Receiver extends UntypedAbstractActor {

        public void onReceive(Object message) throws Exception {
            if (message instanceof ObjectMessage)
                System.out.println(("Hello " + ((ObjectMessage) message).fromWho));
            if (message instanceof String) {
                System.out.println(message);
                if ("kill".equals(message)) {
                    System.out.println("killing...");
                    context().system().terminate();
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
