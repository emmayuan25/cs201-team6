

var contactList = document.getElementById("chat-list");


function addContact(name, userid, userimg) {
    var newContact = document.createElement('div');
    var content = `
        <div class='contact-box' onclick="selectChat(this, `+ userid +`);">
            <img class='contact-icon' src='` + userimg +`' alt='user' />
            <span class='contact-name' >` + name + `</span></div>`;
    newContact.innerHTML = content;
    return newContact;
}


// display message
var chatbox = document.getElementById("chat-box");
function addMsg(text, out, userimg) {
    var newMsg = document.createElement('div');
    console.log(userimg);
    if(out == true) {
        var content =  `
            <div class="msgboxl">
                <div class="msgtext">`
                + text + `</div>
                <img class='msguser' src='`+ userimg +`' alt='user' /></div>`;
        newMsg.innerHTML = content;
        
    } else {
        var content =  `
            <div class="msgboxr">
                <img class='msguser' src='`+ userimg +`' alt='user' />
                <div class="msgtext">`
                + text + `</div></div>`;
        newMsg.innerHTML = content;
    }

    return newMsg;
}



function sendMsg() {
    var msginput = document.getElementById("send-msg-input");

    if(!msginput){
        alert("error");
    } else if(msginput.value == "") {
        alert("input cannot be empty");
    } else {
        console.log(msginput.value);
        var profileImg = localStorage.getItem("profileImg");
        chatbox.appendChild(addMsg(msginput.value, true, profileImg));

        msginput.value = "";
    }
}
