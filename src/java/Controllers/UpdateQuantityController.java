package Controllers;

import Carts.CartDAO;
import Products.Product;
import Users.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateQuantityController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        String url = ERROR;
        try {
            HttpSession session = request.getSession(false);
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (session != null && loginUser != null) {
                String userID = loginUser.getUserID();
                String productID = request.getParameter("productID");
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                CartDAO cartDAO = new CartDAO();
                cartDAO.updateCart(cartDAO.getOrCreateCartID(userID), productID, quantity);

                // Cập nhật lại giỏ hàng
                Map<String, Product> cart = cartDAO.getCartByUserId(userID);
                session.setAttribute("USER_CART", cart);
                request.setAttribute("UPDATE_SUCCESS", "Quantity updated successfully!");
                url = SUCCESS;
            } else {
                request.setAttribute("UPDATE_ERROR", "Please login to update the cart!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("UPDATE_ERROR", "Invalid quantity format.");
            log("Error at UpdateQuantityController: " + e.getMessage(), e);
        } catch (SQLException e) {
            log("Error at UpdateQuantityController: " + e.getMessage(), e);
            request.setAttribute("UPDATE_ERROR", "An error occurred while updating the quantity.");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateQuantityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateQuantityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Update Quantity Controller";
    }
}
