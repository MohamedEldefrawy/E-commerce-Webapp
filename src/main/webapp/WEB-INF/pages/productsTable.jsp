<%--
  Created by IntelliJ IDEA.
  User: voisDev
  Date: 2022-11-30
  Time: 6:22 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<rapid:override name="content">
    <table id="products" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Image</th>
            <th>Category</th>
            <th>Rate</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${products}" var="product">
            <tr>
                <td><c:out value="${product.getId()}"/></td>
                <td><c:out value="${product.getDescription()}"/></td>
                <td><c:out value="${product.getImage()}"/></td>
                <td><c:out value="${product.getCategory()}"/></td>
                <td><c:out value="${product.getRate()}"/></td>
                <td><c:out value="${product.getPrice()}"/></td>
                <td>EDIT|DELETE</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>

    <script>
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

<%@ include file="/WEB-INF/pages/home.jsp" %>
