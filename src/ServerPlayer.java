import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerPlayer extends Thread {

    Socket socket;
    GameRoom game;
    ServerPlayer opponent;
    PrintWriter out;
    BufferedReader in;
    String userName;
    String playerOneOrTwo;
    String fromClient;

    List<Question> questionsForNextRound = new ArrayList<>();


    public ServerPlayer(Socket accept, GameRoom game, String playerOneOrTwo) {
        this.socket = accept;
        this.game = game;
        this.playerOneOrTwo = playerOneOrTwo;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOpponent(ServerPlayer opponent) {
        this.opponent = opponent;
    }

    public ServerPlayer getOpponent() {
        return opponent;
    }

    @Override
    public void run() {

        try {

            while(true) {

                fromClient = in.readLine();

                if (fromClient.equals("GET PLAYER 1 OR 2")) {
                    out.println(playerOneOrTwo);
                }

                else if (fromClient.equals("SENDING USERNAME")) {
                    userName = in.readLine();
                }

                else if (fromClient.equals("GET CATEGORIES")) {
                    game.questionGenerator.shuffleCategories();
                    String[] categories = game.questionGenerator.getCategoriesAsArray(3);
                    out.println(categories[0]);
                    out.println(categories[1]);
                    out.println(categories[2]);
                }

                else if (fromClient.equals("SET CATEGORY")) {
                    game.questionGenerator.setCategory(in.readLine());
                    game.generateQuestionsForNextRound(8);
                    game.resetRoundScores();
                    game.setRoundReadyToStart(true);
                    out.println("CONTINUE");
                }

                else if (fromClient.equals("REQUEST NEW ROUND")) {
                    if (!game.roundReadyToStart()) {
                        out.println("NO");
                    } else {
                        out.println("YES");
                        game.setRoundReadyToStart(false);
                    }
                }

                else if (fromClient.equals("GENERATE QUESTIONS FOR NEXT ROUND")) {

                    this.questionsForNextRound.clear();

                    this.questionsForNextRound.addAll(game.getQuestionsForNextRound());
                }

                else if (fromClient.equals("GET QUESTION")) {

                    Question question = this.questionsForNextRound.remove(0);

                    out.println(opponent.userName);
                    out.println(game.questionGenerator.getCurrentCategory());
                    out.println(question.getQuestion());

                    List<String> choices = question.getChoices();
                    for (String s : choices) {
                        out.println(s);
                    }

                    out.println(question.getCorrectAnswer());
                }

                else if (fromClient.equals("QUESTION ANSWERED")) {
                    out.println("QUESTION ANSWERED");
                }

                else if (fromClient.equals("CONTINUE FROM RESULTS")) {
                    out.println("CONTINUE");
                }

                else if (fromClient.equals("SEND SCORE FOR ROUND")) {
                    game.addRoundScore(playerOneOrTwo, in.readLine());
                }

                else if (fromClient.equals("CHECK IF OPPONENT SCORE IS IN")) {

                    if (game.checkIfOpponentScoresAreIn()) {
                        out.println("YES");
                    } else {
                        out.println("NO");
                    }
                }
                else if (fromClient.equals("OPPONENT GAVE UP")) {
                    game.setOpponentGaveUp(true);
                    out.println("GAVE UP");
                }

                else if (fromClient.equals("GET CURRENT CATEGORY")) {
                    out.println(game.questionGenerator.getCurrentCategory());
                }

                else if (fromClient.contains("GET SCORES")) {

                    out.println(game.getScoresPerRound(playerOneOrTwo));
                    out.println(game.getScoresPerRound(opponent.playerOneOrTwo));
                }


                else {
                    System.out.println("Felaktig input från client");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
