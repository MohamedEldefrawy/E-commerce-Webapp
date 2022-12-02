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
<div style="padding-bottom: 5%">
<a href="./createAdmin.htm" class="btn btn-primary float-right"><i class="fas fa-plus"></i>Add Admin</a>
</div><br/>


    <table class="table">
        <thead class="table table-dark">
        <tr>
            <td>id</td>
            <td>email</td>
            <td>user name</td>

            <th style="width: 20%">
            </th>

        </tr>
        </thead>
        <tbody>
        <c:if test="${!empty admins}">
            <c:forEach var="admin" items="${admins}">
                <tr>
                    <td>${admin.id}</td>
                    <td>${admin.email}</td>
                    <td>${admin.userName}</td>

                    <td class="project-actions text-right">
                                              <a class="btn btn-info btn-sm" href="#">
                                                  <i class="fas fa-pencil-alt">
                                                  </i>
                                                  Edit
                                              </a>
                                              <a class="btn btn-danger btn-sm" href="#">
                                                  <i class="fas fa-trash">
                                                  </i>
                                                  Delete
                                              </a>
                                          </td>

                </tr>
            </c:forEach>

        </c:if>
        </tbody>

    </table>
</rapid:override>

<%@ include file="/WEB-INF/pages/home.jsp" %>
