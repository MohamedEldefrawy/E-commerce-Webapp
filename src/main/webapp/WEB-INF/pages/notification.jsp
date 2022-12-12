<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns:spring="http://www.springframework.org/tags" xmlns:form="http://www.springframework.org/tags/form">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration</title>

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
            <h2 class="text-center p-4">Create a new account</h2>
        </div>
        <!-- /.card-header -->
        <!-- form start -->
        <form  class="form-horizontal"  method="post">
            <!-- /.card-body -->
            <div class="card-footer bg-white ">
                <label class="m-2 text-center d-block" for="signIn">Check Your Mail, Please <br/>Then, Click</label>
                <button type="button" class="btn btn-default m-auto w-25 d-block " id="signIn">
                    <a href="<c:url value="/login.htm" />" >Sign In</a>
                </button>
            </div>
            <!-- /.card-footer -->
        </form>
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
</body>
</html>