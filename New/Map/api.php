<?php
class ApplicationMap
{	
	public static function call($method, $args)
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
		// if ( $method === 'MyServerMethod' ) {
		  // return Array(
			// "foo" => "bar"
		  // );
		// } else {
		  // throw new Exception("This is how you send an error");
		// }

		if($method === 'getDescription')
		{
			//$array=explode(" ", $_POST['$args']);
			$args2 = (string) $args;
			$pieces = explode(" ", $args2);
			$sql = "SELECT IPAlias FROM tracedips where coordX = '$pieces[0]' and coordY = '$pieces[1]'";
			
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
					array_push($coordArray, $row["IPAlias"] . " " . $row["coordX"] . " " . $row["coordY"]);
				}
			}
			mysqli_close($conn);
			return $args2;
		}
		
		if($method === 'getLocations')
		{
			$sql = "SELECT coordX, coordY FROM tracedips";

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