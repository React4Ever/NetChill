package com.netchill.webservices.api;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.plume.jersey.security.permission.PublicApi;

import com.netchill.api.moviedb.MovieDbApiClient;
import com.netchill.api.moviedb.models.Movie;
import com.netchill.api.moviedb.models.MovieDbPaginatedResponse;
import com.netchill.api.moviedb.models.NetflixMovie;
import com.netchill.services.movie.MovieDbService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.netchill.services.configuration.ConfigurationService;

import java.util.List;

@Path("/movies")
@Tag(name = "MovieDB", description = "MovieDB API")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class MovieWs {
    private final ConfigurationService configurationService;
    private final MovieDbService movieDbService;

    @Inject
    public MovieWs(ConfigurationService configurationService, MovieDbService movieDBApiClient) {
        this.configurationService = configurationService;
        this.movieDbService = movieDBApiClient;
    }
    /*
    @GET
    @Path("/genre/{genre}")
    @Operation(description = "MovieDB API")
    public String test(@PathParam("genre") String genre) {
        //mettre API_KEY dans .conf ainsi que baseUrl dans MovieDB
        final String API_KEY = "a0c1e55338f3c5d80fc1e6fbef225e0e";
        final String fetchNetflixOriginals = "/discover/tv?api_key="+API_KEY+"&with_networks=213";
        final String fetchTrending = "/trending/all/week?api_key="+API_KEY+"&language=en-US";
        final String fetchTopRated = "/movie/top_rated?api_key="+API_KEY+"&language=en-US";
        final String fetchActionMovies ="/discover/movie?api_key="+API_KEY+"&with_genres=28";
        final String fetchComedyMovies ="/discover/movie?api_key="+API_KEY+"&with_genres=35";
        final String fetchHorrorMovies = "/discover/movie?api_key="+API_KEY+"&with_genres=27";
        final String fetchRomanceMovies = "/discover/movie?api_key="+API_KEY+"&with_genres=10749";
        final String fetchDocumentaries = "/discover/movie?api_key="+API_KEY+"&with_genres=99";

        if(genre.equals("trending")) genre = fetchTrending;
        else if (genre.equals("netflixOriginals")) genre = fetchNetflixOriginals;
        else if (genre.equals("topRated")) genre = fetchTopRated;
        else if (genre.equals("action")) genre = fetchActionMovies;
        else if (genre.equals("comedy")) genre = fetchComedyMovies;
        else if (genre.equals("horror")) genre = fetchHorrorMovies;
        else if (genre.equals("romance")) genre = fetchRomanceMovies;
        else if (genre.equals("documentaries")) genre = fetchDocumentaries;
        //chercher méthode plus optimal ou sinon continuer

        System.out.println(genre);

        return this.movieDBApiClient.getMovies(genre);
    }

     */
    @GET
    @Path("/top-rated")
    public List<Movie> getTopRated() {
        return movieDbService.getTopRated();
    }
    @GET
    @Path("/netflix-originals")
    public List<NetflixMovie> getNetflixOriginals(){
        return movieDbService.getTopNetflixOriginals();
    }
    @GET
    @Path("/genre/{genre}")
    public List<Movie> getTopMovieByGenre(@PathParam("genre") int genre){
        return movieDbService.getTopMoviesByGenre(genre);
    }
}
