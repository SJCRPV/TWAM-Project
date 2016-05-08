<?php
	$servername = $_SERVER['SERVER_NAME'];
	$username = "root";
	$password = "";
	$dbname = "uplink";
	
	$connection = new mysqli($servername, $username, $password, $dbname);
	
	if($conn->connect_error)
	{
		die("Connection failed: " . $conn->connect_error);
	}
?>