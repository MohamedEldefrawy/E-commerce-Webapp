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
    <link href="<c:url value="/resources/static/css/all.min.css" />" rel="stylesheet">

    <!-- DataTables -->
    <link href="<c:url value="/resources/static/css/dataTables.bootstrap4.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/responsive.bootstrap4.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/buttons.bootstrap4.min.css" />" rel="stylesheet">

    <!-- Theme style -->
    <link href="<c:url value="/resources/static/css/adminlte.min.css" />" rel="stylesheet">
    <!-- jQuery -->
    <%--<script src="<c:url value="/resources/static/js/jquery.min.js" />"></script>--%>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>


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
            We Move The World
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


<!-- Bootstrap 4 -->
<script src="<c:url value="/resources/static/js/bootstrap.bundle.min.js" />"></script>
<%--Fontawesome--%>
<script src="<c:url value="/resources/static/js/all.min.js" />"></script>

<%--Data Table--%>
<script src="<c:url value="/resources/static/js/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/resources/static/js/dataTables.bootstrap4.min.js" />"></script>
<script src="<c:url value="/resources/static/js/dataTables.responsive.min.js" />"></script>
<script src="<c:url value="/resources/static/js/responsive.bootstrap4.min.js" />"></script>
<script src="<c:url value="/resources/static/js/dataTables.buttons.min.js" />"></script>
<script src="<c:url value="/resources/static/js/buttons.bootstrap4.min.js" />"></script>
<script src="<c:url value="/resources/static/js/jszip.min.js" />"></script>
<script src="<c:url value="/resources/static/js/pdfmake.min.js" />"></script>
<script src="<c:url value="/resources/static/js/vfs_fonts.js" />"></script>
<script src="<c:url value="/resources/static/js/buttons.html5.min.js" />"></script>
<script src="<c:url value="/resources/static/js/buttons.print.min.js" />"></script>
<script src="<c:url value="/resources/static/js/buttons.colVis.min.js" />"></script>

<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/resources/static/js/demo.js" />"></script>

<!-- AdminLTE App -->
<script src="<c:url value="/resources/static/js/adminlte.min.js" />"></script>

</body>
</html>