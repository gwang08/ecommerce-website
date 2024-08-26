package Controllers;

import Products.Product;
import Products.ProductDetailDTO;
import Products.ProductDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewProduct")
public class ProductController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        if (productID != null && !productID.isEmpty()) {
            try {
                ProductDAO dao = new ProductDAO();
                Product product = dao.getProductById(productID);
                ProductDetailDTO productDetails = dao.getProductDetailById(productID);  // Fetch product details

                if (product != null && productDetails != null) {
                    request.setAttribute("product", product);
                    request.setAttribute("productDetails", productDetails);  // Set product details as attribute
                    request.getRequestDispatcher("productDetails.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new ServletException(e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        }
    }
}
