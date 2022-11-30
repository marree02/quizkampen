import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerLooserGUI extends JFrame {
    private JPanel panel1;
    protected JLabel winnerOrLooserLabel;
    private JButton exitButton;
    protected JPanel winOrLoseField;
    protected JPanel p1;


    public WinnerLooserGUI(){
        setContentPane(panel1);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(500,500);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
