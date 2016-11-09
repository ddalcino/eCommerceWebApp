<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <%@ include file="/resources/jsp/header.jsp" %>
        <script>
            function validateRegistration() {
                var fail = false;
                if ($('[name=firstname]').val() === "") {
                    $("#fnameWarning").show();
                    fail = true;
                } else {
                    $("#fnameWarning").hide();
                }
                if ($('[name=lastname]').val() === "") {
                    $("#lnameWarning").show();
                    fail = true;
                } else {
                    $("#lnameWarning").hide();
                }
                if ($('[name=email1]').val() === "") {
                    $("#email1Warning").show();
                    fail = true;
                } else {
                    $("#email1Warning").hide();
                }
                if ($('[name=email1').val() !== $('[name=email2]').val()) {
                    // email addresses must match
                    $("#email2Warning").show();
                    fail = true;
                } else {
                    $("#email2Warning").hide();
                }
                if (!isPasswordStrong($('[name=password1]').val())) {
                    $("#passwordWarning1").show();
                    fail = true;
                } else {
                    $("#passwordWarning1").hide();
                }
                if ($('[name=password1]').val() !== $('[name=password2]').val()) {
                    // passwords must match
                    $("#passwordWarning2").show();
                    fail = true;
                } else {
                    $("#passwordWarning2").hide();
                }
//                if (fail) {
//                    alert("You failed to register!!!\nA-hahahaha!");
//                }
                return !fail;
            }
            function isPasswordStrong(pass) {
                if (pass.length < 8) {
                    return false;
                }
                if (/[A-Z]/.test(pass) === false) {
                    return false;
                }
                if (/[a-z]/.test(pass) === false) {
                    return false;
                }
                if (/[0-9]/.test(pass) === false) {
                    return false;
                }
                if (/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(pass) === false) {
                    return false;
                }
                return true;
            }

        </script>
    </head>
    <body>
        <%@include file="/resources/jsp/navbar.jsp" %>
        <div class="container">
            <div class="panel-group" id="loginRegisterAccordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a data-toggle="collapse" data-parent="#loginRegisterAccordion" href="#loginDiv">Login:</a>
                        </h3>
                    </div>
                    <div id="loginDiv" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <form action="<c:url value="/auth/login"/>" class="form-horizontal" method="post">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email">Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="email" name="email" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password">Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="password" name="password" required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12" style="text-align: right">
                                        <label class="control-label col-md-2"></label>
                                        <button class="btn btn-default" type="submit" value="Login">Log Me In!</button>
                                    </div>
                                </div>
                            </form>
                        </div> 
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a data-toggle="collapse" data-parent="#loginRegisterAccordion" href="#registerDiv">
                                Register:</a>
                        </h3>
                    </div>
                    <div id="registerDiv" class="panel-collapse collapse">
                        <div class="panel-body">
                            <form action="<c:url value="/auth/register"/>" class="form-horizontal" 
                                  onsubmit="return validateRegistration();" method="post">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="firstname">First name</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="firstname" required/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="fnameWarning" hidden>
                                            Required!
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="lastname">Last name</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="lastname" required/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="lnameWarning" hidden>
                                            Required!
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email1">Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="email" name="email1"/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="email1Warning" hidden>
                                            Required!
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email2">Confirm Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="email" name="email2"/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="email2Warning" hidden>
                                            Email addresses must match!
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password1">Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="password" name="password1"/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="passwordWarning1" hidden>
                                            Passwords must be at least 8 characters long, 
                                            have upper and lower case letters,
                                            include at least one number 
                                            and at least one special character.
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password2">Confirm Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="password" name="password2"/>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="alert alert-warning" id="passwordWarning2" hidden>
                                            Passwords must match!
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: right">
                                        <button class="btn btn-default" type="submit" value="Register">
                                            Register
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <c:if test="${requestScope.statusMsg != null}">
                        <div class="alert alert-warning">
                            ${requestScope.statusMsg}
                        </div>
                    </c:if>
                </div>
                <div class="col-md-2"></div>
            </div>
        </div>
        <%@ include file="/resources/jsp/footer.jsp" %>
    </body>
</html>
