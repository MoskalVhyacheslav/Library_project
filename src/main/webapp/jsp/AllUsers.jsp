<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="All Users" scope="page"/>
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
                <h1>All Users</h1>

                <c:choose>
                    <c:when test="${empty requestScope.users}">
                        <h3>No users on your website!</h3>
                    </c:when>

                    <c:when test="${not empty requestScope.users}">
                        <div class="table-wrapper">
                            <form action="/controller">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Login</th>
                                        <th>E-Mail</th>
                                        <th>Credit</th>
                                        <th>Credit Books</th>
                                        <th>Block</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach var="user" items="${requestScope.users}" varStatus="userList">
                                        <tr>
                                            <td>${user.login}</td>
                                            <td>${user.mail}</td>
                                            <td>${user.credit}</td>
                                            <td>${user.creditBooks}</td>
                                            <td>${user.blocked}</td>
                                            <c:choose>
                                                <c:when test="${user.blocked eq true}">
                                                    <td>
                                                        <input type="button" value="Unblock User" id="${userList.index}"
                                                               onclick="unblockUser('${user.login}','${userList.index}')"/>
                                                    </td>
                                                </c:when>
                                                <c:when test="${user.blocked eq false}">
                                                    <td>
                                                        <input type="button" value="Block User" id="${userList.index}"
                                                               onclick="blockUser('${user.login}','${userList.index}')"/>
                                                    </td>
                                                </c:when>

                                            </c:choose>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </form>
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