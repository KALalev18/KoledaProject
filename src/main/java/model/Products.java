package model;

public class Products {
    private int ProductId;
    private String ProductType;
    private int CaloriesCount;

    public Products(int productId, String productType, int caloriesCount) {
        this.ProductId = productId;
        this.ProductType = productType;
        this.CaloriesCount = caloriesCount;
    }

    public Products() {

    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        this.ProductId = productId;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        this.ProductType = productType;
    }

    public int getCaloriesCount() {
        return CaloriesCount;
    }

    public void setCaloriesCount(int caloriesCount) {
        this.CaloriesCount = caloriesCount;
    }

    @Override
    public String toString() {
        return "Products{" +
                "ProductId=" + ProductId +
                ", ProductType='" + ProductType + '\'' +
                ", CaloriesCount=" + CaloriesCount +
                '}';
    }
}