package Carts;

import Products.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class CartDAO {

    // SQL queries
    private static final String GET_CART_BY_USER_ID
            = "SELECT p.productID, p.name, p.price, ci.quantity, p.categoryID, p.brandID, p.imageURL, "
            + "cat.categoryName, b.brandName "
            + "FROM CartItem ci "
            + "JOIN Products p ON ci.productID = p.productID "
            + "JOIN Categories cat ON p.categoryID = cat.categoryID "
            + "JOIN Brands b ON p.brandID = b.brandID "
            + "WHERE ci.cartID = (SELECT cartID FROM Cart WHERE userID = ?)";

    private static final String INSERT_CART_ITEM
            = "INSERT INTO CartItem (cartItemID, cartID, productID, quantity) "
            + "VALUES (NEWID(), ?, ?, ?)";

    private static final String CREATE_CART
            = "INSERT INTO Cart(cartID, userID) VALUES(?, ?)";

    private static final String REMOVE_CART_ITEM
            = "DELETE FROM CartItem WHERE cartID = ? AND productID = ?";

    private static final String UPDATE_CART_ITEM
            = "UPDATE CartItem SET quantity = ? WHERE cartID = ? AND productID = ?";

    private static final String UPDATE_PRODUCT_QUANTITY
            = "UPDATE Products SET quantity = quantity - ? WHERE productID = ?";

    public Map<String, Product> getCartByUserId(String userID) throws SQLException, ClassNotFoundException {
        Map<String, Product> cart = new HashMap<>();

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(GET_CART_BY_USER_ID)) {

            ptm.setString(1, userID);
            try (ResultSet rs = ptm.executeQuery()) {
                while (rs.next()) {
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
                    cart.put(product.getProductID(), product);
                }
            }
        }
        return cart;
    }

    public void insertToCart(String cartID, String productID, int quantity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(INSERT_CART_ITEM)) {

            ptm.setString(1, cartID);
            ptm.setString(2, productID);
            ptm.setInt(3, quantity);
            ptm.executeUpdate();
        }
    }

    public void updateCart(String cartID, String productID, int quantity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(UPDATE_CART_ITEM)) {

            // Đảm bảo số lượng không âm
            int newQuantity = Math.max(quantity, 0);

            ptm.setInt(1, newQuantity);
            ptm.setString(2, cartID);
            ptm.setString(3, productID);
            ptm.executeUpdate();
        }
    }

    public String getOrCreateCartID(String userID) throws SQLException, ClassNotFoundException {
        String cartID;
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement("SELECT cartID FROM Cart WHERE userID = ?")) {

            ptm.setString(1, userID);
            try (ResultSet rs = ptm.executeQuery()) {
                if (rs.next()) {
                    cartID = rs.getString("cartID");
                } else {
                    cartID = userID + "_cart";
                    createCart(conn, cartID, userID);
                }
            }
        }
        return cartID;
    }

    private void createCart(Connection conn, String cartID, String userID) throws SQLException {
        try (PreparedStatement ptm = conn.prepareStatement(CREATE_CART)) {
            ptm.setString(1, cartID);
            ptm.setString(2, userID);
            ptm.executeUpdate();
        }
    }

    public void removeProduct(String userID, String productID) throws SQLException, ClassNotFoundException {
        String cartID = getOrCreateCartID(userID);
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(REMOVE_CART_ITEM)) {

            ptm.setString(1, cartID);
            ptm.setString(2, productID);
            ptm.executeUpdate();
        }
    }

    public void removeAllItemsFromCart(String cartID) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement("DELETE FROM CartItem WHERE cartID = ?")) {
            ptm.setString(1, cartID);
            ptm.executeUpdate();
        }
    }

    public void updateProductQuantity(String productID, int quantity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(UPDATE_PRODUCT_QUANTITY)) {

            ptm.setInt(1, quantity);
            ptm.setString(2, productID);
            ptm.executeUpdate();
        }
    }

    public BigDecimal getTotal(Map<String, Product> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : cartItems.values()) {
            total = total.add(product.getPrice().multiply(new BigDecimal(product.getQuantity())));
        }
        return total;
    }

    public int getProductStock(String productID) throws SQLException, ClassNotFoundException {
        String query = "SELECT quantity FROM Products WHERE productID = ?";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(query)) {

            ptm.setString(1, productID);
            try (ResultSet rs = ptm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                } else {
                    throw new SQLException("Product not found");
                }
            }
        }
    }
}
