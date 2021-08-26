<%@ include file="../jspf/taglib.jspf"%>
<html>
<head>
    <c:set var="title" value="Add Librarian" scope="page" />
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

            <section>
                <h1>Add Librarian</h1>

                <form method="post" action="/controller" class="addLibrarian">
                    <input type="hidden" name="command" value="addLibrarian"/>
                    <div class="row uniform">
                        <label for="login">
                            <input type="text" id="login" name="login"  value=""
                                   maxlength="30" placeholder="Login" required>
                        </label>
                        <label for="password">
                            <input type="text" id="password" name="password" value=""
                                   maxlength="30" placeholder="Password" required>
                        </label>
                        <label for="email">
                            <input type="text" id="email" name="email"  value=""
                                   maxlength="30" pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"
                                   placeholder="E-Mail" required>
                        </label>

                        <!-- Break -->
                        <div class="12u$">
                            <ul class="actions">
                                <li><input type="submit" value="Add Librarian" class="special"></li>
                                <li><input type="reset" value="Reset"></li>
                            </ul>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </div>


    <!-- Sidebar -->
    <%@ include file="../jspf/Menu.jspf" %>

</div>
<!-- Scripts -->
<%@ include file="../jspf/Scripts.jspf" %>

</body>
</html>