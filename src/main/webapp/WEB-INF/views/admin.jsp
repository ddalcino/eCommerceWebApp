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
            function setRole() {
                $('[name=role]').val( $("#newRole").prop("selectedIndex") );
                return true;
            }
            
             
            $(document).ready(function () {
                $("button.update_btn").click(function () {
                    // get default values
                    var row = $(this).parent().parent().parent().children("td");
                    var fname = row.eq(0).text();
                    var lname = row.eq(1).text();
                    var email = row.eq(2).text();
                    var role = row.eq(3).text();
                    var userid = row.eq(4).text();
                   
                    // set default values
                    $('[name=firstName]').val(fname);
                    $('[name=lastName]').val(lname);
                    $('[name=email]').val(email);
                    $('[name=userid]').val(userid);
                    if (role === "CUSTOMER") {
                        $("#newRole option").eq(0).prop('selected', true);
                    } else if (role === "SELLER") {
                        $("#newRole option").eq(1).prop('selected', true);
                    } else if (role === "ADMIN") {
                        $("#newRole option").eq(2).prop('selected', true);
                    }
                });
                $("button[id^='delete_user']").click(function () {
                	var this_row = $(this).closest('tr');
                	var userid = this_row.children("td").eq(4).text();
                	//alert("Trying to delete user with id="+userid)
                	//var row = $(this).parent().parent().parent().children("td");
                	//var email = row.eq(2).text();
                 	$.get("<c:url value="/admin/deleteUser/"/>" + userid, function(data, status){
                 		if (status == "success" && data == true) {
                 			this_row.remove();
                			//alert("Deleted that user!!!");
                		} else {
                			alert("Tried but failed to delete user.");
                		}
                 		//alert("Callback fn");
                 		
	               	})
                })
                $("#modalUpdateBtn").click(function () {
                	// Send changes to servlet via Ajax, not Http Post
                	// We need to send the changed names, email, and role, 
                	// and pair them with the userid 
                    var fname = $('[name=firstName]').val();
                    var lname = $('[name=lastName]').val();
                    var email = $('[name=email]').val();
                    var uid = $('[name=userid]').val();
                    var roleIndex = $("#newRole").prop("selectedIndex");
                    var role = $("#newRole").val().toUpperCase();
                    $('[name=role]').val(role);

                    var jsonUser = {
                    		firstName: fname,
                    		lastName: lname,
                    		email: email,
                    		userid: uid,
                    		role: role
                    };
                    $.post('<c:url value="/admin/update"/>/jsonUser/', 
                    	jsonUser,
                    	function(data, status) {
                    		if (status == "success" && data == true) {
	                    		// find row with correct userid and update it
	                    		var row = $("#userid_" + uid).children("td");
	                            row.eq(0).text(fname);
	                            row.eq(1).text(lname);
	                            row.eq(2).text(email);
	                            row.eq(3).text(role);
	                            row.eq(4).text(uid);
                    		}
                    });
                })
            })
           </script>
<!--           
//               	$.get("/cs6320/admin/deleteUser", {email: email}), function(data, status){
//                		if (status == "success" && data == "true") {
//                			alert("Deleted that user!!!");
//                			$(this).closest('tr').remove();
//                		} else {
//                			alert("Tried but failed to delete user.");
//                		}
-->
                
    </head>
    <body>
        <%@include file="/resources/jsp/navbar.jsp" %>
        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Welcome to the Admin's Landing Page!</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-2"><label>First Name:</label></div>
                        <div class="col-md-10">${user.firstName}</div>
                    </div>
                    <div class="row">
                        <div class="col-md-2"><label>Last Name:</label></div>
                        <div class="col-md-10">${user.lastName}</div>
                    </div>
                    <div class="row">
                        <div class="col-md-2"><label>Email:</label></div>
                        <div class="col-md-10">${user.email}</div>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Users List</h3>
                </div>
                <div class="panel-body">
                    <table class="table table-condensed">
                        <thead>
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Userid</th>
                                <th>Action(s)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr id="userid_${user.userid}">
                                    <td class="firstname">${user.firstName}</td>
                                    <td class="lastname">${user.lastName}</td>
                                    <td class="email">${user.email}</td>
                                    <td class="role">${user.role}</td>
                                    <td class="userid">${user.userid}</td>
                                    <td>
                                        <form class="form-horizontal" method="post">
                                            <!-- <input type="hidden" name="email" value="${user.email}"> -->
                                            <!-- Trigger the modal with a button -->
                                            <button type="button" class="btn btn-default update_btn" 
                                                    data-toggle="modal" data-target="#myModal">
                                                Update
                                            </button>
                                            <button id="delete_user_${user.userid}" type="button" class="btn btn-default">
                                                Delete
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Update User</h4>
                    </div>
                    <%-- <form action="<c:url value="/admin/update"/>" class="form-horizontal" method="post"> --%>
                    <div class="modal-body">
                    	<form class="form-horizontal">
                            <!--<input type="hidden" name="actionType" value="update"/>-->
                            <input type="hidden" name="userid" value=""/>
                            <input type="hidden" name="role" value=""/>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="firstName">First name</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="text" name="firstName" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="lastName">Last name</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="text" name="lastName" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="email">Email</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="email" name="email"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="role">Role</label>
                                <div class="col-md-9">
                                    <select class="form-control" id="newRole">
                                        <option value="customer">Customer</option>
                                        <option value="seller">Seller</option> 
                                        <option value="admin">Admin</option> 
                                    </select>
                                </div>
                            </div>
                    	</form>
                    </div>
                    <div class="modal-footer">
                        <button id="modalUpdateBtn" class="btn btn-default" data-dismiss="modal">
                            Update
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            Close
                        </button>
                    </div>
                </div>

            </div>
        </div>
        <%@ include file="/resources/jsp/footer.jsp" %>

    </body>
</html>
