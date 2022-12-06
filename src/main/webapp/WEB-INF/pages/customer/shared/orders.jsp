<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<rapid:override name="content">

    <!-- Navbar Start -->
    <div class="container-fluid mb-5">
        <div class="row border-top px-xl-5">
            <div class="col-lg-3 d-none d-lg-block">
                <a class="btn shadow-none d-flex align-items-center justify-content-between bg-primary text-white w-100"
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
                        <h1 class="m-0 display-5 font-weight-semi-bold"><span
                                class="text-primary font-weight-bold border px-3 mr-1">E</span>Shopper</h1>
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
            </div>
        </div>
    </div>
    <!-- Navbar End -->


    <!-- Page Header Start -->
    <div class="container-fluid pt-5">
        <div class="row px-xl-5 pb-3">
            <c:forEach items="${orders}" var="order">
                <div class="col-lg-4">
                    <div class="card border-secondary mb-5">
                        <div class="card-header bg-secondary border-0">
                            <h4 class="font-weight-semi-bold m-0">Order #${order.getId()}</h4>
                        </div>
                        <div class="card-header bg-secondary border-0">
                            <h6 class="font-weight-medium">${order.getDate()}</h6>
                        </div>
                        <div class="card-body">
                            <h5 class="font-weight-medium mb-3">Products</h5>
                            <c:forEach items="${order.getOrderItems()}" var="orderItem">
                                <div class="d-flex justify-content-between">
                                    <div class="container">
                                        <div>
                                            <h5 class="font-weight-semi-bold m-0">${orderItem.getProduct().getName()}</h5>
                                        </div>
                                        <div class="row">
                                            <div class="col-6 col-md-4">
                                                <img src="<c:url value="/resources/static/images/${orderItem.getProduct().getImage()}"/>" alt="" style="width: 50px;">
                                            </div>
                                            <div class="col-6 col-md-4 d-flex align-items-end">
                                                <p>${orderItem.getProduct().getRate()}/5</p>
                                            </div>
                                            <div class="col-6 col-md-4 d-flex align-items-end">
                                                <p>${orderItem.getProduct().getCategory()}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-6 col-md-4">
                                                <p>$${orderItem.getProduct().getPrice()}</p>
                                            </div>
                                            <div class="col-6 col-md-4">
                                                <p>${orderItem.getQuantity()}x</p>
                                            </div>
                                            <div class="col-6 col-md-4">
                                                <p>$${orderItem.getTotal()}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <hr class="mt-0">
                            <div class="d-flex justify-content-between mb-3 pt-1">
                                <h6 class="font-weight-medium">Subtotal</h6>
                                <h6 class="font-weight-medium">$${order.getTotal()}</h6>
                            </div>
                            <div class="d-flex justify-content-between">
                                <h6 class="font-weight-medium">Shipping</h6>
                                <h6 class="font-weight-medium">$0</h6>
                            </div>
                        </div>
                        <div class="card-footer border-secondary bg-transparent">
                            <div class="d-flex justify-content-between mt-2">
                                <h5 class="font-weight-bold">Total</h5>
                                <h5 class="font-weight-bold">$${order.getTotal()}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Shop Detail End -->
</rapid:override>

<jsp:include page="../shared/home.jsp"/>
