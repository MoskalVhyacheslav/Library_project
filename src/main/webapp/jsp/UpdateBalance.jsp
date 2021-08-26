<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Update Balance" scope="page"/>
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
                <h1>Update Balance</h1>

                <form action="/controller">
                    <input type="hidden" name="command" value="updateBalance"/>
                    <label>
                        <input type="number" name="balance" required>
                    </label>
                    <label>
                        <input type="submit" name="command" value="Sort Books" formmethod="post">
                    </label>
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