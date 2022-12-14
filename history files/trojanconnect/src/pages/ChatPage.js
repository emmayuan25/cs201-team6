import React, {useState} from 'react';
import ContactList from '../components/ContactList';
import Chatbox from '../components/ChatBox'
import Footer from '../components/Footer'

const ChatPage = () => {
    const contactHashmap = [
        {
            username: "Abby Doe",
            id: 0
        },
        {
            username:"Brie Doe",
            id: 1
        },
        {
            username:"Cat Doe",
            id: 2
        },
        {
            username:"Dog Doe",
            id: 3
        },
        {
            username:"Jane Doe",
            id: 4
        }
    ];

    const [selectedChat, setSelectedChat] = useState(null);

    return (
        <>
            <div className='flex flex-row'>
                <ContactList names={contactHashmap} selectedChat={selectedChat} setSelectedChat={setSelectedChat}/>
                <Chatbox names={contactHashmap} selectedChat={selectedChat}/>
            </div>
            <Footer />
        </>
    )
}

export default ChatPage;
