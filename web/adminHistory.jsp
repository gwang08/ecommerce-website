<%@ page import="java.util.List" %>
<%@ page import="Orders.OrderDetail" %>
<%@ page import="Orders.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Order History</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="images/android-chrome-512x512-removebg-preview.png">
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
        }

        .container {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: var(--secondary-color);
            padding: 2rem;
            color: var(--light-color);
        }

        .sidebar h1 {
            font-size: 1.5rem;
            margin-bottom: 2rem;
            text-align: center;
        }

        .sidebar ul {
            list-style-type: none;
        }

        .sidebar ul li {
            margin-bottom: 1rem;
        }

        .sidebar ul li a {
            color: var(--light-color);
            text-decoration: none;
            display: flex;
            align-items: center;
            transition: color 0.3s ease;
        }

        .sidebar ul li a:hover {
            color: var(--primary-color);
        }

        .sidebar ul li a i {
            margin-right: 0.5rem;
        }

        .content {
            flex: 1;
            padding: 2rem;
        }

        .content h1 {
            font-size: 2rem;
            margin-bottom: 2rem;
            color: var(--secondary-color);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        }

        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid var(--light-color);
        }

        th {
            background-color: var(--primary-color);
            color: white;
            font-weight: 500;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .btn {
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-size: 0.9rem;
        }

        .btn-view {
            background-color: var(--primary-color);
            color: white;
        }

        .btn-view:hover {
            background-color: #2980b9;
        }

        .btn-delete {
            background-color: var(--danger-color);
            color: white;
        }

        .btn-delete:hover {
            background-color: #c0392b;
        }

        .alert {
            padding: 1rem;
            border-radius: 4px;
            margin-bottom: 1rem;
        }

        .alert-info {
            background-color: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }

        @media (max-width: 768px) {
            .container {
                flex-direction: column;
            }

            .sidebar {
                width: 100%;
                margin-bottom: 1rem;
            }

            .content {
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h1>Admin Dashboard</h1>
            <ul>
                <li><a href="MainController?action=logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                <li><a href="MainController?action=Search_User"><i class="fas fa-users"></i> Users</a></li>
                <li><a href="MainController?action=AdminHistory"><i class="fas fa-history"></i> Order History</a></li>
            </ul>
        </div>
        <div class="content">
            <h1>Order History</h1>
            <%
                List<Order> orderList = (List<Order>) request.getAttribute("ORDER_LIST");
                List<OrderDetail> orderDetailList = (List<OrderDetail>) request.getAttribute("ORDER_DETAIL_LIST");
                if (orderList != null && !orderList.isEmpty()) {
            %>
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>User ID</th>
                        <th>Date</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Order order : orderList) {
                    %>
                    <tr>
                        <td><%= order.getOrderID() %></td>
                        <td><%= order.getUserID() %></td>
                        <td><%= order.getDate() %></td>
                        <td><%= order.getTotal() %></td>
                        <td><%= order.isStatus() ? "Completed" : "Pending" %></td>
                        <td>
                            <a href="viewOrderDetails?orderID=<%= order.getOrderID() %>" class="btn btn-view"><i class="fas fa-eye"></i> View Details</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } else { %>
            <div class="alert alert-info">
                <i class="fas fa-info-circle"></i> No orders found.
            </div>
            <% } %>
        </div>
    </div>
</body>
</html>
