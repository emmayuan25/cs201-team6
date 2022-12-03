import React from 'react';

import homeIcon from '../assets/home-icon.png';
import searchIcon from '../assets/search-icon.png';
import chatIcon from '../assets/chat-icon.png';
import userIcon from '../assets/user.png';

function Footer() {
    return (
        <div 
        className='mt-2 p-2 flex flex-row justify-evenly w-screen bg-offwhite h-14 text-lg fixed bottom-0'>
            <a href='/'>
                <img className='w-8 justify-evenly flex mr-2' src={homeIcon} alt="user" />
            </a>
            <a href='/search'>
                <img className='w-8 justify-evenly flex mr-2' src={searchIcon} alt="user" />
            </a>
            <a href='/message'>
                <img className='w-8 justify-evenly flex mr-2' src={chatIcon} alt="user" />
            </a>
            <a href='/profile'>
                <img className='w-8 justify-evenly flex mr-2' src={userIcon} alt="user" />
            </a>
            
        </div>
    )
}

export default Footer;