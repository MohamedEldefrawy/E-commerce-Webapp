<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>EShopper - Bootstrap Shop Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="<c:url value="/resources/static/css/owl.carousel.min.css" />" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="<c:url value="/resources/static/css/style.min.css" />" rel="stylesheet">

    <%-- Site Custom Style --%>
    <link href="<c:url value="/resources/static/css/customer-custom-style.css" />" rel="stylesheet">

</head>

<body>
<div class="container-fluid">

</div>
<rapid:block name="content">
    <!-- Navbar Start -->
    <jsp:include page="navbar.jsp"/>
    <!-- Navbar End -->

    <!-- Products Start -->
    <jsp:include page="products.jsp"/>
    <!-- Products End -->
</rapid:block>


<!-- Footer Start -->
<jsp:include page="footer.jsp"/>
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>
</body>
<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value="/resources/static/js/easing.min.js" />"></script>
<script src="<c:url value="/resources/static/js/owl.carousel.min.js" />"></script>

<!-- Contact Javascript File -->
<script src="<c:url value="/resources/static/js/jqBootstrapValidation.min.js" />"></script>
<script src="<c:url value="/resources/static/js/contact.js" />"></script>

<!-- Template Javascript -->
<script src="<c:url value="/resources/static/js/main.js" />"></script>
<script src="<c:url value="/resources/static/js/customerHome.js" />"></script>
</html>