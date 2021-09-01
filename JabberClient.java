import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import org.jivesoftware.smack.SSLXMPPConnection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.Roster;


public class JabberClient {
    LoginWin loginWindow;
    BuddyListWin buddyWindow;
    Session session;
    RosterGroup defaultGroup;
    LinkedList<Conversation> conversations;

    public JabberClient() {
	loginWindow = new LoginWin(this);
	conversations = new LinkedList<Conversation>();
    }

    public void connect(String user, String pass, String host, int port, String service, boolean ssl) {
	try {
	    if (ssl) {
		session = new Session(new SSLXMPPConnection(host, port, service), this);
	    } else {
		session = new Session(new XMPPConnection(host, port, service), this);
	    }

	    session.login(user, pass);
	    session.listen_by_jid("");
	} catch (XMPPException e) {
	    System.out.println("Exception: " + e);
	    System.exit(1);
	}
	loginWindow.close();
	System.out.println("foo");
	buildBuddyList();
    }

    public void buildBuddyList() {
	System.out.println("Building buddy list!!");
	Roster roster = session.getRoster();
	List groups = session.listGroups();

	buddyWindow = new BuddyListWin(this);

	defaultGroup = (RosterGroup) groups.get(0);

	for (int i = 0; i < groups.size(); i++) {
	    buddyWindow.addGroup(((RosterGroup) groups.get(i)).getName());
	}

	List buddies = session.listRoster();
	
	for (int i = 0; i < buddies.size(); i++) {
	    int count;
	    count = 0;

	    RosterEntry buddy = (RosterEntry) buddies.get(i);
	    System.out.println("Got buddy: " + buddy);
	    Iterator inGroups = buddy.getGroups();
	    System.out.println("Buddy is in groups:" + inGroups);
	    for (; inGroups.hasNext(); ) {
		RosterGroup inGroup = (RosterGroup) inGroups.next();
		System.out.println("Got a group: " + inGroup);
		buddyWindow.addBuddy(buddy, inGroup.getName());
		count++;
	    } 

	    if (count == 0) {
		buddyWindow.addBuddy(buddy, defaultGroup.getName());
	    }

	}

	buddyWindow.createAndShowGUI();
    }

    public Conversation startConversation(RosterEntry target) {
	Conversation newCon = new Conversation(session, target, this);
	conversations.add(newCon);
	return newCon;
    }

    public boolean haveConversation(String withBuddy) {
	Conversation check;
	for (int i = 0; i < conversations.size(); i++) {
	    check = conversations.get(i);
	    System.out.println("Checking: " + check.getUser() + " against: " + withBuddy);
	    if (check.getUser().equals(withBuddy)) {
		return true;
	    }
	}
	return false;
    }

    public RosterEntry findBuddy(String user) {
	return buddyWindow.findBuddy(user);
    }

    public void removeConversation(Conversation toRem) {
	conversations.remove(toRem);
    }

    public static void main(String args[]) {
	JabberClient client = new JabberClient();
    }
}
