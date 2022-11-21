import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import './App.css';
import ChatPage from './pages/ChatPage';
import HomePage from './pages/HomePage';
import SearchPage from './pages/SearchPage';
import SignInPage from './pages/SignInPage';
import ProfilePage from './pages/ProfilePage';

function App() {
  const [isLoggedin, setIsLoggedin] = useState(false);
  const logout = () => {
    localStorage.removeItem('token-info');
    setIsLoggedin(false);
  }
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path='/' element={<SignInPage logout={logout} isLoggedin={isLoggedin} setIsLoggedin={setIsLoggedin}/>} />
          <Route exact path='/home' element={<HomePage />} />
          <Route exact path='/search' element={<SearchPage />} />
          <Route exact path='/message' element={<ChatPage />} />
          <Route exact path='/profile' element={
              // isLoggedin ? 
              <ProfilePage /> 
              // : <SignInPage />
            } />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
