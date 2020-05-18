<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<title>BlaSec</title>
	<link href="resources/css/login.css" rel="stylesheet" media="screen">
	<%@ page isELIgnored="false" %>
</head>
<body>
	<div id="login">
        <div class="container">
	        <h3 class="text-center text-white pt-5"><img alt="BlaSec Logo" src="resources/images/logo.png" width="500"></h3>
	            <div id="login-row" class="row justify-content-center align-items-center">
	                 <div id="login-column" class="col-md-6">
	                 ${message}
	                    <div id="login-box" class="col-md-12"> 
	                        <br></br>
	                        <form method="post" action="login" autocomplete="off">
	                            <div class="form-group">
	                                <input type="text" name="username" id="username" class="form-control" placeholder="Username" required="required">
	                            </div>
	                            <div class="form-group">
	                                <input type="password" name="userpassword" id="userpassword" class="form-control" placeholder="Password" required="required">
	                            </div>
	                            <div class="form-group center">
	                                <input type="submit" name="submit" class="btn btn-danger btn-block" value="Login">
	                            </div>
	                        </form>
	                    </div>
	                </div>
	            </div>
	     </div>
    </div>
</body>
</html>