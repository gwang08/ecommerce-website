<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://unpkg.com/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/login.css">
        <link rel="icon" type="image/x-icon" href="images/android-chrome-512x512-removebg-preview.png">
    </head>
    <body>
        <div class="d-flex align-items-center justify-content-center min-vh-100 bg-image">
            <div class="login-container shadow-lg">
                <div class="text-center mb-4">
                    <h2 class="display-4 fw-bold text-dark">Sign in</h2>
                    <p class="lead">Don't have an account? <a href="register.jsp" class="link-primary">Sign up</a></p>
                </div>
                <!-- Conditionally render error messages if they exist -->
                <c:if test="${not empty ADD_ERROR or not empty ERROR}">
                    <div class="error-message">
                        <c:if test="${not empty ADD_ERROR}">
                            <div class="alert alert-danger">${ADD_ERROR}</div>
                        </c:if>
                        <c:if test="${not empty ERROR}">
                            <div class="alert alert-danger">${ERROR}</div>
                        </c:if>
                    </div>
                </c:if>
                <div class="card border-0 rounded-4">
                    <div class="card-body p-4">
                        <form action="MainController" method="POST" onsubmit="return validateCaptcha()">
                            <input type="hidden" name="action" value="Login">
                            <div class="mb-3">
                                <div class="form-floating">
                                    <input type="text" class="form-control rounded-3" name="userID" id="userID" placeholder="Username" required>
                                    <label for="userID">Username</label>
                                </div>
                            </div>
                            <div class="mb-4">
                                <div class="form-floating">
                                    <input type="password" class="form-control rounded-3" name="password" id="password" placeholder="Password" required>
                                    <label for="password">Password</label>
                                </div>
                            </div>
                            <div class="mb-4">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe">
                                    <label class="form-check-label" for="rememberMe">Remember me</label>
                                </div>
                            </div>
                            <div class="mb-4">
                                <!-- reCAPTCHA widget -->
                                <div class="g-recaptcha" data-sitekey="6LemhwMqAAAAAOmIlsmQ1lfYNMTPU-wDUuOyH4b_"></div>
                                <span id="captchaError" class="text-danger"></span>
                            </div>
                            <div class="d-grid mb-4">
                                <button class="btn btn-primary btn-lg" type="submit">Log in</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://unpkg.com/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Google reCAPTCHA script -->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script>
                            function validateCaptcha() {
                                const response = grecaptcha.getResponse();
                                const captchaError = document.getElementById("captchaError");
                                if (response.length === 0) {
                                    captchaError.textContent = "Please verify the reCAPTCHA.";
                                    return false;
                                } else {
                                    captchaError.textContent = "";
                                    return true;
                                }
                            }

                            document.getElementById("rememberMe").addEventListener('change', function () {
                                if (this.checked) {
                                    const userId = document.getElementById("userID").value;
                                    const expires = "expires=" + new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toUTCString();
                                    document.cookie = `rememberMe=${userId}; ${expires}; path=/`;
                                } else {
                                    document.cookie = "rememberMe=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
                                }
                            });
        </script>
    </body>
</html>
