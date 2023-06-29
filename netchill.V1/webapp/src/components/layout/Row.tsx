import React, {useState, useEffect, Children} from 'react';
import "../../../public/assets/css/row.css";
import MovieApi, {Movie} from "../../api/session/MovieApi";
import ApiHttpClient from "../../api/ApiHttpClient";

const base_url = "https://image.tmdb.org/t/p/original/";

type Props = {
  title: string,
  type?: string,
  queryParam?: number,
  isLargerRow: boolean,
}

const apiHttpClient = new ApiHttpClient();
const movieApi = new MovieApi(apiHttpClient);

function Row(children: Props) {
  const [movie, setMovie] = useState<Movie>();

  useEffect(() => { // agit que au chargement de la page
      //si [], faire une fois quand les lignes charges et plus refaire
      if (children.queryParam) {
        movieApi.getMoviesByGenre(children.queryParam)
          .then((res) => {
            setMovie(res);
          })
          .catch((err) => console.log(err))
      } else {
        if (children.title.includes("netflix")) {
          movieApi.getNetflixOriginals().then((res) => {
            setMovie(res);
          })
            .catch((err) => console.log(err))
        }
      }
    },
    [children.queryParam]) //useEffect depend de fetchUrl


  return (
    <div className="row">
      <h2>{children.title}</h2>
      <div className="row_posters">
        {movie.map(movie => (
          <div key={movie.id} className={`row_poster ${children.isLargerRow && "row_poster_large"}`}>
            <img
              src={base_url + (children.isLargerRow ? movie.poster_path : movie.backdrop_path)}
              alt={movie.name}/>
            <h3 className={'title'}>{movie.name ? movie.name : movie.title}</h3>
          </div>
        ))}
      </div>
    </div>
  )
}

export default Row
