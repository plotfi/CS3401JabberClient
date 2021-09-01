import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;



public class Session {
    private XMPPConnection connection = null;
    JabberClient owner;
    
    public Session (XMPPConnection by_connection, JabberClient owner) {
	// Takes in an XMPP Connection, it can be regular or SSL
	this.connection = by_connection;
	this.owner = owner;
    }
    
    public PacketCollector listen_by_jid(String jid) {
	//		 Create a packet filter to listen for new messages from a particular
	//		 user. We use an AndFilter to combine two other filters.
	PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class),
					    new FromContainsFilter(jid));
	
	//		 Assume we've created an XMPPConnection name "connection".
	//		 First, register a packet collector using the filter we created.
	PacketCollector myCollector = connection.createPacketCollector(filter);
	
	//		 Normally, you'd do something with the collector, like wait for new packets.
	//		 Next, create a packet listener. We use an anonymous inner class for brevity.
	PacketListener myListener = new PacketListener() {
		public void processPacket(Packet packet) {
		    Message message = (Message) packet;
                    String username[] = message.getFrom().split("@");
                    System.out.print("\n" + username[0] + "@" + username[1] + ": ");
		    System.out.print(message.getBody() + "\n");

		    String from = message.getFrom();
		    String fromTrimmed = from.substring(0, from.indexOf('/'));
		    if (owner.haveConversation(fromTrimmed)) {
			System.out.println("We've got a conversation window with: " + message.getFrom() + "\n"); 
		    } else {
			System.out.println("We don't have a conversation window with: " + message.getFrom() + "\n"); 
			RosterEntry target = owner.findBuddy(fromTrimmed);
			if (target != null) {
			    Conversation newCon = owner.startConversation(target);
			    newCon.processPacket(packet);
			} else {
			    System.out.println("Couldn't find buddy: " + fromTrimmed);
			}
		    }
		}
	    };
	//		 Register the listener.
	connection.addPacketListener(myListener, filter);
	
	return myCollector;
    }
    
    public XMPPConnection getConnection() {
	// Just a getter for grabing 
	return this.connection;
    }
    
    public void login(String username, String password) throws XMPPException {
	// this takes in a username and a passwrd, and authenticates with the jabber server. 
	this.connection.login(username,password);
    }
    
    public Roster getRoster() {
	// This returns the object for the roster.
	return this.connection.getRoster();
    }
    
	
    public void printRoster() {
	// This prints out the entire contents of the roster.
	Roster roster = this.getRoster();
	
	for (Iterator i=roster.getEntries(); i.hasNext(); ) {
	    Object item = i.next();
	    //if(null != roster.getPresence(wtf.toString())){ 
	    System.out.println( item + " " + roster.getPresence(item.toString()));
	    //}
	    
	}
	
    }
    
    public List listRoster() {
	// This returns a list of all buddies in the roster. 
	Roster roster = this.getRoster();
    	List<Object> processedRoster = new ArrayList<Object>();
    	
    	for (Iterator i=roster.getEntries(); i.hasNext(); ) {
	    Object item = i.next();
	    //if(null != roster.getPresence(wtf.toString())){ 
	    processedRoster.add(item);
	    //}
	    
    	}
    	
    	return processedRoster;
	
    }

    public List listGroups() {
	// This returns a list of all your groups in the roster.
	Roster roster = this.getRoster();
    	List<Object> groups = new ArrayList<Object>();
    	
    	for (Iterator i=roster.getGroups(); i.hasNext(); ) {
	    Object item = i.next();
	    groups.add(item);
    	}
    	
    	return groups;
    }
	
    public void addBuddy(String jid) {
	
    }
    
}
