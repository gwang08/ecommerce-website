package Products;
/**
 *
 * @author Admins
 */
public class ProductDetailDTO {
    private String productDetailID;
    private String productID;
    private String description;
    private String specifications;
    private String warrantyPeriod;

    // Constructor with all parameters
    public ProductDetailDTO(String productDetailID, String productID, String description, String specifications, String warrantyPeriod) {
        this.productDetailID = productDetailID;
        this.productID = productID;
        this.description = description;
        this.specifications = specifications;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Constructor without productDetailID
    public ProductDetailDTO(String productID, String description, String specifications, String warrantyPeriod) {
        this.productID = productID;
        this.description = description;
        this.specifications = specifications;
        this.warrantyPeriod = warrantyPeriod;
        // Assuming productDetailID is auto-generated or assigned elsewhere
        this.productDetailID = null; // or some default value if needed
    }

    public ProductDetailDTO(String description, String specifications, String warrantyPeriod) {
        this.description = description;
        this.specifications = specifications;
        this.warrantyPeriod = warrantyPeriod;
        // Assuming productDetailID is auto-generated or assigned elsewhere
        this.productDetailID = null; //To change body of generated methods, choose Tools | Templates.
    }

    // Getters and setters
    public String getProductDetailID() {
        return productDetailID;
    }

    public void setProductDetailID(String productDetailID) {
        this.productDetailID = productDetailID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
