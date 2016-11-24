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
        
        <script type='text/javascript'>
        	$(document).ready(function () {
        		$("#changeName").click(function () {
        			var fname = $('[name=firstname]').val();
                    var lname = $('[name=lastname]').val();
                    
            		$.ajax({
            		    type: "POST",
            		    url: '<c:url value="/settings/changeName"/>/fullname/',
            		    dataType: "json",
            		    data: {firstname: fname, lastname: lname},
            		    success: function(data, status){
            		    	if (status == "success" && data == true){
	            		    	$("span.fname").text(fname);
	            		    	$("span.lname").text(lname);
	            		    	$("#statusMsg").stop().hide()
	            		    		.text("Name changed successfully!")
	            		    		.slideDown().delay(5000).slideUp();
            		    	} else {
	            		    	$("#statusMsg").stop().hide()
	            		    		.text("Failed to change name!")
	            		    		.slideDown().delay(5000).slideUp();
            		    	}
            		    }
            		});
        		});
        		
        		$("#changeEmail").click(function () {
                    var email1 = $('[name=email1]').val();
                    var email2 = $('[name=email2]').val();
                    if (email1 != "" && email1 == email2) {
                    	$.ajax({
	            		    type: "POST",
	            		    url: '<c:url value="/settings/changeEmail"/>/',
	            		    dataType: "json",
	            		    data: {newEmail: email1},
	            		    success: function(data, status){
	            		    	if (status == "success" && data == true){
		            		    	$("span.email").text(email1);
		            		    	$("#statusMsg").stop().hide()
		            		    		.text("Email address changed successfully!")
		            		    		.slideDown().delay(5000).slideUp();
	            		    	} else {
		            		    	$("#statusMsg").stop().hide()
		            		    		.text("Failed to change email!")
		            		    		.slideDown().delay(5000).slideUp();
	            		    	}
	            		    }
            		    });

                    } else {
        		    	$("#statusMsg").stop().hide()
        		    		.text("Email addresses must have data and be equal!")
        		    		.slideDown().delay(5000).slideUp();
                    }
        		});
        		
        		$("#changePassword").click(function () {
        			var oldPass = $('[name=oldPassword]').val();
                    var newPass1 = $('[name=password1]').val();
                    var newPass2 = $('[name=password2]').val();
                    if (oldPass != "" && newPass1 != "" && newPass2 != "" &&
                    		newPass1 == newPass2){
                    	$.ajax({
	            		    type: "POST",
	            		    url: '<c:url value="/settings/changePassword"/>/',
	            		    dataType: "json",
	            		    data: {oldPassword: oldPass, newPassword: newPass1},
	            		    success: function(data, status){
	            		    	if (status == "success" && data == true){
		            		    	$("#statusMsg").stop().hide()
		            		    		.text("Password changed successfully!")
		            		    		.slideDown().delay(5000).slideUp();
	            		    	} else {
		            		    	$("#statusMsg").stop().hide()
		            		    		.text("Failed to change password!")
		            		    		.slideDown().delay(5000).slideUp();
	            		    	}
	            		    }
            		    });
                    	
                    }
        		});
        		$("#terms_conditions").click(function () {
    		    	$("#statusMsg").stop().hide()
		    			.text("Haha jk I don't know what they are!")
		    			.slideDown().delay(5000).slideUp();
        		});
        		$("#becomeSeller").click(function () {
        			$.ajax({
        				type: "POST",
        				url: '<c:url value="/settings/becomeSeller"/>/',
        				dataType: "json",
        				data: {setSeller: true},
            		    success: function(data, status){
	        		    	if (status == "success" && data == true){
	            		    	$("#statusMsg").stop().hide()
	            		    		.text("Congrats, now you're a seller!")
	            		    		.slideDown().delay(5000).slideUp();
	        		    	} else {
	            		    	$("#statusMsg").stop().hide()
	            		    		.text("Failed to change role!")
	            		    		.slideDown().delay(5000).slideUp();
	        		    	}
            		    }			
        			});
        		});

        	});
        
        </script>
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
                            <form class="form-horizontal">
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
                            </form>
                            <div class="row">
                                <div class="col-md-12" style="text-align: right">
                                    <button id="changeName" class="btn btn-default" type="submit">
                                        Change Name
                                    </button>
                                </div>
                            </div>
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
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email1">Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="email" name="email1"
                                        	value="<c:out value="${sessionScope.user.email}"/>"
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="email2">Confirm Email</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="email" name="email2"
                                        	value="<c:out value="${sessionScope.user.email}"/>"
                                        	required/>
                                    </div>
                                </div>
                            </form>
                            <div class="row">
                                <div class="col-md-12" style="text-align: right">
                                    <button id="changeEmail" class="btn btn-default" type="submit">
                                        Change Email
                                    </button>
                                </div>
                            </div>
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
                                        <input class="form-control" type="password" name="oldPassword"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password1">New Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="password" name="password1"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2" for="password2">Confirm New Password</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="password" name="password2"
                                        	value=""
                                        	required/>
                                    </div>
                                </div>
                            </form>
                            <div class="row">
                                <div class="col-md-12" style="text-align: right">
                                    <button id="changePassword" class="btn btn-default" type="submit">
                                        Change Password
                                    </button>
                                </div>
                            </div>
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
                                    <button id="becomeSeller" class="btn btn-default">
                                        Become a super awesome seller!!!
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    

	            </div>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <div class="alert alert-warning" id="statusMsg" hidden>
                    </div>
                </div>
                <div class="col-md-2"></div>
            </div>
        </div>
        <%@ include file="/resources/jsp/footer.jsp" %>
    </body>
</html>
            