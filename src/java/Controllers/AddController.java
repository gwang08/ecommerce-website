package Controllers;

import Carts.CartDAO;
import Products.Product;
import Products.ProductDAO;
import Users.UserDTO;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admins
 */
public class AddController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (loginUser == null) {
                url = "login.jsp";
                request.setAttribute("ADD_ERROR", "You need to login first!");
            } else {
                String userID = loginUser.getUserID();
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(productID);

                if (product != null) {
                    CartDAO cartDAO = new CartDAO();
                    String cartID = cartDAO.getOrCreateCartID(userID);
                    Map<String, Product> cart = cartDAO.getCartByUserId(userID);

                    // Update or add the product in the cart
                    if (cart.containsKey(productID)) {
                        Product existingProduct = cart.get(productID);
                        existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                        cartDAO.updateCart(cartID, productID, existingProduct.getQuantity());
                    } else {
                        product.setQuantity(1);
                        cart.put(productID, product);
                        cartDAO.insertToCart(cartID, productID, product.getQuantity());
                    }

                    session.setAttribute("USER_CART", cart);
                    url = SUCCESS;
                    request.setAttribute("ADD_SUCCESS", "Product added to cart successfully!");
                } else {
                    request.setAttribute("ADD_ERROR", "Product not found!");
                }
            }
        } catch (Exception e) {
            log("Error at AddController: " + e.getMessage(), e);
            request.setAttribute("ADD_ERROR", "An error occurred while adding the product to the cart.");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Add Product to Cart Controller";
    }
}
