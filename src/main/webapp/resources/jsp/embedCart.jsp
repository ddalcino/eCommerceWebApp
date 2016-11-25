<%-- 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/resources/jsp/header.jsp" %>--%>
    <script type="text/javascript">
	    $(document).ready(function () {
	   		$("button.delete_cart_item").click(function() {
	   			deleteCartItem($(this));
	   		});
	   		
			$("button.update_qty").click(function() {
				changeQtyCartItem($(this));
			});
			
		});
		function stopAndHideCartMsgAnimations() {
			$("#cartStatusMsg").stop().hide();
			$("#cartErrorMsg").stop().hide();
		}
		function deleteCartItem(buttonReference) {
   			var row = buttonReference.parent().children();
			var qty = 0 //row.eq(0).val();
			//var originalQty = row.eq(1).val();
			var cartItemId = row.eq(2).val();
			//alert("New qty=" + qty + "\ncartItemId=" + cartItemId);
			
    		$.ajax({
    		    type: "POST",
    		    url: '<c:url value="/cart/changeQuantity/"/>',
    		    dataType: "json",
    		    data: {cartItemID: cartItemId, quantity: qty},
    		    success: function(data, status){
    		    	stopAndHideCartMsgAnimations()
		    		$(".dropdown-toggle").dropdown('toggle');
    		    	if (status == "success" && data == true){
    		    		// Remove the row from view
   		    			row.parent().parent().hide();
    		    		
    		    		// Decrement cart size
   		    			var oldSize = parseInt($("#cartSize").text());
   		    			$("#cartSize").text(oldSize - 1);
   		    			
    		    		// Notify user that change has occurred
        		    	$("#cartStatusMsg")
        		    		.text("Item removed successfully!")
        		    		.slideDown().delay(5000).slideUp();
    		    	} else {
        		    	$("#cartErrorMsg")
        		    		.text("Failed to remove item!")
        		    		.slideDown().delay(5000).slideUp();
    		    	}
    		    }
    		});
   		}
   		function changeQtyCartItem(buttonReference) {
   			var row = buttonReference.parent().children();
			var qty = row.eq(0).val();
			
			if(qty == 0) { deleteCartItem(buttonReference); return; };
			
			var originalQty = row.eq(1).val();
			var cartItemId = row.eq(2).val();
			
    		$.ajax({
    		    type: "POST",
    		    url: '<c:url value="/cart/changeQuantity/"/>',
    		    dataType: "json",
    		    data: {cartItemID: cartItemId, quantity: qty},
    		    success: function(data, status){
    		    	stopAndHideCartMsgAnimations()
		    		$(".dropdown-toggle").dropdown('toggle');
    		    	if (status == "success" && data == true){
    		    		row.eq(1).val(qty);	// remember verified qty
        		    	$("#cartStatusMsg")
        		    		.text("Quantity changed successfully!")
        		    		.slideDown().delay(5000).slideUp();
    		    	} else {
       		    		row.eq(0).val(originalQty); // change it back!
        		    	$("#cartErrorMsg")
        		    		.text("Failed to change quantity!")
        		    		.slideDown().delay(5000).slideUp();
    		    	}
    		    }
    		});
   		}
	   	
    </script>
<%-- 
</head>
<body>
--%>
<c:if test="${empty sessionScope.cart}">
	<table id="embedCartTable" class="table table-condensed" hidden="true">
</c:if>
<c:if test="${not empty sessionScope.cart}">
	<table id="embedCartTable" class="table table-condensed">
 </c:if>
        <thead>
            <tr>
                <th>Title</th>
                <th>Seller</th>
                <th>Price</th>
                <th>Qty</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${sessionScope.cart}">
                <tr id="itemid_${item.id}">
                	<td>
                		<a href='<c:url value="/item/"/>${item.saleItem.id}'>
                			${item.saleItem.title}
               			</a>
                	</td>
                    <td class="seller">
                    	${item.offer.seller.firstName} 
                    	${item.offer.seller.lastName}
                   	</td>
                    <td class="price">
                    	${item.offer.price}
                   	</td>
                    <td>
                    	<input type="number" name="quantity"
							min="0" max="${item.offer.quantityAvailable}"
							value="${item.quantity}">
						<input type="hidden" name="originalQty" value="${item.quantity}">
						<input type="hidden" name="cartItemId" value="${item.id}">
                    	<button type="button" class="btn btn-default delete_cart_item">
                    		Delete
                    	</button>
                    	<button type="button" class="btn btn-default update_qty">
                    		Change Quantity
                    	</button>
                   	</td>
                </tr>
            </c:forEach>
        </tbody>
	</table>
	<div class="alert alert-success" id="cartStatusMsg" hidden="true"></div>
	<div class="alert alert-warning" id="cartErrorMsg" hidden="true"></div>
<%--
</body>
</html>
--%>