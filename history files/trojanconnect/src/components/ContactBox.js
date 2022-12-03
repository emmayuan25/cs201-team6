import React, {useState} from 'react';
import userpng from '../assets/user.png';

function ContactBox(props) {
    const [isActive, setIsActive] = useState(true);
    
    function handleChange() {
        setIsActive(current => !current);
    }

    return (
        <div 
            className='py-10 cursor-pointer text-center flex'
        >
            <img className='w-8 mx-4 justify-center flex' src={userpng} alt="user" />
            <span className='text-2xl inline-block'>{props.username}</span>
        </div>
    )
}

export default ContactBox;