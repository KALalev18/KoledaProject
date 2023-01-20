package model;

import java.util.List;

public class Menus {
    private int MenuId;
    private String MenuTitle;
    private int MenuCalories;
    public List<Integer> getProducts() {
        return Products;
    }

    public List<model.Products> getProductsObj() {
        return ProductsObj;
    }

    public void setProductsObj(List<model.Products> productsObj) {
        ProductsObj = productsObj;
    }

    public void setProducts(List<Integer> products) {
        Products = products;
    }

    private List<Integer> Products;

    private List<Products> ProductsObj;

    public Menus(int menuId, String menuTitle, int menuCalories) {
        this.MenuId = menuId;
        this.MenuTitle = menuTitle;
        this.MenuCalories = menuCalories;
    }
    public Menus(){

    }


    public int getMenuId() {
        return MenuId;
    }

    public void setMenuId(int menuId) {
        this.MenuId = menuId;
    }

    public String getMenuTitle() {
        return MenuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.MenuTitle = menuTitle;
    }

    public int getMenuCalories() {
        return MenuCalories;
    }

    public void setMenuCalories(int menuCalories) {
        this.MenuCalories = menuCalories;
    }

    @Override
    public String toString() {
        return "Menus{" +
                "MenuId=" + MenuId +
                ", MenuTitle='" + MenuTitle + '\'' +
                ", MenuCalories=" + MenuCalories +
                '}';
    }
}