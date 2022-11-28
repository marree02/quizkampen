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
    Client client;

    public CategoryUI(PrintWriter out, Client client) {
        this.client = client;
        this.out = out;
        setContentPane(panel1);
        setSize(450, 500);
        if (client.windowCentered) setLocationRelativeTo(null);
        setVisible(true);

        category1.addActionListener(this);
        category2.addActionListener(this);
        category3.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        // Steg 4: klienten skickar texten på den knapp som valdes
        out.println("SET CATEGORY");
        out.println(button.getText());
    }
}




