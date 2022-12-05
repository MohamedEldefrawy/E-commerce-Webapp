<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="Products" class="text-center mb-4">
    <h2 class="section-title px-5"><span class="px-2">Products</span></h2>
</div>

<div class="container-fluid pt-5">
    <div id="ProductsCards" class="row px-xl-5 pb-3">
        <c:forEach items="${products}" var="product">
            <div  class="col-lg-3 col-md-6 col-sm-12 pb-1">
                <div class="card product-item border-0 mb-4">
                    <div class="card-header product-img position-relative overflow-hidden bg-transparent border p-0 h">
                        <img style="height: 250px" class="img-fluid w-100"
                             src="<c:url value="/resources/static/images/${product.getImage()}"/>">
                    </div>
                    <div class="card-body border-left border-right text-center p-0 pt-4 pb-3">
                        <h6 class="product-name text-truncate mb-3">${product.getName()}</h6>
                        <div class="d-flex justify-content-center">
                            <h6>$${product.getPrice()}</h6>
                            <h6 class="text-muted ml-2">
                                <del>$${product.getPrice()+1.99}</del>
                            </h6>
                        </div>
                    </div>
                    <div class="card-footer d-flex justify-content-between bg-light border">
                        <div class="input-group quantity mr-3" style="width: 40px;">
                            <div class="input-group-btn" style="width: 40px;">
                                <button class="btn btn-primary btn-minus">
                                    <i class="fa fa-minus"></i>
                                </button>
                            </div>
                            <input id="${product.getId()}" type="text" style="width: 40px;"
                                   class="form-control bg-secondary text-center" value="1">
                            <div class="input-group-btn" style="width: 40px;">
                                <button class="btn btn-primary btn-plus">
                                    <i class="fa fa-plus"></i>
                                </button>
                            </div>
                        </div>
                        <br/>
                        <a href="<c:url value="/customer/products/${product.getId()}/details.htm"/>"
                           class="btn btn-sm text-dark p-0 m-auto"><i class="fas fa-eye text-primary mr-1"></i>View
                            Detail
                        </a>
                        <button class="btn btn-sm text-dark p-0" onclick="addToCart(${product.getId()})"><i
                                class="fas fa-shopping-cart text-primary mr-1"></i>Add
                            To Cart
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    function addToCart(id) {
        let quantity = document.getElementById(id).value;
        fetch("./addToCart/?customerId=2&itemId=" + id + "&quantity=" + quantity, {
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