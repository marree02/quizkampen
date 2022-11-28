public interface Quiz {

    public String getCurrentCategory();

    public void shuffleQuestions();

    public void shuffleCategories();

    public void setCategory(String s);

    public String[] getCategoriesAsArray(int n);

    public Question getNextQuestion();

    public void nextQuestion();

}
