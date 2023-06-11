import axios from 'axios';

/** base url pour faire des requêtes à la BD pour film*/
const instance = axios.create({
    baseURL: "https://api.themoviedb.org/3"
});

export default  instance;