import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;


public class GameGui extends JFrame implements ActionListener {

    Messages m = new Messages();

    private JPanel panel1;
    protected JButton button1;
    protected JButton button2;
    protected JButton button3;
    protected JButton button4;
    protected JLabel categorylabel;
    protected JLabel questionLabel;
    protected JLabel thisPLayerUserNameLabel;
    protected JLabel opponentUserNameLabel;
    private JButton continueButton;
    private JProgressBar progressBar1;

    private Timer timer = new Timer(60, this);

    protected String correctAnswer;
    protected JButton avatarImageButton1;
    protected JButton avatarImageButton2;
    PrintWriter out;
    Client client;

    public GameGui(PrintWriter out, Client client) {

        this.out = out;
        this.client = client;

        timer.start();

        setContentPane(panel1);

        setSize(600,600);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
        continueButton.setEnabled(false);
        setResizable(false);


        button1.addActionListener(e -> {
            if(button1.getText().equals(correctAnswer)) {
                button1.setBackground(Color.green);
                client.roundScore++;
            }
            else {
                button1.setBackground(Color.red);
                setCorrectButton();
            }
            disableButtons();
        });

        button2.addActionListener(e -> {
            if(button2.getText().equals(correctAnswer)) {
                button2.setBackground(Color.green);
                client.roundScore++;
            } else {
                button2.setBackground(Color.red);
                setCorrectButton();
            }
            disableButtons();
        });

        button3.addActionListener(e -> {
            if(button3.getText().equals(correctAnswer)) {
                button3.setBackground(Color.green);
                client.roundScore++;
            }
            else {
                button3.setBackground(Color.red);
                setCorrectButton();
            }
            disableButtons();
        });

        button4.addActionListener(e -> {
            if(button4.getText().equals(correctAnswer)) {
                button4.setBackground(Color.green);
                client.roundScore++;
            }
            else {
                button4.setBackground(Color.red);
                setCorrectButton();
            }
            disableButtons();
        });

        continueButton.addActionListener(e -> {
            out.println(m.QUESTION_ANSWERED);
        });

    }

    public void setCorrectButton(){
        if(button1.getText().equals(correctAnswer))
            button1.setBackground(Color.green);
        else if (button2.getText().equals(correctAnswer)) {
            button2.setBackground(Color.green);
        }
        else if (button3.getText().equals(correctAnswer)) {
            button3.setBackground(Color.green);
        }
        else if (button4.getText().equals(correctAnswer)) {
            button4.setBackground(Color.green);
        }
    }

    public void disableButtons(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button2.setEnabled(false);
        continueButton.setEnabled(true);

        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (progressBar1.getValue() == 100) {

            setCorrectButton();
            disableButtons();

        } else {

            progressBar1.setValue(progressBar1.getValue()+1);
            if (progressBar1.getValue() > 80) {
                progressBar1.setBackground(Color.red);
            }

        }

    }
}
