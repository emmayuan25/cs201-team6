import React, {useState, useEffect} from 'react';

import Footer from '../components/Footer';
import Delete from '../assets/delete.png';

import './ProfilePage.css'

const interestlist = ["cs201", "cs270", "cs104", "viterbi", "computer science",
"engineering", "scope", "math408"];
const userInterests = [true, true, false, true, true,
    true, false, false];


/*
Display checkboxes for all possible interests.

Add button to sign out, delete profile.
*/
const ProfilePage = (props) => {
    // const username = props.username;
    const username = "joe";
    // const interests = props.interests;
    // const interestlist = ["cs201", "cs270", "cs104"];

    const [currentName, setCurrentName] = useState(username);
    const [tempInterest, setTempInterest] = useState("");
    const [interestFlip, setInterestFlip] = useState("");
    

    const handleChange = (e) => {
        setCurrentName(e.target.value);
        console.log(currentName);
    }

    const handleAddInterest = (e) => {
        console.log(e.target.value);
        setTempInterest(e.target.value);
    }

    const addToInterestList = () => {
        // setNewInterest(tempInterest)
        
        interestlist.push(tempInterest)
        console.log(interestlist)
        setTempInterest("");
    }

    const changeInterest = (item) => {
        // console.log(item.value);
        console.log(item);
        let ind = interestlist.findIndex((element) => element === item);
        userInterests[ind] = !userInterests[ind];
        setInterestFlip(!interestFlip);
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
                        onChange={(e) => {e.preventDefault();
                            handleChange(e)}}
                    />
                    <label className='font-bold w-52 text-left pl-2 mt-6'>
                    Interests
                    </label>
                    {/* <input 
                        className='h-10 w-52 rounded ml-4 bg-grey px-2'
                        type="text" 
                        value={tempInterest}
                        onChange={(e) => handleAddInterest(e)}
                    />
                    <button 
                        onClick={(e) => {e.preventDefault();
                            addToInterestList()}}
                        className='flex'
                    >+</button> */}
                    {interestlist.map((interest, index) => {
                        if(userInterests.at(index)){
                            console.log("T");
                            return (<div key={index}
                                className='text-black pl-4 py-1 selected-box 
                                w-52 ml-4 rounded'
                                onClick={() => changeInterest(interest)}
                            >
                                {interest}

                            </div>);
                        }else{
                            console.log("F");
                            return (<div key={index}
                                className='flex text-black text-left pl-4 py-1 bg-white  w-52 ml-4 rounded'
                                onClick={() => changeInterest(interest)}
                            >
                                {interest}
                            </div>);
                        }
                    }
                        
                    )}
                </form>
            </div>
            
            <Footer />
        </>
    )
}

export default ProfilePage;