<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:spring="http://www.springframework.org/tags" xmlns:form="http://www.springframework.org/tags/form">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reset Password</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet"  href="<c:url value="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback" />">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<c:url value="/resources/static/plugins/fontawesome-free/css/all.min.css" />">
    <!-- Theme style -->
    <link rel="stylesheet" href="<c:url value="/resources/static/css/adminlte.min.css"/>">
</head>
<body class="hold-transition sidebar-mini">
<div class="container pt-5">
    <!-- Horizontal Form -->
    <div class="card card-info w-75 m-auto ">
        <div class="bg-white m-3 ">
            <h2 class="text-center p-4">Reset Password</h2>
        </div>
        <!-- /.card-header -->
        <!-- form start -->
        <form:form class="form-horizonta" method="post">
            <div class="card-body">
                <div class="form-group row">
                    <label for="inputPassword3" class="col-sm-3 col-form-label">Password</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                    </div>
                </div>
            </div>
            <!-- /.card-body -->
            <div class="card-footer bg-white ">
                <button type="submit" class="btn btn-info m-auto  w-25 d-block ">Reset</button>
            </div>
            <!-- /.card-footer -->
        </form:form>
    </div>
    <!-- /.card -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="<c:url value="/resources/static/js/jquery.min.js"/>"></script>
<!-- Bootstrap 4 -->
<script src="<c:url value="/resources/static/js/bootstrap.bundle.min.js"/>"></script>
<!-- bs-custom-file-input -->
<script src="<c:url value="/resources/static/plugins/bs-custom-file-input/bs-custom-file-input.min.js"/>"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/resources/static/js/adminlte.min.js"/>"></script>
<!-- AdminLTE for demo purposes -->
<script src="<c:url value="/resources/static/js/demo.js"/>"></script>
<!-- Page specific script -->
<script>
    $(function () {
        bsCustomFileInput.init();
    });
</script>
</body>
</html>