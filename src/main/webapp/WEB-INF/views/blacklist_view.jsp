<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<title>BlaSec</title>
	<%@ page isELIgnored="false" %>
</head>
<body>
	<div align="center">
		<br>
		<h2 style="color: #cc0000;">Negative Database - BlackList View</h2>
		<hr>
		<form action="blacklistsave" method="post" modelAttribute="blacklist">
			<table border="0" cellpadding="5">
				<tr>
					<td align="center"><h6>IP</h6></td>
					<td align="center"><h6>Card Holder's <br> Name</h6></td>
					<td align="center"><h6>Card Holder's <br> Address</h6></td>
					<td align="center"><h6>Card Number</h6></td>
				</tr>
				<tr>
					<td>
						<textarea rows="10" cols="20" name="ip" id="ip" style="border:solid 1px #cc0000;">${blacklist.ip}</textarea>
					</td>
					<td>
						<textarea rows="10" cols="20" name="cardholdername" id="cardholdername" style="border:solid 1px #cc0000;">${blacklist.cardholdername}</textarea>
					</td>
					<td>
						<textarea rows="10" cols="20" name="cardholderaddress" id="cardholderaddress" style="border:solid 1px #cc0000;">${blacklist.cardholderaddress}</textarea>
					</td>
					<td>
						<textarea rows="10" cols="20" name="cardnumber" id="cardnumber" style="border:solid 1px #cc0000;">${blacklist.cardnumber}</textarea>
					</td>
				</tr>									
			</table>
			<br>
			<input type="submit" value="Save" class="btn btn-danger btn-block col-md-6">
		</form>
	</div>
</body>
</html>