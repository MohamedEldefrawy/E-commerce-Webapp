<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>TMNT Pet Shopping</title>
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
<!-- Navbar Start -->
<div class="container-fluid mb-5">
    <div class="row border-top px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a class="btn shadow-none d-flex align-items-center justify-content-between bg-success text-white w-100"
               data-toggle="collapse" href="#navbar-vertical" style="height: 65px; margin-top: -1px; padding: 0 30px;">
                <h6 class="m-0">Categories</h6>
                <i class="fa fa-angle-down text-dark"></i>
            </a>
            <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0"
                 id="navbar-vertical">
                <div class="navbar-nav w-100 overflow-hidden" style="height: 410px">
                    <a href="<c:url value="/customer/home.htm?category=Cats"/>" class="nav-item nav-link">Cats</a>
                    <a href="<c:url value="/customer/home.htm?category=Dogs"/>" class="nav-item nav-link">Dogs</a>
                    <a href="<c:url value="/customer/home.htm?category=Birds"/>" class="nav-item nav-link">Birds</a>
                    <a href="<c:url value="/customer/home.htm?category=Turtles"/>" class="nav-item nav-link">Turtles</a>
                    <a href="<c:url value="/customer/home.htm?category=Hamsters"/>"
                       class="nav-item nav-link">Hamsters</a>
                    <a href="<c:url value="/customer/home.htm"/>"
                       class="nav-item nav-link">All Products</a>
                </div>
            </nav>
        </div>
        <div class="col-lg-9">
            <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                <a href="" class="text-decoration-none d-block d-lg-none">
                    <h1 class="m-0 display-5 font-weight-semi-bold custom-green">
                        <img style="max-height: 100px; max-width: 100px;margin-left: 10px" class="image img-circle mr-auto"
                             src="<c:url value="/resources/static/images/logo/logo_green.png"/>">
                        TMNT</h1>
                </a>
                <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                    <div class="navbar-nav mr-auto py-0">
                        <a href="<c:url value="/customer/home.htm"/>" class="nav-item nav-link active ">Home</a>
                        <a href="<c:url value="/customer/home.htm#Products"/>" class="nav-item nav-link">Shop</a>
                        <a href="<c:url value="/customer/showCart.htm"/>" class="nav-item nav-link">Shopping Cart</a>
                        <a href="<c:url value="/customer/orders.htm"/>" class="nav-item nav-link">Orders</a>
                    </div>
                    <div class="navbar-nav ml-auto py-0">
                        <a href="<c:url value="/logout.htm"/>" class="nav-item nav-link">Logout</a>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</div>
<!-- Navbar End -->

<!-- Products Start -->
<!-- Page Header Start -->
<div class="container-fluid bg-secondary mb-5">
    <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
        <h1 class="font-weight-semi-bold text-uppercase mb-3">Shopping Cart</h1>
        <div class="d-inline-flex">
            <p class="m-0"><a href="">Home</a></p>
            <p class="m-0 px-2">-</p>
            <p class="m-0">Shopping Cart</p>
        </div>
    </div>
</div>
<!-- Page Header End -->


<!-- Cart Start -->
<div class="container-fluid pt-5">
    <div class="row px-xl-5">
        <div class="col-lg-8 table-responsive mb-5">
            <table class="table table-bordered text-center mb-0">
                <thead class="bg-secondary text-dark">
                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Remove</th>
                </tr>
                </thead>
                <tbody class="align-middle">
                <c:if test="${!empty items}">
                    <c:forEach items="${items}" var="item">
                        <tr id = "${item.getId()}">
                            <td class="align-middle"><img src="<c:url value="/resources/static/images/${item.getProduct().getImage()}"/>" alt="" style="width: 50px;"> ${item.getProduct().getName()}</td>
                            <td class="align-middle">$${item.getProduct().getPrice()}</td>
                            <td class="align-middle">
                                <div class="input-group quantity mx-auto" style="width: 100px;">
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-success btn-minus" onclick="decrementQuantity(${item.getProduct().getId()})">
                                            <i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                    <input id="${item.getProduct().getId()}" type="text" class="form-control form-control-sm bg-secondary text-center" style ="height: 25px;"value="${item.getQuantity()}">
                                    <div class="input-group-btn">
                                        <button class="btn btn-sm btn-success btn-plus" onclick="incrementQuantity(${item.getProduct().getId()})" >
                                            <i class="fa fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                            </td>
                            <td class="align-middle">$${item.getTotal()}</td>
                            <td class="align-middle"><button class="btn btn-sm btn-success" onclick="deleteRow(${item.getId()})"><i class="fa fa-times"></i></button></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <div class="col-lg-4">
            <div class="card border-secondary mb-5">
                <div class="card-header bg-secondary border-0">
                    <h4 class="font-weight-semi-bold m-0">Cart Summary</h4>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-3 pt-1">
                        <h6 class="font-weight-medium">Subtotal</h6>
                        <h6 class="font-weight-medium">$${orderTotal}</h6>
                    </div>
                    <div class="d-flex justify-content-between">
                        <h6 class="font-weight-medium">Shipping</h6>
                        <h6 class="font-weight-medium">$0</h6>
                    </div>
                </div>
                <div class="card-footer border-secondary bg-transparent">
                    <div class="d-flex justify-content-between mt-2">
                        <h5 class="font-weight-bold">Total</h5>
                        <h5 class="font-weight-bold">$${orderTotal}</h5>
                    </div>
                    <button onclick="checkout()" class="btn btn-block btn-success my-3 py-3">Proceed To Checkout</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Cart End -->
<!-- Products End -->


<!-- Footer Start -->
<jsp:include page="footer.jsp"/>
<!-- Footer End -->


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
                window.location.href="../error.htm"
        }).catch((reason) => {
            window.location.href = "../login.htm"
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
            } else {
                let oldQuantity = document.getElementById(productId).value;
                document.getElementById(productId).value = oldQuantity -1;
                alert("Not available in stock");
            }
        }).catch((reason) => {
            window.location.href = "../login.htm"
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
                window.location.href="../error.htm"
        }).catch((reason) => {
            window.location.href = "../login.htm"
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
                    window.location.href="../error.htm"
            }).catch((reason) => {
                window.location.href = "../login.htm"
            })
        }
    }

</script>

</html>
