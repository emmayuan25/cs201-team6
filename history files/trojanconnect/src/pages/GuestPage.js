import React, {useState, useEffect, createElement } from 'react';
import { Redirect, useNavigate } from 'react-router-dom';
import './SignUpPage.css';

function GuestPage(props) {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isLoggedin, setIsLoggedin] = [props.isLoggedin, props.setIsLoggedin];
    const navigate = useNavigate();

    const logout = props.logout
    const signUp = props.signUp
    const guest = props.guest
    const signIn = props.signIn

return (
	<>
	<div className= "newBackgroundColor">
	<div style={{ textAlign: 'center', color: 'white' }}> 
    <h1>You are a guest, please login or sign up to view this functionality.</h1>
    <br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br><br></br>
    <button type="Sign In" onClick={signIn} style = {{width: "175px", height: "35px"}}>
		<a href="/"> Back to Login Page </a>
	</button>
    <br></br>
	<br></br>
	<button type="Sign Up" onClick={signUp} style = {{width: "175px", height: "35px"}}>
		<a href="/SignUp"> Create Account </a>
	</button>
	</div>
	</div>
	</>
);
}

export default GuestPage;