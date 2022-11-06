import React from 'react';
import ContactBox from './ContactBox';

function ContactList(props) {
    const names = props.names;

    const namelist = names.map((name) => 
        <div>
            <ContactBox 
                username={name} 
                key={name} 
            />
        </div>
    );

    return (
        <div 
            className='w-1/5 h-screen bg-white border-black border-black border-solid border-2'
        >
            {namelist}
        </div>
    );

}

export default ContactList;