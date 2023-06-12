import React from 'react';
import './App.css';
import Row from './Row';
import requests from './requests';
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>C'est pas encore fini ...</h1>
      </header>
        <Row title={'NETFLIX ORIGINALS'} fetchUrl={requests.fetchNetflixOriginals} isLargerRow/>
        <Row title={'Trending now'} fetchUrl={requests.fetchTrending}/>
        <Row title={'Top Rated'} fetchUrl={requests.fetchTopRated}/>
        <Row title={'Action Movies'} fetchUrl={requests.fetchActionMovies}/>
        <Row title={'Comedy Movies'} fetchUrl={requests.fetchComedyMovies}/>
        <Row title={'Horror Movies'} fetchUrl={requests.fetchHorrorMovies}/>
        <Row title={'Romance Movies'} fetchUrl={requests.fetchRomanceMovies}/>
        <Row title={'Documentaries'} fetchUrl={requests.fetchDocumentaries}/>

    </div>
  );
}

export default App;
