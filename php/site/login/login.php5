<?php

// note, encode password + salt as follows: rawPassword + { + salt + } 
// salt = username

// use sha256 to hash.
// hash('sha256', 'password' . '{' . username . '}');

// user 0 = admin, password admin

?>