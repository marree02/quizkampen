public class GameRoom {

    Quiz questionGenerator;

    private boolean roundReadyToStart = false;


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

}
