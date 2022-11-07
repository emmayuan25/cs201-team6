import React from 'react';
import SearchBar from '../components/SearchBar';
import SearchPageBorder from '../components/SearchPageBorder';
import Footer from '../components/Footer';

const SearchPage = () => {

    return (
        <>
            <div className='flex flex-row'>
                <SearchPageBorder></SearchPageBorder>
                
                <SearchBar></SearchBar>
                
                <SearchPageBorder></SearchPageBorder>
            </div>
            <Footer />
        </>
    )
}

export default SearchPage;
