<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#display').click(function(){
				$.ajax({
					type:'POST',
					url:'DisplayChatsServlet',
					dataType: 'JSON'
					headers: {
						Accept: "application/json",
						"Content-Type" : "application/json"
					},
					success: function(result){
		
						var setChat = $.parseJSON(result);
						var s='';
						for(var i=0;i<setChat.length;i++){
							s+= 'From username: '+ setChat[i].username + '<br> From Image: '+ setChat[i].userImage '<br>';
						}
						document.getElementId('result').innerHTML= s;
					}
				});
			});

		});
	</script>
</head>
<body>
	<form action="DisplayChatsServlet" method="POST">
		<h1>Display current chats</h1>
		<input type="submit">
	</form>
	<div id='result' class='result'></div>
	
	<h1>Navigation</h1>
		<ul>
			<li><a href="HomePage.jsp">Home</a></li>
			<li><a href="Search.jsp">Search</a></li>
			<li><a href="Chat.jsp">Chat</a></li>
			<li><a href="Profile.html">Profile</a></li>
			<li><a href="CreatePost.html">Profile</a></li>
		</ul>
</body>
</html>