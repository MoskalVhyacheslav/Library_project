<%@ include file="../jspf/taglib.jspf"%>
<html>
<head>
    <c:set var="title" value="Add Book" scope="page" />
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
                    <h1>Add Book</h1>

                <form method="post" action="/controller" class="addBook">
                    <input type="hidden" name="command" value="addBook"/>
                    <div class="row uniform">
                        <label for="title">
                            <input type="text" id="title" name="title"  value="" placeholder="Title"
                                   maxlength="30" required>
                        </label>
                        <label for="author">
                            <input type="text" id="author" name="author" value="" placeholder="Author"
                                   maxlength="30" required>
                        </label>
                        <label for="edition">
                            <input type="text" id="edition" name="edition"  value="" placeholder="Edition"
                                   maxlength="30" required>
                        </label>
                        <label for="edition-date">
                            <input type="date" id="edition-date" name="edition-date"  value=""
                                   maxlength="30" placeholder="Edition Date" required>
                        </label>
                        <label for="edition-date">
                            <input type="number" id="credit-days" name="credit-days" min="1" value=""
                                   placeholder="Credit Days" required>
                        </label>
                        <label for="amount">
                            <input type="number" id="amount" name="amount" min="1"  value="Amount"
                                   placeholder="Amount" required>
                        </label>

                        <!-- Break -->
                        <div class="12u$">
                            <ul class="actions">
                                <li><input type="submit" value="Send Message" class="special"></li>
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
<script src="../assets/js/AddBook.js"></script>
<%@ include file="../jspf/Scripts.jspf" %>

</body>
</html>