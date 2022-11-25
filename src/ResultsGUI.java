import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ResultsGUI extends JFrame {
    private JPanel panel1;
    protected JButton fortsättButton;
    private JLabel categoryLabel1;
    private JLabel categoryLabel2;
    private JLabel categoryLabel3;
    private JLabel CategoryLabel4;
    private JLabel usernamePlayer1Label;
    private JLabel userNamePlayer2Label;
    private JLabel resultsLabel;
    private JLabel whosTurnLabel;
    private JLabel player1Icon;
    private JLabel player2Icon;
    PrintWriter out;

    public ResultsGUI(PrintWriter out){
        this.out = out;
        setContentPane(panel1);
        setVisible(true);
        setSize(400,600);



        fortsättButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("CONTINUE FROM RESULTS");
            }
        });
    }
}
