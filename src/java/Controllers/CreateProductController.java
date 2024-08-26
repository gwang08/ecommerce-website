package Controllers;

import Products.Product;
import Products.ProductDAO;
import Products.ProductDetailDTO;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admins
 */
public class CreateProductController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String productID = request.getParameter("productID");
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String categoryID = request.getParameter("categoryID");
        String brandID = request.getParameter("brandID");
        String imageURL = request.getParameter("imageURL");

        // Retrieve product detail data
        String description = request.getParameter("description");
        String specifications = request.getParameter("specifications");
        String warrantyPeriod = request.getParameter("warrantyPeriod");

        // Create a new product DTO
        Product product = new Product(productID, name, price, quantity, categoryID, brandID, imageURL);

        try {
            // Generate a new productDetailID
            ProductDAO dao = new ProductDAO();
            String newProductDetailID = dao.generateNewProductDetailID();

            // Create a new product detail DTO with the generated ID
            ProductDetailDTO productDetails = new ProductDetailDTO(
                    newProductDetailID, // Pass the new productDetailID
                    productID,
                    description,
                    specifications,
                    warrantyPeriod
            );

            // Add the product and product details to the database
            dao.createProduct(product, productDetails);

            // Redirect to the product list page
            response.sendRedirect("ProductListController");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page if needed
        }
    }
}
