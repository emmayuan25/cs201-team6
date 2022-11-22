import React, {useState, useEffect} from 'react';

import Footer from '../components/Footer';
import Delete from '../assets/delete.png';

const ProfilePage = (props) => {
    // const username = props.username;
    const username = "joe";
    // const interests = props.interests;
    const interestlist = ["cs201", "cs270", "cs104"];

    const [currentName, setCurrentName] = useState(username);
    const [newInterest, setNewInterest] = useState("");

    const handleChange = (e) => {
        setCurrentName(e.target.value);
        console.log(currentName);
    }

    const handleAddInterest = (e) => {
        console.log(e.target.value);
        setNewInterest(e.target.value);
    }

    const addToInterestList = () => {
        interestlist.push(newInterest);
        setNewInterest("");
    }

    const deleteInterest = (item) => {
        // console.log(item.value);
    };


    return (
        <>
            <div className='justify-center bg-red h-screen w-screen'>
                <h1 className='text-center py-14 text-5xl text-white bg-red'>Profile</h1>
                <form className='text-white flex justify-center flex-col items-center'>
                    <label className='font-bold w-52 text-left pl-2'>
                    Name
                    </label>
                    <input 
                        className='h-10 w-52 rounded ml-4 bg-grey px-2'
                        type="text" 
                        value={currentName} 
                        onChange={handleChange}
                    />
                    <label className='font-bold w-52 text-left pl-2 mt-6'>
                    Add Interests
                    </label>
                    <input 
                        className='h-10 w-52 rounded ml-4 bg-grey px-2'
                        type="text" 
                        onChange={(e) => handleAddInterest(e)}
                    />
                    <button 
                        onClick={addToInterestList}
                        className='flex'
                    >+</button>
                    {interestlist.map((interest) => 
                        <div 
                            className='flex text-black text-left pl-4 py-1 bg-white  w-52 ml-4 rounded'
                        >
                            {interest}
                            <img 
                                src={Delete}
                                alt="delete" 
                                onClick={deleteInterest(interest)}
                                className='flex cursor-pointer pl-24'    
                            />
                        </div>
                    )}
                </form>
            </div>
            
            <Footer />
        </>
    )
}

export default ProfilePage;