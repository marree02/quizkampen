import javax.swing.*;

public class WaitingOnPlayerGUI extends JFrame {


    private JPanel panel1;

    Client client;

    public WaitingOnPlayerGUI(Client client){
        this.client = client;
        setContentPane(panel1);
        setSize(300,300);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
    }
}
