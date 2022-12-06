<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:form="http://www.springframework.org/tags/form">

<rapid:override name="content">
<div class="card-header">
    <h3 class="card-title">Update Product</h3>
</div>
<div class="card-body mr-auto">
    <form:form modelAttribute="product" method="post" enctype="multipart/form-data">
        <div class="input-group mb-3">
            <label for="ID" class="col-sm-2 col-form-label">ID</label>
            <form:input path="id" id="ID" type="number" class="form-control" disabled="true"
                        value="${product.getId()}"/>
        </div>
        <div class="input-group mb-3">
            <label for="Name" class="col-sm-2 col-form-label">Name</label>
            <form:input id="Name" class="form-control" path="name" value="${product.getName()}"/>
        </div>
        <div class="d-flex justify-content-center align-items-center">
            <form:errors path="name" cssClass="text-danger"/>
        </div>

        <div class="input-group mb-3">
            <label for="Category" class="col-sm-2 col-form-label">Category</label>
            <form:select class="form-control" id="Category" path="category">
                <c:if test="${product.getCategory() == 'Cats'}" var="condition">
                    <form:option value="Cats" selected="true">
                        Cats
                    </form:option>
                    <form:option value="Dogs">
                        Dogs
                    </form:option>
                    <form:option value="Birds">
                        Birds
                    </form:option>
                    <form:option value="Turtles">
                        Turtles
                    </form:option>

                    <form:option value="Hamsters">
                        Hamsters
                    </form:option>
                </c:if>
                <c:if test="${product.getCategory() == 'Dogs'}" var="condition">
                    <form:option value="Cats">
                        Cats
                    </form:option>
                    <form:option value="Dogs" selected="true">
                        Dogs
                    </form:option>
                    <form:option value="Birds">
                        Birds
                    </form:option>
                    <form:option value="Turtles">
                        Turtles
                    </form:option>

                    <form:option value="Hamsters">
                        Hamsters
                    </form:option>
                </c:if>
                <c:if test="${product.getCategory() == 'Turtles'}" var="condition">
                    <form:option value="Cats">
                        Cats
                    </form:option>
                    <form:option value="Dogs">
                        Dogs
                    </form:option>
                    <form:option value="Birds">
                        Birds
                    </form:option>
                    <form:option value="Turtles" selected="true">
                        Turtles
                    </form:option>

                    <form:option value="Hamsters">
                        Hamsters
                    </form:option>
                </c:if>
                <c:if test="${product.getCategory() == 'Birds'}" var="condition">
                    <form:option value="Cats">
                        Cats
                    </form:option>
                    <form:option value="Dogs">
                        Dogs
                    </form:option>
                    <form:option value="Birds" selected="true">
                        Birds
                    </form:option>
                    <form:option value="Turtles">
                        Turtles
                    </form:option>
                    <form:option value="Hamsters">
                        Hamsters
                    </form:option>
                </c:if>
                <c:if test="${product.getCategory() == 'Hamsters'}" var="condition">
                    <form:option value="Cats">
                        Cats
                    </form:option>
                    <form:option value="Dogs" selected="true">
                        Dogs
                    </form:option>
                    <form:option value="Birds">
                        Birds
                    </form:option>
                    <form:option value="Turtles">
                        Turtles
                    </form:option>

                    <form:option value="Hamsters" selected="true">
                        Hamsters
                    </form:option>
                </c:if>
            </form:select>
        </div>
        <div class="input-group mb-3">
            <label for="Description" class="col-sm-2 col-form-label">Description</label>
            <form:input id="Description" class="form-control" path="description"
                        value="${product.getDescription()}"/>
        </div>
        <div class="d-flex justify-content-center align-items-center">
            <form:errors path="description" cssClass="text-danger"/>
        </div>
        <div class="input-group mb-3">
            <label for="Units" class="col-sm-2 col-form-label">Units</label>
            <form:input id="Units" type="number" class="form-control" path="inStock"
                        value="${product.getInStock()}"/>
        </div>
        <div class="d-flex justify-content-center align-items-center">
            <form:errors path="inStock" cssClass="text-danger"/>
        </div>
        <div class="input-group mb-3">
            <label for="Image" class="col-sm-2 col-form-label">Image</label>
            <div class="d-flex flex-row">
                <form:input path="image" id="Image" name="image" class="form-control" type="file"/>
                <img style="max-height: 120px; max-width: 120px" class="image img-thumbnail mr-auto"
                     alt="${product.getImage()}"
                     src="<c:url value="/resources/static/images/${product.getImage()}"/>">
            </div>
        </div>
        <div class="input-group mb-3">
            <label for="Price" class="col-sm-2 col-form-label">Price</label>
            <form:input id="Price" class="form-control" path="price" value="${product.getPrice()}"/>
        </div>
        <div class="d-flex justify-content-center align-items-center">
            <form:errors path="price" cssClass="text-danger"/>
        </div>

        <div class="d-flex justify-content-center align-content-center">
            <input class="btn btn-primary" type="submit" value="Save">
        </div>
    </form:form>
</div>
<!-- /.card-body -->
</rapid:override>
<jsp:include page="../shared/home.jsp"/>
