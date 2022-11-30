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



    <form:form modelAttribute="user" method="put">
            <div class="from-row md-4">
                <label for="email">e-mail</label>
                <form:input path="email" cssClass="form-control" id="firstName"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div class="from-row">
                <label for="user name">username</label>
                <form:input path="lastName" cssClass="form-control" id="lastName"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>

            <div>
                <label for="salary"><spring:message code="user.salary"/> </label>
                <form:input path="salary" cssClass="form-control" id="salary" type="number" min="0" step="1"/>
                <form:errors path="salary" cssClass="error"/>
            </div>
            <br>
            <input type="submit" value="add user">
    </form:form>




</rapid:override>
<%@ include file="/WEB-INF/pages/home.jsp" %>
