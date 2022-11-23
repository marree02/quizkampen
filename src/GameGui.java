import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    protected String correctAnswer;

    public GameGui() {
        setContentPane(panel1);
        setVisible(true);
        setSize(700,700);
        setLocationRelativeTo(null);



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button1.getText().equals(correctAnswer))
                    button1.setBackground(Color.green);
                else
                    button1.setBackground(Color.red);

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button2.getText().equals(correctAnswer))
                    button2.setBackground(Color.green);
                else {
                    button2.setBackground(Color.red);
                    setCorrectButton();
                }


            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button3.getText().equals(correctAnswer))
                    button3.setBackground(Color.green);
                else {
                    button3.setBackground(Color.red);
                    setCorrectButton();
                }

            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button4.getText().equals(correctAnswer))
                    button4.setBackground(Color.green);
                else {
                    button4.setBackground(Color.red);
                    setCorrectButton();
                }

            }
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
}
