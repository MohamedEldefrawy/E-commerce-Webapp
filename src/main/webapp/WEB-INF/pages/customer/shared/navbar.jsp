<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Topbar Start -->
<div class="container-fluid">
    <div class="row align-items-center py-3 px-xl-5">
        <div class="col-lg-3 d-none d-lg-block">
            <a href="" class="text-decoration-none">
                <h1 class="m-0 display-5 font-weight-semi-bold text-success">
                    <img style="max-height: 100px; max-width: 100px;margin-left: 10px" class="image img-circle mr-auto"
                         src="<c:url value="/resources/static/images/logo/logo_green.png"/>">
                    TMNT</h1>
            </a>
        </div>
        <div class="col-lg-6 col-6 text-left">
            <div class="input-group">
                <input id="Search" type="text" class="form-control" placeholder="Search for products"/>
                <div class="input-group-append">
                    <button id="btnSearch" class="input-group-text bg-transparent text-success">
                        <b class="fa fa-search"></b>
                    </button>
                </div>
            </div>
        </div>
    </div>


    <!-- Navbar Start -->
    <div class="container-fluid mb-5">
        <div class="row border-top px-xl-5">
            <div class="col-lg-3 d-none d-lg-block">
                <a class="btn shadow-none d-flex align-items-center justify-content-between bg-success text-white w-100"
                   data-toggle="collapse" href="#navbar-vertical"
                   style="height: 65px; margin-top: -1px; padding: 0 30px;">
                    <h6 class="m-0 text-white">Categories</h6>
                    <i class="fa fa-angle-down text-white"></i>
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
                        <h1 class="m-0 display-5 font-weight-semi-bold text-success">
                            <img style="max-height: 100px; max-width: 100px;margin-left: 10px" class="image img-circle mr-auto"
                                 src="<c:url value="/resources/static/images/logo/logo_green.png"/>">
                            TMNT</h1>
                    </a>
                    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                        <div class="navbar-nav mr-auto py-0">
                            <a href="<c:url value="/customer/home.htm"/>" class="nav-item nav-link active">Home</a>
                            <a href="<c:url value="/customer/home.htm#Products"/>" class="nav-item nav-link">Shop</a>
                            <a href="<c:url value="/customer/showCart.htm"/>" class="nav-item nav-link">Shopping Cart</a>
                            <a href="<c:url value="/customer/orders.htm"/>" class="nav-item nav-link">Orders</a>
                        </div>
                        <div class="navbar-nav ml-auto py-0">
                            <a href="<c:url value="/logout.htm"/>" class="nav-item nav-link">Logout</a>
                        </div>
                    </div>
                </nav>
                <div id="header-carousel" class="carousel slide" data-ride="carousel">
                    <div id="CarouselItems" class="carousel-inner">
                        <c:forEach items="${products}" var="product">
                            <div class="carousel-item" style="height: 410px;">
                                <img class="img-fluid"
                                     src="<c:url value="/resources/static/images/${product.getImage()}"/>"
                                     alt="${product.getName()}">
                                <div class="carousel-caption d-flex flex-column align-items-center justify-content-center">
                                    <div class="p-3" style="max-width: 700px;">
                                        <h4 class="text-light text-uppercase font-weight-medium mb-3">10% Off Your First
                                            Order
                                        </h4>
                                        <h3 class="display-4 text-white font-weight-semi-bold mb-4">${product.getName()}</h3>
                                        <a href="#Products" class="btn btn-light py-2 px-3">Shop Now</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                    <a class="carousel-control-prev" href="#header-carousel" data-slide="prev">
                        <div class="btn btn-dark" style="width: 45px; height: 45px;">
                            <span class="carousel-control-prev-icon mb-n2"></span>
                        </div>
                    </a>
                    <a class="carousel-control-next" href="#header-carousel" data-slide="next">
                        <div class="btn btn-dark" style="width: 45px; height: 45px;">
                            <span class="carousel-control-next-icon mb-n2"></span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
<%--/div--%>
    <!-- Navbar End -->