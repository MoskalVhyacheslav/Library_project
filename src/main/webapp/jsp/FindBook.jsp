<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Find Book" scope="page"/>
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

            <!-- Content -->
            <section>
                <header class="main">
                    <h2>Find book</h2>
                </header>


                <form action="/controller">
                    <input type="hidden" name="command" value="findBook"/>
                    <div class="row uniform">
                        <div class="4u 12u$(small)">
                            <label>
                                <input type="text" name="title" placeholder="Title" maxlength="30"/>
                            </label>
                            <label>
                                <input type="text" name="author" placeholder="Author" maxlength="30"/>
                            </label>
                            <div class="4u 12u$(small)">
                                <label>
                                    <input type="submit" value="Find Book" formmethod="post">
                                </label>
                            </div>
                        </div>
                    </div>
                </form>

                <c:set var="books" value="${sessionScope.findBooks}"/>
                <%@ include file="../jspf/bookList.jspf" %>
                <c:remove var="findBooks" scope="session"/>

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