import React, {useState, useEffect} from 'react';
import ContactBox from './ContactBox';

function ContactList(props) {
    const names = props.names;
    const [selectedChat, setSelectedChat] = [props.selectedChat, props.setSelectedChat];
    // const [selectedChat, setSelectedChat] = useState(null);
    const colors = [];
    for(var i = 0; i < names.length; i++){
        if(names[i].id == selectedChat){
            colors.push(true)
        }else{
            colors.push(false)
        }
    }

    // useEffect = (() => {
    //     colors = []
    //     for(var i = 0; i < names.length; i++){
    //         if(names[i].id.equals(selectedChat)){
    //             colors.push(true)
    //         }else{
    //             colors.push(false)
    //         }
    //     }
    // }, [selectedChat])
    
    // const handleSelect = (e) => {
    //     this.setSelected(current => !current);
    //     console.log(this)
    // }

    const namelist = names.map((name, index) => 
        <div key={name.id} style={{backgroundColor: colors[index] ? '#FFCC00' : 'white'}} onClick={() => {setSelectedChat(name.id)}}>
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
