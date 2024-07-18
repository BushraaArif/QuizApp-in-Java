public class Question {

    private int questionId;
    private int categoryId;
    private String questionText;

    public Question(int questionId, int categoryId, String questionText) {
        this.questionId = questionId;
        this.categoryId = categoryId;
        this.questionText = questionText;
    }

    // Getters
    public int getQuestionId() {
        return questionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getQuestionText() {
        return questionText;
    }

    // Setters
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
