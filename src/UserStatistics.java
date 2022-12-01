import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserStatistics extends JFrame {
    private JPanel panel1;
    protected JTextArea textArea;

    public UserStatistics(){
        setContentPane(panel1);
        setSize(300,530);
        setVisible(true);
        setResizable(false);


        try{

            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/UserStatistic.txt"));
            String line = null;
            List<String> list = new ArrayList<>();


            while ((line = bufferedReader.readLine()) != null){
                list.add(line);


            }

            Collections.sort(list,Collections.reverseOrder());

            for (int i = 0; i < 20; i++) {
                textArea.append(list.get(i) + "\n");

            }



        }catch (Exception e){

        }

    }

}
