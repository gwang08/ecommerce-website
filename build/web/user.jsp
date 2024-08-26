<%@page import="Users.UserDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Management Dashboard</title>
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

            .search-form {
                display: flex;
                margin-bottom: 2rem;
            }

            .search-form input {
                flex: 1;
                padding: 0.5rem;
                border: 1px solid var(--secondary-color);
                border-radius: 4px 0 0 4px;
            }

            .search-form button {
                padding: 0.5rem 1rem;
                background-color: var(--primary-color);
                color: white;
                border: none;
                border-radius: 0 4px 4px 0;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .search-form button:hover {
                background-color: #2980b9;
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

            .alert {
                padding: 1rem;
                border-radius: 4px;
                margin-bottom: 1rem;
            }

            .alert-danger {
                background-color: #fadbd8;
                color: var(--danger-color);
                border: 1px solid var(--danger-color);
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
                <h1>User Management</h1>
                <ul>
                    <li><a href="MainController?action=logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                    <li><a href="MainController?action=ProductList"><i class="fas fa-box"></i> Product</a></li>
                    <li><a href="MainController?action=AdminHistory"><i class="fas fa-users"></i> History</a></li>
                </ul>
            </div>
            <div class="content">
                <%
                    String search = request.getParameter("search");
                    if (search == null) {
                        search = "";
                    }
                    UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
                %>

                <h1>User List</h1>

                <form class="search-form" action="MainController" method="POST">
                    <input type="text" name="search" placeholder="Search by name or ID" value="<%= search%>"/>
                    <button type="submit" name="action" value="Search_User">
                        <i class="fas fa-search"></i> Search
                    </button>
                </form>

                <%
                    List<UserDTO> list = (List<UserDTO>) request.getAttribute("LIST_USER");
                    if (list != null && !list.isEmpty()) {
                %>

                <table>
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>User ID</th>
                            <th>Full Name</th>
                            <th>Role ID</th>
                            <th>Password</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (int i = 0; i < list.size(); i++) {
                                UserDTO user = list.get(i);
                        %>
                        <tr>
                    <form action="MainController" method="POST">
                        <td><%= i + 1%></td>
                        <td><%= user.getUserID()%></td>
                        <td><input type="text" name="fullName" value="<%= user.getFullName()%>" required></td>
                        <td><input type="text" name="roleID" value="<%= user.getRoleID()%>" required></td>
                        <td><input type="password" name="password" value="<%= user.getPassword()%>" required></td>
                        <td><%= user.isStatus() ? "Active" : "Inactive"%></td>
                        <td>
                            <input type="hidden" name="userID" value="<%= user.getUserID()%>">
                            <button type="submit" name="action" value="UpdateUser" class="btn btn-primary">
                                <i class="fas fa-edit"></i> Update
                            </button>
                            <a href="MainController?action=Delete&userID=<%= user.getUserID()%>&search=<%= search%>" class="btn btn-danger">
                                <i class="fas fa-trash-alt"></i> Delete
                            </a>
                            <a href="MainController?action=orderHistory" class="btn btn-info">
                                <i class="fas fa-history"></i> History
                            </a>

                        </td>
                    </form>
                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <%
                    } else {
                        out.println("<p>No users found.</p>");
                    }

                    String error = (String) request.getAttribute("ERROR");
                    if (error != null && !error.isEmpty()) {
                        out.println("<div class='alert alert-danger'>" + error + "</div>");
                    }
                %>
            </div>
        </div>
    </body>
</html>