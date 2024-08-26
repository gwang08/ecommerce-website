/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class ProductDetailDAO {

    private static final String GET_PRODUCT_DETAIL_BY_ID
            = "SELECT * FROM ProductDetails WHERE productID = ?";

    private static final String GET_PRODUCT_BY_ID
            = "SELECT p.productID, p.name, p.price, p.quantity, p.categoryID, p.brandID, p.imageURL, "
            + "c.categoryName, b.brandName "
            + "FROM Products p "
            + "JOIN Categories c ON p.categoryID = c.categoryID "
            + "JOIN Brands b ON p.brandID = b.brandID "
            + "WHERE p.productID = ?";

    public ProductDetailDTO getProductDetailByID(String productID)
            throws SQLException, ClassNotFoundException {
        ProductDetailDTO productDetail = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(GET_PRODUCT_DETAIL_BY_ID);
            pst.setString(1, productID);
            rs = pst.executeQuery();

            if (rs.next()) {
                String productDetailID = rs.getString("productDetailID");
                String description = rs.getString("description");
                String specifications = rs.getString("specifications");
                String warrantyPeriod = rs.getString("warrantyPeriod");

                productDetail = new ProductDetailDTO(productDetailID, productID, description, specifications, warrantyPeriod);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return productDetail;
    }

    public Product getProductByID(String productID)
            throws SQLException, ClassNotFoundException {
        Product product = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            pst = conn.prepareStatement(GET_PRODUCT_BY_ID);
            pst.setString(1, productID);
            rs = pst.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductID(rs.getString("productID"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategoryID(rs.getString("categoryID"));
                product.setBrandID(rs.getString("brandID"));
                product.setImageURL(rs.getString("imageURL"));
                product.setCategoryName(rs.getString("categoryName"));
                product.setBrandName(rs.getString("brandName"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return product;
    }

}
