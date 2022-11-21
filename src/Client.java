import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    String userName;

    public Client() {

        userName = JOptionPane.showInputDialog(null, "Ange ditt namn: ");
        // TODO: sätt titel userInput
        Welcome welcome = new Welcome();
        welcome.setWelcomeLabel("VÄLKOMMEN " + userName);

        this.start();
        
    }

    public void run() {


        try (
                Socket socket = new Socket("127.0.0.1", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            System.out.println("Klienttråd startad");
            out.println(userName);

            if (in.readLine().equals("1")) {
                System.out.println("spelare 1");
                Category category = new Category();

            } else {
                System.out.println("spelare 2");
                Welcome welcome = new Welcome();
                welcome.setWaitingLabel("väntar på att spelare väljer kategori");
            }

            while(true) {

            }



        } catch (
                UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        public static void main (String[]args){
            Client c = new Client();
        }
    }



