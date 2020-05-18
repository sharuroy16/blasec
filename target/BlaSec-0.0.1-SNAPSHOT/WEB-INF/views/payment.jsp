<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<title>BlaSec</title>
</head>
<body>
	 <div class="col-md-6 offset-md-3">
          <span class="anchor" id="formPayment"></span>
          <br></br>
		  <div class="card card-outline-secondary">
              <div class="card-body" style="background-color: #fff4b3;">
                   <h3 class="text-center" style="color: #cc0000;">Credit Card Payment</h3>
                   <hr>
                   <form class="form" autocomplete="off" action="validatePayment" method="post" modelAttribute="customercarddetails">
                        <div class="form-group">
                              <label>Card Holder's Name</label>
                              <input type="text" class="form-control" id="nameonthecard" name="nameonthecard" pattern="\w+ \w+.*" title="First and Last Name" required="required">
                        </div>
                        <div class="form-group">
                              <label>Card Number</label>
                              <input type="text" class="form-control" id="cardnumber" name="cardnumber" maxlength="16" pattern="\d{16}" title="Credit/Debit Card Number" required="required">
                        </div>
                        <div class="form-group row">
                              <label class="col-md-12">Card Expiry Date</label>
                              <div class="col-md-4">
                                   <select class="form-control" id="month" name="month" size="0">
                                            <option value="01">01</option>
                                            <option value="02">02</option>
                                            <option value="03">03</option>
                                            <option value="04">04</option>
                                            <option value="05">05</option>
                                            <option value="06">06</option>
                                            <option value="07">07</option>
                                            <option value="08">08</option>
                                            <option value="09">09</option>
                                            <option value="10">10</option>
                                            <option value="11">11</option>
                                            <option value="12">12</option>
                                    </select>
                               </div>
                               <div class="col-md-4">
                                    <select class="form-control" id="year" name="year" size="0">
                                            <option>2020</option>
                                            <option>2021</option>
                                            <option>2022</option>
                                            <option>2023</option>
                                            <option>2024</option>
                                            <option>2025</option>
                                    </select>
                               </div>
                               <div class="col-md-4">
                                    <input type="text" class="form-control" id="cvv" name="cvv" maxlength="3" pattern="\d{3}" title="Three digits at the back of your card" required="required" placeholder="CVV">
                               </div>
                        </div>
                        <div class="row">
                               <label class="col-md-12">Amount</label>
                        </div>
                        <div class="form-group form-inline">
                               <div class="input-group">
                                    <div class="input-group-prepend"><span class="input-group-text">INR</span></div>
                                    <input type="text" class="form-control text-right" id="txnamount" name="txnamount" required="required">
                                    <div class="input-group-append"><span class="input-group-text">.00</span></div>
                               </div>
                         </div>
                         <div class="form-group">
                              <label>Billing Address</label>
                              <input type="text" class="form-control" id="billingadrs" name="billingadrs" required="required">
                         </div>
                         <hr>
                         <div class="form-group row">
                               <div class="col-md-6">
                                    <button type="reset" class="btn btn-default btn-lg btn-block">Reset</button>
                               </div>
                               <div class="col-md-6">
                                     <button type="submit" class="btn btn-danger btn-lg btn-block">Pay</button>
                               </div>
                          </div>
                   </form>
             </div>
         </div>
         <br></br>
	 </div>
</body>
</html>