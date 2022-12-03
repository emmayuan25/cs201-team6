import React, {useState, useEffect, createElement } from 'react';
import { Redirect, useNavigate } from 'react-router-dom';
import './SignUpPage.css';

function SignUpPage(props) {
const [name, setName] = useState('');
const [email, setEmail] = useState('');
const [password, setPassword] = useState('');
const [isLoggedin, setIsLoggedin] = [props.isLoggedin, props.setIsLoggedin];
const navigate = useNavigate();

const login = (e) => {
	e.preventDefault();
	console.log(name, email, password);
	const userData = {
	name,
	email,
	password,
	};
	localStorage.setItem('token-info', JSON.stringify(userData));
	setName('');
	setEmail('');
	setPassword('');
	setIsLoggedin(true);
	// const navigate = useNavigate();
	navigate('home');
};

const signUp = props.signUp

return (
	<>
	<div className= "newBackgroundColor">
	<div style={{ textAlign: 'center', color: 'white' }}> 
		<h1 style = {{ fontSize: 65}}> Trojan Connect</h1>
		<p style= {{ fontSize: 30 }}> Sign up and connect with your fellow Trojans!</p>
		<>
			<form action="">
			<input
				type="text"
				onChange={(e) => setName(e.target.value)}
				value={name}
				placeholder="Username"
			/>
			</form>
			<form action2 = "">
			<input
				type="password"
				onChange={(e) => setPassword(e.target.value)}
				value={password}
				placeholder="Password"
			/>
			</form>
            <br></br>
			<button type="Sign Up" onClick={signUp} style = {{width: "175px", height: "35px"}}>
				<a href="/"> Create Account </a>
			</button>
		</>
	</div>
	</div>
	</>
);
}

export default SignUpPage;