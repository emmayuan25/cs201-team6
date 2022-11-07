import React from 'react';

function SearchBar(props) {
    const bufferStyle = {
        backgroundColor: '#990000',
        height: '40px',
    }

    const searchBarStyle = {
        height: '100px',
        width: '100%',
        backgroundColor: '#ffcc00'
    }

    const h1Style = {
        fontSize: "70px",
        textAlign: "center",
        color: "white"
      };

    const dropDownStyle = {
        height: '70px',
        backgroundColor: 'white',

    }

    const dropTextStyle = {
        fontSize: '30px',
        fontWeight: 'bold',
        lineHeight: '70px',
        paddingLeft: '60px'
    }

    const userStyle = {
        height: '30px',
        float: 'left',
        margin: '20px'
    }

    const searchStyle = {
        height: '70px',
        float: 'left',
        marginTop: '15px',
        marginLeft: '30px'
    }

    const user = require('../assets/user.png');

    const search = require('../assets/search.png');

    return (
        <div className='w-1/3 h-screen bg-red'>
            <div style={bufferStyle}></div>
            <div style={searchBarStyle}>
                <img src={search} style = {searchStyle} alt='Search'></img>
                <h1 style={h1Style}>Interest</h1>
            </div>
            <div style={dropDownStyle}>
                <img src={user} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 1</p>
            </div>
            <div style={dropDownStyle}>
                <img src={user} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 2</p>
            </div>
            <div style={dropDownStyle}>
                <img src={user} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 3</p>
            </div>
            <div style={dropDownStyle}>
                <img src={user} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 4</p>
            </div>
            <div style={dropDownStyle}>
                <img src={user} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 5</p>
            </div>

        </div>
    );

}

export default SearchBar;