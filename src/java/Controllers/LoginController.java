package Controllers;

import Carts.CartDAO;
import Products.Product;
import Products.ProductDAO;
import Users.UserDAO;
import Users.UserDTO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

//@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String USER_PAGE = "home.jsp";
    private static final String ADMIN_PAGE = "ProductListController";
    private static final String US = "US";
    private static final String AD = "AD";
    private static final String SECRET_KEY = "6LemhwMqAAAAAOk3tYAPCRU5ed8xT6CUnS1nEcPq";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String grecaptcharesponse = request.getParameter("g-recaptcha-response");

            if (grecaptcharesponse == null || grecaptcharesponse.isEmpty()) {
                request.setAttribute("captchaError", "Please verify the reCAPTCHA.");
            } else if (verifyRecaptcha(grecaptcharesponse)) {
                UserDAO dao = new UserDAO();
                UserDTO loginUser = dao.checkLogin(userID, password);

                if (loginUser != null && loginUser.isStatus()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);

                    try {
                        CartDAO cartDAO = new CartDAO();
                        Map<String, Product> cart = cartDAO.getCartByUserId(loginUser.getUserID());
                        session.setAttribute("USER_CART", cart);
                    } catch (Exception e) {
                        log("Error loading cart: " + e.toString());
                        request.setAttribute("ERROR", "Error loading cart. Please try again.");
                        url = ERROR;
                    }

                    ProductDAO productDao = new ProductDAO();
                    List<Product> phoneProducts = productDao.getProductsByCategory("C1");
                    List<Product> computerProducts = productDao.getProductsByCategory("C2");
                    List<Product> electronicProducts = productDao.getProductsByCategory("C3");

                    request.setAttribute("phoneProducts", phoneProducts);
                    request.setAttribute("computerProducts", computerProducts);
                    request.setAttribute("electronicProducts", electronicProducts);

                    String roleID = loginUser.getRoleID();
                    if (AD.equals(roleID)) {
                        url = ADMIN_PAGE;
                    } else if (US.equals(roleID)) {
                        url = USER_PAGE;
                    } else {
                        request.setAttribute("ERROR", "Role not supported");
                    }
                } else {
                    request.setAttribute("ERROR", "Incorrect User ID, Password, or Account is Inactive!");
                }
            } else {
                request.setAttribute("captchaError", "reCAPTCHA verification failed!");
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
            request.setAttribute("ERROR", "An unexpected error occurred. Please try again.");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private boolean verifyRecaptcha(String gRecaptchaResponse) {
        try {
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

            OutputStream out = conn.getOutputStream();
            out.write(postParams.getBytes());
            out.flush();
            out.close();

            InputStream res = conn.getInputStream();
            Scanner scanner = new Scanner(res, "UTF-8");
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rememberMe")) {
                    try {
                        String userID = cookie.getValue();
                        UserDAO dao = new UserDAO();
                        UserDTO loginUser = dao.getUser(userID);
                        if (loginUser != null && loginUser.isStatus()) {
                            HttpSession session = request.getSession();
                            session.setAttribute("LOGIN_USER", loginUser);
                            String roleID = loginUser.getRoleID();
                            if (AD.equals(roleID)) {
                                request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
                            } else if (US.equals(roleID)) {
                                request.getRequestDispatcher(USER_PAGE).forward(request, response);
                            } else {
                                request.setAttribute("ERROR", "Role not supported");
                                request.getRequestDispatcher(ERROR).forward(request, response);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
