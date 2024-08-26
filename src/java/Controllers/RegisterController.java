package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Users.UserDAO;
import Users.UserDTO;

public class RegisterController extends HttpServlet {

    private static final String SUCCESS = "register.jsp"; // Redirect back to register.jsp to show the success message
    private static final String ERROR = "register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        try {
            if ("checkUser".equals(action)) {
                // Handle Ajax request to check the username
                String userID = request.getParameter("userID");
                UserDAO dao = new UserDAO();
                boolean userExists = dao.checkUserExists(userID);
                response.getWriter().write(userExists ? "exists" : "available");
                return; // Skip the rest of the processing for Ajax requests
            }

            // Handle user registration
            String url = ERROR;
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            UserDAO dao = new UserDAO();
            boolean userExists = dao.checkUserExists(userID);

            if (userExists) {
                request.setAttribute("ERROR", "Username already exists. Please choose another.");
            } else if (!password.equals(confirmPassword)) {
                request.setAttribute("ERROR", "Passwords do not match!");
            } else {
                // Set default role and status for the new user
                String roleID = "US";
                boolean status = true;

                UserDTO newUser = new UserDTO(userID, fullName, password, roleID, status);
                boolean isRegistered = dao.registerUser(newUser);

                if (isRegistered) {
                    request.setAttribute("SUCCESS", "Account created successfully! Please login.");
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Registration failed. Please try again.");
                }
            }

            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            log("Error at RegisterController: " + e.toString());
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
        return "Short description";
    }
}
