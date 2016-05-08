<?php
	session_start();
	include 'cleanInput.php';
	
	if(@$_REQUEST["submit"] == "Login")
	{
		$usernameInput = test_input($_REQUEST["username"]);
		$passwordInput = test_input($_REQUEST["password"]);
		
		if($usernameInput == null)
		{
			print "Username is empty.\n";
		}
		if($passwordInput == null)
		{
			print "Password is empty.\n";
		}

	}
?>

<!doctype html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="loginForm.css">
	</head>
	
	<body>
		<img src="../img/loginBackground.png">
		<form id="loginForm" action = "loginScreen.php" method="post">
			Username: <input type="text" name="username"></input><br>
			Password: <input type="password" name="password"></input><br>
			<input type="submit" name="submit" value="Login"></input>
		</form>
	</body>
</html>