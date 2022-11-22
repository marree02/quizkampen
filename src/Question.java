import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private String question;
    private String correctAnswer;
    private List<String> choices = new ArrayList<>();
    private Category category;

    public Question(String question) {
        this.question = question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Category getCategory() {
        return category;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void shuffleChoices() {
        Collections.shuffle(choices);
    }
}
