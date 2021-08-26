<%@ include file="../jspf/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Error Page" scope="page"/>
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

            <h1>Error Page!</h1>
            <h2>Error: ${sessionScope.errorMessage}</h2>
            <c:remove var="errorMessage" scope="session"/>

        </div>
    </div>


    <!-- Sidebar -->
    <%@ include file="../jspf/Menu.jspf" %>

</div>
<!-- Scripts -->
<%@ include file="../jspf/Scripts.jspf" %>

</body>
</html>