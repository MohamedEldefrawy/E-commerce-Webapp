<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<rapid:override name="content">

    <!-- Navbar Start -->
    <div class="container-fluid">
        <div class="row border-top px-xl-5">
            <div class="col-lg-3 d-none d-lg-block">
                <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100"
                   data-toggle="collapse" href="#navbar-vertical"
                   style="height: 65px; margin-top: -1px; padding: 0 30px;">
                    <h6 class="m-0">Categories</h6>
                    <i class="fa fa-angle-down text-dark"></i>
                </a>
                <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0"
                     id="navbar-vertical">
                    <div class="navbar-nav w-100 overflow-hidden" style="height: 410px">
                        <a href="<c:url value="/customer/home.htm?category=Cats"/>" class="nav-item nav-link">Cats</a>
                        <a href="<c:url value="/customer/home.htm?category=Dogs"/>" class="nav-item nav-link">Dogs</a>
                        <a href="<c:url value="/customer/home.htm?category=Birds"/>" class="nav-item nav-link">Birds</a>
                        <a href="<c:url value="/customer/home.htm?category=Turtles"/>"
                           class="nav-item nav-link">Turtles</a>
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
                        <h1 class="m-0 display-5 font-weight-semi-bold"><span
                                class="text-primary font-weight-bold border px-3 mr-1">E</span>Shopper</h1>
                    </a>
                    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                        <div class="navbar-nav mr-auto py-0">
                            <a href="<c:url value="/customer/home.htm"/>" class="nav-item nav-link active">Home</a>
                            <a href="<c:url value="/customer/home.htm#products"/>" class="nav-item nav-link">Shop</a>
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Pages</a>
                                <div class="dropdown-menu rounded-0 m-0">
                                    <a href="<c:url value="/customer/showCart.htm"/>" class="dropdown-item">Shopping Cart</a>
                                    <a href="checkout.html" class="dropdown-item">Checkout</a>
                                </div>
                            </div>
                            <a href="contact.html" class="nav-item nav-link">Contact</a>
                        </div>
                        <div class="navbar-nav ml-auto py-0">
                            <a href="" class="nav-item nav-link">Login</a>
                            <a href="" class="nav-item nav-link">Register</a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
    <!-- Navbar End -->


    <!-- Page Header Start -->
    <div class="container-fluid bg-secondary mb-5">
        <div class="d-flex flex-column align-items-center justify-content-center" style="min-height: 300px">
            <h1 class="font-weight-semi-bold text-uppercase mb-3">${product.getName()} Details</h1>
            <div class="d-inline-flex">
                <p class="m-0"><a href="<c:url value="/customer/home.htm"/>">Home</a></p>
                <p class="m-0 px-2">-</p>
                <p class="m-0">${product.getName()} Details</p>
            </div>
        </div>
    </div>
    <!-- Page Header End -->


    <!-- Shop Detail Start -->
    <div class="container-fluid py-5">
        <div class="row px-xl-5">
            <div class="col-lg-5 pb-5">
                <div id="product-carousel" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner border">
                        <div class="carousel-item active">
                            <img class="w-100 h-100"
                                 src="<c:url value="/resources/static/images/${product.getImage()}"/>"
                                 alt="${product.getName()}">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#product-carousel" data-slide="prev">
                        <i class="fa fa-2x fa-angle-left text-dark"></i>
                    </a>
                    <a class="carousel-control-next" href="#product-carousel" data-slide="next">
                        <i class="fa fa-2x fa-angle-right text-dark"></i>
                    </a>
                </div>
            </div>

            <div class="col-lg-7 pb-5">
                <h3 class="font-weight-semi-bold">${product.getName()}</h3>
                <div class="d-flex mb-3">
                    <div class="text-primary mr-2">
                        <small class="pt-3">Rate: ${product.getRate()}/5</small>
                    </div>
                </div>
                <h3 class="font-weight-semi-bold mb-4">$${product.getPrice()}</h3>
                <p class="mb-4">${product.getDescription()}.</p>
                <div class="d-flex align-items-center mb-4 pt-2">
                    <div class="input-group quantity mr-3" style="width: 130px;">
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-minus">
                                <i class="fa fa-minus"></i>
                            </button>
                        </div>
                        <input  id= ${product.getId()} type="text" class="form-control bg-secondary text-center" value="1">
                        <div class="input-group-btn">
                            <button class="btn btn-primary btn-plus">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </div>
                    <button onclick="addToCart(${product.getId()})" class="btn btn-primary px-3"><i class="fa fa-shopping-cart mr-1"></i> Add To Cart</button>
                </div>
                <div class="d-flex pt-2">
                    <p class="text-dark font-weight-medium mb-0 mr-2">Share on:</p>
                    <div class="d-inline-flex">
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-twitter"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-linkedin-in"></i>
                        </a>
                        <a class="text-dark px-2" href="">
                            <i class="fab fa-pinterest"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function addToCart(id) {
            let quantity = document.getElementById(id).value;
            fetch("../../addToCart/?itemId=" + id + "&quantity=" + quantity, {
                method: "POST",
                headers: {
                    'Accept': '*/*'
                }
            }).then(response => response.json()).then(data => {
                if (data) {
                    alert("Item added successfully");
                } else
                    window.location.href="../../../error.htm"
            }).catch((reason) => {
                window.location.href = "../../../login.htm"
            })

        }
    </script>
    <!-- Shop Detail End -->
</rapid:override>

<jsp:include page="../shared/home.jsp"/>
