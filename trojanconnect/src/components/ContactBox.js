import React, {useState} from 'react';
import userpng from '../assets/user.png';

function ContactBox(props) {
    const [isActive, setIsActive] = useState(true);
    
    function handleChange(event) {
        setIsActive(current => !current);
    }

    return (
        <div 
            className='my-10 cursor-pointer text-center flex'
            style={{backgroundColor: isActive ? 'white' : '#FFCC00'}}
            onClick={handleChange}
        >
            <img className='w-8 mx-4 justify-center flex' src={userpng} alt="user" />
            <span className='text-2xl inline-block'>{props.username}</span>
        </div>
    )
}

export default ContactBox;