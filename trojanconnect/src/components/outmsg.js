import React from 'react';

function OutMsg(props) {
    return (
        <div className='bg-transparent text-white m-10 text-right text-lg'>
            {/* <img src="../assets/user.png" alt="user"></img> */}
            <div>{props.text}</div>
        </div>
    )
}

export default OutMsg;