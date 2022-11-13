import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import './App.css';
import ChatPage from './pages/ChatPage';
import HomePage from './pages/HomePage';
import SearchPage from './pages/SearchPage';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path='/' element={<HomePage />} />
          <Route exact path='/search' element={<SearchPage />} />
          <Route exact path='/message' element={<ChatPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
