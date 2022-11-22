import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Category extends JFrame {

    private JButton category1;
    private JPanel panel1;
    private JButton category2;
    private JButton category3;
    private JButton button1;
    private JLabel chooseCategoryLabel;

    public Category() {
        setContentPane(panel1);
        setVisible(true);
        setSize(450,500);
        setLocationRelativeTo(null);
    }


}
