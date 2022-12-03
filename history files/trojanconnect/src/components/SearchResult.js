import React from 'react';

function SearchResult(props) {
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
        height: '10vh',
        width: '32vw',
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

    const profile = props.profile

    return (
            <div style={dropDownStyle}>
                <img src={profile} style = {userStyle} alt='User'></img>
                <p style={dropTextStyle}>Person 1</p>
            </div>
    );

}

export default SearchResult;