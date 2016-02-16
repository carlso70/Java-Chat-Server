/**
 * Created by jimmycarlson on 2/12/16.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Reciever implements Runnable {
    Thread activity = new Thread(this);
    MulticastSocket mSocket;
    JTextArea text;
    public Reciever(MulticastSocket sock,  JTextArea textArea) {
        mSocket = sock;
        text = textArea;
        activity.start();
    }
    public void run() {
        byte[] data = new byte[1024];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length);
                mSocket.receive(packet);
                String mess = new String(data, 0, packet.getLength());
                text.append(mess + "\n");
            } catch (IOException e) {
                break;
            }
        }
    }
}
