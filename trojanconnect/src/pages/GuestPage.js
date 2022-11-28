import React, {useState, useEffect, createElement } from 'react';
import { Redirect, useNavigate } from 'react-router-dom';
import './SignUpPage.css';

function GuestPage(props) {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isLoggedin, setIsLoggedin] = [props.isLoggedin, props.setIsLoggedin];
    const navigate = useNavigate();

return (
	<>
	<div className= "newBackgroundColor">
	<div style={{ textAlign: 'center', color: 'white' }}> 
    <h1>You are a guest, please login or sign up to view this functionality.</h1>
	</div>
	</div>
	</>
);
}

export default GuestPage;