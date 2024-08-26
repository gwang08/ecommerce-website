/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orders;

import Products.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.DBUtils;

/**
 *
 * @author Admins
 */
public class OrderDetailDAO {
    public void addOrderDetail(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ptm = conn.prepareStatement("INSERT INTO OrderDetails (orderDetailID, orderID, productID, price, quantity, status) VALUES (?, ?, ?, ?, ?, ?)")) {

            ptm.setString(1, orderDetail.getOrderDetailID());
            ptm.setString(2, orderDetail.getOrderID());
            ptm.setString(3, orderDetail.getProductID());
            ptm.setBigDecimal(4, orderDetail.getPrice());
            ptm.setInt(5, orderDetail.getQuantity());
            ptm.setBoolean(6, orderDetail.isStatus());
            ptm.executeUpdate();
        }
    }
    
    private static final String GET_ORDER_DETAILS_BY_ORDER_ID = 
        "SELECT orderDetailID, orderID, productID, price, quantity, status FROM OrderDetails WHERE orderID = ?";

    public List<OrderDetail> getOrderDetailsByOrderID(String orderID) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Giả sử bạn có một phương thức để lấy connection từ pool hoặc DriverManager
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ORDER_DETAILS_BY_ORDER_ID);
            ps.setString(1, orderID);
            rs = ps.executeQuery();

            while (rs.next()) {
                String orderDetailID = rs.getString("orderDetailID");
                String orderID_db = rs.getString("orderID");
                String productID = rs.getString("productID");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");
                boolean  status = rs.getBoolean("status");

                // Tạo đối tượng OrderDetailsDTO và thêm vào danh sách
                OrderDetail detail = new OrderDetail(orderDetailID, orderID, productID, price, quantity, status);
                orderDetailsList.add(detail);
            }
        } finally {
            // Đóng tất cả các tài nguyên
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return orderDetailsList;
    }
    
    private static final String GET_ALL_ORDER_DETAILS = 
        "SELECT orderDetailID, orderID, productID, price, quantity, status FROM OrderDetails";

    public List<OrderDetail> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_ORDER_DETAILS);
            rs = ps.executeQuery();

            while (rs.next()) {
                String orderDetailID = rs.getString("orderDetailID");
                String orderID = rs.getString("orderID");
                String productID = rs.getString("productID");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");
                boolean status = rs.getBoolean("status");

                OrderDetail detail = new OrderDetail(orderDetailID, orderID, productID, price, quantity, status);
                orderDetailsList.add(detail);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }

        return orderDetailsList;
    }
}
