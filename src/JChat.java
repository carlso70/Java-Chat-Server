import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by jimmycarlson on 2/12/16.
 */
public class JChat extends JFrame implements ActionListener {
    String name;
    InetAddress iadr;
    int port;
    MulticastSocket mSocket;
    JTextArea text = new JTextArea();
    JScrollPane scroll = new JScrollPane(text);
    JTextField write = new JTextField();
    JButton quit = new JButton("Exit");

    public JChat(String username, String groupAd , int port) throws IOException {
        name = username;
        iadr = InetAddress.getByName(groupAd);
        this.port = port;
        mSocket = new MulticastSocket(port);
        mSocket.joinGroup(iadr);
        new Reciever(mSocket, text);
        sendMessenge("Online");
        setTitle("Chatting with " + name);
        text.setEditable(true);
        add(quit, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(write, BorderLayout.SOUTH);
        quit.addActionListener(this);
        write.addActionListener(this);
        setSize(600,275);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void sendMessenge(String s){
        byte data[] = (name + ": " + s).getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,iadr,port);
        try {
            mSocket.send(packet);
        } catch (IOException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Data overflow!");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == write) {
            sendMessenge(write.getText());
            write.setText("");
        }
        if (e.getSource() == quit) {
            try {
                sendMessenge(name + " Offline");
                mSocket.leaveGroup(iadr);
            }catch (IOException i) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Data overflow , connection error");
            }
            mSocket.close();
            dispose();
            System.exit(0);
        }
    }
}
