import React from 'react';
import userpng from '../assets/user.png';

function OutMsg(props) {
    return (
        <div className='bg-transparent text-white m-10 text-right text-lg flex justify-end'>
            <div className='border p-2 rounded-lg'>{props.text}</div>
            <img className='w-8 h-8 justify-center flex ml-4' src={userpng} alt="user" />
        </div>
    )
}

export default OutMsg;
