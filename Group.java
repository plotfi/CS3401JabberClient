import java.util.LinkedList;
import java.util.Random;

import org.jivesoftware.smack.RosterEntry;


public class Group {
    private String name;
    private LinkedList<RosterEntry> members;

    String names[] = {"Dick", "Jane", "Bob", "Joe", "Fred", "Jill", "Ann",
		    "James", "Liz", "Julie"};
    String lastNames[] = {"Smith", "Brown", "Farmer", "King"};

    public Group() {
	members = new LinkedList<RosterEntry>();
    }

    public Group(String name) {
	members = new LinkedList<RosterEntry>();
	this.name = name;
    }

    public void addBuddy(RosterEntry toAdd) {
	members.add(toAdd);
    } 

    public void addRandomBuddies(int num) {
	/*String newName;
	Random rand = new Random(System.currentTimeMillis());
	for (int i = 0; i < num; i++) {
	    newName = names[rand.nextInt(names.length)] +
		lastNames[rand.nextInt(lastNames.length)];
	    addBuddy(new Buddy(newName));
	    } */
    }

    public LinkedList getMembers() {
	return members;
    }

    public int getNumber() {
	return members.size();
    }

    public RosterEntry getBuddy(int i) {
	return members.get(i);
    }

    public String toString() {
	return name;
    }
}
