import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ResultsGUI extends JFrame {
    protected JPanel panel1;
    protected JButton continueButton;
    protected JLabel categoryLabel1;
    protected JLabel categoryLabel2;
    protected JLabel categoryLabel3;
    protected JLabel categoryLabel4;
    protected JLabel usernamePlayerLabel;
    protected JLabel usernameOpponentLabel;
    private JLabel player1Icon;
    private JLabel player2Icon;
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
        setSize(400,600);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        giveUpButton.setEnabled(false);



        giveUpButton.addActionListener(e -> out.println("OPPONENT GAVE UP"));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("CONTINUE FROM RESULTS");
            }
        });

    }
}
