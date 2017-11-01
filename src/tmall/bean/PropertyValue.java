package tmall.bean;

public class PropertyValue {

    private int id;
    private Product product;//属性值维系着一个产品ID
    private Property property;//属性值维系着一个属性ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
