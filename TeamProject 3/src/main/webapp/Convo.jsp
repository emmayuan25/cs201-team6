<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	var socket;
	function connectToServer() {
		socket = new WebSocket("ws://localhost:8080/Test-Web/ws");
		socket.onopen = function(event) {
			document.getElementById("mychat").innerHTML += "Connected!"
		}
		socket.onmessage = function(event) {
			document.getElementById("mychat").innerHTML += event.data + "<br />";
		}
		
		socket.onclose = function(event) {
			document.getElementById("mychat").innerHTML += "Disconnected!";
		}
		function sendMessage() {
			socket.send(${user.username}+": " + document.chatform.message.value);
			return false;
		}
	}
	
	$(document).ready(function(){
		$('#display').click(function(){
			$.ajax({
				type:'GET',
				url:'ConvoServlet',
				dataType: 'JSON'
				headers: {
					Accept: "application/json",
					"Content-Type" : "application/json"
				},
				success: function(result){
	
					var listPost = $.parseJSON(result);
					var s='';
					for(var i=0;i<listPost.length;i++){
						s+= 'ID: '+ listPost[i].postID + '<br> Username: '+ listPost[i].username + '<br> Image: '+ listPost[i].postImage + '<br> Text: '+ listPost[i].postText+'<br> profilepic:' + listPost[i].profilePicture+'<br>';
					}
					document.getElementId('result').innerHTML= s;
				}
			});
		});

	});
</script>
</head>
<body onload="connectToServer()">

	<form action="ConvoServlet" method="GET">
		<h1>Display convo</h1>
		<input type="submit">
	</form>
	<div id="mychat"></div>
	
	<br>
	<form name="chatform" action="ConvsoServlet" method="POST">
		<input type="text" name="message" value="Type Here!" /><br />
		<input type="button" value="Send Message" onclick="sendMessage();" />
	</form>
</body>
</html>