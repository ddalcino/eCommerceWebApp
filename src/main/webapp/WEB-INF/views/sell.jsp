<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/resources/jsp/header.jsp" %>
</head>
<body>
	<%@include file="/resources/jsp/navbar.jsp" %>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h2>Sell a new item!</h2>
				<form action="<c:url value="/sell/newItem"/>" class="form-horizontal"
						method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="control-label col-md-2" for="title">Title</label>
						<div class="col-md-10">
							<input class="form-control" type="text" maxlength="45" name="title" required/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2" for="description">Description</label>
						<div class="col-md-10">
							<textarea class="form-control" name="description" rows="10" cols="30" maxlength="500"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2" for="imageFile">Image</label>
						<div class="col-md-10">
							<input id="imageInput" type="file" accept="image/*;capture=camera" name="imageFile">
						</div>
				<!--	<label class="control-label col-md-2" for="filename">Filename</label>
						<div class="col-md-4">
							<input class="form-control" type="text" name="filename" required>
						</div>  -->
					</div>
					<div class="form-group">
						<label class="control-label col-md-2" for="price">Price</label>
						<div class="col-md-10">
							<div class="input-group"> 
        						<span class="input-group-addon">$</span>
        						<input type="number" min="0" step="0.01" 
        							data-number-to-fixed="2" data-number-stepfactor="100" 
        							class="form-control currency" name="price" />
    						</div>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-2" for="qty">Quantity Available</label>
						<div class="col-md-10">
							<input class="form-control" type="number" name="qty" value="1" required/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" style="text-align: right">
							<button class="btn btn-default" type="submit">Put it up for sale!</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<%@ include file="/resources/jsp/footer.jsp" %>
</body>
</html>