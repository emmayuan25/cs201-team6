import React, {useState} from 'react';

function ContactBox(props) {
    const [isActive, setIsActive] = useState(true);
    
    function handleChange(event) {
        setIsActive(current => !current);
    }

    return (
        <div 
            className='my-10 cursor-pointer text-center'
            style={{backgroundColor: isActive ? 'white' : 'yellow'}}
            onClick={handleChange}
        >
            {/* <img src="../assets/user.png" alt="user" /> */}
            <span className='text-2xl inline-block'>{props.username}</span>
        </div>
    )
}

export default ContactBox;