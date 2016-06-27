<?php
	function debugToConsole($data) 
	{
		if(is_array($data) || is_object($data))
		{
			echo("<script>console.log('PHP: ".json_encode($data)."');</script>");
		} else {
			echo("<script>console.log('PHP: ".$data."');</script>");
		}
	}
	
	function fetchInformation() 
	{
		$servername = "95.93.234.88";
		$username = "a2277994_admin";
		$password = "07700g8O";
		$dbname = "a2277994_twam";
		
		echo("hai");
		
		$conn = @mysql_connect($servername, $username, $password)or die(mysql_error());
		$db = @mysql_select_db($dbname,$conn)or die(mysql_error());  
		
		$sql = "SELECT ID, Test1, Test2 FROM a2277994_twam";
		$result = $conn->query($sql);
		
		if($result->num_rows <= 0)
		{
			die("No records found");
		}
		else
		{
			while($row = $result->fetch_assoc())
			{
				$data = "<a href=edit.php?id=". $row["ID"] . ">" . $row["ID"] . "-> Name: " . $row["Test1"] . " &lt;" . $row["Test2"] . "&gt;";
				echo("printing to console");
				debugToConsole($data);
			}
		}
		return false;
	}
?>