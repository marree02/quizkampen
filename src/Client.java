import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class Client extends Thread {

    WelcomeUI welcomeGui;

    CategoryUI categoryGui;

    GameGui gameGui;

    ResultsGUI resultsGUI;

    String userName;

    String opponentUserName;

    int roundScore = 0;

    int playerTotalScore = 0;

    int totalScoreOpponent = 0;

    boolean playerTurn;

    boolean windowCentered;

    int numberOfQuestionsPerRound;
    int roundCounter = 0;
    int questionCounter = 0;
    int numberOfRounds;




    public Client() {

        userName = JOptionPane.showInputDialog(null, "Ange ditt namn: ");
        welcomeGui = new WelcomeUI(this);
        welcomeGui.setWelcomeLabel("VÄLKOMMEN " + userName.toUpperCase());
        welcomeGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());

        // Öppnar upp användarstatistik. Bör kanske vara en knapp man kan öppna upp på någon av de existerande GUIs vi har?
        // Vart vet jag inte ännu. Förslagsvis VälkomstGUI och WinnerLooserGUI

        /*UserStatistics userStatistics = new UserStatistics();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/UserStatistic.txt"));
            while (true){
                String line = bufferedReader.readLine();
                if (line == null){
                    break;
                }
                    userStatistics.textArea.append(line + "\n");

            }
        }catch (Exception e){
            //TODO: handle
        }
        */

        Properties p = new Properties();

       try{
           p.load(new FileInputStream("src/Settings.properties"));
       }catch(Exception e){
           System.out.println("filen kunde ej hittas");
       }

        this.numberOfQuestionsPerRound = Integer.parseInt(p.getProperty("questions"));
        this.numberOfRounds = Integer.parseInt(p.getProperty("rounds"));
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
                windowCentered = false;
            } else {
                playerTurn = false;
                windowCentered = true;
            }

            out.println("SENDING USERNAME");
            out.println(userName);

            welcomeGui.dispose();

            while (roundCounter < numberOfRounds) {

                if (playerTurn) {
                    categoryGui = new CategoryUI(out, this);
                    categoryGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());
                    out.println("GET CATEGORIES");
                    categoryGui.category1.setText(in.readLine());
                    categoryGui.category2.setText(in.readLine());
                    categoryGui.category3.setText(in.readLine());
                    if(in.readLine().equals("CONTINUE"));
                    categoryGui.dispose();
                }

                if (!playerTurn) {
                    welcomeGui = new WelcomeUI(this);
                    welcomeGui.setWelcomeLabel("VÄLKOMMEN " + userName.toUpperCase());
                    welcomeGui.setWaitingLabel("väntar på att motståndaren väljer kategori");
                    out.println("REQUEST NEW ROUND");
                    while (in.readLine().equals("NO")) {
                        out.println("REQUEST NEW ROUND");
                    }
                    welcomeGui.dispose();

                }

                out.println("GENERATE QUESTIONS FOR NEXT ROUND");


                while (questionCounter < numberOfQuestionsPerRound) {

                    gameGui = new GameGui(out, this);
                    out.println("GET QUESTION");
                    gameGui.thisPLayerUserNameLabel.setText(userName);
                    opponentUserName = in.readLine();
                    gameGui.opponentUserNameLabel.setText(opponentUserName);
                    gameGui.categorylabel.setText("KATEGORI: " + in.readLine());
                    gameGui.questionLabel.setText(in.readLine());
                    gameGui.button3.setText(in.readLine());
                    gameGui.button1.setText(in.readLine());
                    gameGui.button4.setText(in.readLine());
                    gameGui.button2.setText(in.readLine());
                    gameGui.correctAnswer = in.readLine();

                    if(in.readLine().equals("QUESTION ANSWERED"));
                    gameGui.dispose();
                    questionCounter++;
                }

                out.println("SEND SCORE FOR ROUND");
                out.println(roundScore);

                out.println("GET CURRENT CATEGORY");
                String currentCategory = in.readLine();


                out.println("GET SCORES");
                String myScoreForThisRound = in.readLine();
                String opponentScoreForThisRound = in.readLine();
                playerTotalScore = playerTotalScore + roundScore;

                if (roundCounter == 0) {
                    resultsGUI = new ResultsGUI(out, this);
                    resultsGUI.usernamePlayerLabel.setText(userName);
                    resultsGUI.usernameOpponentLabel.setText(opponentUserName);
                    resultsGUI.setTitle(userName);
                    resultsGUI.categoryLabel1.setText(currentCategory);
                    resultsGUI.playerScoreRound1.setText(myScoreForThisRound);
                    resultsGUI.opponentScoreRound1.setText(opponentScoreForThisRound);
                }

                if (roundCounter == 1) {
                    resultsGUI.setVisible(true);
                    resultsGUI.categoryLabel2.setText(currentCategory);
                    resultsGUI.playerScoreRound2.setText(myScoreForThisRound);
                    resultsGUI.opponentScoreRound2.setText(opponentScoreForThisRound);

                }
                if (roundCounter == 2) {
                    resultsGUI.setVisible(true);
                    resultsGUI.categoryLabel3.setText(currentCategory);
                    resultsGUI.playerScoreRound3.setText(myScoreForThisRound);
                    resultsGUI.opponentScoreRound3.setText(opponentScoreForThisRound);
                }

                if (roundCounter == 3) {
                    resultsGUI.setVisible(true);
                    resultsGUI.categoryLabel4.setText(currentCategory);
                    resultsGUI.playerScoreRound4.setText(myScoreForThisRound);
                    resultsGUI.opponentScoreRound4.setText(opponentScoreForThisRound);
                }

                out.println("CHECK IF OPPONENT SCORE IS IN");
                while(in.readLine().equals("NO")) {
                    Thread.sleep(500);
                    out.println("CHECK IF OPPONENT SCORE IS IN");
                }

                out.println("GET SCORES");
                myScoreForThisRound = in.readLine();
                opponentScoreForThisRound = in.readLine();
                totalScoreOpponent = totalScoreOpponent + Integer.parseInt(opponentScoreForThisRound);
                resultsGUI.playerTotalScore.setText(String.valueOf(playerTotalScore));
                resultsGUI.opponentTotalScore.setText(String.valueOf(totalScoreOpponent));

                if (roundCounter == 0) {
                    resultsGUI.opponentScoreRound1.setText(opponentScoreForThisRound);
                    if(Integer.parseInt(myScoreForThisRound) > Integer.parseInt(opponentScoreForThisRound)){
                        resultsGUI.playerScoreRound1.setForeground(Color.green);
                        resultsGUI.opponentScoreRound1.setForeground(Color.red);
                    }else{
                        resultsGUI.playerScoreRound1.setForeground(Color.red);
                        resultsGUI.opponentScoreRound1.setForeground(Color.green);
                    }
                }

                if (roundCounter == 1) {
                    resultsGUI.opponentScoreRound2.setText(opponentScoreForThisRound);
                    if(Integer.parseInt(myScoreForThisRound) > Integer.parseInt(opponentScoreForThisRound)){
                        resultsGUI.playerScoreRound2.setForeground(Color.green);
                        resultsGUI.opponentScoreRound2.setForeground(Color.red);
                    }else{
                        resultsGUI.playerScoreRound2.setForeground(Color.red);
                        resultsGUI.opponentScoreRound2.setForeground(Color.green);
                    }
                }

                if (roundCounter == 2) {
                    resultsGUI.opponentScoreRound3.setText(opponentScoreForThisRound);
                    if(Integer.parseInt(myScoreForThisRound) > Integer.parseInt(opponentScoreForThisRound)){
                        resultsGUI.playerScoreRound3.setForeground(Color.green);
                        resultsGUI.opponentScoreRound3.setForeground(Color.red);
                    } else {
                        resultsGUI.playerScoreRound3.setForeground(Color.red);
                        resultsGUI.opponentScoreRound3.setForeground(Color.green);
                    }
                }

                if (roundCounter == 3) {
                    resultsGUI.opponentScoreRound4.setText(opponentScoreForThisRound);
                    if(Integer.parseInt(myScoreForThisRound) > Integer.parseInt(opponentScoreForThisRound)){
                        resultsGUI.playerScoreRound4.setForeground(Color.green);
                        resultsGUI.opponentScoreRound4.setForeground(Color.red);
                    }else{
                        resultsGUI.playerScoreRound4.setForeground(Color.red);
                        resultsGUI.opponentScoreRound4.setForeground(Color.green);
                    }
                }

                resultsGUI.continueButton.setEnabled(true);

                if(in.readLine().equals("CONTINUE"));

                resultsGUI.continueButton.setEnabled(false);
                resultsGUI.setVisible(false);

                roundScore = 0;
                playerTurn = !playerTurn; // Byte av spelare
                questionCounter = 0;

                roundCounter++;
            }
            WinnerLooserGUI winnerLooserGUI = new WinnerLooserGUI(this);

            if(totalScoreOpponent > playerTotalScore){
                winnerLooserGUI.winnerOrLooserLabel.setText("Du förlorar!");
            } else if (totalScoreOpponent < playerTotalScore) {
                winnerLooserGUI.winnerOrLooserLabel.setText("Du vann!");
            } else {
                winnerLooserGUI.winnerOrLooserLabel.setText("oavgjort!");
            }

            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("src/Userstatistic.txt", true)));
            printWriter.println(userName + " -" + playerTotalScore);
            printWriter.close();

            /*

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        Client c = new Client();

    }
}



