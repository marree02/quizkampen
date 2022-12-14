import javax.swing.*;

public class WelcomeUI extends JFrame {

    private JLabel welcomeLabel;
    private JLabel waitingLabel;
    private JPanel welcomePanel;
    private JButton button1;
    Client client;

    public WelcomeUI(Client client) {

        this.client = client;
        setContentPane(welcomePanel);
        setSize(500,400);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public void setWelcomeLabel(String text) {
        this.welcomeLabel.setText(text);
    }

    public JLabel getWaitingLabel() {
        return waitingLabel;
    }

    public void setWaitingLabel(String text) {
        this.waitingLabel.setText(text);
    }
}
