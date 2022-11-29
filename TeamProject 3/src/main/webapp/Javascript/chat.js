console.log("here");

var contactList = document.getElementById("chat-list");

console.log(contactList);

function addContact(name, userid) {
    var newContact = document.createElement('div');
    var content = `
        <div class='contact-box' onclick="selectChat(this, `+ userid +`);">
            <img class='contact-icon' src='./assets/user.png' alt='user' />
            <span class='contact-name' >` + name + `</span></div>`;
    newContact.innerHTML = content;
    return newContact;
}

contactList.appendChild(addContact('user1', 1));contactList.appendChild(addContact('user2', 2));contactList.appendChild(addContact('user3', 3));

// display message
var chatbox = document.getElementById("chat-box");
function addMsg(text, out) {
    var newMsg = document.createElement('div');
    if(out == true) {
        var content =  `
            <div class="msgboxl">
                <div class="msgtext">`
                + text + `</div>
                <img class='msguser' src="./assets/user.png" alt='user' /></div>`;
        newMsg.innerHTML = content;
        
    } else {
        var content =  `
            <div class="msgboxr">
                <img class='msguser' src='./assets/user.png' alt='user' />
                <div class="msgtext">`
                + text + `</div></div>`;
        newMsg.innerHTML = content;
    }

    return newMsg;
}


chatbox.appendChild(addMsg("test message out", true));
chatbox.appendChild(addMsg("test message in", false));
chatbox.appendChild(addMsg("test message out", true));


function sendMsg() {
    var msginput = document.getElementById("send-msg-input");
    if(!msginput){
        alert("error");
    } else if(msginput.value == "") {
        alert("input cannot be empty");
    } else {
        console.log(msginput.value);
        chatbox.appendChild(addMsg(msginput.value, true));
        // send to backend;
        msginput.value = "";
    }
}