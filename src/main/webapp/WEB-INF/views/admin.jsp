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
            function dontSend() {
                return false;
            }
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
                    $('[name=firstname]').val(fname);
                    $('[name=lastname]').val(lname);
                    $('[name=email]').val(email);
                    $('[name=email1]').val(email);
                    $('[name=userid]').val(userid);
                    if (role === "CUSTOMER") {
                        $("#newRole option").eq(0).prop('selected', true);
                    } else if (role === "SELLER") {
                        $("#newRole option").eq(1).prop('selected', true);
                    } else if (role === "ADMIN") {
                        $("#newRole option").eq(2).prop('selected', true);
                    }
                });
                $("#action_button").click(function () {
                	$.get("<c:url value="/admin/user"/>", function(data, status){
                		var stringRep = ""
                		for (key in data) {
                			stringRep += "data[\"" + key + "\"] = " + data[key] + "\n"
                		}
                		alert("Data: " + stringRep +
                				"\nData members: " + data.firstName + " " + data.lastName +
                				"\nStatus: " + status
                				);
                		$("#stuff").text("stuff!" + data.firstName);
                	})
                })
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
                                <tr>
                                    <td class="firstname">${user.firstName}</td>
                                    <td class="lastname">${user.lastName}</td>
                                    <td class="email">${user.email}</td>
                                    <td class="role">${user.role}</td>
                                    <td class="userid">${user.userid}</td>
                                    <td>
                                        <form action="admin" class="form-horizontal" method="post">
                                            <input type="hidden" name="email" value="${user.email}">
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
            <button type="button" id="action_button" class="btn btn-default"> Ajax </button>
            <span id="stuff"></span>
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
                    <form action="<c:url value="/admin/update"/>" class="form-horizontal" method="post">
                        <div class="modal-body">
                            <!--<input type="hidden" name="actionType" value="update"/>-->
                            <input type="hidden" name="email" value=""/>
                            <input type="hidden" name="userid" value=""/>
                            <input type="hidden" name="role" value=""/>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="firstname">First name</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="text" name="firstname" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="lastname">Last name</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="text" name="lastname" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" for="email1">Email</label>
                                <div class="col-md-9">
                                    <input class="form-control" type="email" name="email1"/>
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
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-default" type="submit" onclick="setRole();" value="Register">
                                Update
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="dontSend();">
                                Close
                            </button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
        <%@ include file="/resources/jsp/footer.jsp" %>

    </body>
</html>
