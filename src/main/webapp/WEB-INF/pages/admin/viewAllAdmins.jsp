<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<rapid:override name="content">
    <div class="d-flex justify-content-center align-items-center">
        <a href="./createAdmin.htm" class="btn btn-success"><i class="fas fa-plus"></i></a>
    </div>
    <table id="admins" class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Username</th>
            <th style="width: 20%"></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${!empty admins}">
            <c:forEach items="${admins}" var="admin">
                <tr id="${admin.getId()}">
                    <td><c:out value="${admin.getId()}"/></td>
                    <td><c:out value="${admin.getEmail()}"/></td>
                    <td><c:out value="${admin.getUserName()}"/></td>
                    <td class="project-actions text-right">
                        <div class="d-flex justify-content-center align-items-center">
                            <a href="<c:url value="/admins/updateAdmin.htm?id=${admin.getId()}"/>"
                               class="btn btn-warning mr-2"><i
                                    class="far fa-edit"></i></a>

                            <button onclick="deleteById(${admin.getId()})" type="submit" class="btn btn-danger"><i
                                    class="fas fa-trash-alt"></i></button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>

    <script>
        $(function () {
            $('#admins').DataTable({
                "paging": true,
                "lengthChange": false,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": false,
                "responsive": true,
                "buttons": ["addButton"]
            });
        })

        function deleteById(id) {
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
                        window.location.href="../error.htm"
                }).catch((reason) => {
                    window.location.href = "../login.htm"
                })
            }
        }
    </script>
</rapid:override>


<jsp:include page="../shared/home.jsp"/>