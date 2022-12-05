<%--
  Created by IntelliJ IDEA.
  User: voisDev
  Date: 2022-12-01
  Time: 1:42 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns:spring="http://www.springframework.org/tags" xmlns:form="http://www.springframework.org/tags/form">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Verification</title>

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
      <h2 class="text-center p-4">Verification</h2>
    </div>
    <!-- /.card-header -->
    <!-- form start -->
    <form:form  class="form-horizontal" modelAttribute="customer"  method="post">
      <div class="card-body">
        <div class="form-group row ">
          <label for="inputVerificationCode" class="col-sm-3 col-form-label">Verification Code</label>
          <div class="col-sm-9">
            <form:input type="text"  cssClass="form-control" id="inputVerificationCode" name="verificationCode" placeholder="Verification Code" path="code"/>
          </div>
        </div>
      </div>
      <!-- /.card-body -->
      <div class="card-footer   bg-white">
        <button type="submit" class="btn btn-info w-25 d-block m-auto  " value="verify">Verify</button>
        <div class="mb-2"></div>
        <button type="button" class="btn btn-info w-25 d-block m-auto" value="resend">Re-send</button>
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

</body>
</html>
