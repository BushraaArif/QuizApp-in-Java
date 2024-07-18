public class Category {
    private int categoryId;
    private String categoryName;
    
    
    
    public Category(int categoryId, String categoryName) // Constructor initializing all fields.(Category id, categoryName)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    
    // Getter And Setters
    
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}