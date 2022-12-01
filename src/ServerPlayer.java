import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
    String avatar;
    Messages m = new Messages();

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

                if (fromClient.equals(m.GET_PLAYER_1_OR_2)) {
                    out.println(playerOneOrTwo);
                }

                else if (fromClient.equals(m.SENDING_USERNAME)) {
                    userName = in.readLine();


                } else if (fromClient.equals(m.SENDING_AVATAR)) {
                    avatar = in.readLine();


                } else if (fromClient.equals(m.GET_CATEGORIES)) {
                    game.questionGenerator.shuffleCategories();
                    String[] categories = game.questionGenerator.getCategoriesAsArray(3);
                    out.println(categories[0]);
                    out.println(categories[1]);
                    out.println(categories[2]);
                }

                else if (fromClient.equals(m.SET_CATEGORY)) {
                    game.questionGenerator.setCategory(in.readLine());
                    game.generateQuestionsForNextRound(4);
                    game.resetRoundScores();
                    game.setRoundReadyToStart(true);
                    out.println(m.CONTINUE);
                }

                else if (fromClient.equals(m.REQUEST_NEW_ROUND)) {
                    if (!game.roundReadyToStart()) {
                        out.println(m.NO);
                    } else {
                        out.println(m.YES);
                        game.setRoundReadyToStart(false);
                    }
                }

                else if (fromClient.equals(m.GENERATE_QUESTIONS_FOR_NEXT_ROUND)) {
                    this.questionsForNextRound.clear();
                    this.questionsForNextRound.addAll(game.getQuestionsForNextRound());
                }

                else if (fromClient.equals(m.GET_QUESTION)) {

                    Question question = this.questionsForNextRound.remove(0);

                    out.println(opponent.userName);
                    out.println(opponent.avatar); // skickar avatar
                    out.println(game.questionGenerator.getCurrentCategory());
                    out.println(question.getQuestion());

                    List<String> choices = question.getChoices();
                    for (String s : choices) {
                        out.println(s);
                    }

                    out.println(question.getCorrectAnswer());
                }

                else if (fromClient.equals(m.QUESTION_ANSWERED)) {
                    out.println(m.QUESTION_ANSWERED);
                }

                else if (fromClient.equals(m.CONTINUE_FROM_RESULTS)) {
                    out.println(m.CONTINUE);
                }

                else if (fromClient.equals(m.SEND_SCORE_FOR_ROUND)) {
                    game.addRoundScore(playerOneOrTwo, in.readLine());
                }

                else if (fromClient.equals(m.CHECK_IF_OPPONENT_SCORE_IS_IN)) {

                    if (game.checkIfOpponentScoresAreIn()) {
                        out.println(m.YES);
                    } else {
                        out.println(m.NO);
                    }
                }
                else if (fromClient.equals(m.OPPONENT_GAVE_UP)) {
                    game.setOpponentGaveUp(true);
                    out.println(m.GAVE_UP);
                }

                else if (fromClient.equals(m.CHECK_IF_OPPONENT_GAVE_UP)) {
                    if (game.isOpponentGaveUp()) {
                        out.println(m.YES);
                    } else {
                        out.println(m.NO);
                    }

                }

                else if (fromClient.equals(m.GET_CURRENT_CATEGORY)) {
                    out.println(game.questionGenerator.getCurrentCategory());
                }

                else if (fromClient.contains(m.GET_SCORES)) {
                    out.println(game.getScoresPerRound(playerOneOrTwo));
                    out.println(game.getScoresPerRound(opponent.playerOneOrTwo));
                }


                else {
                    System.out.println("Felaktig input från client");
                    System.out.println(fromClient);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
