import React from 'react';

function InMsg(props) {
    return (
        <div className='bg-transparent text-white m-10 text-lg'>
            <div>{props.text}</div>
            {/* <img src="../assets/user.png" alt="user"></img> */}
        </div>
    )
}

export default InMsg;
