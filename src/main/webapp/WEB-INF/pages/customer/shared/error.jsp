<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

</head>

<body>
<!-- Topbar Start -->
<div class="row align-items-center py-3 px-xl-5">
    <div class="col-lg-3 d-none d-lg-block">
        <a href="" class="text-decoration-none">
            <h1 class="m-0 display-5 font-weight-semi-bold"><span class="text-success font-weight-bold border px-3 mr-1">E</span>Shopper</h1>
        </a>
    </div>
    <div class="col-lg-6 col-6 text-left">
    </div>
</div>
<!-- Navbar End -->

<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">400</h1>
    </div>
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Please Check Internet Connection</h1>
    </div>
</div>
<!-- Page Header End -->




<!-- Back to Top -->
<a href="#" class="btn btn-success back-to-top"><i class="fa fa-angle-double-up"></i></a>


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
</body>
<script>
    function deleteRow(itemId) {
        let row = document.getElementById(itemId);
        fetch("?itemId=" + itemId, {
            method: "DELETE",
            headers: {
                'Accept': '*/*'
            }
        }).then(response => response.json()).then(data => {
            if (data) {
                row.remove();
                window.location.reload();
            } else
                alert("Something Wrong!!")
        }).catch((reason) => {
            alert(reason);
        })
    }
    function incrementQuantity(productId) {
        fetch("./increment/?productId=" + productId, {
            method: "PUT",
            headers: {
                'Accept': '*/*'
            }
        }).then(response => response.json()).then(data => {
            if (data) {
                window.location.reload();
            } else
                alert("Something Wrong!!")
        }).catch((reason) => {
            alert(reason);
        })
    }
    function decrementQuantity(productId) {
        fetch("./decrement/?productId=" + productId, {
            method: "PUT",
            headers: {
                'Accept': '*/*'
            }
        }).then(response => response.json()).then(data => {
            if (data) {
                window.location.reload();
            } else
                alert("Something Wrong!!")
        }).catch((reason) => {
            alert(reason);
        })
    }
    function checkout() {
        let result = confirm("Are you sure?");
        if(result) {
            fetch("./submitOrder.htm", {
                method: "POST",
                headers: {
                    'Accept': '*/*'
                }
            }).then(response => response.json()).then(data => {
                if (data) {
                    window.location.reload()
                } else
                    alert("Something Wrong!!")
            }).catch((reason) => {
                alert(reason);
            })
        }
    }

</script>

</html>
