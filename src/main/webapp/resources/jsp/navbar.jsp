<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span> 
            </button>
            <a class="navbar-brand" href="#">WebSiteName</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <c:if test="${sessionScope.user != null}">
                    <li class="active"><a href="#">Home</a></li>
                    <c:forEach var="navbarItem" items="${requestScope.navbarItems}">
                        <li${(navbarItem.isActive ? " class=\"active\"": "")}>
                            <a href="${navbarItem.href}"><c:out value="${navbarItem.label}"/></a>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <li><a href="<c:url value="/#registerDiv"/>"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                        <li><a href="<c:url value="/#loginDiv"/>"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                        	<a href="<c:url value="/settings"/>"><span class="glyphicon glyphicon-user"></span> 
                        	<c:out value="${sessionScope.user.email}" /></a>
                        </li>
                        <li><a href="<c:url value="/logout"/>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
