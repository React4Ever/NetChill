import React from 'react';
import './App.css';
import Row from './Row';
import requests from './requests';
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Welcome to NetChill !</h1>
      </header>
        <Row title={'NETFLIX ORIGINALS'} fetchUrl={requests.fetchNetflixOriginals}/>
        <Row title={'Trending now'} fetchUrl={requests.fetchTrending}/>
    </div>
  );
}

export default App;
