import React from 'react';
// import FeedBox from '../components/Feedbox';
import HomePageBorder from '../components/HomePageBorder';
import Footer from '../components/Footer';
import FeedBox from '../components/FeedBox';

const HomePage = () => {
    return (
        <>
            <div className='flex flex-row'>
                <HomePageBorder></HomePageBorder>
                
                <FeedBox></FeedBox>
                
                <HomePageBorder></HomePageBorder>
            </div>
            <Footer />
        </>
    )
}

export default HomePage;
