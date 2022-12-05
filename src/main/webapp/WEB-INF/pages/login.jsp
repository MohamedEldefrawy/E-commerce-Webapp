<%--
  Created by IntelliJ IDEA.
  User: voisDev
  Date: 2022-11-30
  Time: 6:19 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <style>
        span.error {
            color: red;
            display: block;
            float: right;
        }
    </style>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet"
          href="<c:url value="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback" />">
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
            <h2 class="text-center p-4">Login</h2>
        </div>
        <!-- /.card-header -->
        <!-- form start -->
        <form:form class="form-horizontal" modelAttribute="loginModel" method="post">
        <div class="card-body">
            <div class="form-group row">
                <label for="inputEmail3" class="col-sm-3 col-form-label">Email</label>
                <div class="col-sm-9">
                    <form:input type="email" class="form-control" id="inputEmail3" placeholder="Email"
                                path="email"/>
                </div>
            </div>
            <div class="d-flex justify-content-end align-content-center">
                <form:errors path="email" cssClass="error"/>
            </div>
            <div class="form-group row">
                <label for="inputPassword3" class="col-sm-3 col-form-label">Password</label>
                <div class="col-sm-9">
                    <form:input maxlength="30" minlength="8" size="8" type="password" class="form-control" id="inputPassword3"
                                placeholder="Password"
                                path="password"/>
                </div>
                <div class="d-flex justify-content-end align-content-center">
                    <form:errors path="password" cssClass="error"/>
                </div>
            </div>
            <!-- /.card-body -->
            <div class="card-footer d-flex justify-content-center mb-3 bg-white">
                <button type="submit" class="btn btn-info mr-2 w-25 ">Sign in</button>
                <button type="button" class="btn btn-default w-25 ">
                    <a href="<c:url value="registration.htm" />">Sign Up</a>
                </button>
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
</body>
</html>