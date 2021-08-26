<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="All Orders" scope="page"/>
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
                    <h1>All Orders</h1>

                <c:choose>
                    <c:when test="${empty requestScope.orders}">
                        <h3>No books!</h3>
                    </c:when>

                    <c:when test="${not empty requestScope.orders}">
                        <div class="table-wrapper">
                            <table>
                                <thead>
                                <tr>
                                    <th>User</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Credit Date or Credit</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach var="order" items="${requestScope.orders}">
                                    <tr>
                                        <td>${order.user.login}</td>
                                        <td>${order.book.title}</td>
                                        <td>${order.book.author}</td>
                                        <c:choose>
                                            <c:when test="${order.credit!=0}">
                                                <td>${order.credit}</td>
                                            </c:when>

                                            <c:otherwise>
                                                <td>${order.creditDate}</td>
                                            </c:otherwise>
                                        </c:choose>

                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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