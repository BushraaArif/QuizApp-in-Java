public class Result {
    private int resultId;
    private int userId;
    private int categoryId;
    private int score;

    // Constructors
    public Result() {}

    public Result(int userId, int categoryId, int score) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.score = score;
    }

    // Getters and Setters
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
