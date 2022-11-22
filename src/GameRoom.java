public class GameRoom {

    Quiz q;


    public GameRoom(){

        String[] filenames = {"java.txt", "golf.txt", "svamp.txt"};

        this.q = new QuestionGenerator(filenames);

        q.shuffleQuestions();

    }

}
