package tmall.bean;

import java.util.List;

public class Category {
    //Database fields
    private int id;
    private String name;
    //Relations
    private List<Product> productList;// 维持了一对多的关系，用于首页分类选择
    private List<List<Product>> productListGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<List<Product>> getProductListGroup() {
        return productListGroup;
    }

    public void setProductListGroup(List<List<Product>> productListGroup) {
        this.productListGroup = productListGroup;
    }

    //test
    public String toString() {
        return "Category [name=" + name + "]";
    }
}
