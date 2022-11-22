import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

public class Category extends JFrame {

    protected JButton category1;
    private JPanel panel1;
    protected JButton category2;
    protected JButton category3;
    private JButton button1;
    private JLabel chooseCategoryLabel;

    public Category() {
        setContentPane(panel1);
        setVisible(true);
        setSize(450,500);
        setLocationRelativeTo(null);


        Properties p = new Properties();
        try{
            p.load(new FileInputStream("src/Settings.properties"));
        }catch (Exception e){
            System.out.println("Filen kunde inte hittas");
        }
        category1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*hämta txt filen
                nya jbuttons med svar
                ny actionevent
                if-sats true/false visar rätt svar
                */
                int rounds;
                int questions;
                rounds = Integer.parseInt(p.getProperty("rounds"));
                questions = Integer.parseInt(p.getProperty("questions"));
            }
        });
    }


}
