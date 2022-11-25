import javax.swing.*;

public class WaitingOnPlayerGUI extends JFrame {


    private JPanel panel1;

    Client client;

    public WaitingOnPlayerGUI(Client client){
        this.client = client;
        setVisible(true);
        setSize(300,300);
        setContentPane(panel1);

    }
}
