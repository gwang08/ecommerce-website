/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Products;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class ProductDAO {

    private static final String GET_PRODUCT_BY_CATEGORIES
            = "SELECT p.productID, p.name, p.price, p.quantity, p.categoryID, p.brandID, p.imageURL, "
            + "c.categoryName, b.brandName "
            + "FROM Products p "
            + "JOIN Categories c ON p.categoryID = c.categoryID "
            + "JOIN Brands b ON p.brandID = b.brandID "
            + "WHERE p.categoryID = ?";

    private static final String SEARCH_PRODUCTS
            = "SELECT p.productID, p.name, p.price, p.quantity, p.categoryID, p.brandID, p.imageURL, "
            + "c.categoryName, b.brandName "
            + "FROM Products p "
            + "JOIN Categories c ON p.categoryID = c.categoryID "
            + "JOIN Brands b ON p.brandID = b.brandID "
            + "WHERE p.name LIKE ?";

    private static final String GET_PRODUCT_BY_ID
            = "SELECT p.productID, p.name, p.price, p.quantity, p.categoryID, p.brandID, p.imageURL, "
            + "c.categoryName, b.brandName "
            + "FROM Products p "
            + "JOIN Categories c ON p.categoryID = c.categoryID "
            + "JOIN Brands b ON p.brandID = b.brandID "
            + "WHERE p.productID = ?";

    private static final String GET_PRODUCT_DETAIL_BY_ID
            = "SELECT pd.productDetailID, pd.productID, pd.description, pd.specifications, pd.warrantyPeriod "
            + "FROM ProductDetails pd "
            + "WHERE pd.productID = ?";

    private static final String UPDATE_PRODUCT = "UPDATE Products SET name = ?, price = ?, quantity = ?, categoryID = ?, brandID = ?, imageURL = ? WHERE productID = ?";
    private static final String UPDATE_PRODUCT_DETAILS = "UPDATE ProductDetails SET description = ?, specifications = ?, warrantyPeriod = ? WHERE productID = ?";

    public List<Product> getProductsByCategory(String categoryID)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(GET_PRODUCT_BY_CATEGORIES);
            ptm.setString(1, categoryID);
            rs = ptm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getString("productID"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategoryID(rs.getString("categoryID"));
                product.setBrandID(rs.getString("brandID"));
                product.setImageURL(rs.getString("imageURL"));
                product.setCategoryName(rs.getString("categoryName")); // Lấy categoryName
                product.setBrandName(rs.getString("brandName"));       // Lấy brandName
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return productList;
    }

    public Map<String, List<Product>> searchProductsGroupedByCategory(String query)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        Map<String, List<Product>> categoryMap = new LinkedHashMap<>();

        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(SEARCH_PRODUCTS);
            ptm.setString(1, "%" + query + "%");
            rs = ptm.executeQuery();

            while (rs.next()) {
                String categoryID = rs.getString("categoryID");
                Product product = new Product();
                product.setProductID(rs.getString("productID"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategoryID(rs.getString("categoryID"));
                product.setBrandID(rs.getString("brandID"));
                product.setImageURL(rs.getString("imageURL"));
                product.setCategoryName(rs.getString("categoryName"));
                product.setBrandName(rs.getString("brandName"));

                categoryMap.computeIfAbsent(categoryID, k -> new ArrayList<>()).add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return categoryMap;
    }

    public Product getProductById(String productID)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        Product product = null;

        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(GET_PRODUCT_BY_ID);
            ptm.setString(1, productID);
            rs = ptm.executeQuery();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return product;
    }

    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        List<Product> productList = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        String query = "SELECT * \n"
                + "FROM Products \n"
                + "ORDER BY CAST(SUBSTRING(productID, 2, LEN(productID) - 1) AS INT) ASC;";
        try (PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("productID"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getString("categoryID"),
                        rs.getString("brandID"),
                        rs.getString("imageURL")
                );
                productList.add(product);
            }
        } finally {
            con.close();
        }
        return productList;
    }

    // Get product details by productID
    public Product getProductsById(String productID) throws SQLException, ClassNotFoundException {
        Product product = null;
        Connection con = DBUtils.getConnection();
        String query = "SELECT * FROM Products WHERE productID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getString("productID"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getString("categoryID"),
                        rs.getString("brandID"),
                        rs.getString("imageURL")
                );
            }
        } finally {
            con.close();
        }
        return product;
    }

    public List<Product> searchProducts(String searchQuery) throws SQLException, ClassNotFoundException {
        List<Product> products = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        // Câu truy vấn tìm kiếm theo tên hoặc ID
        String query = "SELECT * FROM Products WHERE name LIKE ? OR productID LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            String searchPattern = "%" + searchQuery + "%";
            ps.setString(1, searchPattern); // Tìm kiếm theo tên sản phẩm
            ps.setString(2, searchPattern); // Tìm kiếm theo ID sản phẩm

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String productID = rs.getString("productID");
                String productName = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");
                String categoryID = rs.getString("categoryID");
                String brandID = rs.getString("brandID");
                String imageURL = rs.getString("imageURL");

                Product product = new Product(productID, productName, price, quantity, categoryID, brandID, imageURL);
                products.add(product);
            }
        } finally {
            con.close();
        }

        return products;
    }

    public ProductDetailDTO getProductDetailById(String productID)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        ProductDetailDTO productDetail = null;

        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(GET_PRODUCT_DETAIL_BY_ID);
            ptm.setString(1, productID);
            rs = ptm.executeQuery();
            if (rs.next()) {
                productDetail = new ProductDetailDTO(
                        rs.getString("productDetailID"),
                        rs.getString("productID"),
                        rs.getString("description"),
                        rs.getString("specifications"),
                        rs.getString("warrantyPeriod")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return productDetail;
    }

    public boolean updateProduct(Product product, ProductDetailDTO productDetails) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.getConnection();
        PreparedStatement psProduct = null;
        PreparedStatement psDetails = null;

        try {
            con.setAutoCommit(false); // Bắt đầu giao dịch

            // Cập nhật bảng Products
            psProduct = con.prepareStatement(UPDATE_PRODUCT);
            psProduct.setString(1, product.getName());
            psProduct.setBigDecimal(2, product.getPrice());
            psProduct.setInt(3, product.getQuantity());
            psProduct.setString(4, product.getCategoryID());
            psProduct.setString(5, product.getBrandID());
            psProduct.setString(6, product.getImageURL());
            psProduct.setString(7, product.getProductID());

            int productRowsAffected = psProduct.executeUpdate();

            // Cập nhật bảng ProductDetails
            psDetails = con.prepareStatement(UPDATE_PRODUCT_DETAILS);
            psDetails.setString(1, productDetails.getDescription());
            psDetails.setString(2, productDetails.getSpecifications());
            psDetails.setString(3, productDetails.getWarrantyPeriod());
            psDetails.setString(4, product.getProductID());

            int detailsRowsAffected = psDetails.executeUpdate();

            con.commit(); // Commit giao dịch

            return productRowsAffected > 0 && detailsRowsAffected > 0;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback giao dịch nếu có lỗi
                } catch (SQLException rollbackEx) {
                    throw new SQLException("Error during transaction rollback", rollbackEx);
                }
            }
            throw e;

        } finally {
            if (psProduct != null) {
                psProduct.close();
            }
            if (psDetails != null) {
                psDetails.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean deleteProduct(String productID) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement psProduct = null;
        PreparedStatement psDetails = null;

        try {
            con = DBUtils.getConnection();
            con.setAutoCommit(false); // Start transaction

            // First delete from ProductDetails table
            String deleteDetailsQuery = "DELETE FROM ProductDetails WHERE productID = ?";
            psDetails = con.prepareStatement(deleteDetailsQuery);
            psDetails.setString(1, productID);
            psDetails.executeUpdate();

            // Then delete from Products table
            String deleteProductQuery = "DELETE FROM Products WHERE productID = ?";
            psProduct = con.prepareStatement(deleteProductQuery);
            psProduct.setString(1, productID);
            int rowsAffected = psProduct.executeUpdate();

            con.commit(); // Commit transaction

            return rowsAffected > 0;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    throw new SQLException("Error during transaction rollback", rollbackEx);
                }
            }
            throw e;

        } finally {
            if (psProduct != null) {
                psProduct.close();
            }
            if (psDetails != null) {
                psDetails.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void createProduct(Product product, ProductDetailDTO productDetails) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement psProduct = null;
        PreparedStatement psDetails = null;

        try {
            con = DBUtils.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Insert into Products table
            String productQuery = "INSERT INTO Products (productID, name, price, quantity, categoryID, brandID, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?)";
            psProduct = con.prepareStatement(productQuery);
            psProduct.setString(1, product.getProductID());
            psProduct.setString(2, product.getName());
            psProduct.setBigDecimal(3, product.getPrice());
            psProduct.setInt(4, product.getQuantity());
            psProduct.setString(5, product.getCategoryID());
            psProduct.setString(6, product.getBrandID());
            psProduct.setString(7, product.getImageURL());
            psProduct.executeUpdate();

            // Generate a new productDetailID
            String newProductDetailID = generateNewProductDetailID();

            // Insert into ProductDetails table
            String detailsQuery = "INSERT INTO ProductDetails (productDetailID, productID, description, specifications, warrantyPeriod) VALUES (?, ?, ?, ?, ?)";
            psDetails = con.prepareStatement(detailsQuery);
            psDetails.setString(1, newProductDetailID);
            psDetails.setString(2, product.getProductID()); // Ensure this matches productID
            psDetails.setString(3, productDetails.getDescription());
            psDetails.setString(4, productDetails.getSpecifications());
            psDetails.setString(5, productDetails.getWarrantyPeriod());
            psDetails.executeUpdate();

            con.commit(); // Commit transaction

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    throw new SQLException("Error during transaction rollback", rollbackEx);
                }
            }
            throw e; // Rethrow exception to be handled by the caller

        } finally {
            if (psProduct != null) {
                try {
                    psProduct.close(); // Close PreparedStatement
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psDetails != null) {
                try {
                    psDetails.close(); // Close PreparedStatement
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close(); // Close Connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to generate a new productDetailID
    public String generateNewProductDetailID() {
        // Example ID generation, replace with actual logic
        return "PD" + System.currentTimeMillis();
    }

    public void updateProductQuantity(String productID, int newQuantity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement("UPDATE Products SET quantity = ? WHERE productID = ?")) {
            ptm.setInt(1, newQuantity);
            ptm.setString(2, productID);
            ptm.executeUpdate();
        }
    }

}
