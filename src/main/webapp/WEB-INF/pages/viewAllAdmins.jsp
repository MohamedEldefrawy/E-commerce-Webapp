<%--
  Created by IntelliJ IDEA.
  User: voisDev
  Date: 2022-11-30
  Time: 2:05 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="content">
    <table class="table">
        <thead class="table table-dark">
        <tr>
            <td>id</td>
            <td>email</td>
            <td>user name</td>
        </tr>
        </thead>
        <tbody>
        <c:if test="${!empty admins}">
            <c:forEach var="admin" items="${admins}">
                <tr>
                    <td>${admin.id}</td>
                    <td>${admin.email}</td>
                    <td>${admin.userName}</td>
                </tr>
            </c:forEach>

        </c:if>
        </tbody>

    </table>
</rapid:override>

<%@ include file="/WEB-INF/pages/home.jsp" %>
