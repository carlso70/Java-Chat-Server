import javax.swing.*;
import java.io.IOException;

/**
 * Created by jimmycarlson on 2/12/16.
 */
public class Client {
    public static void main (String[] args) throws IOException {
        String name = JOptionPane.showInputDialog(null,"Enter your name.");
        new JChat(name, "224.0.0.7" , 9876);
    }
}
