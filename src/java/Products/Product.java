/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Products;

import java.math.BigDecimal;

/**
 *
 * @author Admins
 */
public class Product {

    private String productID;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String categoryID;
    private String brandID;
    private String imageURL; // Thêm thuộc tính này
    private String categoryName; // Thêm thuộc tính này
    private String brandName;    // Thêm thuộc tính này

    public Product(String productID, String name, BigDecimal price, int quantity, String categoryID, String brandID, String imageURL, String categoryName, String brandName) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.brandID = brandID;
        this.imageURL = imageURL;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }

    public Product(String productID, String name, BigDecimal price, int quantity, String categoryID, String brandID, String imageURL) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.brandID = brandID;
        this.imageURL = imageURL;
    }

    public Product(String productID, String name, BigDecimal price, int quantity, String imageURL) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageURL = imageURL;
    }

    Product(String productID, String productName, double price, int quantity, String categoryID, String brandID, String imageURL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Product() {
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
