<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register Page</title>
        <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/register.css">
        <link rel="icon" type="image/x-icon" href="images/android-chrome-512x512-removebg-preview.png">
        <script>
            function validateForm() {
                let password = document.getElementById("password").value;
                let confirmPassword = document.getElementById("confirmPassword").value;
                let errorMsg = document.getElementById("error-msg");

                if (password !== confirmPassword) {
                    errorMsg.textContent = "Passwords do not match!";
                    return false;
                }
                return true;
            }

            function checkUsername() {
                let userID = document.getElementById("userID").value;
                let errorMsg = document.getElementById("error-msg");

                if (userID.trim() === "") {
                    errorMsg.textContent = "Username cannot be empty!";
                    return;
                }

                let xhr = new XMLHttpRequest();
                xhr.open("POST", "RegisterController", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        if (xhr.responseText === "exists") {
                            errorMsg.textContent = "Username already exists. Please choose another.";
                        } else {
                            errorMsg.textContent = ""; // Clear the error message if username is available
                        }
                    }
                };

                xhr.send("action=checkUser&userID=" + encodeURIComponent(userID));
            }

            window.onload = function () {
                document.getElementById("userID").addEventListener("blur", checkUsername);
            };
        </script>
    </head>
    <body>
        <div class="bg-image">
            <section class="py-3 py-md-5 py-xl-8 d-flex align-items-center justify-content-center min-vh-100">
                <div class="register-container">
                    <div class="text-center mb-5">
                        <h2 class="display-5 fw-bold">Sign up</h2>
                        <p>Already have an account? <a href="login.jsp">Sign in</a></p>
                    </div>

                    <!-- JSTL Error Message -->
                    <c:if test="${not empty ERROR}">
                        <div id="error-msg" class="text-danger mb-3">${ERROR}</div>
                    </c:if>

                    <!-- JSTL Success Message -->
                    <c:if test="${not empty SUCCESS}">
                        <div id="success-msg" class="text-success mb-3">${SUCCESS}</div>
                    </c:if>

                    <form action="RegisterController" method="POST" onsubmit="return validateForm();">
                        <input type="hidden" name="action" value="Register">
                        <div class="row gy-3">
                            <div class="col-12">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control border-0 border-bottom rounded-0" name="userID" id="userID" placeholder="Username" required>
                                    <label for="userID">Username</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control border-0 border-bottom rounded-0" name="fullName" id="fullName" placeholder="Full Name" required>
                                    <label for="fullName">Full Name</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control border-0 border-bottom rounded-0" name="password" id="password" placeholder="Password" required>
                                    <label for="password">Password</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control border-0 border-bottom rounded-0" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required>
                                    <label for="confirmPassword">Confirm Password</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="d-grid">
                                    <button class="btn btn-primary btn-lg" type="submit">Sign Up</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </body>
</html>
