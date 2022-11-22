import React, { useState } from 'react';
import InMsg from './InMsg';
import OutMsg from './OutMsg';

const exChat = [
    {to: "Emma", from: "Cat", text: "First message"},
    {to: "Cat", from: "Emma", text: "Response"},
    {to: "Emma", from: "Cat", text: "Random Conversation"},
    {to: "Emma", from: "Cat", text: "Filler Text"},
    {to: "Cat", from: "Emma", text: "Additional Input"}
]
function ChatBox(props) {
    const [message, setMessage] = useState("");
    const messages =  exChat.map((chat, index) => {
        if(chat.to == "Emma")   return(<InMsg key={index} text={chat.text}/>);
        else    return(<OutMsg key={index} text={chat.text}/>);
    });

    const sendMessage = (message) => {
        console.log({to: "Cat", from: "Emma", text: message})
    }
    return (
        <div className="bg-red w-4/5 h-screen scroll-smooth overflow-y-auto pb-8">
            {messages}
            <form onSubmit={(e) => {
                    console.log("SUBMITTED")
                    sendMessage(message);
                    setMessage("")
                }}>
                    <input type='text' placeholder='Chat message' value={message}
                    onChange={(e) => setMessage(e.target.value)}/>
                    <input type='submit' value='Submit'/>
                </form>
        </div>
    )
}

export default ChatBox;
