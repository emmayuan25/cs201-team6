import React, { useState, useEffect } from 'react';
import SearchBar from '../components/SearchBar';
import SearchPageBorder from '../components/SearchPageBorder';
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
    return (
        <>
            <div className='flex flex-row'>
                <SearchPageBorder></SearchPageBorder>
                <form onSubmit={(e) => {
                    console.log("SUBMITTED")
                    e.preventDefault();
                    getUsers(search);
                    setSearch("")
                }}>
                    <input type='text' placeholder='Search' value={search}
                    onChange={(e) => setSearch(e.target.value)}/>
                    <input type='submit' value='Submit'/>
                </form>
                <SearchBar></SearchBar>
                {results.map((user) => (
                    <div key={user.uid} onClick={() => {
                        console.log(user.uid);
                    }}>
                        <img src={user.profile}/>
                        <div> {user.name}</div>
                    <SearchResult profile={user.profile}/>
                    </div>
                ))}
                <SearchPageBorder></SearchPageBorder>
            </div>
            <Footer />
        </>
    )
}

export default SearchPage;
