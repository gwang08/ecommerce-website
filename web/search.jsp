<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Search Results - FPTUniversityShop</title>
        <link rel="stylesheet" href="css/home.css"> <!-- Corrected the CSS file path -->
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
                    <div class="user-menu">
                        <ul>
                            <c:choose>
                                <c:when test="${not empty sessionScope.LOGIN_USER}">
                                    <li><a href="MainController?action=logout">Logout</a></li>
                                    <li><a href="MainController?action=orderHistory">History</a></li>
                                    </c:when>
                                    <c:otherwise>
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
                    <c:if test="${not empty ADD_SUCCESS}">
                        <div class="alert alert-danger">${ADD_SUCCESS}</div>
                    </c:if>
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
            <h2>Search Results</h2>
            <c:if test="${not empty PRODUCT_MAP}">
                <c:forEach var="entry" items="${PRODUCT_MAP}">
                    <h3>${entry.value[0].categoryName}</h3>
                    <div class="product-container-wrapper">
                        <button class="scroll-button left" onclick="document.getElementById('product-container-${entry.key}').scrollBy({left: -300, behavior: 'smooth'})">&lt;</button>
                        <div id="product-container-${entry.key}" class="product-container">
                            <c:forEach var="product" items="${entry.value}">
                                <div class="product-item">
                                    <a href="ProductDetailController?productID=${product.productID}">
                                        <img src="${product.imageURL}" alt="${product.name}" class="product-image"/>
                                        <h4>${product.name}</h4>
                                        <p>ID: ${product.productID}</p>
                                        <p>
                                            Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="" /> ₫
                                        </p>
                                        <p>Quantity: ${product.quantity}</p>
                                        <p>Category: ${product.categoryName}</p>
                                        <p>Brand: ${product.brandName}</p>
                                    </a>
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.LOGIN_USER}">
                                            <a href="MainController?action=add&productID=${product.productID}" class="add-to-cart-btn">Add to Cart</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="login.jsp" class="add-to-cart-btn">Add to Cart</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:forEach>
                        </div>
                        <button class="scroll-button right" onclick="document.getElementById('product-container-${entry.key}').scrollBy({left: 300, behavior: 'smooth'})">&gt;</button>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty PRODUCT_MAP}">
                <p>No products found.</p>
            </c:if>
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
                            <img src="images/facebook.jpg" alt="Facebook Logo">
                        </a>
                        <a href="https://www.youtube.com/channel/UC4vAAvyjYMpirmYayY4_lHQ/featured" class="social-icon">
                            <img src="images/youtube.jpg" alt="YouTube Logo">
                        </a>
                        <a href="https://www.linkedin.com/school/fpt-university/" class="social-icon">
                            <img src="images/linkedin.jpg" alt="LinkedIn Logo">
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
