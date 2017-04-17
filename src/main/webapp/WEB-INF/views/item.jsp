<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@ include file="/resources/jsp/header.jsp" %>
        <script>
        	$(document).ready(function() {
        		<%-- TODO: the addItem function is messy! This should be calling the same 
        			 code that is called when the cart is initially generated, not 
        			 building new items in parallel. This implementation makes the cart 
        			 difficult to maintain. This was a quick-and-dirty solution, and it 
        			 happened because the cart is initially built in jsp, but additions
        			 need to happen in javascript on the client side.  --%>
        		$("button.addItem").click(function () {
        			var row = $(this).parent().children();
        			var sellItemOfferId = row.eq(0).val();
        			var qty = row.eq(1).val();
        			
            		$.ajax({
            		    type: "POST",
            		    url: '<c:url value="/cart/addItem/"/>',
            		    dataType: "json",
            		    data: {saleItemOfferID: sellItemOfferId, quantity: qty},
            		})
            		.done(function(data) { 
            			stopAndHideCartMsgAnimations();
            			
            			// add a new row to the dropdown cart
            			var table = $("#embedCartTable")
            			table.append('<tr>' + 
                    			

            					'<td> <figure> <a href="<c:url value="/item/"/>${item.offer.saleItem.id}">'+
            					'<img src="/cs6320/images/?filename='+
            							data.offer.saleItem.imgPath + '" alt="image representing a(n) ' +
            							data.offer.saleItem.title + '" class="img-responsive"/> </a>' +
            					'<figcaption> <a href="<c:url value="/item/"/>' + data.offer.saleItem.id + '">' +
            						data.offer.saleItem.title +
            					'</a></figure></figcaption></td><td class="seller">' + 
            					data.offer.seller.firstName + ' ' + data.offer.seller.lastName + 
         	   					'</td><td class="price">' + data.offer.price + 
         	   					'</td><td>' +
         	   					'<input type="number" name="quantity" class="form-control bfh-number" min="0" max="' + 
         	   					data.offer.quantityAvailable + '" value="' + data.quantity + '">' + 
         	   					'<input type="hidden" name="originalQty" value="' + data.quantity + '">'+
        						'<input type="hidden" name="cartItemId" value="' + data.id + '">' +
        						'<button type="button" class="btn btn-default delete_cart_item">' +
                        			'Delete</button>' +
                            	'<button type="button" class="btn btn-default update_qty">' +
                            		'Change Quantity' +
                            	'</button>' + 
                            	'</td></tr>');
            			
            			//now increment cart size
            			var oldSize = parseInt($("#cartSize").text());
        		    	$("#cartSize").text(oldSize + 1);
            			
            			//TODO: wire up the buttons
            			var newRow = $('#embedCartTable tr:last').children();
        		    	var btnDelete = newRow.eq(3).children().eq(3);
        		    	var btnChangeQty = newRow.eq(3).children().eq(4);
        		    	btnDelete.click(function() {
        		    		deleteCartItem($(this));
        		    	});
        		    	btnChangeQty.click(function(){
        		    		changeQtyCartItem($(this));
        		    	})
             			
            			// attempt to open the dropdown cart
           	    		$(".dropdown-toggle").dropdown('toggle');
           		    	$("#cartStatusMsg")
           		    		.text("Added item successfully!")
           		    		.slideDown().delay(5000).slideUp();
            		})
            	    .fail(function() { 
            	    	$("#cartErrorMsg")
           		    		.text("Failed to add item!")
           		    		.slideDown().delay(5000).slideUp();
            	    });
            	    //.always(function() { alert("complete"); });
        		});
        	});
	    	//stopAndHideCartMsgAnimations();
	    	//if (status == "success" && data == true){
	    	//	// attempt to open the dropdown cart
	    	//	$(".dropdown-toggle").dropdown('toggle');
	    	//	//row.eq(1).val(qty);	// remember verified qty
		    //	$("#cartStatusMsg")
		    //		.text("Added item successfully!")
		    //		.slideDown().delay(5000).slideUp();
	    	//} else {
		    	//	//row.eq(0).val(originalQty); // change it back!
		    //	$("#cartErrorMsg")
		    //		.text("Failed to add item!")
		    //		.slideDown().delay(5000).slideUp();
	    	//}
        </script>
	</head>
<body>
    <%@include file="/resources/jsp/navbar.jsp" %>
    <div class="container">
    	<div class="row">
    		<div class="col-md-4">
    			<img src="/cs6320/images/?filename=${saleItem.imgPath}" 
    				alt="image representing a(n) ${saleItem.title}"
    				class="img-responsive"/>
    		</div>
    		<div class="col-md-8">
    			<h2>${saleItem.title}</h2>
    			<h3>Rating: ${rating}</h3>
    			<h3>Lowest Price: ${lowestPrice}</h3>
    			<p> Description: ${saleItem.description} </p>
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-md-12">
    			<h3>${fn:length(offers)} Offers Available:</h3>
	             <table class="table table-condensed">
	                <thead>
	                    <tr>
	                        <th>Seller ID</th>
	                        <th>Seller Avg. Rating</th>
	                        <th>Price</th>
	                        <th>Qty available</th>
	                        <th>Add To Cart</th>
	                        <th></th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach var="offer" items="${offers}">
	                        <tr id="itemid_${offer.id}">
	                            <td class="seller">
	                            	${offer.seller.firstName} 
	                            	${offer.seller.lastName}
                            	</td>
                            	<td>unimpl</td>
	                            <td class="price">${offer.price}</td>
	                            <td>${offer.quantityAvailable}</td>
	                            <td>
	                            	<input type="hidden" name="offerid" value="${offer.id}">
	                            	<input type="number" class="form-control bfh-number"
	                            		name="quantity" size="3"
 										min="1" max="${offer.quantityAvailable}"
 										value="1">
	                            	<button type="button" class="btn btn-default addItem">
	                            		Add To Cart
	                            	</button>
                            	</td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	             <%-- The real table: with predigested data
	                <thead>
	                    <tr>
	                        <th>Seller</th>
	                        <th>Seller Rating</th>
	                        <th>Price</th>
	                        <th>Add To Cart</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach var="offer" items="${saleOfferViews}">
	                        <tr id="itemid_${offer.id}">
	                            <td class="seller">
	                            	${offer.user.firstName} ${offer.user.lastName}
                            	</td>
	                            <td class="rating">${offer.rating}</td>
	                            <td class="price">${offer.price}</td>
	                            <td>
	                            	<button type="button" class="btn btn-default">
	                            		Add To Cart
	                            	</button>
                            	</td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            --%>  		
	            </table> 
    		</div>
    	</div>
    </div>
    <%@ include file="/resources/jsp/footer.jsp" %>
</body>
</html>