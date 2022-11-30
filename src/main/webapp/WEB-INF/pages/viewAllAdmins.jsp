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
<style>
overlay {
  position: fixed;
  display: none;
  width: 100%;
  height: 100%;
  top: 5%;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  z-index: 2;
  cursor: pointer;
}
form-overlay-popup {
  position: fixed;
  display: none;
  width: 40%;
  margin-top:300%;
  left:50%;
  top: 50%;
  right: 50%;
  background-color: rgba(1,1,1,1);
  border: 3px solid #f1f1f1;
  z-index: 1;
}
#text{
  position: absolute;
  top: 50%;
  left: 50%;
  font-size: 50px;
  color: white;
  transform: translate(-50%,-50%);
  -ms-transform: translate(-50%,-50%);
}
</style>
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
<script>
$('#abc_id').click(function() {
            updateSubContent("abc.jsp");
        });
</script>
<%@ include file="/WEB-INF/pages/home.jsp" %>
