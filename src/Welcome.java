import javax.swing.*;

public class Welcome extends JFrame {

    private JLabel titelLabel;
    private JLabel welcomeLabel;
    private JLabel waitingLabel;
    private JPanel welcomePanel;

    public Welcome() {

        setContentPane(welcomePanel);
        setVisible(true);
        setSize(300,500);
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
