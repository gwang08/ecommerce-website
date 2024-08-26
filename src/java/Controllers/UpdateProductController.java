package Controllers;

import Products.Product;
import Products.ProductDAO;
import Products.ProductDetailDTO;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateProduct")
public class UpdateProductController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        // Lấy dữ liệu từ form
        String productID = request.getParameter("productID");
        String name = request.getParameter("name");
        String priceParam = request.getParameter("price");
        BigDecimal price = null;
        if (priceParam != null && !priceParam.isEmpty()) {
            price = new BigDecimal(priceParam);
        } else {
            throw new IllegalArgumentException("Price is missing or invalid.");
        }
        
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String categoryID = request.getParameter("categoryID");
        String brandID = request.getParameter("brandID");
        String imageURL = request.getParameter("imageURL");

        // Lấy dữ liệu chi tiết sản phẩm
        String description = request.getParameter("description");
        String specifications = request.getParameter("specifications");
        String warrantyPeriod = request.getParameter("warrantyPeriod");

        // Tạo đối tượng Product
        Product product = new Product(productID, name, price, quantity, categoryID, brandID, imageURL);

        // Tạo đối tượng ProductDetailDTO
        ProductDetailDTO productDetails = new ProductDetailDTO(description, specifications, warrantyPeriod);

        // Cập nhật sản phẩm và chi tiết sản phẩm trong cơ sở dữ liệu
        ProductDAO dao = new ProductDAO();
        boolean updated = dao.updateProduct(product, productDetails);

        if (updated) {
            response.sendRedirect("ProductListController");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }

    } catch (IllegalArgumentException e) {
        // Xử lý lỗi nhập liệu không hợp lệ (ví dụ: thiếu giá hoặc giá không hợp lệ)
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp"); // Redirect đến trang lỗi nếu cần
    }
}
}
