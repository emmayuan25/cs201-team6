import React from 'react';
import userpng from '../assets/user.png';

function InMsg(props) {
    return (
        <div className='bg-transparent text-white m-10 text-lg flex'>
            <img className='w-8 h-8 justify-center flex mr-4' src={userpng} alt="user" />
            <div className='border p-2 rounded-lg'>{props.text}</div>
        </div>
    )
}

export default InMsg;
