<?php
class ApplicationIPTracer // As you defined in metadata.json
{
  public static function call($method, $args) {
    if ( $method === 'MyServerMethod' ) {
      return "test";
    } else {
      throw new Exception("This is how you send an error");
    }
    return false;
  }
  
  public static function MyServerMethod($method, $args)
  {
	return Array("foo" => "bar");
  }

}
// class ApplicationIPTracer // As you defined in metadata.json
// {
	// function debugToConsole($data) 
	// {
		// if(is_array($data) || is_object($data))
		// {
			// echo("<script>console.log('PHP: ".json_encode($data)."');</script>");
		// } else {
			// echo("<script>console.log('PHP: ".$data."');</script>");
		// }
	// }
	
	// public static function call($method) 
	// {
		// if ( $method === 'fetchInformation' ) 
		// {
			// $servername = "95.93.234.88";
			// $username = "root";
			// $password = "";
			// $dbname = "twam";
			
			// $conn = new mysqli($servername, $username, $password, $dbname);
			
			// if($conn->connect_error)
			// {
				// die("Connection failed: " . $conn->connect_error);
			// }
			
			// $sql = "SELECT ID, Username, Password FROM logins";
			// $result = $conn->query($sql);
			
			// if($result->num_rows <= 0)
			// {
				// die("No records found");
			// }
			// else
			// {
				// while($row = $result->fetch_assoc())
				// {
					// $data = "<a href=edit.php?id=". $row["ID"] . ">" . $row["ID"] . "-> Name: " . $row["Username"] . " &lt;" . $row["Password"] . "&gt;";
					// debugToConsole($data);
				// }
			// }
		// } 
		// else 
		// {
		  // throw new Exception("I did not find the function.");
		// }
		// return false;
	// }
	
    // return false;
  // }
// }
?>