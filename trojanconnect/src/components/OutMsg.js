import React from 'react';
import userpng from '../assets/user.png';

function OutMsg(props) {
    return (
        <div className='bg-transparent text-white m-10 text-right text-lg flex justify-end'>
            <div>{props.text}</div>
            <img className='w-8 justify-center flex ml-2' src={userpng} alt="user" />
        </div>
    )
}

export default OutMsg;
