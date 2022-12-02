<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.springframework.org/tags/form">

<rapid:override name="content">
<div class="card card-info">
    <div class="card-header">
        <h3 class="card-title">Update Admin</h3>
    </div>
    <div class="card-body">
        <form:form modelAttribute="admin" method="post" enctype="multipart/form-data">
            <div class="input-group mb-3">
                <label for="ID" class="col-sm-2 col-form-label">ID</label>
                <input type="number" id="ID" class="form-control" disabled value="${admin.getId()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="userName" class="col-sm-2 col-form-label">Username</label>
                <form:input id="userName" class="form-control" path="userName" value="${admin.getUserName()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="email" class="col-sm-2 col-form-label">E-mail</label>
                <form:input id="email" class="form-control" path="email" value="${admin.getEmail()}"/>
            </div>
            <div class="d-flex justify-content-center align-content-center">
                <input class="btn btn-primary" type="submit" value="Save">
            </div>
        </form:form>
    </div>
</div>
</rapid:override>
<jsp:include page="../shared/home.jsp"/>
