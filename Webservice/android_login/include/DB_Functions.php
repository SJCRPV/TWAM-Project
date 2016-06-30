<?php
 

class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($username, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM logins WHERE Username = ?");
 
        $stmt->bind_param("s", $username);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			
			$encrypted_password = $user['Password'];
            if ($password == $encrypted_password) {
                // user authentication details are correct
                return $user;
            }
                
            
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT Username from logins WHERE Username = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user exists 
            $stmt->close();
            return true;
        } else {
            // user no bueno
            $stmt->close();
            return false;
        }
    }
 
    
    
 
}
 
?>