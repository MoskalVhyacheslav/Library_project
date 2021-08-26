<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Update Book" scope="page"/>
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
                <c:set var="book" value="${updateBook}"/>

                <h1>Update Book</h1>
                <h2>Title: ${book.title}</h2>
                <h2>Title: ${book.author}</h2>

                <form method="post" action="/controller" class="updateBook">
                    <input type="hidden" name="command" value="updateBook"/>
                    <div class="row uniform">
                        <div class="6u$ 12u$(xsmall)">
                            <input type="text" name="edition" value="${book.edition}"
                                   maxlength="30" placeholder="Edition" required>
                        </div>
                        <div class="6u$ 12u$(xsmall)">
                            <input type="date" name="edition-date" value="${book.editionDate}"
                                   minlength="4" placeholder="Edition Date" required>
                        </div>
                        <div class="6u$ 12u$(xsmall)">
                            <input type="number" name="credit-days" value="${book.creditDays}"
                                   placeholder="Credit Days" maxlength="5" required>
                        </div>
                        <div class="6u$ 12u$(xsmall)">
                            <input type="number" name="amount" maxlength="5" value="${book.amount}"
                                   placeholder="Amount" required>
                        </div>

                        <!-- Break -->
                        <div class="12u$">
                            <ul class="actions">
                                <li><input type="submit" value="Update " class="special"></li>
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