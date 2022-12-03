import React from 'react';
// import FeedBox from '../components/Feedbox';
import HomePageBorder from '../components/HomePageBorder';
import Footer from '../components/Footer';
import { Redirect, useNavigate } from 'react-router-dom';
import FeedBox from '../components/FeedBox';

function HomePage(props) {

    const navigate = useNavigate();

    const navigateChat = () => {
        navigate('../message');
    };

    return (
        <>
            <div className='flex flex-row' onClick={navigateChat}>
                <HomePageBorder></HomePageBorder>
                
                <FeedBox></FeedBox>
                
                <HomePageBorder></HomePageBorder>
            </div>
            <Footer />
        </>
    );
    
}

export default HomePage;
