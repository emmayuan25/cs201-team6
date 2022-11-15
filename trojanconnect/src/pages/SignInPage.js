import { useState, useEffect } from 'react';
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

return (
	<>
	<div style={{ textAlign: 'center' }}>
		<h1>Trojan Connect</h1>
		{!isLoggedin ? (
		<>
			<form action="">
			<input
				type="text"
				onChange={(e) => setName(e.target.value)}
				value={name}
				placeholder="Name"
			/>
			<input
				type="email"
				onChange={(e) => setEmail(e.target.value)}
				value={email}
				placeholder="Email"
			/>
			<input
				type="password"
				onChange={(e) => setPassword(e.target.value)}
				value={password}
				placeholder="Password"
			/>
			<button type="submit" onClick={login}>
				GO
			</button>
			</form>
		</>
		) : (
		<>
			<h1>Currently Logged In</h1>
			<button onClickCapture={logout}>Logout</button>
		</>
		)}
	</div>
	</>
);
}

export default SignInPage;