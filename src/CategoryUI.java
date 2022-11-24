import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Properties;

public class CategoryUI extends JFrame implements ActionListener {

    protected JButton category1;
    private JPanel panel1;
    protected JButton category2;
    protected JButton category3;
    private JButton button1;
    private JLabel chooseCategoryLabel;
    PrintWriter out;
    GameGui gameGui;
    Properties p;

    public CategoryUI(PrintWriter out) {
        this.out = out;
        setContentPane(panel1);
        setVisible(true);
        setSize(450, 500);
        setLocationRelativeTo(null);


        category1.addActionListener(this);
        category2.addActionListener(this);
        category3.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        // Steg 4: klienten skickar texten på den knapp som valdes
        out.println(button.getText());


        p = new Properties();

        try {
            p.load(new FileInputStream("src/Settings.properties"));
        } catch (Exception ex) {
            System.out.println("Filen kunde inte hittas");
        }

        int question = Integer.parseInt(p.getProperty("questions"));
        int rounds = Integer.parseInt(p.getProperty("rounds"));


        setVisible(false);


                /*
                hämta txt frågor
                nya jbutton knappar
                true/false sats
                 */
        // out.close();
    }
}




