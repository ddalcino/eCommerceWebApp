<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/resources/jsp/header.jsp"%>
<script type='text/javascript'>
$(document).ready(function () {
	$('.update_btn').click(function () {
		var row = $(this).parent().parent().children("td")
		var saleItemOfferId = row.eq(0).text();
		var price = row.eq(2).find("input").val();
		var qty = row.eq(3).find("input").val();
		//alert("Attempt to set price to " + price + ", qty to " + qty + 
		//		", for saleItemOffer=" + saleItemOfferId);
		$.ajax({
		    type: "POST",
		    url: '<c:url value="/sell/updateListing/"/>',
		    dataType: "json",
		    data: {
		    	sellItemOfferId: saleItemOfferId, 
		    	newPrice: price, 
		    	newQty: qty
		    },
		    success: function(data, status){
		    	// stop old animations
		    	$("#sellStatusMsg").stop().hide();
				$("#sellErrorMsg").stop().hide();
				
				if (status == "success" && data == true){
		    		$("#sellStatusMsg")
		    			.text("Offer updated successfully!")
		    			.slideDown().delay(5000).slideUp();
		    	} else {
		    		$("#sellErrorMsg")
	    				.text("Failed to update offer!")
	    				.slideDown().delay(5000).slideUp();
		    	}
		    }
		});
	})
})
</script>
</head>
<body>
	<%@include file="/resources/jsp/navbar.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h2>Seller Inventory:</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<h2>Seller Inventory:</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-condensed">
					<thead>
						<tr>
							<th class="col-md-2">Image</th>
							<th class="col-md-4">Title</th>
							<th class="col-md-2">Price</th>
							<th class="col-md-2">Quantity Available</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="offer" items="${sellItemOffers}">
							<tr>
								<td>
									<a href="<c:url value="/item/"/>${offer.saleItem.id}"> <img
										src="/cs6320/images/?filename=${offer.saleItem.imgPath}"
    									alt="image representing a(n) ${offer.saleItem.title}"
										class="img-responsive" />
									</a>
									<!-- ${offer.id}  -->
								</td>
								<td><a href="<c:url value="/item/"/>${offer.saleItem.id}">
										${offer.saleItem.title} </a></td>
								<td>
									<div class="input-group">
										<span class="input-group-addon">$</span> 
										<input type="number"
											min="0" step="0.01" data-number-to-fixed="2"
											data-number-stepfactor="100" class="form-control currency"
											value="${offer.price}" name="newPrice" />
									</div>
								</td>
								<td> 
									<input type="number" class="form-control bfh-number" min="0" value="${offer.quantityAvailable}"> 
								</td>
								<td>
									<button type="button" class="btn btn-default update_btn">
										Update Offer
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<!-- Trigger the modal with a button -->
		<div class="row">
			<div class="col-md-12" style="text-align: right">
				<button type="button" class="btn btn-default sell_new_btn"
					data-toggle="modal" data-target="#sellNewModal">Sell a new item
				</button>
			</div>
		</div>
		<div class="row">
			<div class="alert alert-success" id="sellStatusMsg" hidden="true"></div>
			<div class="alert alert-warning" id="sellErrorMsg" hidden="true"></div>
		</div>
	</div>
	<!-- Modal -->
	<div id="sellNewModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Sell a new item!</h4>
				</div>
				<div class="modal-body">

					<form action="<c:url value="/sell/newItem"/>"
						class="form-horizontal" method="post"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="control-label col-md-2" for="title">Title</label>
							<div class="col-md-10">
								<input class="form-control" type="text" maxlength="45"
									name="title" required />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2" for="description">Description</label>
							<div class="col-md-10">
								<textarea class="form-control" name="description" rows="10"
									cols="30" maxlength="500"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2" for="imageFile">Image</label>
							<div class="col-md-10">
								<label class="btn btn-default btn-file">
									<input id="imageInput" type="file" name="imageFile"
										accept="image/*;capture=camera" style="display:none;">
									Choose file
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2" for="price">Price</label>
							<div class="col-md-10">
								<div class="input-group">
									<span class="input-group-addon">$</span> <input type="number"
										min="0" step="0.01" data-number-to-fixed="2"
										data-number-stepfactor="100" class="form-control currency"
										name="price" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2" for="qty">Quantity
								Available</label>
							<div class="col-md-10">
								<input class="form-control" type="number" name="qty" value="1"
									required />
							</div>
						</div>
						<div class="row">
							<div class="col-md-12" style="text-align: right">
								<button class="btn btn-default" type="submit">Put it up
									for sale!</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="/resources/jsp/footer.jsp"%>
</body>
</html>