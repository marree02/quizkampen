import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    Quiz questionGenerator;

    private boolean roundReadyToStart = false;

    private int playersFinishedWithRound = 2;

    List<Question> questionsForNextRound = new ArrayList<>();


    public GameRoom(){

        String[] filenames = {"java.txt", "golf.txt", "svamp.txt"};

        this.questionGenerator = new QuestionGenerator(filenames);

        questionGenerator.shuffleQuestions();

    }

    public synchronized void setRoundReadyToStart(boolean trueOrFalse) {
        roundReadyToStart = trueOrFalse;
    }

    public synchronized boolean roundReadyToStart() {
        return roundReadyToStart;
    }

    public synchronized void finishRound() {
        playersFinishedWithRound++;
    }

    public synchronized int getPlayersFinishedWithRound() {
        return playersFinishedWithRound;
    }

    public synchronized void setPlayersFinishedWithRound(int playersFinishedWithRound) {
        this.playersFinishedWithRound = playersFinishedWithRound;
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
}
