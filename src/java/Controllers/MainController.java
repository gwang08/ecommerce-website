package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String WELCOME = "HomeController";

    private static final String ADD = "add";
    private static final String ADD_CONTROLLER = "AddController";

    private static final String VIEW_CART = "view";
    private static final String VIEW_CART_PAGE = "viewCart.jsp";

    private static final String SEARCH = "Search";
    private static final String SEARCH_CONTROLLER = "SearchController";

    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";

    private static final String LOGOUT = "logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";

    private static final String REGISTER = "Register";
    private static final String REGISTER_CONTROLLER = "RegisterController";

    private static final String PRODUCT_LIST_CONTROLLER = "ProductListController";

    private static final String SEARCH_USER = "Search_User";
    private static final String SEARCH_USER_CONTROLLER = "SearchUserController";

    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateController";

    private static final String UPDATEPRODUCT = "update";
    private static final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductController";

    private static final String SORT = "sort";
    private static final String SORT_CONTROLLER = "SortController";

    private static final String REMOVE_PRODUCT = "removeProduct";
    private static final String REMOVE_PRODUCT_CONTROLLER = "RemoveController";

    private static final String UPDATE_QUANTITY = "updateQuantity";
    private static final String UPDATE_QUANTITY_CONTROLLER = "UpdateQuantityController";
    
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";
    
    private static final String CHECK = "Check";
    private static final String CHECK_CONTROLLER = "CheckController";
    
    private static final String HISTORY_ORDERS = "orderHistory";
    private static final String HISTORY_ORDERS_CONTROLLER = "OrderHistoryController";

    private static final String ORDER_DETAILS = "OrderDetails";
    private static final String ORDER_DETAILS_CONTROLLER = "OrderDetailsController";

    private static final String UPDATEUSER = "UpdateUser";
    private static final String UPDATE_USER_CONTROLLER = "UpdateUserController";
    
    private static final String ADMIN_HISTORY = "AdminHistory";
    private static final String ADMIN_HISTORY_CONTROLLER = "AdminHistoryController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = WELCOME;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String action = request.getParameter("action");
                if (ADD.equals(action)) {
                    url = ADD_CONTROLLER;
                } else if (SEARCH.equals(action)) {
                    url = SEARCH_CONTROLLER;
                } else if (LOGIN.equals(action)) {
                    url = LOGIN_CONTROLLER;
                } else if (LOGOUT.equals(action)) {
                    url = LOGOUT_CONTROLLER;
                } else if (REGISTER.equals(action)) {
                    url = REGISTER_CONTROLLER;
                } else if (VIEW_CART.equals(action)) {
                    url = VIEW_CART_PAGE;
                } else if ("ProductList".equals(action)) {
                    url = PRODUCT_LIST_CONTROLLER;
                } else if (SEARCH_USER.equals(action)) {
                    url = SEARCH_USER_CONTROLLER;
                } else if (UPDATE.equals(action)) {
                    url = UPDATE_CONTROLLER;
                } else if (UPDATEPRODUCT.equals(action)) {
                    url = UPDATE_PRODUCT_CONTROLLER;
                } else if (SORT.equals(action)) {
                    url = SORT_CONTROLLER;
                } else if (REMOVE_PRODUCT.equals(action)) {
                    url = REMOVE_PRODUCT_CONTROLLER;
                } else if (UPDATE_QUANTITY.equals(action)) {
                    url = UPDATE_QUANTITY_CONTROLLER;
                } else if (DELETE.equals(action)) {
                    url = DELETE_CONTROLLER;
                } else if (CHECK.equals(action)) {
                    url = CHECK_CONTROLLER;
                }else if (HISTORY_ORDERS.equals(action)) {
                    url = HISTORY_ORDERS_CONTROLLER;
                } else if (ORDER_DETAILS.equals(action)) {
                    url = ORDER_DETAILS_CONTROLLER; 
                } else if (UPDATEUSER.equals(action)) {
                    url = UPDATE_USER_CONTROLLER;
                } else if (ADMIN_HISTORY.equals(action)) {
                    url = ADMIN_HISTORY_CONTROLLER;
                }
            }
        } catch (Exception e) {
            log("Error at MainController " + e.toString());
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
