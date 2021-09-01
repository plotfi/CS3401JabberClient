import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWin implements ActionListener {
    JFrame loginFrame;
    JPanel loginPanel;
    
    JLabel serviceLabel, hostLabel, portLabel, sslLabel, userLabel, passLabel;
    JTextField serviceField, hostField, portField, userField;
    JPasswordField passField;
    JCheckBox sslBox;

    JButton connectButton;
    JButton quitButton;

    GridLayout layout;

    JabberClient owner;

    public LoginWin(JabberClient owner) {
	this.owner = owner;
	createAndShowGUI();
    }

    private void createAndShowGUI() {
	layout = new GridLayout(7, 2);

	loginFrame = new JFrame("Jabber login window");
	loginPanel = new JPanel();
	loginFrame.add(loginPanel);
	loginPanel.setLayout(layout);
	
	serviceLabel = new JLabel("Service:");
	loginPanel.add(serviceLabel);

	serviceField = new JTextField("gmail.com");
	loginPanel.add(serviceField);

	hostLabel = new JLabel("Hostname:");
	loginPanel.add(hostLabel);

	hostField = new JTextField("talk.google.com");
	loginPanel.add(hostField);

	portLabel = new JLabel("Port:");
	loginPanel.add(portLabel);

	portField = new JTextField("5223");
	loginPanel.add(portField);

	sslLabel = new JLabel("SSL Encryption?");
	loginPanel.add(sslLabel);

	sslBox = new JCheckBox();
	loginPanel.add(sslBox);

	userLabel = new JLabel("Username:");
	loginPanel.add(userLabel);
	
	userField = new JTextField();
	loginPanel.add(userField);

	passLabel = new JLabel("Password:");
	loginPanel.add(passLabel);

	passField = new JPasswordField();
	loginPanel.add(passField);

	connectButton = new JButton("Connect");
	connectButton.addActionListener(this);
	loginPanel.add(connectButton);

	quitButton = new JButton("Quit");
	quitButton.addActionListener(this);
	loginPanel.add(quitButton);

	loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	loginFrame.setSize(300, 200);
	loginFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	String service, hostname, portStr, username, password;
	if (e.getSource() == connectButton) {
	    service = serviceField.getText();
	    hostname = hostField.getText();
	    portStr = portField.getText();
	    username = userField.getText();
	    password = new String(passField.getPassword());

	    int port = (new Integer(portStr)).intValue();

	    owner.connect(username, password, hostname, port, service, sslBox.isSelected());
	} 
	if (e.getSource() == quitButton) {
	    System.out.println("Quitting.");
	    System.exit(1);
	}
    }

    public void close() {
	loginFrame.dispose();
    }

    public static void main(String args[]) {
	LoginWin test = new LoginWin(null);
	test.createAndShowGUI();
	
    }
}
