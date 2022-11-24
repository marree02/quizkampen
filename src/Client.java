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

    WaitingOnPlayerGUI waitingOnPlayerGUI = new WaitingOnPlayerGUI();

    String userName;

    boolean playerTurn;

    GameRoom game;

    GameGui gameGui;

    String correctAnswer;

    int numberOfQuestionsPerRound = 2;

    int questionCounter = 0;

    int numberOfRounds = 2;

    public Client() {
        waitingOnPlayerGUI.setVisible(false);
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


            System.out.println("Klienttråd startad");

            //Det första som händer - skickar userName till servern
            out.println(userName);


            //Steg 2: klienten tar emot om den är spelare ett eller två
            if (in.readLine().equals("1")) {
                playerTurn = true;
            } else {
                playerTurn = false;
            }

            if (playerTurn) { // Spelare vars tur det är att välja kategori

                welcomeGui.setVisible(false);

                categoryGui = new CategoryUI(out);

                categoryGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());

                // Steg 3: klienten tar emot 3 strängar med de kategorier som ska visas

                categoryGui.category1.setText(in.readLine());

                categoryGui.category2.setText(in.readLine());

                categoryGui.category3.setText(in.readLine());

            } else { // Spelare som väntar på att den andra ska välja kategori

                in.readLine(); in.readLine(); in.readLine();

                welcomeGui.setWaitingLabel("väntar på att motståndaren väljer kategori");

                //Väntar på att motståndaren valt kategori
                in.readLine();

                out.println("INGEN KATEGORI");

                }

            //Väntar på meddelande från servern om att kategori är vald och första ronden kan starta
            in.readLine();


            // fönstret görs osynligt 2ggr för spelare 1, TODO bör fixas

            welcomeGui.setVisible(false);

            while (questionCounter < numberOfQuestionsPerRound) {

                gameGui = new GameGui(out);

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
                System.out.println(in.readLine());
                System.out.println("nästa fråga");

                    gameGui.setVisible(false);

                    questionCounter++;

                }
                questionCounter = 0;
                roundCounter++;
                gameGui.setVisible(false);
                ResultsGUI resultsGUI = new ResultsGUI(out);
                in.readLine();
                resultsGUI.setVisible(false);
                if (!playerTurn) {
                    waitingOnPlayerGUI.setVisible(true);
                }
            }


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



