import React, {useState, useEffect, createElement } from 'react';
import { Redirect, useNavigate } from 'react-router-dom';
import './SignInPage.css';

function SignInPage(props) {
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

// useEffect(() => {
// 	if(isLoggedin){
// 	const navigate = useNavigate();
// 	navigate('home');}
// }, [isLoggedin])


const logout = props.logout
const signUp = props.signUp
const guest = props.guest

return (
	<>
	<div className= "newBackgroundColor">
	<div style={{ textAlign: 'center', color: 'white' }}> 
		<h1 style = {{ fontSize: 65}}> Trojan Connect</h1>
		<p style= {{ fontSize: 30 }}> Sign in and connect with your fellow Trojans!</p>
		{!isLoggedin ? (
		<>
			<form action="">
			<input
				type="text"
				onChange={(e) => setName(e.target.value)}
				value={name}
				placeholder="Username"
			/>
			</form>
			{/* <input
				type="email"
				onChange={(e) => setEmail(e.target.value)}
				value={email}
				placeholder="Email"
			/> */}
			<form action2 = "">
			<input
				type="password"
				onChange={(e) => setPassword(e.target.value)}
				value={password}
				placeholder="Password"
			/>
			</form>
			<br></br>
			<button type="submit" onClick={login} style = {{width: "175px", height: "35px"}}>
				Login
			</button>
			<p style= {{ fontSize: 30 }}> New User? Sign up below <br></br> Forgot Password?</p>
			<form action3 = "">
			<button type="Guest" onClick={guest} style = {{width: "175px", height: "35px"}}>
				<a href="/Guest"> Use As Guest </a>
			</button>
			<br></br>
			<br></br>
			<button type="Sign Up" onClick={signUp} style = {{width: "175px", height: "35px"}}>
				<a href="/SignUp"> Create Account </a>
			</button>
			</form>
		</>
		) : (
		<>
			<h1>Currently Logged In</h1>
			<button onClickCapture={logout}> Logout</button>
		</>
		)}
	</div>
	</div>
	</>
);
}

export default SignInPage;