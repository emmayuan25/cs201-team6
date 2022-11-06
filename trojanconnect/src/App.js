import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import './App.css';
import ChatPage from './pages/ChatPage';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route exact path='/message' element={<ChatPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
