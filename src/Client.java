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

    WaitingOnPlayerGUI waitingOnPlayerGUI;

    String userName;

    boolean playerTurn;

    GameRoom game;

    GameGui gameGui;

    String correctAnswer;

    int numberOfQuestionsPerRound = 2;

    int roundCounter = 0;

    int questionCounter = 0;

    int numberOfRounds = 2;

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
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))

        ) {


            out.println("GET PLAYER 1 OR 2");


            if (in.readLine().equals("1")) {
                playerTurn = true;
            } else {
                playerTurn = false;
            }


            out.println("SENDING USERNAME");
            out.println(userName);


            if (playerTurn) {
                welcomeGui.setVisible(false);
                categoryGui = new CategoryUI(out);
                categoryGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());
                out.println("REQUEST CATEGORIES");
                categoryGui.category1.setText(in.readLine());
                categoryGui.category2.setText(in.readLine());
                categoryGui.category3.setText(in.readLine());
                out.println("REQUEST NEW ROUND");
                in.readLine();
                categoryGui.setVisible(false);
            }

            if (!playerTurn) {
                welcomeGui.setVisible(true);
                welcomeGui.setWaitingLabel("väntar på att motståndaren väljer kategori");
                out.println("REQUEST NEW ROUND");
                in.readLine();
                welcomeGui.setVisible(false);
            }

            while(true) {

                gameGui = new GameGui(out);
                out.println("GET QUESTION");
                gameGui.thisPLayerUserNameLabel.setText(userName);
                gameGui.opponentUserNameLabel.setText(in.readLine());
                gameGui.categorylabel.setText("KATEGORI: " + in.readLine());
                gameGui.questionLabel.setText(in.readLine());
                gameGui.button1.setText(in.readLine());
                gameGui.button2.setText(in.readLine());
                gameGui.button3.setText(in.readLine());
                gameGui.button4.setText(in.readLine());
                gameGui.correctAnswer = in.readLine();

                in.readLine();

            }




            /*




                    // Läser in från servern
                    gameGui.thisPLayerUserNameLabel.setText(userName);
                    gameGui.opponentUserNameLabel.setText(in.readLine());
                    gameGui.categorylabel.setText("KATEGORI: " + in.readLine());
                    gameGui.questionLabel.setText(in.readLine());
                    gameGui.button1.setText(in.readLine());
                    gameGui.button2.setText(in.readLine());
                    gameGui.button3.setText(in.readLine());
                    gameGui.button4.setText(in.readLine());
                    gameGui.correctAnswer = in.readLine();

                    System.out.println("Spelare redo för fråga");

                    //Väntar på nästa fråga från servern
                    in.readLine();

                    gameGui.setVisible(false);

                    questionCounter++;

                }
                questionCounter = 0;
                roundCounter++;
                gameGui.setVisible(false);
                ResultsGUI resultsGUI = new ResultsGUI(out);
                in.readLine();
                resultsGUI.setVisible(false);

                if (!playerTurn && questionCounter<2 && roundCounter<2) {
                    waitingOnPlayerGUI.setVisible(true);
                }

                // Få till så WinnerLooserGUI kommer upp efter sista rundan detta funkar inte just här men något liknande kanske fungerar
                //if(roundCounter>=2 && questionCounter>=2){
                //    WinnerLooserGUI winnerLooserGUI = new WinnerLooserGUI();
               // }
            }

        */

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client c = new Client();

    }
}



