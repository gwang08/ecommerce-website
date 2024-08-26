/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Products.Product;
import Products.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
public class SearchProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        ProductDAO dao = new ProductDAO();
        List<Product> products = null;

        try {
            // Tìm kiếm sản phẩm theo tên và ID
            products = dao.searchProducts(searchQuery);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Chuyển hướng đến trang lỗi nếu có lỗi xảy ra
            response.sendRedirect("error.jsp");
            return;
        }

        // Đưa kết quả tìm kiếm vào request và chuyển hướng đến trang kết quả tìm kiếm
        request.setAttribute("PRODUCT_LIST", products);
        request.getRequestDispatcher("productList.jsp").forward(request, response);
    }
}
