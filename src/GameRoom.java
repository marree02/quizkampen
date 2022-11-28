import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    Quiz questionGenerator;

    private boolean roundReadyToStart = false;

    List<Question> questionsForNextRound = new ArrayList<>();

    String roundScorePlayer1 = "Väntar...";
    String roundScorePlayer2 = "Väntar...";



    public GameRoom(){

        String[] filenames = {"java.txt", "golf.txt", "svamp.txt", "huvudstäder.txt"};

        this.questionGenerator = new QuestionGenerator(filenames);

        questionGenerator.shuffleQuestions();

    }

    public synchronized String getScoresPerRound(String playerOneOrTwo) {
        if (playerOneOrTwo.equals("1")) {
            return roundScorePlayer1;
        } else {
            return roundScorePlayer2;
        }
    }

    public synchronized void addRoundScore(String playerOneOrTwo, String score) {
        if (playerOneOrTwo.equals("1")) {
            roundScorePlayer1 = score;
        }
        if (playerOneOrTwo.equals("2")) {
            roundScorePlayer2 = score;
        }
    }

    public synchronized void setRoundReadyToStart(boolean trueOrFalse) {
        roundReadyToStart = trueOrFalse;
    }

    public synchronized boolean roundReadyToStart() {
        return roundReadyToStart;
    }


    public synchronized void generateQuestionsForNextRound(int numberOfQuestions) {
        questionsForNextRound.clear();
        for (int i = 0; i < numberOfQuestions; i++) {
            questionsForNextRound.add(questionGenerator.getNextQuestion());
        }
    }

    public synchronized List<Question> getQuestionsForNextRound() {
        return questionsForNextRound;
    }

    public synchronized boolean checkIfOpponentScoresAreIn() {
        if (!roundScorePlayer1.equals("Väntar...") && !roundScorePlayer2.equals("Väntar...")) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void resetRoundScores() {
        roundScorePlayer1 = "Väntar...";
        roundScorePlayer2 = "Väntar...";
    }

}
