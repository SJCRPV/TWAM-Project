<?php
class ApplicationMap
{	
	public static function call($method, $args)
	{
		// if ( $method === 'MyServerMethod' ) {
		  // return Array(
			// "foo" => "bar"
		  // );
		// } else {
		  // throw new Exception("This is how you send an error");
		// }
		
		if($method === 'getLocations')
		{
			$servername = "localhost";
			$username = "root";
			$password = "";
			$dbname = "twam";
			
			$conn = new mysqli($servername, $username, $password, $dbname);
			
			if($conn->connect_error)
			{
				die("Connection failed: " . $conn->connect_error);
			}
			
			$sql = "SELECT ID, IP, coordX, coordY FROM tracedips";
			//$sql = "SELECT ID, Username, Password FROM logins";
			$result = $conn->query($sql);
			
			$coordArray = array();
			if($result->num_rows <= 0)
			{
				die("No records found");
			}
			else
			{
				while($row = $result->fetch_assoc())
				{
					// $tempStr = "ID: " . $row["ID"] . " | IP: " . $row["IP"] . " | coordX: " . $row["coordX"] . " | coordY: " . $row["coordY"] . "\n";
					array_push($coordArray, $row["coordX"] . " " . $row["coordY"]);
				}
			}
			mysqli_close($conn);
			return $coordArray;
		}
		return false;
	}
}
?>