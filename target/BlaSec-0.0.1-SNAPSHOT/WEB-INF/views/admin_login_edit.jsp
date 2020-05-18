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
		<h2 style="color: #cc0000;">Edit Login Details</h2>
		<hr>
		<form:form action="save" method="post" modelAttribute="logindetails">
			<table border="0" cellpadding="5">
				<tr>
					<td>ID: </td>
					<td>${logindetails.id}
						<form:hidden path="id"/>
					</td>
				</tr>			
				<tr>
					<td>User Name: </td>
					<td><form:input path="username" /></td>
				</tr>
				<tr>
					<td>User Password: </td>
					<td><form:input path="userpassword" /></td>
				</tr>
				<tr>
					<td>Security Question: </td>
					<td><form:input path="securityquestion" /></td>
				</tr>	
				<tr>
					<td>Security Answer: </td>
					<td><form:input path="securityanswer" /></td>
				</tr>
				<tr>
					<td>Open Status: </td>
					<td><form:input path="openstatus" /></td>
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