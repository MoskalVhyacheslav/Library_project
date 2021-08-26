<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="User Page" scope="page"/>
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
                <h1>User Page</h1>
                <h2>Your balance: ${sessionScope.user.balance}</h2>
                <h2>Your sum of credit: ${sessionScope.user.credit}</h2>
                <h2>All orders:</h2>

                <c:choose>
                    <c:when test="${empty requestScope.userOrders}">
                        <strong>
                            You haven't any books!
                        </strong>
                    </c:when>

                    <c:when test="${not empty requestScope.userOrders}">
                        <div class="table-wrapper">
                            <table>
                                <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Days for read</th>
                                    <th>Credit Date or Credit</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="order" items="${requestScope.userOrders}" varStatus="userOrders">
                                    <tr>
                                        <td>${order.book.title}</td>
                                        <td>${order.book.author}</td>
                                        <td>${order.book.creditDays}</td>
                                        <c:choose>
                                            <c:when test="${order.credit != 0}">
                                                <td>${order.credit}</td>
                                                <td>
                                                    <input type="button" value="Pay and Delete Order"
                                                           id="${userOrders.index}"
                                                           onclick="deleteOrder
                                                                   ('${order.book.title}',
                                                                   '${order.book.author}','${userOrders.index}')"/>
                                                </td>
                                            </c:when>

                                            <c:otherwise>
                                                <td>${order.creditDate}</td>
                                                <td>
                                                    <input type="button" value="Delete Order"
                                                           id="${userOrders.index}"
                                                           onclick="deleteOrder
                                                                   ('${order.book.title}',
                                                                   '${order.book.author}','${userOrders.index}')"/>
                                                </td>
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