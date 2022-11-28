import React, { useState, Redirect } from 'react';
import SearchBar from '../components/SearchBar';
import Footer from '../components/Footer';
import SearchResult from '../components/SearchResult';

const mockUsers = [{name: 'Adam', uid: 1, profile: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1600px-Image_created_with_a_mobile_phone.png'},
    {name: 'Emma', uid: 2, profile: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1600px-Image_created_with_a_mobile_phone.png'},
    {name: 'Jacky', uid: 3, profile: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1600px-Image_created_with_a_mobile_phone.png'},
    {name: 'John', uid: 4, profile: 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1600px-Image_created_with_a_mobile_phone.png'}]


const SearchPage = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [results, setResults] = useState([]);
    const [search, setSearch] = useState("");

    const getUsers = (term) => {
        console.log(term);
        var users = []
        // replace with query
        for (let user of mockUsers)
            if(user.name.toLowerCase().includes(term.toLowerCase())){
                users.push(user)
            }
        setResults(users)
    }
    
    // useEffect(() => {
    //     setIsLoaded(true);        
    // }, [results]);

    const toChat = (userID) => {
        localStorage.setItem("chatID", userID);
    }

    return (
        <>
            <div className='justify-center bg-red py-8 mx-auto items-center flex flex-col h-screen'>
                <form 
                    className='h-24 w-1/3 items-center'
                    onSubmit={(e) => {
                    console.log("SUBMITTED")
                    e.preventDefault();
                    getUsers(search);
                    setSearch("")
                    }}
                >
                <input
                    className='h-10 w-2/3 rounded px-2 border-2'
                    type='text' placeholder='Search for user' value={search}
                    onChange={(e) => setSearch(e.target.value)}/>
                <input 
                    type='submit' 
                    value='Submit'
                    className='bg-red border-2 h-10 px-2 rounded font-bold cursor-pointer'
                />
                </form>
                {/* <SearchBar /> */}
                {results.map((user) => (
                    <div
                        className='bg-white rounded-lg w-72 h-16 content-center flex flex-row border-2' 
                        key={user.uid} onClick={() => {
                        console.log(user.uid);
                    }}>
                        <img className='w-16' src={user.profile} />
                        <a href='/message' 
                            className='w-36 justify-center m-auto text-xl cursor-pointer'
                            onClick={toChat(user.uid)}
                        >
                            {user.name}
                        </a>
                    </div>
                ))}
            </div>
            <Footer />
        </>
    )
}

export default SearchPage;
