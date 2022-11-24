import javax.swing.*;
import java.awt.*;


public class GameGui extends JFrame {
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
    protected String correctAnswer;
    protected boolean userClickedContinue = false;

    public GameGui() {

        setContentPane(panel1);
        setVisible(true);
        setSize(550,600);
        setLocationRelativeTo(null);
        continueButton.setVisible(false);
        setResizable(false);

        button1.addActionListener(e -> {
            if(button1.getText().equals(correctAnswer)) {
                button1.setBackground(Color.green);
            }
            else {
                button1.setBackground(Color.red);
                setCorrectButton();
            }
            setButtonDefault();
        });

        button2.addActionListener(e -> {
            if(button2.getText().equals(correctAnswer))
                button2.setBackground(Color.green);

            else {
                button2.setBackground(Color.red);
                setCorrectButton();
            }
            setButtonDefault();
        });

        button3.addActionListener(e -> {
            if(button3.getText().equals(correctAnswer)) {
                button3.setBackground(Color.green);
            }
            else {
                button3.setBackground(Color.red);
                setCorrectButton();
            }
            setButtonDefault();
        });

        button4.addActionListener(e -> {
            if(button4.getText().equals(correctAnswer)) {
                button4.setBackground(Color.green);
            }
            else {
                button4.setBackground(Color.red);
                setCorrectButton();
            }
            setButtonDefault();
        });

        continueButton.addActionListener(e -> {
            userClickedContinue = (true);
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

    public void setButtonDefault(){
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
       // continueButton.setEnabled(true);
        continueButton.setVisible(true);
    }
}
