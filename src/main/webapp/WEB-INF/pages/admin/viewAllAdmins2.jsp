<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<rapid:override name="content">
    <div style="padding-bottom: 5%">
    <a href="./createAdmin.htm" class="btn btn-primary float-right"><i class="fas fa-plus"></i>Add Admin</a>
    </div><br/>
    <table id="products" class="table table-bordered table-hover">
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
                <tr>
                    <td><c:out value="${admin.getId()}"/></td>
                    <td><c:out value="${admin.getEmail()}"/></td>
                    <td><c:out value="${admin.getUserName()}"/></td>
                    <td class="project-actions text-right">

                      <a class="btn btn-info btn-sm" href="#" data-method="delete">
                          <i class="fas fa-pencil-alt"></i>
                          Edit
                      </a>
                      <%--form method="delete">
                          <input type="hidden" name="id" value="${admin.getId()}" />
                          <input type="submit" value="Delete" />
                      </form--%>
                      <button id = "${admin.getId()}" class ="btn btn-danger btn-sm" onclick="deleteSomething(${admin.getId()})">
                          <i class="fas fa-trash"></i>
                          Delete

                      </button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
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

        // function deleteSomething(id) {
        //     console.log(id);
        //   fetch('?id=' + id,  {
        //     method: 'DELETE'
        //   })
        // }
        function deleteSomething(id) {
            $.ajax({
                type: "DELETE",
                url: "?id=" + id,
                success: function(response) {
                    console.log("success")
                    window.location.reload()
                },
                error: function(xhr, status, error) {
                    console.log(xhr);
                    if(xhr.status===500) {
                        window.location.reload()
                    }
                    //alert('Error: ' + error.message);
                },
            });
        }
    </script>
</rapid:override>


<jsp:include page="../shared/home.jsp"/>