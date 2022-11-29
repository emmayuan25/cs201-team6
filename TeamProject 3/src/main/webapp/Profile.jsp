<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Edit Profile</h1>
	<form action="EditProfileServlet" name="editform" method="POST">
		<lable for="username">username:<lable>
		<input type="text" id="username" name="username" placeholder="${user.username}">
		
		<br>
		<lable for="image">image:<lable>
		<input type="text" id="image" name="image" placeholder="${user.profilePicture}">
		
		<br>
    	<label for="interest">Interest:</label>
   		<br>
   		<input id="interest1" type="checkbox" name="interest" value="marshall">
		<label for="interest1"> Marshall</label><br>
		<input id="interest2" type="checkbox" name="interest" value="viterbi">
		<label for="interest2"> Viterbi</label><br>
		<input  id="interest3" type="checkbox" name="interest" value ="dornsife">
		<label for="interest3">Dornsife</label>
		<input  id="interest4" type="checkbox" name="interest" value ="computerScience">
		<label for="interest4">Computer Science</label>
		<input  id="interest5" type="checkbox" name="interest" value ="csba">
		<label for="interest5">CSBA</label>
		<input  id="interest6" type="checkbox" name="interest" value ="businessAdmin">
		<label for="interest6">Business Administration</label>
		<input  id="interest7" type="checkbox" name="interest" value ="csgames">
		<label for="interest7">CS Games</label>
		<input  id="interest8" type="checkbox" name="interest" value ="cais">
		<label for="interest8">CAIS</label>
		<input  id="interest9" type="checkbox" name="interest" value ="athenaHacks">
		<label for="interest9">Athena Hacks</label>
		<input  id="interest10" type="checkbox" name="interest" value ="scope">
		<label for="interest10">Scope</label>
		<input type="submit">
	</form>
	
	<h1>Navigation</h1>
		<ul>
			<li><a href="HomePage.jsp">Home</a></li>
			<li><a href="Search.jsp">Search</a></li>
			<li><a href="Chat.jsp">Chat</a></li>
			<li><a href="Profile.jsp">Profile</a></li>
		</ul>
</body>
</html>