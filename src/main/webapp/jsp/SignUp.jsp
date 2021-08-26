<%@ include file="../jspf/taglib.jspf"%>
<html>
<head>
    <link rel="stylesheet" href="../assets/css/SignUp.css" />
    <c:set var="title" value="Sign Up" scope="page" />
    <%@ include file="../jspf/Head.jspf" %>
</head>
<body>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Main -->
    <div id="main">
        <div class="inner">

            <!-- Header -->
            <%@ include file="../jspf/Header.jspf" %>

            <!-- Banner -->
            <div class="container">
                <form class="registration"  action="/controller">
                    <input type="hidden" name="command" value="signUp"/>
                    <h1>Registration Form</h1>

                    <label for="username">
                        <span>Username</span>

                        <input type="text" id="username" minlength="3" maxlength="16" name="login" required>

                        <ul class="input-requirements">
                            <li>At least 3 characters long</li>
                        </ul>
                    </label>

                    <label for="email">
                        <span>E-mail</span>
                        <input type="text" id="email" minlength="3" maxlength="30" name="email"
                               pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" required>
                    </label>

                    <label for="password">
                        <span>Password</span>

                        <input type="password" id="password" maxlength="16" minlength="4" name="password" required>

                        <ul class="input-requirements">
                            <li>At least 4 characters long (and less than 16 characters)</li>
                        </ul>
                    </label>

                    <label for="password_repeat">
                        <span>Repeat Password</span>
                        <input type="password" id="password_repeat" maxlength="16" minlength="4" required>
                    </label>

                    <label>
                        <input type="submit"  value="Sign Up" formmethod="post" >
                    </label>
                </form>
            </div>
    </div>
    </div>

    <!-- Sidebar -->
    <%@ include file="../jspf/Menu.jspf" %>

</div>

<!-- Scripts -->
<script src="../assets/js/SignUp.js"></script>
<%@ include file="../jspf/Scripts.jspf" %>

</body>
</html>