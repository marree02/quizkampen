import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Client extends Thread {

    UsernameAndAvatarGUI usernameAndAvatarGUI;
    WelcomeUI welcomeGui;

    CategoryUI categoryGui;

    GameGui gameGui;

    ResultsGUI resultsGUI;

    String userName;
    String opponentUserName;

    JButton selectedAvatar;

    String selectedAvatarNumber = "1";

    String opponentSelectedAvatarNumber;

    List<JButton> avatarButtons = new ArrayList<>();

    int roundScore = 0;

    int playerTotalScore = 0;

    int totalScoreOpponent = 0;

    boolean playerTurn;

    boolean windowCentered;

    boolean proceed;

    int numberOfQuestionsPerRound;
    int roundCounter = 0;
    int questionCounter = 0;
    int numberOfRounds;

    public synchronized boolean isProceed() {
        return proceed;
    }

    public synchronized void setProceed(){
        proceed = true;


    }


    public Client() {

        usernameAndAvatarGUI = new UsernameAndAvatarGUI(this);

        avatarButtons.add(usernameAndAvatarGUI.b1);
        avatarButtons.add(usernameAndAvatarGUI.b2);
        avatarButtons.add(usernameAndAvatarGUI.b3);
        avatarButtons.add(usernameAndAvatarGUI.b4);
        avatarButtons.add(usernameAndAvatarGUI.b5);
        avatarButtons.add(usernameAndAvatarGUI.b6);

        while (isProceed() == false){

        }
        usernameAndAvatarGUI.dispose();

        welcomeGui = new WelcomeUI(this);
        welcomeGui.setWelcomeLabel("VÄLKOMMEN " + userName.toUpperCase());
        welcomeGui.setTitle("QUIZKAMPEN " + userName.toUpperCase());


        UserStatistics userStatistics = new UserStatistics();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/UserStatistic.txt"));
            String line = null;
            List<String> list = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null){
              list.add(line);

            }
            Collections.sort(list,Collections.reverseOrder());

            for (int i = 0; i < list.size(); i++) {
                userStatistics.textArea.append(list.get(i) + "\n");
            }


        }catch (Exception e){

        }



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
                Socket socket = new Socket("127.0.0.1", 7676);
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
            out.println(userName); // UserInput

            out.println("SENDING AVATAR");
            out.println(selectedAvatarNumber); // Avatar

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
                        out.println("CHECK IF OPPONENT GAVE UP");
                        if (in.readLine().equals("YES")) {
                            welcomeGui.setVisible(false);
                            WinnerLooserGUI winnerLooserGUI = new WinnerLooserGUI(this);
                            winnerLooserGUI.winnerOrLooserLabel.setText("Du vann! Motståndaren gav upp");
                            winnerLooserGUI.winOrLoseField.setBackground(Color.green);
                            winnerLooserGUI.p1.setBackground(Color.green);
                            Thread.sleep(3000);
                            System.exit(0);
                        }
                        out.println("REQUEST NEW ROUND");
                    }
                    welcomeGui.dispose();

                }

                out.println("GENERATE QUESTIONS FOR NEXT ROUND");


                while (questionCounter < numberOfQuestionsPerRound) {

                    gameGui = new GameGui(out, this);
                    out.println("GET QUESTION");
                    gameGui.thisPLayerUserNameLabel.setText(userName);

                    gameGui.avatarImageButton1.setIcon(selectedAvatar.getIcon());

                    opponentUserName = in.readLine();
                    gameGui.opponentUserNameLabel.setText(opponentUserName);

                    opponentSelectedAvatarNumber = in.readLine();
                    gameGui.avatarImageButton2.setIcon(avatarButtons.get(Integer.parseInt(opponentSelectedAvatarNumber)-1).getIcon());


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
                    resultsGUI.avatarImageButtonResult1.setIcon(selectedAvatar.getIcon());
                    resultsGUI.avatarImageButtonResult2.setIcon(avatarButtons.get(Integer.parseInt(opponentSelectedAvatarNumber)-1).getIcon());
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

                out.println("CHECK IF OPPONENT GAVE UP");
                if (in.readLine().equals("YES")) {
                    resultsGUI.setVisible(false);
                    WinnerLooserGUI winnerLooserGUI = new WinnerLooserGUI(this);
                    winnerLooserGUI.winnerOrLooserLabel.setText("Du vann!\n Motståndaren gav upp");
                    winnerLooserGUI.winOrLoseField.setBackground(Color.green);
                    winnerLooserGUI.p1.setBackground(Color.green);
                }


                out.println("CHECK IF OPPONENT SCORE IS IN");
                while(in.readLine().equals("NO")) {
                    Thread.sleep(500);
                    out.println("CHECK IF OPPONENT SCORE IS IN");
                }

                resultsGUI.giveUpButton.setEnabled(true);

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

                if (in.readLine().equals("GAVE UP")) {
                    resultsGUI.giveUpButton.setText("Du ger upp");
                    resultsGUI.giveUpButton.setBackground(Color.red);
                    resultsGUI.panel1.setBackground(Color.RED);
                    resultsGUI.p1.setBackground(Color.red);
                    resultsGUI.p2.setBackground(Color.red);
                    resultsGUI.p3.setBackground(Color.red);
                    Client.sleep(3000);
                    System.exit(0);
                }

                resultsGUI.continueButton.setEnabled(false);
                resultsGUI.setVisible(false);

                roundScore = 0;
                playerTurn = !playerTurn; // Byte av spelare
                questionCounter = 0;

                roundCounter++;

            }
            WinnerLooserGUI winnerLooserGUI = new WinnerLooserGUI(this);

            if(totalScoreOpponent > playerTotalScore){
                winnerLooserGUI.winnerOrLooserLabel.setText("Du förlorade!");
                winnerLooserGUI.winOrLoseField.setBackground(Color.red);
                winnerLooserGUI.p1.setBackground(Color.red);
            } else if (totalScoreOpponent < playerTotalScore) {
                winnerLooserGUI.winnerOrLooserLabel.setText("Du vann!");
                winnerLooserGUI.winOrLoseField.setBackground(Color.green);
                winnerLooserGUI.p1.setBackground(Color.green);
            } else {
                winnerLooserGUI.winnerOrLooserLabel.setText("Oavgjort!");
            }

            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("src/Userstatistic.txt", true)));
            printWriter.println(playerTotalScore + " poäng | " + "Användarnamn: " + userName);
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        Client c = new Client();

    }
}



