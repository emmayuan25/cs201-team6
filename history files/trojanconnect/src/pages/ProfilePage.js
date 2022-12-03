import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom'

import Footer from '../components/Footer';

import './ProfilePage.css'

const interestlist = ["cs201", "cs270", "cs104", "viterbi", "computer science",
"engineering", "scope", "math408"];
const userInterests = [true, true, false, true, true,
    true, false, false];

const profiles = [
    "http://pm1.narvii.com/6246/715984500f1c060fd0487b4831921e6b9b0498fe_00.jpg",
    "https://i.pinimg.com/originals/fb/15/f0/fb15f07243e81de329a17151300b5e99.png"
]


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
    const [profile, setProfile] = useState(profiles[0]);
    const [interestFlip, setInterestFlip] = useState("");
    
    const navigate = useNavigate();

    const handleChange = (e) => {
        setCurrentName(e.target.value);
        console.log(currentName);
    }

    const handleProfileChange = (e) => {
        setProfile(e.target.value);
        console.log(profile);
    }

    const changeInterest = (item) => {
        // console.log(item.value);
        let ind = interestlist.findIndex((element) => element === item);
        userInterests[ind] = !userInterests[ind];
        setInterestFlip(!interestFlip);
    };

    const deleteProfile = () => {
        navigate("/");
    }

    const logOut = () => {
        navigate("/");
    }

    return (
        <>
            <div className='justify-center bg-red h-screen w-screen'>
                <h1 className='text-center py-10 text-5xl text-white bg-red'>Profile</h1>
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
                    <label className='mt-6 font-bold w-52 text-left pl-2'>
                    Profile Picture
                    </label>
                    <input 
                        className='h-10 w-52 rounded ml-4 bg-grey px-2'
                        type="text" 
                        value={profile} 
                        onChange={(e) => {e.preventDefault();
                            handleProfileChange(e)}}
                    />
                    <figure className="image m-4">
                        <img className='is-rounded' src={profile}/>
                    </figure>
                    <label className='font-bold w-52 text-left pl-2 mt-2'>
                    Interests
                    </label>
                    {interestlist.map((interest, index) => {
                        if(userInterests.at(index)){
                            return (<div key={index}
                                className='text-black text-left pl-4 py-1 selected-box 
                                w-52 ml-4 rounded'
                                onClick={() => changeInterest(interest)}
                            >
                                {interest}

                            </div>);
                        }else{
                            return (<div key={index}
                                className='text-black text-left pl-4 py-1 bg-white  w-52 ml-4 rounded'
                                onClick={() => changeInterest(interest)}
                            >
                                {interest}
                            </div>);
                        }
                    }
                        
                    )}
                    <div className='box mb-6 pb-6'>
                        <button className='mt-6 mb-6 pl-4 py-1 bg-black w-52 ml-4 rounded'
                        onClick={() => logOut()}>
                            Log Out
                        </button>
                        <button className='mb-6 pl-4 py-1 bg-black w-52 ml-4 rounded'
                        onClick={() => deleteProfile()}>
                            Delete Profile
                        </button>
                    </div>
                </form>
            </div>
            
            <Footer />
        </>
    )
}

export default ProfilePage;