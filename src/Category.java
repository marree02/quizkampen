import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class Category extends JFrame {

    private JButton category1;
    private JPanel panel1;
    private JButton category2;
    private JButton category3;

    public Category() {
        setContentPane(panel1);
        setVisible(true);
        setSize(300,500);
        setLocationRelativeTo(null);


        Properties p = new Properties();
        try{
            p.load(new FileInputStream("src/gameSettings.properties"));
        }catch (Exception e){
            System.out.println("Filen kunde inte hittas");
        }
        category1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }


}
