<%@ include file="../jspf/taglib.jspf"%>
<html>
<head>
    <c:set var="title" value="All Books" scope="page"/>
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
                    <h1>All Books</h1>

                <h3>Sort books by:</h3>
                <form action="/controller">
                    <input type="hidden" name="command" value="sortBooks"/>
                    <div class="row uniform">
                        <div class="12u$">
                            <div class="select-wrapper">
                                <label>
                                    <select name="order by" id="order by">
                                        <option value="">- Order By -</option>
                                        <option value="title">Title</option>
                                        <option value="author">Author</option>
                                        <option value="edition">Edition</option>
                                        <option value="edition-date">Edition Date</option>
                                    </select>
                                </label>
                            </div>
                        </div>
                        <div class="4u 12u$(small)">
                            <label>
                                <input type="submit" name="command" value="Sort Books" formmethod="post">
                            </label>
                        </div>
                    </div>
                </form>

                <c:set var="books" value="${sessionScope.sortedBooks}"/>
                <%@ include file="../jspf/bookList.jspf"%>
                <c:remove var="sortedBooks" scope="session"/>

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