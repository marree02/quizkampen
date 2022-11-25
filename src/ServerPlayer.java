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


    int numberOfQuestionsPerRound = 2;

    int roundCounter = 0;

    int questionCounter = 0;

    int numberOfRounds = 2;

    boolean userClickedContinue = false;

    String nextCategory;

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
                    game.setRoundReadyToStart(false);
                    game.questionGenerator.shuffleCategories();
                    String[] categories = game.questionGenerator.getCategoriesAsArray(3);
                    out.println(categories[0]);
                    out.println(categories[1]);
                    out.println(categories[2]);
                }

                else if (fromClient.equals("SET CATEGORY")) {
                    nextCategory = in.readLine();
                    game.setRoundReadyToStart(true);
                    out.println();
                }

                else if (fromClient.equals("REQUEST NEW ROUND")) {
                    if (!game.roundReadyToStart()) {
                        out.println("NO");
                    } else {
                        out.println();
                        game.setRoundReadyToStart(false);
                    }
                }

                else if (fromClient.equals("GENERATE QUESTIONS FOR NEXT ROUND")) {
                    questionsForNextRound.clear();
                    questionsForNextRound.addAll(game.getQuestionsForNextRound());
                }


                else if (fromClient.equals("GET QUESTION")) {

                    Question question = questionsForNextRound.remove(0);

                    out.println(opponent.userName);
                    out.println(game.questionGenerator.getCurrentCategory());
                    out.println(game.questionGenerator.getQuestion(question));

                    String[] choices = game.questionGenerator.getChoicesAsArray(question);
                    for (String s : choices) {
                        System.out.println(s);
                        out.println(s);
                    }

                    out.println(game.questionGenerator.getCorrectAnswer(question));
                }

                else if (fromClient.equals("QUESTION ANSWERED")) {
                    out.println("QUESTION ANSWERED");
                }

                else if (fromClient.equals("CONTINUE FROM RESULTS")) {
                    out.println("CONTINUE");
                }

                else if (fromClient.equals("CHECK IF BOTH PLAYERS HAVE FINISHED ROUND")) {
                    if (game.getPlayersFinishedWithRound() < 2) {
                        out.println("NO");
                    } else {
                        game.questionGenerator.setCategory(nextCategory);
                        game.generateQuestionsForNextRound(8);
                        game.setPlayersFinishedWithRound(0);
                        out.println("YES");
                    }

                }

                else if (fromClient.equals("FINISH ROUND")) {
                    game.finishRound();
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

                else if (fromClient.equals("GET CURRENT CATEGORY")) {
                    out.println(game.questionGenerator.getCurrentCategory());
                }

                else if (fromClient.contains("GET SCORES")) {

                    List<String> myScoresPerRound = game.getScoresPerRound(playerOneOrTwo);
                    List<String> opponentScoresPerRound = game.getScoresPerRound(opponent.playerOneOrTwo);

                    int currentRound = myScoresPerRound.size() - 1;
                    out.println(myScoresPerRound.get(currentRound));
                    if (opponentScoresPerRound.size() < myScoresPerRound.size()) {
                        out.println("-");
                    } else {
                        out.println(opponentScoresPerRound.get(currentRound));
                    }

                }

                else {
                    System.out.println("Felaktig input från client");
                }

            }

            //Steg 2: Varje servertråd skickar till sin klient om klienten är spelare 1 eller 2
            /*
            out.println(playerOneOrTwo);

            while(roundCounter < numberOfRounds) {

                String[] categories = game.questionGenerator.getCategoriesAsArray(3);

                //Steg 3: en sträng för varje kategori som kan väljas skickas till båda klienterna
                out.println(categories[0]);
                out.println(categories[1]);
                out.println(categories[2]);

                //Steg 4: väntar på att få vald kategori från klienten
                String chosenCategory = in.readLine();

                //If blir true om kategorin kommer från den spelare som ska välja kategori
                //Då meddelas den andre spelaren att kategori är vald
                if (!chosenCategory.equals("INGEN KATEGORI")) {
                    game.questionGenerator.setCategory(chosenCategory);
                    opponent.out.println();
                }

                out.println();

                while (questionCounter < numberOfQuestionsPerRound) {

                    out.println(opponent.userName);
                    out.println(game.questionGenerator.getCurrentCategory());
                    out.println(game.questionGenerator.getCurrentQuestion());

                    String[] choices = game.questionGenerator.getChoicesAsArray();
                    for (String s : choices) {
                        System.out.println(s);
                        out.println(s);
                    }

                    out.println(game.questionGenerator.getCorrectAnswer());

                    // Väntar på att klienten ska svara på frågan
                    in.readLine();

                    // skickar sträng så klienten vet att den ska starta nytt gameGui
                    out.println();

                    game.questionGenerator.nextQuestion();

                    questionCounter++;
                }
                    in.readLine();
                    out.println();
                    questionCounter = 0;
                    roundCounter++;
            }

            // out.close();


             */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
