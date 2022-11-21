import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {


    public Client() {

        String userInput = JOptionPane.showInputDialog(null, "Ange ditt namn: ");
        // TODO: sätt titel userInput

        this.start();


    }

    public void run() {


        try (
                Socket socket = new Socket("127.0.0.1", 10667);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {


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



