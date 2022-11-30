<%--
  Created by IntelliJ IDEA.
  User: voisDev
  Date: 2022-11-30
  Time: 6:19 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login</title>

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
                    <h2 class="text-center p-4">Login</h2>
                </div>
                <!-- /.card-header -->
                <!-- form start -->
                <form class="form-horizontal">
                    <div class="card-body">
                        <div class="form-group row">
                            <label for="inputEmail3" class="col-sm-3 col-form-label">Email</label>
                            <div class="col-sm-9">
                                <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputPassword3" class="col-sm-3 col-form-label">Password</label>
                            <div class="col-sm-9">
                                <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="offset-sm-3 col-sm-9">
                                <div class="form-check">
                                    <input type="checkbox" class="form-check-input" id="exampleCheck2">
                                    <label class="form-check-label" for="exampleCheck2">login as admin</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.card-body -->
                    <div class="card-footer d-flex justify-content-center mb-3 bg-white">
                        <button type="submit" class="btn btn-info mr-2 w-25 ">Sign in</button>
                        <button type="submit" class="btn btn-default w-25 ">Sign Up</button>
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