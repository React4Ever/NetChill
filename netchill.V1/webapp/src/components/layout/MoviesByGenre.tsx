import React from 'react';
import Row from "./Row";

function MoviesByGenre() {
  return (
    <div>
      <Row title={"NETFLIX ORIGINALS"} type={"netflix"}  isLargerRow={true}/>
      {/*<Row title={"Trending"} type={"trending"} isLargerRow={false}/>
      <Row title={"Top Rated"} type={"topRated"} isLargerRow={false}/>*/}
      <Row title={"Action"} queryParam={28} isLargerRow={false}/>
      {/*<Row title={"Comedy"} queryParam={"comedy"} isLargerRow={false}/>
      <Row title={"Horror"} queryParam={"horror"} isLargerRow={false}/>
      <Row title={"Romance"} queryParam={"romance"} isLargerRow={false}/>
      <Row title={"Documentaries"} queryParam={"documentaries"} isLargerRow={false}/>*/}
    </div>
  );
}

export default MoviesByGenre;
