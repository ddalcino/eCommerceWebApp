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
					<h2>Hey, check out the awesome things we have to sell you!</h2>
				</div>
			</div>
			<div class="row">
				<div>
					<table class="table table-condensed">
						<thead>
							<tr>
								<th class="col-md-4">Image</th>
								<th class="col-md-6">Title</th>
								<th class="col-md-2">Price</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${saleItems}">
								<tr>
								<td>
									<a href="<c:url value="/item/"/>${item.id}">
										<img src="/cs6320/images/?filename=${item.imgPath}"
											class="img-responsive"/>
									</a>
								</td>
								<td>
									<a href="<c:url value="/item/"/>${item.id}">
										${item.title}
									</a>
								</td>
								<td>
									<!-- space for lowest price here --></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<%@ include file="/resources/jsp/footer.jsp" %>
</body>
</html>