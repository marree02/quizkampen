public interface Quiz {

    public String getCurrentCategory();

    public void shuffleQuestions();

    public void shuffleCategories();

    public void setCategory(String s);

    public String[] getCategoriesAsArray(int n);

    public String getQuestion(Question question);

    public Question getNextQuestion();

    public String getCorrectAnswer(Question question);

    public String[] getChoicesAsArray(Question question);

    public void nextQuestion();

}
