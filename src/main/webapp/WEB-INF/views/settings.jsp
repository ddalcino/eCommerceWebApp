<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
    Document   : landingPage
    Created on : Oct 3, 2016, 7:57:18 PM
    Author     : dave
--%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/resources/jsp/header.jsp" %>
    </head>
    <body>
        <%@include file="/resources/jsp/navbar.jsp" %>
        <div class="container">
        	<h2>Welcome to your User Settings page!</h2>
	        <div class="panel-group" id="SettingsAccordion">
	            <div class="panel panel-default">
	            
	            
	                <div class="panel-heading">
	                    <h3 class="panel-title">
	                    	<a data-toggle="collapse" data-parent="#SettingsAccordion" 
	                    			href="#accountName">
	                    		Change Name On Account:
	                    	</a>
	                    </h3>
	                </div>
	                <div id="accountName" class="panel-collapse collapse in">
                    	<div class="panel-body">
                            <form action="<c:url value="/settings/changeName"/>" class="form-horizontal" 
                                  onsubmit="return validateName();" method="post">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="firstname">First name</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="firstname"
                                        	value="<c:out value="${sessionScope.user.firstName}"/>"
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="lastname">Last name</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="lastname"
                                        	value="<c:out value="${sessionScope.user.lastName}"/>"
                                        	required/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: right">
                                        <button class="btn btn-default" type="submit">
                                            Change Name
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>


	                <div class="panel-heading">
	                    <h3 class="panel-title">
	                    	<a data-toggle="collapse" data-parent="#SettingsAccordion" 
	                    			href="#accountEmail">
	                    		Change Email Associated With Account:
	                    	</a>
	                    </h3>
	                </div>
	                <div id="accountEmail" class="panel-collapse collapse">
                    	<div class="panel-body">
                            <form action="<c:url value="/settings/changeEmail"/>" class="form-horizontal" 
                                  onsubmit="return validateEmail();" method="post">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email1">Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="email1"
                                        	value="<c:out value="${sessionScope.user.email}"/>"
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email2">Confirm Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="email2"
                                        	value="<c:out value="${sessionScope.user.email}"/>"
                                        	required/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: right">
                                        <button class="btn btn-default" type="submit">
                                            Change Email
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
 
 
 	                <div class="panel-heading">
	                    <h3 class="panel-title">
	                    	<a data-toggle="collapse" data-parent="#SettingsAccordion" 
	                    			href="#accountPassword">
	                    		Change Account Password:
	                    	</a>
	                    </h3>
	                </div>
	                <div id="accountPassword" class="panel-collapse collapse">
                    	<div class="panel-body">
                            <form action="<c:url value="/settings/changePassword"/>" class="form-horizontal" 
                                  onsubmit="return validatePassword();" method="post">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="oldPassword">Old Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="oldPassword"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password1">New Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="password1"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password2">Confirm New Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="text" name="password2"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12" style="text-align: right">
                                        <button class="btn btn-default" type="submit">
                                            Change Password
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
 
 
  	                <div class="panel-heading">
	                    <h4 class="panel-title">
	                    	<a data-toggle="collapse" data-parent="#SettingsAccordion" 
	                    			href="#accountType">
	                    		Become a Seller:
	                    	</a>
	                    </h4>
	                </div>
	                <div id="accountType" class="panel-collapse collapse">
                    	<div class="panel-body">
                            <form action="<c:url value="/settings/becomeSeller"/>" class="form-horizontal" 
                                  method="post">
                                <div class="row">
                                	<div class="col-md-12">
	                                	<h3>Sell your cool stuff and make lots of $$$!!!</h3>
	                                	<p>
	                                		Anyone can become a seller! Just read our exhaustive list of terms and 
	                                		conditions, and you too can make $X an hour working from home! I swear
	                                		it's not a scam!
	                                	</p>
	                                </div>
	                                <div class="col-md-12"  style="text-align: right">
	                                	<button id="terms_conditions" class="btn btn-default">
	                                            View Terms and Conditions
	                                    </button>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-12" style="text-align: right">
                                        <button id="become_seller" class="btn btn-default">
                                            Become A Seller!
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    

	            </div>
            </div>
        </div>
        <%@ include file="/resources/jsp/footer.jsp" %>
    </body>
</html>
            