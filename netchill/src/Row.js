import React, {useState, useEffect} from 'react'
import axios from './axios';
import "./Row.css";

const base_url = "https://image.tmdb.org/t/p/original/";
function Row({title, fetchUrl, isLargerRow}){
    const [movies, setMovies] = useState([]);
    //run a piece of code in a specific condition -> mettre les images au moment du chargement
    useEffect(()=>{ // agit que au chargement de la page
        //si [], faire une fois quand les lignes charges et plus refaire
        async function fetchData(){
            const request = await axios.get(fetchUrl); //cherche l'url dans le fichier requests.js
            setMovies(request.data.results);
            return request;
        }
        fetchData();
    }, [fetchUrl]) //useEffect depend de fetchUrl
    return(
        <div className="row">
            <h2>{title}</h2>
            <div className="row_posters">
                {}
                {movies.map(movie =>(
                    <img key={movie.id} className={`row_poster ${isLargerRow && "row_poster_large"}`} src={base_url+ (isLargerRow ? movie.poster_path : movie.backdrop_path)} alt={movie.name}/>
                ))}
            </div>
        </div>
    )
}

export default Row