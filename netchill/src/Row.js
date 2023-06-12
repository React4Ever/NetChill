import React, {useState, useEffect} from 'react'
import axios from './axios';
import "./Row.css";
import YouTube from "react-youtube";
import movieTrailer from 'movie-trailer'

const base_url = "https://image.tmdb.org/t/p/original/";

function Row({title, fetchUrl, isLargerRow}) {
    const [movies, setMovies] = useState([]);
    const [trailerUrl, setTrailerUrl] = useState("");
    //run a piece of code in a specific condition -> mettre les images au moment du chargement
    useEffect(() => { // agit que au chargement de la page
        //si [], faire une fois quand les lignes charges et plus refaire
        async function fetchData() {
            const request = await axios.get(fetchUrl); //cherche l'url dans le fichier requests.js
            console.log(request.data);
            setMovies(request.data.results);
            return request;
        }

        fetchData();
    }, [fetchUrl]) //useEffect depend de fetchUrl
    const opts = {
        height: '390',
        width: '100%',
        playerVars: {
            // https://developers.google.com/youtube/player_parameters
            autoplay: 1,
        }
    }

    const handleClick = (movie) => {
        if (trailerUrl) {
            setTrailerUrl('');
        } else {
            movieTrailer(movie?.name || "")
                .then(url => {
                    const urlParams = new URLSearchParams(new URL(url).search); //avoir la partie recherche de l'url
                    setTrailerUrl(urlParams.get('v'));//retourne la valeur après 'v='
                }).catch(error => console.log(error))
        }
    }
    return (
        <div className="row">
            <h2>{title}</h2>
            <div className="row_posters">
                {}
                {movies.map(movie => (
                    <img
                        key={movie.id}
                        onClick={() => handleClick(movie)}
                        className={`row_poster ${isLargerRow && "row_poster_large"}`}
                        src={base_url + (isLargerRow ? movie.poster_path : movie.backdrop_path)}
                        alt={movie.name}/>
                ))}
            </div>
            {trailerUrl && <YouTube videoId={trailerUrl} opts={opts}/>} {/*si on a le trailerUrl alors on met la video*/ }
        </div>
    )
}

export default Row