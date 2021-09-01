import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Message;


public class Conversation implements PacketListener {
    RosterEntry target;
    Session session;
    Chat thisChat;
    ConversationWin window;
    JabberClient owner;

    public Conversation(Session session, RosterEntry target, JabberClient owner) {
	this.session = session;
	this.target = target;
	this.owner = owner;

	thisChat = session.getConnection().createChat(target.getUser());
	thisChat.addMessageListener(this);

	window = new ConversationWin(this);
    }

    public void processPacket(Packet packet) {
	System.out.println("I got a packet: " + packet);
	Message message = (Message) packet;

	window.appendText(target.getName() + ": " + message.getBody() + "\n");
    }

    public void sendMessage(String text) {
	try {
	    thisChat.sendMessage(text);
	} catch (XMPPException e) {
	    System.out.println("Caught exception: " + e);
	    System.exit(1);
	}
    }

    public void remove() {
	owner.removeConversation(this);
    }

    public String getName() {
	return target.getName();
    }

    public String getUser() {
	return target.getUser();
    }
}
