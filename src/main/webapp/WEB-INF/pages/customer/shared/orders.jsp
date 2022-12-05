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
                                        <div class="row">
                                            <div class="col-6 col-sm-4">
                                                <img src="<c:url value="/resources/static/images/${orderItem.getProduct().getImage()}"/>" alt="" style="width: 50px;">
                                            </div>
                                            <div class="col-6 col-sm-4">
                                                <h5 class="font-weight-semi-bold m-0">${orderItem.getProduct().getName()}</h5>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-6 col-sm-4">
                                            <p>${orderItem.getProduct().getCategory()}</p>
                                        </div>
                                        <div class="col-6 col-sm-4">
                                            <p>${orderItem.getProduct().getRate()}/5</p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <p>${orderItem.getQuantity()}x</p>
                                        <p>$${orderItem.getTotal()}</p>
                                    </div>
                                </div>
                            </c:forEach>
                            <%--div class="col-lg-8 table-responsive mb-5">
                                <table class="table table-bordered text-center mb-0">
                                    <thead class="bg-secondary text-dark">
                                    <tr>
                                        <th>Product</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                        <th>Category</th>
                                        <th>Rate</th>
                                    </tr>
                                    </thead>
                                    <tbody class="align-middle">
                                    <c:if test="${!empty order.getOrderItems()}">
                                        <c:forEach items="${order.getOrderItems()}" var="orderItem">
                                            <tr id = "${orderItem.getId()}">
                                                <td class="align-middle"><img src="<c:url value="/resources/static/images/${orderItem.getProduct().getImage()}"/>" alt="" style="width: 50px;"> ${item.getProduct().getName()}</td>
                                                <td class="align-middle">$${orderItem.getProduct().getPrice()}</td>
                                                <td class="align-middle">${orderItem.getQuantity()}</td>
                                                <td class="align-middle">$${orderItem.getTotal()}</td>
                                                <td class="align-middle">$${orderItem.getProduct().getCategory()}</td>
                                                <td class="align-middle">$${orderItem.getProduct().getRate()}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div--%>
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
    <!--Orders End-->
    <script>
        function addToCart(id) {
            let quantity = document.getElementById(id).value;
            fetch("../../addToCart/itemId=" + id + "&quantity=" + quantity, {
                method: "POST",
                headers: {
                    'Accept': '*/*'
                }
            }).then(response => response.json()).then(data => {
                if (data) {
                    alert("Item added successfully");
                } else
                    alert("Something Wrong!!")
            }).catch((reason) => {
                alert(reason);
            })

        }
    </script>
    <!-- Shop Detail End -->
</rapid:override>

<jsp:include page="../shared/home.jsp"/>
