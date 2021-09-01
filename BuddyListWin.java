import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jivesoftware.smack.RosterEntry;


public class BuddyListWin extends MouseAdapter {
    JFrame buddyListFrame;
    JPanel buddyListPanel;

    JScrollPane scrollPane;

    JTree buddyTree;
    DefaultMutableTreeNode top;

    BuddyList buddyList;

    JabberClient owner;

    public BuddyListWin(JabberClient owner) {
	this.owner = owner;

	if (owner == null) {
	    createTestList();
	    createAndShowGUI();
	} else {
	    buddyList = new BuddyList();
	}
    }

    public void createAndShowGUI() {
	buddyListFrame = new JFrame("Jabber Buddy List");
	buddyListFrame.setSize(300,500);

	buddyListPanel = new JPanel();
	buddyListFrame.add(buddyListPanel);
	buddyListPanel.setLayout(new BorderLayout());

	top = new DefaultMutableTreeNode("top");
	buddyTree = new JTree(top);
	fillBuddyTree();
	expandAll(buddyTree);

	buddyTree.setRootVisible(false);

	buddyTree.addMouseListener(this);
	scrollPane = new JScrollPane(buddyTree);
	buddyListPanel.add(scrollPane);

	buddyListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	buddyListFrame.setVisible(true);

	sizeComponents();
    }

    public void expandAll(JTree tree) { 
	int row = 0; 
	while (row < tree.getRowCount()) {
	    tree.expandRow(row);
	    row++;
	}
    } 


    public void mousePressed(MouseEvent e) {
	if (e.getClickCount() == 2) {
	    DefaultMutableTreeNode selected = (DefaultMutableTreeNode) buddyTree.getLastSelectedPathComponent();
	    System.out.println("Selected in tree: " + selected);
	    if (selected.isLeaf()) {
		System.out.println("It's a leaf node that's selected!\n");

		owner.startConversation((RosterEntry) selected.getUserObject());
	    }
	}
    }

    public void sizeComponents() {
	Dimension max = buddyListPanel.getMaximumSize();
	buddyListPanel.setSize(max);
	scrollPane.setSize(max);

    }

    public void fillBuddyTree() {
	DefaultMutableTreeNode groupNode = null;
	DefaultMutableTreeNode buddyNode = null;
	Group group;
	RosterEntry buddy;

	for (int i = 0; i < buddyList.getNumber(); i++) {
	    group = buddyList.getGroup(i);
	    groupNode = new DefaultMutableTreeNode(group);
	    top.add(groupNode);
	    for (int j = 0; j < group.getNumber(); j++) {
		buddy = group.getBuddy(j);
		buddyNode = new DefaultMutableTreeNode(buddy);
		groupNode.add(buddyNode);
	    }
	}
	
    }

    public void addGroup(String name) {
	buddyList.addGroup(new Group(name));
    }

    public void addBuddy(RosterEntry buddy, String groupName) {
	Group buddysGroup = buddyList.findGroup(groupName);
	buddysGroup.addBuddy(buddy);
    }

    public RosterEntry findBuddy(String from) {
	return buddyList.findBuddy(from);
    }

    public void createTestList() {
	String groups[] = {"School", "Family", "Other"};
	buddyList = new BuddyList(groups);
    }

    public static void main(String args[]) {
	BuddyListWin test = new BuddyListWin(null);
    }
}
