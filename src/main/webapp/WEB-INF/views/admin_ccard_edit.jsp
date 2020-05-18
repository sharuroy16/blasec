<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<title>BlaSec</title>
	<%@ page isELIgnored="false" %>
</head>
<body>
	<div align="center">
		<br>
		<h2 style="color: #cc0000;">Edit Customer Card Details</h2>
		<hr>
		<form:form action="saveCCard" method="post" modelAttribute="customercarddetails">
			<table border="0" cellpadding="5">
				<tr>
					<td>ID: </td>
					<td>${customercarddetails.id}
						<form:hidden path="id"/>
					</td>
				</tr>			
				<tr>
					<td>User Name: </td>
					<td><form:input path="username" /></td>
				</tr>
				<tr>
					<td>Name on the card: </td>
					<td><form:input path="nameonthecard" /></td>
				</tr>
				<tr>
					<td>Card Number: </td>
					<td><form:input path="cardnumber" /></td>
				</tr>	
				<tr>
					<td>CVV: </td>
					<td><form:input path="cvv" /></td>
				</tr>
				<tr>
					<td>Expiry Date: </td>
					<td><form:input path="expirydate" /></td>
				</tr>	
				<tr>
					<td>Billing Address: </td>
					<td><form:input path="billingadrs" /></td>
				</tr>
				<tr>
					<td> <br> </td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Save" class="btn btn-danger btn-block"></td>
				</tr>						
			</table>
		</form:form>
	</div>
</body>
</html>