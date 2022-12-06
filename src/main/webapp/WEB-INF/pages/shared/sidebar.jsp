<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="<c:url value="/admins/home.htm"/>" class="brand-link">
        <img src="<c:url value="/resources/static/images/logo/logo.png"/>" alt="T.M.N.T Logo"
             class="brand-image">
        <span class="brand-text font-weight-light">TMNT</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- SidebarSearch Form -->
        <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search"
                       aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu"
                data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
                <li class="nav-item">
                    <a href="<c:url value="/admins/products/show.htm"/>" class="nav-link">
                        <%--                        <i class="nav-icon fas fa-tachometer-alt"></i>--%>
                        <i class="fas fa-store"></i>
                        <p>
                            Products
                        </p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<c:url value="/admins/admins.htm"/>" class="nav-link">
                        <i class="fas fa-users"></i>
                        <p>
                            Admins
                        </p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
