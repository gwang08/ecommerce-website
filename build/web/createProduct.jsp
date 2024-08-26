<!DOCTYPE html>
<html>
<head>
    <title>Create New Product</title>
    <link rel="icon" type="image/x-icon" href="images/android-chrome-512x512-removebg-preview.png">
    <style>
        .form-container {
            width: 300px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .form-container label {
            display: block;
            margin: 10px 0 5px;
        }
        .form-container input[type="text"],
        .form-container input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .form-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container input[type="submit"]:hover {
            background-color: #218838;
        }
        .form-container a {
            display: block;
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>Create New Product</h1>
        <form action="CreateProductController" method="post">
            <label for="productID">Product ID:</label>
            <input type="text" id="productID" name="productID" required>

            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required>

            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" required>

            <label for="categoryID">Category ID:</label>
            <input type="text" id="categoryID" name="categoryID" required>

            <label for="brandID">Brand ID:</label>
            <input type="text" id="brandID" name="brandID" required>

            <label for="imageURL">Image URL:</label>
            <input type="text" id="imageURL" name="imageURL" placeholder="Enter image URL">

            <!-- Product Detail Fields -->
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" required>

            <label for="specifications">Specifications:</label>
            <input type="text" id="specifications" name="specifications" required>

            <label for="warrantyPeriod">Warranty Period:</label>
            <input type="text" id="warrantyPeriod" name="warrantyPeriod" required>

            <input type="submit" value="Create Product">
        </form>
        <a href="productList.jsp">Back to Product List</a>
    </div>
</body>
</html>
