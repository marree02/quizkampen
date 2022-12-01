import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class WinnerLooserGUI extends JFrame {
    private JPanel panel1;
    protected JLabel winnerOrLooserLabel;
    protected JButton exitButton;
    protected JPanel winOrLoseField;
    protected JPanel p1;
    protected JButton statisticButton;




    public WinnerLooserGUI(Client client){

        setContentPane(panel1);
        setVisible(true);
        setSize(700,300);
        if (client.windowCentered) setLocationRelativeTo(null);
        setResizable(false);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                System.exit(0);
            }
        });
        statisticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserStatistics userStatistics = new UserStatistics();
            }
        });
    }
}
