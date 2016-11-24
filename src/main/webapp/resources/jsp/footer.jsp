<%-- 
    Document   : footer
    Created on : Feb 2, 2016, 7:47:26 PM
    Author     : dave
    Contains a footer that should be within the body of an html document.
--%>
        <footer class="footer">
            <div class="container">
                <p class="text-muted">
                    <c:if test="${user == null}">
                        You are not logged in. Please log in first!!!
                    </c:if>
                    <c:if test="${user != null}">
                        Hello, 
                        <span class="fname"><c:out value="${user.firstName}"/></span>
                        <span class="lname"><c:out value="${user.lastName}"/></span>,
                        you are logged in as 
                        <span class="email"><c:out value="${user.email}"/></span>.
                    </c:if>
                </p>
            </div>
        </footer>
