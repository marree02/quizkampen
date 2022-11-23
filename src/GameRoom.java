public class GameRoom {

    Quiz questionGenerator;


    public GameRoom(){

        String[] filenames = {"java.txt", "golf.txt", "svamp.txt"};

        this.questionGenerator = new QuestionGenerator(filenames);

        questionGenerator.shuffleQuestions();

    }

}
