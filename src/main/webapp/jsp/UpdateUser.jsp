<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Update User" scope="page"/>
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
                <h1>Update User</h1>

                <h2>Your Login: ${sessionScope.user.login}</h2>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="updateUser"/>
                    <div class="row uniform">
                        <div class="6u$ 12u$(xsmall)">
                            <input type="text" name="email" value="${sessionScope.user.mail}"
                                  minlength="6" maxlength="30" pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"
                                   placeholder="Email">
                        </div>
                        <!-- Break -->
                        <div class="12u$">
                            <ul class="actions">
                                <li><input type="submit" value="Update User" class="special"></li>
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