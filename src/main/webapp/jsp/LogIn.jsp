<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Log In" scope="page"/>
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

            <form class="registration" action="/controller">
                <input type="hidden" name="command" value="logIn"/>
                <div class="row uniform">
                    <div class="12u$">
                        <input type="hidden" name="command" value="logIn"/>
                        <!-- Banner -->
                        <label for="username">
                            <span>Login</span>
                            <input type="text" id="username" minlength="4" maxlength="16" name="login" required>
                        </label>
                        <label for="password">
                            <span>Password</span>
                            <input type="password" id="password" maxlength="16" minlength="4"
                                   name="password" required>
                        </label>
                        <div class="4u 12u$(small)">
                            <label>
                                <input type="submit" value="Log In" formmethod="post">
                            </label>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Sidebar -->
    <%@ include file="../jspf/Menu.jspf" %>

</div>

<!-- Scripts -->
<%@ include file="../jspf/Scripts.jspf" %>

</body>
</html>