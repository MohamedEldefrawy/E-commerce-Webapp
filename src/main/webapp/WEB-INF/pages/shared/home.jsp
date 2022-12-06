<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TMNT</title>

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

    <%-- Site Custom Style --%>
    <link href="<c:url value="/resources/static/css/admin-custom-style.css" />" rel="stylesheet">

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
        <!-- Main content -->
        <div class="col-lg-8 col-md-8 col">

        </div>
        <section class="content">
            <div class="p-1 m-auto col-lg-10 col-md-10 col-sm-12">
                <rapid:block name="content">
                    <div class="text-center">
                        <h1>Welcome To TMNT Admin Panel</h1>
                    </div>
                </rapid:block>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <footer class="main-footer">
        <div class="d-flex justify-content-center align-items-center">
            Copyright &copy; 2022-2023 &nbsp;<span>_VO<strong>IS</strong></span>. All rights reserved.
        </div>
    </footer>
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

<!-- AdminLTE App -->
<script src="<c:url value="/resources/static/js/adminlte.min.js" />"></script>

</body>
</html>