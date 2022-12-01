import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ResultsGUI extends JFrame {
    Messages m;
    protected JPanel panel1;
    protected JButton continueButton;
    protected JLabel categoryLabel1;
    protected JLabel categoryLabel2;
    protected JLabel categoryLabel3;
    protected JLabel categoryLabel4;
    protected JLabel usernamePlayerLabel;
    protected JLabel usernameOpponentLabel;
    protected JLabel playerScoreRound1;
    protected JLabel playerScoreRound2;
    protected JLabel playerScoreRound3;
    protected JLabel playerScoreRound4;
    protected JLabel opponentScoreRound1;
    protected JLabel opponentScoreRound2;
    protected JLabel opponentScoreRound3;
    protected JLabel opponentScoreRound4;
    protected JLabel playerTotalScore;
    protected JLabel opponentTotalScore;
    protected JButton avatarImageButtonResult1;
    protected JButton avatarImageButtonResult2;
    protected JButton giveUpButton;
    protected JPanel p1;
    protected JPanel p2;
    protected JPanel p3;
    Client client;
    PrintWriter out;

    public ResultsGUI(PrintWriter out, Client client){
        this.client = client;
        this.out = out;
        setContentPane(panel1);
        setSize(500,600);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        giveUpButton.setEnabled(false);



        giveUpButton.addActionListener(e -> out.println(m.OPPONENT_GAVE_UP));
        continueButton.addActionListener(e -> out.println(m.CONTINUE_FROM_RESULTS));

    }
}
