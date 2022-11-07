import React from 'react';

function FeedBox(props) {
    const h1Style = {
        backgroundColor: "#990000",
        padding: "10px",
        fontSize: "70px",
        textAlign: "center",
        color: "white"
      };

    const imgStyle = {
        marginLeft: 'auto',
        marginRight: 'auto',
        display: 'block',
        width: '80%',
        height: 'auto',
        paddingRight: '20px',
        paddingLeft: '20px',
        paddingTop: '50px'
    }

    const feedItemStyle = {
        backgroundColor: 'black',
        width: '100%',
        paddingBottom: '30px',
        border: '0px'
    }

    const bufferStyle = {
        backgroundColor: '#990000',
        height: '110px',
    }

    const captionStyle = {
        backgroundColor: '#ffcc00',
        padding: '30px',
        fontSize: '20px'
    }

    const nextButtonStyle = {
        width: '50px',
        margin: 'auto',
        marginTop: '25px',
        marginBottom: '25px'
    }

    const campus = require('../assets/campus.jpg');

    const nextButton = require('../assets/next.png');

    return (
        <div className="bg-red w-1/2 h-screen">
            <h1 style={h1Style}>HOME</h1>

            <div style={feedItemStyle}>
                <img style={imgStyle} src={campus} alt="Campus"></img>
            </div>

            <div style={captionStyle}>
                <t>Happy to be part of the Trojan Family! Over the last semester we at CLUB club have done something!</t>
            </div>

            <div style={bufferStyle}>
                <img style = {nextButtonStyle} src={nextButton} alt="Next"></img>
            </div>

        </div>
    )
}

export default FeedBox;
