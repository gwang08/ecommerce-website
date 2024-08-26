/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orders;

import Products.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class OrderDAO {

    private static final String INSERT_ORDER
            = "INSERT INTO Orders (orderID, userID, date, total, status) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_DETAIL
            = "INSERT INTO OrderDetails (orderDetailID, orderID, productID, price, quantity, status) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_MAX_ORDER_ID = "SELECT MAX(orderID) FROM Orders";

    private static final String GET_MAX_ORDER_DETAIL_ID = "SELECT MAX(orderDetailID) FROM OrderDetails";
    
    private static final String GET_ORDERS_BY_USER_ID = 
    "SELECT [orderID], [userID], [date], [total], [status] FROM [FPTShop].[dbo].[Orders] WHERE [userID] = ?";

    public void insertOrder(Order order) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(INSERT_ORDER)) {
            ptm.setString(1, order.getOrderID());
            ptm.setString(2, order.getUserID());
            ptm.setDate(3, new java.sql.Date(order.getDate().getTime()));
            ptm.setBigDecimal(4, order.getTotal());
            ptm.setBoolean(5, order.isStatus());
            ptm.executeUpdate();
        }
    }

    public void insertOrderDetail(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(INSERT_ORDER_DETAIL)) {
            ptm.setString(1, orderDetail.getOrderDetailID());
            ptm.setString(2, orderDetail.getOrderID());
            ptm.setString(3, orderDetail.getProductID());
            ptm.setBigDecimal(4, orderDetail.getPrice());
            ptm.setInt(5, orderDetail.getQuantity());
            ptm.setBoolean(6, orderDetail.isStatus());
            ptm.executeUpdate();
        }
    }

    public String generateOrderID() throws SQLException, ClassNotFoundException {
        String maxOrderID = null;

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(GET_MAX_ORDER_ID);
                ResultSet rs = ptm.executeQuery()) {

            if (rs.next()) {
                maxOrderID = rs.getString(1);
            }
        }

        if (maxOrderID != null) {
            // Lấy số thứ tự từ orderID (giả sử orderID có định dạng "ORDxxx")
            int orderNumber = Integer.parseInt(maxOrderID.substring(3));

            // Tăng số thứ tự lên 1
            orderNumber++;

            // Định dạng số thứ tự với 3 chữ số, ví dụ "001", "002"
            String newOrderNumber = String.format("%03d", orderNumber);

            // Tạo orderID mới
            return "ORD" + newOrderNumber;
        } else {
            // Nếu không có orderID nào trong cơ sở dữ liệu, bắt đầu từ "ORD001"
            return "ORD001";
        }
    }

    public String generateOrderDetailID() throws SQLException, ClassNotFoundException {
        String maxOrderDetailID = null;

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ptm = conn.prepareStatement(GET_MAX_ORDER_DETAIL_ID);
                ResultSet rs = ptm.executeQuery()) {

            if (rs.next()) {
                maxOrderDetailID = rs.getString(1);
            }
        }

        if (maxOrderDetailID != null) {
            // Lấy số thứ tự từ orderDetailID (giả sử orderDetailID có định dạng "ODDxxx")
            int orderDetailNumber = Integer.parseInt(maxOrderDetailID.substring(3));

            // Tăng số thứ tự lên 1
            orderDetailNumber++;

            // Định dạng số thứ tự với 3 chữ số, ví dụ "001", "002"
            String newOrderDetailNumber = String.format("%03d", orderDetailNumber);

            // Tạo orderDetailID mới
            return "ODD" + newOrderDetailNumber;
        } else {
            // Nếu không có orderDetailID nào trong cơ sở dữ liệu, bắt đầu từ "ODD001"
            return "ODD001";
        }
    }
    
    


    public static List<Order> getOrdersByUserId(String userID) throws SQLException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                ps = con.prepareStatement(GET_ORDERS_BY_USER_ID);
                ps.setString(1, userID);
                rs = ps.executeQuery();

                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String userID_db = rs.getString("userID");
                    Date orderDate = rs.getDate("date");
                    BigDecimal total = rs.getBigDecimal("total");
                    boolean  status = rs.getBoolean("status");

                    orders.add(new Order(orderID, userID_db, orderDate, total, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

        return orders;
    }
    
    private static final String GET_ALL_ORDERS = 
        "SELECT orderID, userID, date, total, status FROM Orders";

    public List<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_ORDERS);
            rs = ps.executeQuery();

            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String userID = rs.getString("userID");
                Date date = rs.getDate("date");
                BigDecimal total = rs.getBigDecimal("total");
                boolean status = rs.getBoolean("status");

                Order order = new Order(orderID, userID, date, total, status);
                orders.add(order);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return orders;
    }

}
