var resultbox = document.getElementById("search-result");
var userID = localStorage.getItem("userID");

function getSearchResult() {
	var input = document.getElementById("search-input").value;
	var resultbox = document.getElementById("search-result");
	resultbox.innerHTML = "";
	
	console.log(input);
    $.ajax({
		type: "GET",
		url: "SearchServlet",
		async: true,
		data: {
			keyword: input
		},
		success: function(result) {
			console.log(result);
			resultbox.appendChild(addContact(result.username, result.userID, result.profilePicture));
        }
	});
	return false;
}

function addContact(name, userid, userimg) {
    var newContact = document.createElement('div');
    var content = `
        <div class='contact-box' onclick="gotoChat();addNewChat(`+ userid +`)">
            <img class='contact-icon' src='` + userimg +`' alt='user' />
            <span class='contact-name' >` + name + `</span></div>`;
    newContact.innerHTML = content;
    return newContact;
}

function addNewChat(fID) {
	
	var oldContact = getContactList(fID);
    console.log(oldContact);
    if(oldContact == false) {
		console.log("new user");
		$.ajax({
			type: "GET",
			url: "StartNewChatServlet",
			data: {
				userID: userID,
				friendID: fID,
				message: "Send a message!"
			},
			success: function(result) {
				console.log(result);
	        }
		});
		return false;
	}	
}

function gotoChat() {
	window.location = "Chat.html"
}

function getContactList(fID) {
	var userID = localStorage.getItem("userID");
	// var userID = 1;
    console.log(fID);
	$.ajax({
		type: "GET",
		url: "DisplayChatsServlet",
		async: true,
		data: {
			UID: userID
		},
		success: function(result) {
			var res = "false";
			for(var i = 0; i < result.chatsList.length; i++) {
				console.log(result.chatsList[i].friendID);
				if(parseInt(result.chatsList[i].friendID) === parseInt(fID)) {
					console.log(result.chatsList[i].friendID);
					console.log(fID);
					res = "true";
				}
            }
            return res;
        }
	});
    return false;
}