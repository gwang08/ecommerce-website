/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Carts.CartDAO;
import Products.Product;
import Users.UserDAO;
import Users.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class UpdateController extends HttpServlet {

    private static final String ERROR = "SearchUserController";
    private static final String SUCCESS = "SearchUserController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession(false);
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (session != null && loginUser != null) {
                String userID = loginUser.getUserID();
                String productID = request.getParameter("productID");
                CartDAO cartDAO = new CartDAO();

                // Remove product from the cart
                cartDAO.removeProduct(userID, productID);

                // Update cart in session
                Map<String, Product> cart = cartDAO.getCartByUserId(userID);
                session.setAttribute("USER_CART", cart);

                // Redirect to view cart page
                url = SUCCESS;
                request.setAttribute("REMOVE_SUCCESS", "Product removed from cart successfully!");
            } else {
                request.setAttribute("REMOVE_ERROR", "You need to login first!");
            }
        } catch (Exception e) {
            log("Error at RemoveController: " + e.toString());
            request.setAttribute("REMOVE_ERROR", "An error occurred while removing the product from the cart.");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
