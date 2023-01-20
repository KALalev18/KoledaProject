package model;

public class Calories {
    private int CalculationId;
    private int MenuId;
    private int Height;
    private int Weight;
    private int Age;
    private int CaloriesToTake;
    private int ExtraTakenCalories;

    public Calories(int calculationId, int menuId, int height, int weight, int age, int caloriesToTake, int extraTakenCalories) {
        this.CalculationId = calculationId;
        this.MenuId = menuId;
        this.Height = height;
        this.Weight = weight;
        this.Age = age;
        this.CaloriesToTake = caloriesToTake;
        this.ExtraTakenCalories = extraTakenCalories;
    }

    public Calories() {

    }

    public int getCalculationId() {
        return CalculationId;
    }

    public void setCalculationId(int calculationId) {
        this.CalculationId = calculationId;
    }

    public int getMenuId() {
        return MenuId;
    }

    public void setMenuId(int menuId) {
        this.MenuId = menuId;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        this.Height = height;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        this.Weight = weight;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public int getCaloriesToTake() {
        return CaloriesToTake;
    }

    public void setCaloriesToTake(int caloriesToTake) {
        this.CaloriesToTake = caloriesToTake;
    }

    public int getExtraTakenCalories() {
        return ExtraTakenCalories;
    }

    public void setExtraTakenCalories(int extraTakenCalories) {
        this.ExtraTakenCalories = extraTakenCalories;
    }

    @Override
    public String toString() {
        return "Calories{" +
                "CalculationId=" + CalculationId +
                ", MenuId=" + MenuId +
                ", Height=" + Height +
                ", Weight=" + Weight +
                ", Age=" + Age +
                ", CaloriesToTake=" + CaloriesToTake +
                ", ExtraTakenCalories=" + ExtraTakenCalories +
                '}';
    }
}