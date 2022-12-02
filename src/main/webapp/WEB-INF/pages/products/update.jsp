<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.springframework.org/tags/form">

<rapid:override name="content">
<div class="card card-info">
    <div class="card-header">
        <h3 class="card-title">Update Product</h3>
    </div>
    <div class="card-body">
        <form:form modelAttribute="product" method="post" enctype="multipart/form-data">
            <div class="input-group mb-3">
                <label for="ID" class="col-sm-2 col-form-label">ID</label>
                <input type="number" id="ID" class="form-control" disabled value="${product.getId()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="Name" class="col-sm-2 col-form-label">Name</label>
                <form:input id="Name" class="form-control" path="name" value="${product.getName()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="Category" class="col-sm-2 col-form-label">Category</label>
                <form:input id="Category" class="form-control" path="category" value="${product.getCategory()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="Description" class="col-sm-2 col-form-label">Description</label>
                <form:input id="Description" class="form-control" path="description"
                            value="${product.getDescription()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="Image" class="col-sm-2 col-form-label">Image</label>
                <div class="d-flex flex-row">
                    <input id="Image" name="image" class="form-control" type="file"/>
                    <img style="max-height: 120px; max-width: 120px" class="image img-thumbnail mr-auto"
                         alt="${product.getImage()}"
                         src="<c:url value="/resources/static/images/${product.getImage()}"/>">
                </div>
            </div>
            <div class="input-group mb-3">
                <label for="Price" class="col-sm-2 col-form-label">Price</label>
                <form:input id="Price" class="form-control" path="price" value="${product.getPrice()}"/>
            </div>
            <div class="input-group mb-3">
                <label for="Rate" class="col-sm-2 col-form-label">Rate</label>
                <input type="number" id="Rate" class="form-control" disabled value="${product.getRate()}"/>
            </div>
            <div class="d-flex justify-content-center align-content-center">
                <input class="btn btn-primary" type="submit" value="Save">
            </div>
        </form:form>
    </div>
    <!-- /.card-body -->
</div>
</rapid:override>
<%--<%@ include file="../shared/home.jsp" %>--%>
<jsp:include page="../shared/home.jsp"/>
