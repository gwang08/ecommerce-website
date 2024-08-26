package Controllers;

import Carts.CartDAO;
import Orders.Order;
import Orders.OrderDAO;
import Orders.OrderDetail;
import Products.Product;
import Users.UserDTO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admins
 */
public class CheckController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
        String userID = loginUser.getUserID();
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();

        // Lấy giỏ hàng từ session
        Map<String, Product> cartItems = (Map<String, Product>) session.getAttribute("USER_CART");

        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("message", "Không có sản phẩm nào trong giỏ hàng để thanh toán.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewCart.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            // Kiểm tra số lượng sản phẩm trong kho
            for (Product product : cartItems.values()) {
                int stockQuantity = cartDAO.getProductStock(product.getProductID());
                if (product.getQuantity() > stockQuantity) {
                    request.setAttribute("message", "Số lượng sản phẩm " + product.getName() + " không đủ trong kho.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("viewCart.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
            }

            // Tạo một đơn hàng mới
            String orderID = orderDAO.generateOrderID();
            BigDecimal total = cartDAO.getTotal(cartItems);
            Order order = new Order(orderID, userID, new Date(), total, true);
            orderDAO.insertOrder(order);

            // Lưu chi tiết đơn hàng
            for (Product product : cartItems.values()) {
                OrderDetail orderDetail = new OrderDetail(orderDAO.generateOrderDetailID(), orderID, product.getProductID(), product.getPrice(), product.getQuantity(), true);
                orderDAO.insertOrderDetail(orderDetail);

                // Cập nhật số lượng sản phẩm trong kho
                cartDAO.updateProductQuantity(product.getProductID(), product.getQuantity());
            }

            // Xóa tất cả sản phẩm trong giỏ hàng sau khi thanh toán
            String cartID = cartDAO.getOrCreateCartID(userID);
            cartDAO.removeAllItemsFromCart(cartID);

            // Xóa giỏ hàng từ session
            session.removeAttribute("USER_CART");

            // Thông báo thanh toán thành công
            request.setAttribute("message", "Thanh toán thành công! Sản phẩm trong kho đã được cập nhật.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("paymentSuccess.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra trong quá trình thanh toán.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("viewCart.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
