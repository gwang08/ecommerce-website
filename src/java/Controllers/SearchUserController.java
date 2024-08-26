package Controllers;

import Users.UserDAO;
import Users.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchUserController", urlPatterns = {"/SearchUserController"})
public class SearchUserController extends HttpServlet {

    private static final String ERROR = "user.jsp";
    private static final String SUCCESS = "user.jsp";

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String url = ERROR;
    try {
        UserDAO aDao = new UserDAO();
        String search = request.getParameter("search");
        List<UserDTO> list;
        
        if (search != null && !search.trim().isEmpty()) {
            list = aDao.searchUsers(search); // Search by name or ID
        } else {
            list = aDao.getAppoinments(); // Load all users if no search term
        }

        if (list != null && list.size() > 0) {
            request.setAttribute("LIST_USER", list);
        } else {
            request.setAttribute("ERROR", "No users found.");
        }

        url = SUCCESS;
    } catch (Exception e) {
        log("Error at processRequest: " + e.getMessage());
        request.setAttribute("ERROR", "Something went wrong.");
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
        return "SearchUserController Servlet";
    }
}
