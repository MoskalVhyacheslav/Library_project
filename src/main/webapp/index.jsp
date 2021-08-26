<%@ include file="jspf/taglib.jspf"%>
<html>
<head>
    <c:set var="title" value="Homepage" scope="page" />
    <%@ include file="jspf/Head.jspf" %>
</head>
<body>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Main -->
    <div id="main">
        <div class="inner">

            <!-- Header -->
            <%@ include file="jspf/Header.jspf" %>

            <!-- Banner -->
            <section id="banner">
                <div class="content">
                    <header>
                        <h1>Hi, It's Library<br />
                            by Moskal Vhyacheslav</h1>
                    </header>
                    <p></p>
                    <ul class="actions">
                        <li><a href="#" class="button big">Learn More</a></li>
                    </ul>
                </div>

            </section>
        </div>
    </div>

    <!-- Sidebar -->
    <%@ include file="jspf/Menu.jspf" %>
</div>
<!-- Scripts-->
<%@ include file="jspf/Scripts.jspf" %>
</body>
</html>