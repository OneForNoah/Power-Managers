<?php
$q=$_GET["q"];

$xml=simplexml_load_file($q) or die("Error: Can't get data");
//handle some attributes?
echo($xml);

?>
