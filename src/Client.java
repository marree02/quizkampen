import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    WelcomeUI welcome;
    CategoryUI category;

    String userName;

    boolean playerTurn;
    GameRoom game;

    GameGui gameGui;

    public Client() {

        userName = JOptionPane.showInputDialog(null, "Ange ditt namn: ");
        welcome = new WelcomeUI();
        welcome.setWelcomeLabel("VÄLKOMMEN " + userName.toUpperCase());
        welcome.setTitle("QUIZKAMPEN " + userName.toUpperCase());

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
                playerTurn = true;
            } else {
                playerTurn = false;
            }

            if (playerTurn) {
                welcome.setVisible(false);
                System.out.println("spelare 1");
                category = new CategoryUI(out);
                category.setTitle("QUIZKAMPEN " + userName.toUpperCase());
                String categoryString = in.readLine();
                System.out.println(categoryString);
                category.category1.setText(categoryString);
                categoryString = in.readLine();
                System.out.println(categoryString);
                category.category2.setText(categoryString);
                categoryString = in.readLine();
                System.out.println(categoryString);
                category.category3.setText(categoryString);
            } else {
                System.out.println("spelare 2");
                welcome.setTitle("QUIZKAMPEN " + userName.toUpperCase());
                welcome.setWaitingLabel("väntar på att spelare väljer kategori");
            }
            if(in.readLine().equals("Spelare 1 har valt kategori")){
                if (!playerTurn) {
                    out.println("Spelare 1 har valt kategori");
                }

                gameGui = new GameGui();
                welcome.setVisible(false);
            }



            System.out.println("Spelare redo för fråga");


            while(true) {

            }


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        public static void main (String[]args){
            Client c = new Client();
        }
    }



