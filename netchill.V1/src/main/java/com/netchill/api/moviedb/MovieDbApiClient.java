package com.netchill.api.moviedb;
import com.netchill.api.moviedb.models.Movie;
import com.netchill.api.moviedb.models.MovieDbPaginatedResponse;
import com.netchill.api.moviedb.models.NetflixMovie;
import com.netchill.services.configuration.ConfigurationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
public class MovieDbApiClient {
  // Logger pour ecrire des logs plusieur niveau .info .debug .error .warn
  private static final Logger logger = LoggerFactory.getLogger(MovieDbApiClient.class);
  private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
  private static final int NETFLIX_ID = 213;
  private final ObjectMapper objectMapper;
  private final MovieDbApiRetrofit movieDbApi;
  private final ConfigurationService configurationService;

  @Inject
  private MovieDbApiClient(ObjectMapper objectMapper, ConfigurationService configurationService) {
    this.configurationService = configurationService;
    JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory.create(objectMapper);
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(this.configurationService.getMovieDbApiBaseUrl())
        .client(
            new OkHttpClient
                .Builder()
                // Si aucune réponse au bout de 60secondes, on considère que c'est une erreur
                .connectTimeout(60, TimeUnit.SECONDS)
                // Si aucune réponse au bout de 60secondes, on considère que c'est une erreur
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                // Ne pas suivre les redirections
                .followRedirects(false)
                .followSslRedirects(false)
                .addInterceptor(chain -> {
                  Request original = chain.request();
                  Request request =
                      original
                          .newBuilder()
                          // Ajout du header Content-Type: application/json
                          // Pour récupérer du JSON
                          .addHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MEDIA_TYPE_JSON))
                          .build();

                  return chain.proceed(request);
                })
                .build())
        .addConverterFactory(jacksonConverterFactory)
        .build();

    this.objectMapper = objectMapper;
    // Crée l'api grace à l'interface retrofit
    this.movieDbApi = retrofit.create(MovieDbApiRetrofit.class);
    System.out.println(retrofit.baseUrl());
  }

  public MovieDbPaginatedResponse<Movie> getMoviesByGenre(int genre, @Nullable Integer page) {
    return executeRequest(movieDbApi.getMovieByGenre(this.configurationService.getMovieDbApiKey(), genre));
  }
  public MovieDbPaginatedResponse<Movie> getTopRated(@Nullable Integer page){
    return executeRequest(movieDbApi.getTopRated(this.configurationService.getMovieDbApiKey(), "en-US"));
  }

  public MovieDbPaginatedResponse<NetflixMovie> getNetflixOriginals(@Nullable Integer page){
    return executeRequest(movieDbApi.getNetflixOriginals(
        this.configurationService.getMovieDbApiKey(),
        NETFLIX_ID,
        page
    ));
  }

  public <T> T executeRequest(Call<T> apiCall) {
    retrofit2.Response<T> response;
    try {
      response = apiCall.execute();
      if (response.isSuccessful()) {
        logger.debug("Request executed successfully");
        return response.body();
      }

      logger.error("Response is an error");
      throw new RuntimeException("HTTP response error : " + response.code() + ": " + response.message() +
          "\nbody=" + response.body() + "withRequest: " + apiCall.request().url() + " method: " + apiCall.request().method() + "request: " + objectMapper.writeValueAsString(apiCall.request().body()));
    } catch (IOException e) {
      throw new RuntimeException("Error during call to the referientiel API", e);
    }
  }
}
