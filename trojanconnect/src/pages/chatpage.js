import React from 'react';
import ContactList from '../components/ContactList';
import Chatbox from '../components/ChatBox'
import Footer from '../components/Footer'

const ChatPage = () => {
    const contactHashmap = ["Abby Doe", "Brie Doe","Cat Doe","Dog Doe", "Jane Doe"];

    return (
        <>
            <div className='flex flex-row'>
                <ContactList names={contactHashmap}/>
                <Chatbox />
                
            </div>
            <Footer />
        </>
    )
}

export default ChatPage;