<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guest Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#display').click(function(){
				$.ajax({
					type:'GET',
					url:'DisplayAllPostsServlet',
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
	<form action="DisplayAllPostsServlet" method="GET">
			<lable for="display" >Display Posts</lable>
			<input type="submit" id="display" value="click">
		</form>
		<div id="result"></div>
		
		<h1>Go back home</h1>
		<li><<a href="Login.html"> Login page </a></li>
	
</body>
</html>