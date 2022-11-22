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
    public String[] getCategoriesAsArray(int n) {
        String[] categories = new String[3];
        for (int i = 0; i < 3; i++) {
            categories[i] = categoryList.get(i).getCategoryName();
        }
        return categories;
    }

    @Override
    public Question getCurrentQuestion() {
        return categoryList.get(currentCategory).getCurrentQuestion();
    }

    @Override
    public String[] getChoicesAsArray() {
        String[] choices = new String[getCurrentQuestion().getChoices().size()];

        for (int i = 0; i < choices.length; i++) {
            choices[i] = getCurrentQuestion().getChoices().get(i);
        }


        return choices;
    }

    @Override
    public void nextQuestion() {
        categoryList.get(currentCategory).currentQuestion++;

    }

    @Override
    public void setCategory(int n) {
        currentCategory = n;
    }

    @Override
    public String getCurrentCategory() {
        return categoryList.get(currentCategory).getCategoryName();
    }

    @Override
    public void setCategory(String s) {
        for (int i = 0; i < categoryList.size(); i++) {

            if (categoryList.get(i).getCategoryName().equals(s)) {
                currentCategory = i;
            }
        }
    }

    @Override
    public void shuffleQuestions() {
        for (Category c : categoryList) {
            c.shuffleQuestions();
        }
    }


    public void loadCategoryFiles(String [] filenames) {

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
