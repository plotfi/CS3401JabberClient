import java.util.LinkedList;

import org.jivesoftware.smack.RosterEntry;

public class BuddyList {
    private LinkedList<Group> groups;

    public BuddyList() {
	groups = new LinkedList<Group>();
    }

    public BuddyList(String groupNames[]) {
	groups = new LinkedList<Group>();
	for (int i = 0; i < groupNames.length; i++) {
	    Group toAdd = new Group(groupNames[i]);
	    toAdd.addRandomBuddies(10);
	    addGroup(toAdd);
	}
    }

    public Group findGroup(String name) {
	
	for (int i = 0; i < groups.size(); i++) {
	    Group test = groups.get(i);
	    if (test.toString().equals(name)) {
		return test;
	    }
	}
	return groups.get(0);
	    
    }

    public RosterEntry findBuddy(String from) {
	for (int i = 0; i < groups.size(); i++) {
	    Group test = groups.get(i);
	    LinkedList groupList = test.getMembers();
	    for (int j = 0; j < groupList.size(); j++) {
		RosterEntry check = (RosterEntry) groupList.get(j);
		System.out.println("Checking user: " + check.getUser() + " against: " + from);
		if (check.getUser().equals(from))
		    return check;
	    }
	}
	return null;

    }

    public void addGroup(Group toAdd) {
	groups.add(toAdd);
    }

    public Object[] getGroups() {
	return groups.toArray();
    }

    public int getNumber() {
	return groups.size();
    }

    public Group getGroup(int i) {
	return groups.get(i);
    }
}
