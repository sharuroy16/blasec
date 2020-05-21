# blasec
BlaSec - A mock secured payment gateway to avoid fraudulent transactions

Check out for more information on the project:

https://medium.com/@sharuroy/blasec-part-1-b8d651e881a9


Payment gateways/Payment portals have become essential to the security of businesses that accepts online payments. But online fraud has risen drastically and is wrongful and even, it is a criminal deception which is intended for personal or financial gain. These payments are performed online over the Internet. Debit/Credit card fraud is the most common type of fraud nowadays. It is basically when a fraudster steals card details and buys good online or even transfer money. But many companies offer only basic security measures to reduce such debit/credit card frauds. Hence it is essential to develop a payment gateway or modify an existing one and incorporate more features that offers better security and quality fraud protection.

This project gives an insight on various methods that helps in recognize fraudulent credit card transactions and if a customer exhibits many of these suspicious behaviors, the transaction might potentially be considered fraudulent.

Following concepts are used to check for unusual patterns during online transaction:
	1: Behavior and Location Analysis (BLA)
	2: Address Verification Service
	3: Device Identification
	4: Negative Database Security
	
This is a java-based web application using Spring MVC, Hibernate and Spring Data JPA technologies and framework.
	

Technologies/Tools:
*******************
	1: Eclipse IDE — Oxygen (Release 4.15)
	2: MySQL Server — Version 8.0.20
	3: MySQL Workbench 8.0.20 for GUI
	4: Spring MVC framework 5.1
	5: Hibernate framework 5.4
	6: Java 8 (jdk-13 and jre1.8.0_231)
	7: Apache Tomcat 9
	8: Spring Data JPA 2.1.5 (Java Persistence API)
	9: Java Servlet 3.1 and Java Servlet JSP 2.3.1
	10:Maxmind GeoIP 2.12.0
	
	
Pre-requisites:
***************
	1: Unzip the GeoLite2-City_20200505.tar.gz from the 'Additional' folder (free version).
	2: Correct the NEG_IP_LOC, NEG_CNUM_LOC, NEG_CNAME_LOC, NEG_CADDR_LOC and GEO_LITE_LOC in BlasecConstants file.
	3: Admin Login: admin, admin123.
	4: Correct Database details in persistance.xml file.
	5: In code, validateGeographicLocation() and addUserTxnDetails() in BlasecServiceImpl file, instead of machine IP, mock IP address is hard-coded because 	GeoLite2 contains only limited IP information as its free, else get a paid one from MaxMind.
		Working IPs for testing:
		nameip.put("home", "76.126.242.196");
		nameip.put("google.com", "74.125.224.133");
		nameip.put("etrade.com", "12.153.224.22");
		nameip.put("ebay.com", "66.135.205.13");
		nameip.put("whitehouse.gov", "72.247.136.110");
		nameip.put("bbc.co.uk","212.58.241.131");
		nameip.put("tcs.in", "202.71.129.225");
		
Tables
*******
CREATE DATABASE blasec;

CREATE TABLE logindetails (
  id int NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL unique,
  userpassword varchar(100) NOT NULL,
  securityquestion varchar(100) NOT NULL,
  securityanswer varchar(100) NOT NULL,
  openstatus char(1) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--admin admin123 AES encrypted
insert into logindetails values(1, 'mHAahNKa+FFH8T0CqKtqXA==', '7TcgkClwuFsncpFRTzPh4A==','null','null','Y'); 

CREATE TABLE customercarddetails (
  id int NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  nameonthecard varchar(45) NOT NULL,
  cardnumber long NOT NULL,
  cvv int NOT NULL,
  expirydate varchar(10) NOT NULL,
  billingadrs varchar(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usertxnhistory (
  id int NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  txnamount long NOT NULL,
  txncontinent varchar(45) NOT NULL,
  txncountry varchar(45) NOT NULL,
  txnlatitude double NOT NULL,
  txnlongitude double NOT NULL,
  txntimezone varchar(45) NOT NULL,
  txndate varchar(10) NOT NULL,
  remarks varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
Check out for more information on the project, setup, explanation and tutorials:

https://medium.com/@sharuroy/blasec-part-1-b8d651e881a9
	
Reach out to me at:
Linkedln: https://www.linkedin.com/in/sharuroy/
Instagram: https://www.instagram.com/sharru.roy/