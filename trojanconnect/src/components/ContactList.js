import React, {useState} from 'react';
import ContactBox from './ContactBox';

function ContactList(props) {
    const names = props.names;

    const [selected, setSelected] = useState(false);
    
    const handleSelect = (e) => {
        this.setSelected(current => !current);
        console.log(this)
    }

    const namelist = names.map((name) => 
        <div style={{backgroundColor: selected ? '#FFCC00' : 'white'}} onClick={handleSelect}>
            <ContactBox 
                username={name.username} 
                key={name.id} 
            />
        </div>
    );

    return (
        <div className='w-1/5 h-screen bg-offwhite'>
            {namelist}
        </div>
    );

}

export default ContactList;
