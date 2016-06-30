<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['Username']) && isset($_POST['Password'])) {
 
    // receiving the post params
    $username = $_POST['Username'];
    $password = $_POST['Password'];
 
    
    $user = $db->getUserByEmailAndPassword($username, $password);
 
    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        
        
        $response["user"]["Username"] = $user["Username"];
        
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
?>