<?php
	$service="http://95.93.234.88:8000/android_login/userdb.php";
	$conn = new mysqli("localhost", "root", "", "twam");

	//$path_params = explode('/', trim($_SERVER['PATH_INFO']));
	$input = json_decode(file_get_contents('php://input'),true);// input data in json
	$input2 = file_get_contents('php://input');	
	//$input=$_REQUEST;	// input data from form
		
	
	
	
    
	$response = array("error" => FALSE);
	$response["error"] = FALSE;
	
	
	switch($_SERVER['REQUEST_METHOD'] ) {

		case 'GET':
			/*if(isset($path_params[1])){
				if($path_params[1] == "users"){
					$sql="select * from user";
					
					if (isset($path_params[2]) && is_numeric($path_params[2])){  // id
						$sql.= " where id=" . $path_params[2];
					} 
				} 	
			} */
			
			
			$ip2 = $_GET['IP'];
			
			
			$result = $conn->query("SELECT IP from tracedIPs WHERE IP = '$ip2'");
			$num_rows = $result->num_rows;
			if ($num_rows > 0) {
			
			$sql="Select coordX from tracedIPs WHERE  IP = '$ip2' ";
			$result=$conn->query($sql);
			$getcoordX = $result->fetch_assoc();
			
			$sql="Select coordY from tracedIPs WHERE  IP = '$ip2' ";
			$result=$conn->query($sql);
			$getcoordY = $result->fetch_assoc();
			
			
			
			$response["error"] = FALSE;
			$response["coordX"] = $getcoordX;
			$response["coordY"] = $getcoordY;
			
			
			echo json_encode($response);
			}
			else{
				
				$response["error"] = TRUE;
			$response["error_msg"] = "That IP has not been traced yet!";
			echo json_encode($response);
			}
			/*$result=$conn->query($sql);
			while($row = $result->fetch_assoc()){
				$list[]=$row;
			}
			print_r(json_encode($list)); //json
			//print "<pre>"; print_r($list); print "</pre>"; // array
			*/
			
			
			break;
			
		case 'POST':
		
			$ip2 = $_POST['IP'];

			$coordX2 = $_POST['coordX'];
			$coordY2 = $_POST['coordY'];
			
			
			$result = $conn->query("SELECT IP from tracedIPs WHERE IP = '$ip2'");
			$num_rows = $result->num_rows;
			if ($num_rows > 0) {
			$response["error"] = TRUE;
			$response["error_msg"] = "That IP has already been traced!";
			echo json_encode($response);
			}else
			{
					//Testing if coords already exist
					$sql="SELECT * from tracedIPs WHERE coordX = '$coordX2' AND coordY = '$coordY2'";
					$result=$conn->query($sql);
					
					if ($result->num_rows > 0) {
					// Coords already exist 
					
					$response["error"] = TRUE;
					$response["error_msg"] = "Your computer network failed, please try again!";
					echo json_encode($response);
					} else {
					// Its all good
					$response["error"] = FALSE;
					$sql="insert into tracedIPs (IP, coordX, coordY) values ('$ip2', '$coordX2', '$coordY2')  ";
					$result=$conn->query($sql);
					
					echo json_encode($response);
					}
			}
			
			
			
			break;
		case 'PUT':	
			/*echo 'PUT metodo';
			print "$ip<br>\n";
			if (isset($path_params[2]) && is_numeric($path_params[2])){  // id
				$sql="update user set Ip='$ip', where id=" . $path_params[2];
				$result=$conn->query($sql);
				print "rows changed:" . $conn->affected_rows;	
			
			} else {
				print "error no id";
			} */
			
			function multiexplode ($delimiters,$string) {
    
				$ready = str_replace($delimiters, $delimiters[0], $string);
				$launch = explode($delimiters[0], $ready);
				return  $launch;
			}
			$text2 = "here is a sample: this text, and this will be exploded. this also | this one too :)";
			$text = "{IP=342.33.44.23, IPAlias=banana}";
			$exploded = multiexplode(array("=","&","=","&"),$input2);
			
			$ip = $exploded["1"];
			$alias = $exploded["3"];
			
			
			
			
			$result = $conn->query("SELECT * from tracedIPs WHERE IP = '$ip' ");
			$num_rows = $result->num_rows;
			if ($num_rows > 0) {
			$response["error"] = FALSE;
			$sql="UPDATE tracedIPs SET IPAlias='$alias' WHERE IP = '$ip'  ";
			$result=$conn->query($sql);
					
			echo json_encode($response);
			}else{
				$response["error"] = TRUE;
				$response["error_msg"] = "That IP is invalid!";
				echo json_encode($response);
				
			}
			

			break;
		case 'DELETE':	
			/*echo 'DELETE metodo';
			if (isset($path_params[2]) && is_numeric($path_params[2])){  // id
				$sql="delete from user where id=" . $path_params[2];
				$result=$conn->query($sql);
				print "rows changed:" . $conn->affected_rows;	
			
			} else {
				print "error no id";
			} 
			*/
			$ip2 = $_GET['IP'];
						
			$result = $conn->query("SELECT IP from tracedIPs WHERE IP = '$ip2'");
			$num_rows = $result->num_rows;
			if ($num_rows > 0) {
				$sql="DELETE from tracedIPs WHERE IP = '$ip2'";
				$result=$conn->query($sql);
				$response["error"] = FALSE;
				echo json_encode($response);
			
			}else
			{			
				$response["error"] = TRUE;
				$response["error_msg"] = "That IP doesn't exist...";
				echo json_encode($response);
			}
			
			
			break;
			
			
		default: 
			echo "user service";
	}
	
	// GET
	// Retrieve all users with a GET from URI http://localhost/lab08/userdb.php/users
	// Retrieve one user with a GET from URI http://localhost/lab08/userdb.php/users/1
	
	// POST INSERT http://localhost/lab08/userdb.php/users ou sem users
	
	// put UPDATE URI http://localhost/lab08/userdb.php/users/1
	
	// DELETE delete URI http://localhost/lab08/userdb.php/users/1


?>

	