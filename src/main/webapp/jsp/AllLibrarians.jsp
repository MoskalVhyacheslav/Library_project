<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="All Librarians" scope="page"/>
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
                <h1>All Librarians</h1>

                <c:choose>
                    <c:when test="${empty requestScope.librarians}">
                        <h3>No librarians on your website!</h3>
                    </c:when>

                    <c:when test="${not empty requestScope.librarians}">
                        <form action="/controller">
                            <div class="table-wrapper">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Login</th>
                                        <th>E-Mail</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="librarian" items="${requestScope.librarians}"
                                               varStatus="librarianList">
                                        <tr>
                                            <td>${librarian.login}</td>
                                            <td>${librarian.mail}</td>
                                            <td>
                                                <input type="button" value="Delete Librarian"
                                                       id="${librarianList.index}"
                                                       onclick="deleteLibrarian('${librarian.login}','${librarianList.index}')"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </form>
                    </c:when>
                </c:choose>
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