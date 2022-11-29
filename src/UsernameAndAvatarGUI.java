import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

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
        setSize(400, 500);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);



            userNameInput.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = userNameInput.getText();
                    System.out.println(text);

                }


            });

            continueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (continueButton == e.getSource()){
                        client.userName= userNameInput.getText(); // plockar texten
                        client.selectedAvatar= selectedAvatar; // plockar avatar
                        client.setProceed();

                    }
                    //out.println("SENDING USERNAME");?
                }


            });
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b1 == e.getSource()){
                    selectedAvatar = b1;

                }
                if (b2 == e.getSource()){
                    b2.setSelected(true);
                    b2.getIcon();
                }
                if (b3 == e.getSource()){
                    b3.setSelected(true);
                    b3.getIcon();
                }
                if (b4 == e.getSource()){
                    b4.setSelected(true);
                    b4.getIcon();
                }
                if (b5 == e.getSource()){
                    b5.setSelected(true);
                    b5.getIcon();
                }
                if (b6 == e.getSource()){
                    b6.setSelected(true);
                    b6.getIcon();
                }
            }


        });

    }

    public void disableAvatarButtons(){
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
    }

    public JTextField getUserNameInput(String userName) {
        return userNameInput;
    }

    public void setUserNameInput(JTextField userNameInput) {
        this.userNameInput = userNameInput;
    }






    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
