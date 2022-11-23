import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    WelcomeUI welcomeGui;
    CategoryUI categoryGui;

    String userName;

    boolean playerTurn;
    GameRoom game;

    GameGui gameGui;

    public Client() {

        userName = JOptionPane.showInputDialog(null, "Ange ditt namn: ");
        welcomeGui = new WelcomeUI();
        welcomeGui.setWelcomeLabel("VÄLKOMMEN " + userName.toUpperCase());
        welcomeGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());

        this.start();
    }

    public void run() {


        try (
                Socket socket = new Socket("127.0.0.1", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))

        ) {


            System.out.println("Klienttråd startad");

            //Det första som händer - skickar userName till servern
            out.println(userName);


            //Steg 2: klienten tar emot om den är spelare ett eller två
            if (in.readLine().equals("1")) {
                playerTurn = true;
            } else {
                playerTurn = false;
            }

            if (playerTurn) { // Spelare 1

                welcomeGui.setVisible(false);

                categoryGui = new CategoryUI(out);

                categoryGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());

                // Steg 3: klienten tar emot 3 strängar med de kategorier som ska visas
                String categoryString = in.readLine();

                categoryGui.category1.setText(categoryString);

                categoryString = in.readLine();

                categoryGui.category2.setText(categoryString);

                categoryString = in.readLine();

                categoryGui.category3.setText(categoryString);

            } else { // Spelare 2

                welcomeGui.setWaitingLabel("väntar på att spelare väljer kategori");

            }

            if(in.readLine().equals("KATEGORI VALD")){
                if (!playerTurn) {
                    out.println("KATEGORI VALD");
                }

                gameGui = new GameGui();
                welcomeGui.setVisible(false);
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



