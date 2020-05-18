<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<title>BlaSec</title>
	<%@ page isELIgnored="false" %>
	<link href="resources/css/table.css" rel="stylesheet" media="screen">
</head>
<body>
	<div align="center">
		<br>
		<h2 style="color: #cc0000;">Search Result</h2>
		<hr>
		<br>
		<h3>Login Details</h3>
		<table border="1" cellpadding="5" id="index">
			<tr>
				<th>ID</th>
				<th>User Name</th>
				<th>User Password</th>
				<th>Security Question</th>
				<th>Security Answer</th>
				<th>Open Status</th>
			</tr>
			<c:forEach items="${result}" var="loginDetails">
			<tr>
				<td>${loginDetails.id}</td>
				<td>${loginDetails.username}</td>
				<td>${loginDetails.userpassword}</td>
				<td>${loginDetails.securityquestion}</td>
				<td>${loginDetails.securityanswer}</td>
				<td>${loginDetails.openstatus}</td>
			</tr>
			</c:forEach>
		</table>
		<br>
		<h3>Customer Card Details</h3>
			<table border="1" cellpadding="5" id="index">
			<tr>
				<th>ID</th>
				<th>User Name</th>
				<th>Name on the card</th>
				<th>Card Number</th>
				<th>CVV</th>
				<th>Expiry Date</th>
				<th>Billing Address</th>
			</tr>
			<c:forEach items="${resultCCardDetails}" var="customercarddetails">
			<tr>
				<td>${customercarddetails.id}</td>
				<td>${customercarddetails.username}</td>
				<td>${customercarddetails.nameonthecard}</td>
				<td>${customercarddetails.cardnumber}</td>
				<td>${customercarddetails.cvv}</td>
				<td>${customercarddetails.expirydate}</td>
				<td>${customercarddetails.billingadrs}</td>
			</tr>
			</c:forEach>
		</table>
		<br>
		<h3>Customer Transaction History</h3>
			<table border="1" cellpadding="5" id="index">
			<tr>
				<th>ID</th>
				<th>User Name</th>
				<th>Transaction Amount</th>
				<th>Continent</th>
				<th>Country</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Time Zone</th>
				<th>Transaction Date</th>
				<th>Remarks</th>
			</tr>
			<c:forEach items="${resultUserTxnHistory}" var="usertxnhistory">
			<tr>
				<td>${usertxnhistory.id}</td>
				<td>${usertxnhistory.username}</td>
				<td>${usertxnhistory.txnamount}</td>
				<td>${usertxnhistory.txncontinent}</td>
				<td>${usertxnhistory.txncountry}</td>
				<td>${usertxnhistory.txnlatitude}</td>
				<td>${usertxnhistory.txnlongitude}</td>
				<td>${usertxnhistory.txntimezone}</td>
				<td>${usertxnhistory.txndate}</td>
				<td>${usertxnhistory.remarks}</td>
			</tr>
			</c:forEach>
		</table>
	</div>	
</body>
</html>