import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionGenerator implements Quiz {

    private List<Category> categoryList = new ArrayList<>();

    private int currentCategory = 0;

    public QuestionGenerator(String[] filenames) {
        loadCategoryFiles(filenames);
        Collections.shuffle(categoryList);
    }

    @Override
    public synchronized String[] getCategoriesAsArray(int n) {
        String[] categories = new String[3];
        for (int i = 0; i < 3; i++) {
            categories[i] = categoryList.get(i).getCategoryName();
        }
        return categories;
    }

    @Override
    public synchronized String getCurrentQuestion() {
        return categoryList.get(currentCategory).getCurrentQuestion().getQuestion();
    }

    @Override
    public synchronized String getCorrectAnswer() {
        return categoryList.get(currentCategory).getCurrentQuestion().getCorrectAnswer();
    }

    @Override
    public synchronized String[] getChoicesAsArray() {
        String[] choices = new String[categoryList.get(currentCategory).getCurrentQuestion().getChoices().size()];

        for (int i = 0; i < choices.length; i++) {
            choices[i] = categoryList.get(currentCategory).getCurrentQuestion().getChoices().get(i);
        }


        return choices;
    }

    @Override
    public synchronized void nextQuestion() {
        categoryList.get(currentCategory).currentQuestion++;

    }

    @Override
    public synchronized String getCurrentCategory() {
        return categoryList.get(currentCategory).getCategoryName();
    }

    @Override
    public synchronized void setCategory(String s) {
        for (int i = 0; i < categoryList.size(); i++) {

            if (categoryList.get(i).getCategoryName().equals(s)) {
                currentCategory = i;
            }
        }
    }

    @Override
    public synchronized void shuffleQuestions() {
        for (Category c : categoryList) {
            c.shuffleQuestions();
        }
    }


    public synchronized void loadCategoryFiles(String [] filenames) {

        for (String filename : filenames) {

            try (BufferedReader bf = new BufferedReader(new FileReader(filename))) {

                Category newCategory = new Category();

                newCategory.setCategoryName(bf.readLine());

                bf.readLine();

                String line;

                while ((line = bf.readLine()) != null) {

                    newCategory.addQuestion(line);

                    while (!(line = bf.readLine()).equals("---")) {

                        if (line.startsWith("*")) {
                            newCategory.getCurrentQuestion().addChoice(line.substring(1));
                            newCategory.getCurrentQuestion().setCorrectAnswer(line.substring(1));
                        } else {
                            newCategory.getCurrentQuestion().addChoice(line);
                        }

                    }


                }

                categoryList.add(newCategory);

            } catch (FileNotFoundException e) {
                System.out.println(filename + ": File not found.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }





    }


}

class Category {

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

class Question {

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

