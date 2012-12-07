<?php
	
	$nb_ppage = 5;
	$mysqli = mysqli_connect('localhost', 'root', 'test_reseau', 'resap');
	
	/* Vérification de la connexion */
	if (mysqli_connect_errno()) {
		printf('Échec de la connexion : %s\n', mysqli_connect_error());
		exit();
	}
	
	if(isset($_POST["id"])) {		
		$request_id = $_POST["id"];
		switch($request_id)  {
			case "insert" :
				$query = 'INSERT INTO posts VALUES (NULL,"' . $_POST["title"] . '","' . $_POST["content"] . '",0,"' . date("Y-m-j H:i:s") . '")';
				$mysqli->query($query)  or die (mysqli_error());
			break;
		}
	}

	if(isset($_POST["request"])) {
		$request = json_decode($_POST["request"]);
		$page = $request->page;
		switch($request->id)  {
			case "insert" :
				$query = 'INSERT INTO posts VALUES (NULL,"' . $request->title . '","' . $request->content . '",0,"' . date("Y-m-j H:i:s") . '")';
				$mysqli->query($query)  or die (mysqli_error());
				echo "Article soumis";
			break;
			case "date" :
				$values = $mysqli->query('SELECT * FROM posts ORDER BY date DESC LIMIT '. $nb_ppage .' OFFSET '. (($page) * $nb_ppage)) or die (mysqli_error());

				$json = array();
				$temp_json = array();
				while($row = $values->fetch_object()) {
					$row->title = utf8_encode($row->title);
					$row->content = utf8_encode($row->content);
					$temp_json = $row;
					array_push($json, $temp_json);
				}
				echo json_encode($json);
			break;
			case "top" :
				$values = $mysqli->query('SELECT * FROM posts ORDER BY likes DESC LIMIT '. $nb_ppage .' OFFSET '. (($page) * $nb_ppage)) or die (mysqli_error());

				$json = array();
				$temp_json = array();
				while($row = $values->fetch_object()) {
					$row->title = utf8_encode($row->title);
					$row->content = utf8_encode($row->content);
					$temp_json = $row;
					array_push($json, $temp_json);
				}
				echo json_encode($json);
			break;
			case "random" :
				$values = $mysqli->query('SELECT * FROM posts ORDER BY RAND() LIMIT '. $nb_ppage .' OFFSET '. (($page) * $nb_ppage)) or die (mysqli_error());

				$json = array();
				$temp_json = array();
				while($row = $values->fetch_object()) {
					$row->title = utf8_encode($row->title);
					$row->content = utf8_encode($row->content);
					$temp_json = $row;
					array_push($json, $temp_json);
				}
				echo json_encode($json);
			break;
			case "like" :
				$query = 'UPDATE posts SET likes = likes + 1 WHERE id = ' . $request->id_elem;
				$mysqli->query($query)  or die (mysqli_error());
				echo "Like soumis";
			break;
		}
	}

	/*$values = $mysqli->query('SELECT * FROM posts') or die (mysqli_error());

	$json = array();
	$temp_json = array();
	while($row = $values->fetch_object()) {
		
		$row->content = utf8_encode($row->content);
		$temp_json = $row;
		array_push($json, $temp_json);
	}
	
	echo json_encode($json);*/
?>
