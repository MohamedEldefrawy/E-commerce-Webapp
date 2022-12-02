<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.springframework.org/tags/form">

<rapid:override name="content">
<div class="card card-info">
    <div class="card-header">
        <h3 class="card-title">Create Admin</h3>
    </div>
    <div class="card-body">
        <form:form modelAttribute="admin" method="post">
            <div class="input-group mb-3">
                <label for="email" class="col-sm-2 col-form-label">E-mail</label>
                <form:input id="email" class="form-control" path="email"/>
            </div>
            <div class="input-group mb-3">
                <label for="username" class="col-sm-2 col-form-label">Username</label>
                <form:input id="username" class="form-control" path="username"/>
            </div>

            <div class="d-flex justify-content-center align-content-center">
                <input class="btn btn-primary" type="submit" value="Create">
            </div>
        </form:form>
    </div>
    <!-- /.card-body -->
</div>
</rapid:override>
<jsp:include page="../shared/home.jsp"/>