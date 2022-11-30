<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AdminLTE 3 | Collapsed Sidebar</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <%--    <link rel="stylesheet" href="../../plugins/fontawesome-free/css/all.min.css">--%>
    <link href="<c:url value="/resources/static/css/all.min.css" />" rel="stylesheet">

    <!-- Theme style -->
    <%--    <link rel="stylesheet" href="../../dist/css/adminlte.min.css">--%>
    <link href="<c:url value="/resources/static/css/adminlte.min.css" />" rel="stylesheet">

</head>
<body class="hold-transition sidebar-mini sidebar-collapse">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="navbar.jsp"/>
    <!-- Main Sidebar Container -->
    <jsp:include page="sidebar.jsp"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">

        </section>

        <!-- Main content -->
        <section class="content">
            <rapid:block name="content">
            </rapid:block>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <footer class="main-footer">
        <div class="float-right d-none d-sm-block">
            <b>Version</b> 3.2.0
        </div>
        <strong>Copyright &copy; 2014-2021 <a href="https://adminlte.io">AdminLTE.io</a>.</strong> All rights reserved.
    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Control sidebar content goes here -->
    </aside>
    <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<%--<script src="../../plugins/jquery/jquery.min.js"></script>--%>
<script src="<c:url value="/resources/static/js/jquery.min.js" />"></script>

<!-- Bootstrap 4 -->
<%--<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>--%>
<script src="<c:url value="/resources/static/js/bootstrap.bundle.min.js" />"></script>

<!-- AdminLTE App -->
<%--<script src="../../dist/js/adminlte.min.js"></script>--%>
<script src="<c:url value="/resources/static/js/adminlte.min.js" />"></script>

<!-- AdminLTE for demo purposes -->
<%--<script src="../../dist/js/demo.js"></script>--%>
<script src="<c:url value="/resources/static/js/demo.js" />"></script>

</body>
</html>