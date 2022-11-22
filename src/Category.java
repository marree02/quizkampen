import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {

    private String categoryName;

    private List<Question> questionList = new ArrayList<>();

    public int currentQuestion;


    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void addQuestion(String text) {
        this.questionList.add(new Question(text));
        currentQuestion = this.questionList.size()-1;
    }

    public Question getCurrentQuestion() {
        return questionList.get(currentQuestion);
    }

    public void shuffleQuestions() {
        Collections.shuffle(this.questionList);

        for (Question q : this.questionList) {
            q.shuffleChoices();
        }

    }
}
