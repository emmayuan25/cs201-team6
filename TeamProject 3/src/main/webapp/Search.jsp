<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#submitbttn').click(function(){
				var search = $('#search').val();
				$.ajax({
					type:'GET',
					url:'SearchServlet',
					data:{ search: search}
					headers: {
						Accept: "application/json",
						"Content-Type" : "application/json"
					},
					success: function(result){
						var listUsers = $.parseJSON(result);
						var s='';
						for(var i=0;i<listUsers.length;i++){
							s+= 'ID: '+ listUsers[i].userID + '<br> Username: '+ listUsers[i].username + '<br> Image: '+ listUsers[i].userImage + '<br><br>';
						}
						document.getElementId('result').innerHTML= s;
					}
				});
			});

		});
	</script>
</head>
<body>
	<h1>Search</h1>
	<form action="SearchServlet">
		<lable for="search">search:<lable>
		<input type="text" id="search" name="search">
		<input type="submit" id="submitbttn">
		<span id="result"></span>
	</form>
	
	<br>
	<h1>Navigation</h1>
		<ul>
			<li><a href="HomePage.jsp">Home</a></li>
			<li><a href="Search.jsp">Search</a></li>
			<li><a href="Chat.jsp">Chat</a></li>
			<li><a href="Profile.jsp">Profile</a></li>
		</ul>
</body>
</html>