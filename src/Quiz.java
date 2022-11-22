public interface Quiz {

    public void setCategory(int n);

    public String getCurrentCategory();

    public void shuffleQuestions();

    public void setCategory(String s);

    public String[] getCategoriesAsArray(int n);

    public Question getCurrentQuestion();

    public String[] getChoicesAsArray();

    public void nextQuestion();

}
