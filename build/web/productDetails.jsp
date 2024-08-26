<%@page import="Products.ProductDetailDTO"%>
<%@ page import="Products.Product" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Details</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>
            :root {
                --primary-color: #3498db;
                --secondary-color: #2c3e50;
                --success-color: #2ecc71;
                --danger-color: #e74c3c;
                --light-color: #ecf0f1;
                --dark-color: #34495e;
            }

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Roboto', sans-serif;
                background-color: var(--light-color);
                color: var(--dark-color);
                line-height: 1.6;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }

            .container {
                width: 90%;
                max-width: 1200px;
                display: flex;
                justify-content: space-between;
                padding: 2rem;
                background: #fff;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                margin: 2rem;
            }

            .image-section {
                width: 40%;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .image-section img {
                max-width: 100%;
                height: auto;
                border-radius: 8px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            }

            .details-section {
                width: 55%;
            }

            .details-section h1 {
                font-size: 2rem;
                margin-bottom: 1.5rem;
                color: var(--secondary-color);
            }

            .details-section table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1.5rem;
            }

            .details-section th,
            .details-section td {
                padding: 0.75rem;
                text-align: left;
                border-bottom: 1px solid var(--light-color);
            }

            .details-section th {
                background-color: var(--primary-color);
                color: white;
                font-weight: 500;
            }

            .details-section input[type="text"],
            .details-section input[type="number"] {
                width: 100%;
                padding: 0.5rem;
                border: 1px solid var(--light-color);
                border-radius: 4px;
                font-size: 1rem;
            }

            .details-section input[readonly] {
                background-color: #f8f9fa;
                cursor: not-allowed;
            }

            .btn {
                display: inline-block;
                padding: 0.75rem 1.5rem;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1rem;
                text-decoration: none;
                transition: background-color 0.3s ease;
                margin-right: 1rem;
            }

            .btn-primary {
                background-color: var(--primary-color);
                color: white;
            }

            .btn-primary:hover {
                background-color: #2980b9;
            }

            .btn-danger {
                background-color: var(--danger-color);
                color: white;
            }

            .btn-danger:hover {
                background-color: #c0392b;
            }

            .btn-link {
                color: var(--primary-color);
            }

            .btn-link:hover {
                text-decoration: underline;
            }

            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                }

                .image-section,
                .details-section {
                    width: 100%;
                }

                .image-section {
                    margin-bottom: 2rem;
                }
            }
        </style>
    </head>
    <body>
        <%
            Product product = (Product) request.getAttribute("product");
            ProductDetailDTO productDetails = (ProductDetailDTO) request.getAttribute("productDetails");

            if (product == null || productDetails == null) {
        %>
        <div class="container">
            <p>Product details not available.</p>
        </div>
        <%
        } else {
        %>
        <div class="container">
            <div class="image-section">
                <img src="<%= product.getImageURL()%>" alt="<%= product.getName()%>">
            </div>
            <div class="details-section">
                <h1>Product Details</h1>
                <form action="updateProduct" method="post">
                    <table>
                        <tr>
                            <th>ID</th>
                            <td><input type="text" name="productID" value="<%= product.getProductID()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Name</th>
                            <td><input type="text" name="name" value="<%= product.getName()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Price</th>
                            <td><input type="number" name="price" value="<%= product.getPrice()%>" required></td>
                        </tr>
                        <tr>
                            <th>Quantity</th>
                            <td><input type="number" name="quantity" value="<%= product.getQuantity()%>" required></td>
                        </tr>
                        <tr>
                            <th>Category</th>
                            <td><input type="text" name="categoryID" value="<%= product.getCategoryID()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Brand</th>
                            <td><input type="text" name="brandID" value="<%= product.getBrandID()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Product Detail ID</th>
                            <td><input type="text" name="productDetailID" value="<%= productDetails.getProductDetailID()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td><input type="text" name="description" value="<%= productDetails.getDescription()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Specifications</th>
                            <td><input type="text" name="specifications" value="<%= productDetails.getSpecifications()%>" readonly></td>
                        </tr>
                        <tr>
                            <th>Warranty Period</th>
                            <td><input type="text" name="warrantyPeriod" value="<%= productDetails.getWarrantyPeriod()%>" required></td>
                        </tr>
                    </table>
                    <input type="hidden" name="imageURL" value="<%= product.getImageURL()%>">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-edit"></i> Update Product
                    </button>
                </form>
                <form action="deleteProduct" method="post" style="display: inline;">
                    <input type="hidden" name="productID" value="<%= product.getProductID()%>">
                    <button type="submit" class="btn btn-danger">
                        <i class="fas fa-trash-alt"></i> Delete Product
                    </button>
                </form>
                <a href="ProductListController" class="btn btn-link">
                    <i class="fas fa-arrow-left"></i> Back to Product List
                </a>
            </div>
        </div>
        <%
            }
        %>
    </body>
</html>