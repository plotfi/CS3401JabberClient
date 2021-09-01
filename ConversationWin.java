import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class ConversationWin implements ActionListener, WindowListener {

    JFrame frame;
    JPanel mainPanel;
    JPanel inputPanel;

    GridBagLayout mainLayout;
    //Layout inputLayout

    JScrollPane textPane;
    JTextArea textArea;

    JScrollPane inputPane;
    JTextArea inputArea;

    JButton sendButton;

    Conversation owner;

    public ConversationWin(Conversation owner) {
	this.owner = owner;
	createAndShowGUI();
    }

    private void createAndShowGUI() {
	GridBagConstraints c = new GridBagConstraints();;

	if (owner == null) 
	    frame = new JFrame("Convo win");
	else
	    frame = new JFrame("Conversation with " + owner.getName());

	frame.addWindowListener(this);
	mainPanel = new JPanel();
	frame.add(mainPanel);
	
	mainPanel.setLayout(new GridLayout(2, 1));

	textArea = new JTextArea();
	textPane = new JScrollPane(textArea);
	mainPanel.add(textPane, BorderLayout.CENTER);

	inputPanel = new JPanel();
	inputPanel.setLayout(new GridLayout(1, 2));

	inputArea = new JTextArea();
	inputPane = new JScrollPane(inputArea);
	inputPanel.add(inputPane, BorderLayout.SOUTH);

	sendButton = new JButton("Send");
	sendButton.addActionListener(this);
	inputPanel.add(sendButton);
	
	mainPanel.add(inputPanel);

	frame.setSize(450, 300);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setVisible(true);
    }

    public void appendText(String text) {
	textArea.append(text);
    }

    public void actionPerformed(ActionEvent e) {
	System.out.println("We want to send: " + inputArea.getText());
	appendText("me: " + inputArea.getText() + "\n");
	owner.sendMessage(inputArea.getText());
	inputArea.setText("");
    }

    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}

    public void windowClosing(WindowEvent e) {
	System.out.println("Window is closing!");
	owner.remove();
    }


    public static void main(String args[]) {
	ConversationWin test = new ConversationWin(null);
    }
}
