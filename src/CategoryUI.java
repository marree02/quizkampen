import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Properties;

public class CategoryUI extends JFrame {

    protected JButton category1;
    private JPanel panel1;
    protected JButton category2;
    protected JButton category3;
    private JButton button1;
    private JLabel chooseCategoryLabel;
    PrintWriter out;

    public CategoryUI(PrintWriter out) {
        this.out = out;
        setContentPane(panel1);
        setVisible(true);
        setSize(450,500);
        setLocationRelativeTo(null);

        Properties p = new Properties();

        try{
            p.load(new FileInputStream("src/Settings.properties"));
        }catch(Exception e){
            System.out.println("Filen kunde inte hittas");
        }
        category1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JButton button = (JButton) e.getSource();

                out.println(button.getText() + "\n");

                int question = Integer.parseInt(p.getProperty("questions"));
                int rounds = Integer.parseInt(p.getProperty("rounds"));
                /*
                hämta txt frågor
                nya jbutton knappar
                true/false sats
                 */
                out.close();
            }
        });
    }


}
