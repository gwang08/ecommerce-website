<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cart - F-TECH STORE</title>
        <link rel="stylesheet" href="css/viewCart.css">
        <link rel="icon" type="image/x-icon" href="images/android-chrome-512x512-removebg-preview.png">
    </head>
    <body>
        <header>
            <div class="FPTlogo">
                <a href="MainController" class="logo large">
                    <img src="images/LogoSample_ByTailorBrands__1_-re.png" alt="FPTShop Logo">
                </a>
            </div>
            <div class="search-container">
                <form id="search-form" action="MainController" method="POST">
                    <input type="text" id="search" name="search" placeholder="Search products..." required autocomplete="off" value="${SEARCH_QUERY}">
                    <button type="submit" name="action" value="Search">
                        <img src="images/search-interface-symbol.png" alt="Search Icon" class="search-image">
                    </button>
                </form>
            </div>
            <div class="welcome-message">
                <c:if test="${not empty sessionScope.LOGIN_USER}">
                    <p>${sessionScope.LOGIN_USER.fullName}</p>
                </c:if>
            </div>
            <div class="user-container">
                <div class="UserLogo">
                    <a href="login.jsp" class="logo">
                        <img src="images/profile-user.jpg" alt="User Logo">
                    </a>
                    <!-- Functional Menu -->
                    <div class="user-menu">
                        <ul>
                            <c:choose>
                                <c:when test="${not empty sessionScope.LOGIN_USER}">
                                    <!-- Hiển thị Logout nếu người dùng đã đăng nhập -->
                                    <li><a href="MainController?action=logout">Logout</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <!-- Hiển thị Login nếu người dùng chưa đăng nhập -->
                                    <li><a href="login.jsp">Login</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                </div>  
            </div>
            <div class="cart-container">
                <div class="CartLogo">
                    <a href="MainController?action=view" class="logo">
                        <img src="images/grocery-store.png" alt="Cart Logo">
                    </a>
                </div>  
            </div>
            <div>
                <nav>
                    <ul>
                        <li><a href="MainController">Home</a></li>
                        <li><a href="#footer">About Us</a></li>
                    </ul>
                </nav>
            </div>        
        </header>

        <main>
            <h1>Your Shopping Cart</h1>
            <!-- Display error message -->
            <c:if test="${not empty requestScope.message}">
                <div class="alert alert-error">
                    <fmt:message key="${requestScope.message}"/>
                </div>
            </c:if>
            <c:choose>
                <c:when test="${not empty sessionScope.USER_CART}">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="entry" items="${sessionScope.USER_CART}">
                                <tr>
                                    <td>
                                        <img src="${entry.value.imageURL}" alt="${entry.value.name}" class="cart-product-image">
                                        ${entry.value.name}
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${entry.value.price}" type="currency" currencySymbol=""/>₫
                                    </td>
                                    <td>
                                        <form action="UpdateQuantityController" method="post">
                                            <input type="hidden" name="productID" value="${entry.key}">
                                            <input type="number" name="quantity" value="${entry.value.quantity}" min="1" required>
                                            <button class="update-btn" type="submit">Update</button>
                                        </form>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${entry.value.price * entry.value.quantity}" type="currency" currencySymbol=""/>₫
                                    </td>
                                    <td>
                                        <form action="MainController" method="post">
                                            <input type="hidden" name="productID" value="${entry.key}">
                                            <button class="remove-btn" type="submit" name="action" value="removeProduct">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3"><strong>Total Price:</strong></td>
                                <td colspan="2">
                                    <c:set var="totalPrice" value="0"/>
                                    <c:forEach var="entry" items="${sessionScope.USER_CART}">
                                        <c:set var="itemTotal" value="${entry.value.price * entry.value.quantity}"/>
                                        <c:set var="totalPrice" value="${totalPrice + itemTotal}"/>
                                    </c:forEach>
                                    <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol=""/>₫
                                </td>

                            </tr>
                        </tfoot>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>Bạn chưa có sản phẩm nào trong giỏ hàng!</p>
                </c:otherwise>
            </c:choose>
            <tfoot>
                <tr>
                    <td colspan="5">
                        <form action="CheckController" method="post">
                            <button type="submit" class="checkout-btn">Checkout</button>
                        </form>
                    </td>
                </tr>
            </tfoot>
        </main>

        <footer id="footer">
            <div class="footer-content">
                <div class="footer-section about">
                    <h3>F-TECH STORE</h3>
                    <p>F-TECH STORE is your one-stop destination for the latest electronics, computers, and mobile devices. We strive to provide the best quality products at competitive prices with top-notch customer service.</p>
                    <div class="contact">
                        <p><strong>Address:</strong> Lô E2a-7, Đường D1 Khu Công nghệ cao, P. Long Thạnh Mỹ, TP. Thủ Đức, TP. Hồ Chí Minh</p>
                        <p><strong>Phone:</strong> 028 7300 1866</p>
                        <p><strong>Email:</strong> daihocfpt@fpt.edu.vn</p>
                    </div>
                </div>
                <div class="footer-section social">
                    <h3>Follow Us</h3>
                    <div class="social-icons">
                        <a href="https://www.facebook.com/FPTU.HCM" class="social-icon">
                            <img src="images/facebook.jpg" alt="FaceBook Logo">
                        </a>
                        <a href="https://www.youtube.com/channel/UC4vAAvyjYMpirmYayY4_lHQ/featured" class="social-icon">
                            <img src="images/youtube.jpg" alt="Youtube Logo">
                        </a>
                        <a href="https://www.linkedin.com/school/fpt-university/" class="social-icon">
                            <img src="images/linkedin.jpg" alt="Linkedin Logo">
                        </a>
                        <a href="https://www.instagram.com/fptuniversityhcm/" class="social-icon">
                            <img src="images/instagram.jpg" alt="Instagram Logo">
                        </a>
                    </div>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2024 F-TECH STORE. All rights reserved.</p>
                <a href="#" class="back-to-top">Back to Top</a>
            </div>
        </footer>
    </body>
</html>
