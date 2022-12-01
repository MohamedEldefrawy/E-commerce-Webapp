<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns:form="http://www.springframework.org/tags/form">

<rapid:override name="content">
<div class="card card-info">
    <div class="card-header">
        <h3 class="card-title">Create Product</h3>
    </div>
    <div class="card-body">
        <form:form modelAttribute="product" method="post" enctype="multipart/form-data">
            <div class="input-group mb-3">
                <label for="Category" class="col-sm-2 col-form-label">Category</label>
                <form:input id="Category" class="form-control" path="category"/>
            </div>
            <div class="input-group mb-3">
                <label for="Description" class="col-sm-2 col-form-label">Description</label>
                <form:input id="Description" class="form-control" path="description"/>
            </div>
            <div class="input-group mb-3">
                <label for="Image" class="col-sm-2 col-form-label">Image</label>
                <input id="Image" name="image" class="form-control" type="file"/>
            </div>
            <div class="input-group mb-3">
                <label for="Price" class="col-sm-2 col-form-label">Price</label>
                <form:input id="Price" class="form-control" path="price"/>
            </div>
            <div class="d-flex justify-content-center align-content-center">
                <input class="btn btn-primary" type="submit" value="Create">
            </div>
        </form:form>
    </div>
    <!-- /.card-body -->
</div>
</rapid:override>
<%@ include file="/WEB-INF/pages/home.jsp" %>