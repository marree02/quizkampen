import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UsernameAndAvatarGUI extends JFrame {
    protected JPanel Panel1;
    protected JTextField userNameInput;
    protected JLabel usernameHeadLable;
    protected JButton quizlogoHeadLable;
    protected JButton b1;
    protected JButton b2;
    protected JButton b3;
    protected JButton b4;
    protected JButton b5;
    protected JButton b6;
    protected JButton continueButton;
    protected JButton selectedAvatar;

    PrintWriter out;
    Client client;


    public UsernameAndAvatarGUI(Client client) {
        this.client = client;
        this.out = out;

        setContentPane(Panel1);
        setSize(550, 600);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);
        selectedAvatar = b1;


        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (continueButton == e.getSource()) {
                    client.userName = userNameInput.getText(); // plockar texten
                    client.selectedAvatar = selectedAvatar; // plockar avatar
                    client.setProceed();

                }
            }


        });
        b1.addActionListener(e -> {
            if (b1 == e.getSource()) {
                selectedAvatar = b1;
                b1.setBorderPainted(true);
                client.selectedAvatarNumber = "1";
            }
        });

        b2.addActionListener(e -> {
            if (b2 == e.getSource()) {
                selectedAvatar = b2;
                b2.setBorderPainted(true);
                client.selectedAvatarNumber = "2";
            }
        });

        b3.addActionListener(e -> {
            if (b3 == e.getSource()) {
                selectedAvatar = b3;
                b3.setBorderPainted(true);
                client.selectedAvatarNumber = "3";
            }
        });

        b4.addActionListener(e -> {
            if (b4 == e.getSource()) {
                selectedAvatar = b4;
                b4.setBorderPainted(true);
                client.selectedAvatarNumber = "4";
            }
        });

        b5.addActionListener(e -> {
            if (b5 == e.getSource()) {
                selectedAvatar = b5;
                b5.setBorderPainted(true);
                client.selectedAvatarNumber = "5";
            }
        });

        b6.addActionListener(e -> {
            if (b6 == e.getSource()) {
                selectedAvatar = b6;
                b6.setBorderPainted(true);
                client.selectedAvatarNumber = "6";
            }
        });


    }

    public JTextField getUserNameInput(String userName) {
        return userNameInput;
    }

    public void setUserNameInput(JTextField userNameInput) {
        this.userNameInput = userNameInput;
    }

    public void disableAvatarButtons() {
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
    }

}

