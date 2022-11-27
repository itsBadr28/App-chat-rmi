package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class ClientRMIGUI extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JPanel textPanel, inputPanel;
    private JTextField textField;
    private String name, message;
    private Font meiryoFont = new Font("Meiryo", Font.PLAIN, 14);
    private Border blankBorder = BorderFactory.createEmptyBorder(10,10,20,10);//top,r,b,l
    private ChatClient3 chatClient;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    protected JTextArea textArea, userArea;
    protected JFrame frame;
    protected JButton privateMsgButton, runButton, sendButton;
    protected JPanel clientPanel, userPanel;


    public static void main(String args[]){
        try{
            for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception e){
        }
        new ClientRMIGUI();
    }

    public ClientRMIGUI(){

        frame = new JFrame("Client Chat App");

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                if(chatClient != null){
                    try {
                        sendMessage("Bye all, I am leaving");
                        chatClient.serverIF.leaveChat(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });
        // Reda
        Container c = getContentPane();
        JPanel outerPanel = new JPanel(new BorderLayout());

        outerPanel.add(getInputPanel(), BorderLayout.CENTER);
        outerPanel.add(getTextPanel(), BorderLayout.NORTH);
        outerPanel.add(makeButtonPanel(), BorderLayout.SOUTH);

        c.setLayout(new BorderLayout());
        c.add(outerPanel, BorderLayout.CENTER);
        c.add(getUsersPanel(), BorderLayout.NORTH);


        frame.add(c);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setLocation(250, 250);
        textField.requestFocus();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public JPanel getTextPanel(){
        String welcome = "Welcome enter your name and press Run .\n";
        textArea = new JTextArea(welcome, 14, 34);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setFont(meiryoFont);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel = new JPanel();
        textPanel.add(scrollPane);

        textPanel.setFont(new Font("Meir yo", Font.PLAIN, 14));
        return textPanel;
    }

    public JPanel getInputPanel(){
        inputPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        inputPanel.setBorder(blankBorder);
        textField = new JTextField();
        textField.setFont(meiryoFont);
        inputPanel.add(textField);
        return inputPanel;
    }


    public JPanel getUsersPanel(){

        userPanel = new JPanel(new BorderLayout());
        String  userStr = "Users List";

        JLabel userLabel = new JLabel(userStr, JLabel.CENTER);
        userPanel.add(userLabel, BorderLayout.NORTH);
        userLabel.setFont(new Font("Meiryo", Font.HANGING_BASELINE, 16));

        String[] noClientsYet = {"Chat is empty"};
        setClientPanel(noClientsYet);

        clientPanel.setFont(meiryoFont);
//        userPanel.setBorder(blankBorder);
//        userPanel.add(clientPanel, BorderLayout.CENTER);
        //size reda
        userPanel.setPreferredSize(new Dimension(100, 50));
        return userPanel;
    }

    /**
     * Populate current user panel with a
     * selectable list of currently connected users
     * @param currClients
     */
    public void setClientPanel(String[] currClients) {
        clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();

        for(String s : currClients){
            listModel.addElement(s);
        }
        if(currClients.length > 2){
            privateMsgButton.setEnabled(true);
        }

        //Create the list and put it in a scroll pane.
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        list.setFont(meiryoFont);
        JScrollPane listScrollPane = new JScrollPane(list);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }


    public JPanel makeButtonPanel() {
        sendButton = new JButton("Send Message");
        sendButton.addActionListener(this);
        sendButton.setEnabled(false);
        //size button
        sendButton.setPreferredSize(new Dimension(20, 20));

        privateMsgButton = new JButton("Send Private Message");
        privateMsgButton.addActionListener(this);
        privateMsgButton.setEnabled(false);

        runButton = new JButton("Run ");
        runButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(privateMsgButton);
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(runButton);
        buttonPanel.add(sendButton);

        return buttonPanel;
    }

    /**
     * Action handling on the buttons
     */
    @Override
    public void actionPerformed(ActionEvent e){

        try {
            //get connected to chat service
            if(e.getSource() == runButton){
                name = textField.getText();
                if(name.length() != 0){
                    frame.setTitle(name + "'s console ");
                    textField.setText("");
                    textArea.append("username : " + name + " connecting to chat...\n");
                    getConnected(name);
                    if(!chatClient.connectionProblem){
                        runButton.setEnabled(false);
                        sendButton.setEnabled(true);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Enter your name to Start");
                }
            }

            //get text and clear textField
            if(e.getSource() == sendButton){
                message = textField.getText();
                textField.setText("");
                sendMessage(message);
                System.out.println("Sending message : " + message);
            }

            if(e.getSource() == privateMsgButton){
                int[] privateList = list.getSelectedIndices();

                for(int i=0; i<privateList.length; i++){
                    System.out.println("selected index :" + privateList[i]);
                }
                message = textField.getText();
                textField.setText("");
                sendPrivate(privateList);
            }

        }
        catch (RemoteException remoteExc) {
            remoteExc.printStackTrace();
        }

    }//end actionPerformed

    // --------------------------------------------------------------------

    /**
     * Send a message, to be relayed to all chatters
     * @param chatMessage
     * @throws RemoteException
     */
    private void sendMessage(String chatMessage) throws RemoteException {
        chatClient.serverIF.updateChat(name, chatMessage);
    }

    private void sendPrivate(int[] privateList) throws RemoteException {
        String privateMessage = "[PM from " + name + "] :" + message + "\n";
        chatClient.serverIF.sendPM(privateList, privateMessage);
    }

    /**
     * Make the connection to the chat server
     * @param userName
     * @throws RemoteException
     */
    private void getConnected(String userName) throws RemoteException{
        //remove whitespace and non word characters to avoid malformed url
        String cleanedUserName = userName.replaceAll("\\s+","_");
        cleanedUserName = userName.replaceAll("\\W+","_");
        try {
            chatClient = new ChatClient3(this, cleanedUserName);
            chatClient.startClient();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}//end class










