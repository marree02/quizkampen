import javax.swing.*;

public class Welcome extends JFrame {

    private JLabel welcomeLabel;
    private JLabel waitingLabel;
    private JPanel welcomePanel;
    private JButton button1;

    public Welcome() {

        setContentPane(welcomePanel);
        setVisible(true);
        setSize(400,500);
        setLocationRelativeTo(null);

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
