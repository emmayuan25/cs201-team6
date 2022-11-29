<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HomePage</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#display').click(function(){
				$.ajax({
					type:'GET',
					url:'DisplayServlet',
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
<body>
<h1>Navigation</h1>
		<ul>
			<li><a href="HomePage.jsp">Home</a></li>
			<li><a href="Search.jsp">Search</a></li>
			<li><a href="Chat.jsp">Chat</a></li>
			<li><a href="Profile.jsp">Profile</a></li>
			<li><a href="CreatePost.html">Create Post</a></li>
		</ul>
		<p> Name: ${user.username}</p>
		
		<form action="DisplayServlet">
			<lable for="display" >Display Posts</lable>
			<input type="submit" id="display" value="click">
		</form>
		<div id="result"></div>
		
		
		
	
</body>
</html>