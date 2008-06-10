<?php
include_once('../shared/types/Individual.php');
include_once('../shared/types/AbstractEntry.php');

final class SystemUser extends AbstractEntry {
    
    const GET_SYSTEM_USER_QUERY = 
    	"SELECT su.id, su.enabled as isEnabled, su.password as passwordHash, su.userName as username, h.name, h.nameQualification, h.givenNames 
    	 FROM systemuser su, human h 
    	 WHERE su.id = ? AND su.id = h.id";
    
    const GET_SYSTEM_USER_BY_USERNAME_QUERY = 
        "SELECT su.id, su.enabled as isEnabled, su.password as passwordHash, su.userName as username, h.name, h.nameQualification, h.givenNames 
    	 FROM systemuser su, human h 
    	 WHERE su.userName = ? AND su.id = h.id";
    
    const BROWSE_SYSTEM_USERS_QUERY = 
        "SELECT su.id, su.enabled as isEnabled, su.password as passwordHash, su.userName as username, h.name, h.nameQualification, h.givenNames 
    	 FROM systemuser su, human h 
    	 WHERE su.id = h.id 
    	 ORDER BY username";
    
    public $username;
    public $passwordHash;
    public $isEnabled;
    public $individual;
    
    
    
    function __construct($id, $username, $passwordHash, $isEnabled, $individual) {
        parent::__construct($id);
        $this->username = $username;
        $this->passwordHash = $passwordHash;
        $this->isEnabled = $isEnabled;
    }
    
	/**
     * Counts the number of system users in the database.
     * 
     * @param db connection $db
     * @return integer the count of system users
     */
    static function count($db) {
        return AbstractEntry::doCount("systemuser",  $db);
    }
    
    static function get($id, $db) {
        $resultSet = AbstractEntry::doQueryWithIdParameter(SystemUser::GET_SYSTEM_USER_QUERY, $id, $db);
        if (count($resultSet) == 0) {
            return null;
        }
        $systemUser = buildSystemUserFromResultSetRow($resultSet[0]);
        return $systemUser; 
    }
    
    static function getAll($db) {
        $resultSet = AbstractEntry::doSimpleQuery(SystemUser::BROWSE_SYSTEM_USERS_QUERY, $db);
        return SystemUser::buildArrayofSystemUsers($resultSet);
    }
    
    static function getPage($pageNumber, $pageSize, $db) {
        $resultSet = AbstractEntry::doPagingQuery(SystemUser::BROWSE_SYSTEM_USERS_QUERY, $pageNumber, $pageSize, $db);
        return SystemUser::buildArrayofSystemUsers($resultSet);
    }
    
    /**
     * Looks up user by username.
     *
     * @param string $username
     * @param database connection $db
     * @return SystemUser
     */
    static function getByUsername($username, $db) {
        $resultSet = AbstractEntry::doQueryWithSingleParamter(SystemUser::GET_SYSTEM_USER_BY_USERNAME_QUERY, $username, $db);
        if (count($resultSet) == 0) {
            return null;
        }
        return SystemUser::buildSystemUserFromResultSetRow($resultSet[0]);
    }
    
    /**
     * Creates password hash. Compatible with acegi.
     * 
     * <p>
     * Plain password is concatenated with <code>{</code>, the username and <code>}</code>
     * and then a SHA-256 hash applied.
     * </p>
     *
     * @param string $username
     * @param string $plainTextPassword
     * @return string hashed password
     */
    public static function createPasswordHash($username, $plainTextPassword) {
        
        return hash('sha256', $plainTextPassword . '{' . $username . '}');
    }
    
    static function insert($individual, $user, $db) {
        // TODO build
    }
    
    static function delete($id, $db) {
        // TODO
    }
    
    static function isSafeToDelete($id, $db) {
        // TODO
    }
    
    /**
     * Checks if hash of supplied password matches
     * the password hash stored in the database. 
     *
     * @param string $plainTextPassword
     * @return boolean true if matches, else false
     */
    public function checkPassword($plainTextPassword) {
         $hash = SystemUser::createPasswordHash($this->username, $plainTextPassword);
         if ($hash == $this->passwordHash) {
             return true;
         }
         else {
             return false;
         }
    }
    
    private static function buildArrayofSystemUsers($resultSet) {
        $systemUsers = array();
        foreach ($resultSet as $resultSetRow) {
            $systemUser = SystemUser::buildSystemUserFromResultSetRow($resultSetRow);
            array_push($systemUsers, $systemUser);
        }
        return $systemUsers;
    }
    
    private static function buildSystemUserFromResultSetRow($resultSetRow) {
        
        $id = $resultSetRow['id'];
        $username = $resultSetRow['username'];
        $passwordHash = $resultSetRow['passwordHash'];
        $isEnabled = $resultSetRow['isEnabled'];
        
        $name = $resultSetRow['name'];
        $nameQualification = $resultSetRow['nameQualification'];
        $givenNames = $resultSetRow['givenNames'];
        
        $individual = new Individual($id, $name, $nameQualification, $givenNames);
        $systemUser = new SystemUser($id, $username, $passwordHash, $isEnabled, $individual);
        return $systemUser;
        	
    }
    
}

?>