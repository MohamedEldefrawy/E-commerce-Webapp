<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<rapid:override name="content">
    <div class="d-flex justify-content-center align-items-center">
        <a href="<c:url value="/admins/products/create.htm"/>" class="btn btn-success"><i class="fas fa-plus"></i></a>
    </div>
    <table id="products" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Units</th>
            <th>Image</th>
            <th>Category</th>
            <th>Rate</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr id="${product.getId()}">
                <td><c:out value="${product.getId()}"/></td>
                <td><c:out value="${product.getName()}"/></td>
                <td><c:out value="${product.getDescription()}"/></td>
                <td><c:out value="${product.getInStock()}"/></td>
                <td>
                    <img style="max-height: 100px; max-width: 100px" class="image img-thumbnail  img-circle mr-auto"
                         src="<c:url value="/resources/static/images/${product.getImage()}"/>">
                </td>
                <td><c:out value="${product.getCategory()}"/></td>
                <td><c:out value="${product.getRate()}"/></td>
                <td><c:out value="${product.getPrice()}"/></td>
                <td>
                    <div class="d-flex justify-content-center align-items-center">
                        <a href="<c:url value="/admins/products/update.htm?id=${product.getId()}"/>"
                           class="btn btn-warning mr-2"><i
                                class="far fa-edit"></i></a>

                        <button onclick="deleteRow(${product.getId()})" type="submit" class="btn btn-danger"><i
                                class="fas fa-trash-alt"></i></button>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <script>
        function deleteRow(id) {
            let result = confirm("Are you sure?");
            let row = document.getElementById(id);
            if (result) {
                fetch("?id=" + id, {
                    method: "DELETE",
                    headers: {
                        'Accept': '*/*'
                    }
                }).then(response => response.json()).then(data => {
                    if (data) {
                        alert("Item has been deleted successfully");
                        row.remove();
                    } else
                        window.location.href = "../../error.htm"
                }).catch((reason) => {
                    window.location.href = "../../login.htm"
                })
            }
        }

        $(function () {
            $('#products').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": false,
                "responsive": true,
            });
        })
    </script>
</rapid:override>

<jsp:include page="../shared/home.jsp"/>
