var resultbox = document.getElementById("search-result");

function addContact(name, userid) {
    var newContact = document.createElement('div');
    var content = `
        <div class='contact-box' onclick="gotoChat();">
            <img class='contact-icon' src='./assets/user.png' alt='user' />
            <span class='contact-name' >` + name + `</span></div>`;
    newContact.innerHTML = content;
    return newContact;
}

function gotoChat() {
    window.location = "Chat.html";
}

resultbox.appendChild(addContact("user1", 1));
resultbox.appendChild(addContact("user2", 1));
