package com.code2go.moviecatalogservice.resource;

import com.code2go.moviecatalogservice.model.CatalogItem;
import com.code2go.moviecatalogservice.model.Movie;
import com.code2go.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private WebClient.Builder weBuilder;*/

    @RequestMapping("/{userId}")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject(
                "http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream().map(rating -> {
                    // For each movie ID, call movie info service and get details
                    Movie movie = restTemplate.getForObject(
                            "http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

                    // Put them all together
                    return new CatalogItem(movie.getName(), "Desc", rating.getRating());

                    //Using Web Client builder - New reactive way instead of RestTemplate
            /*Movie movie = weBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

                })
                .collect(Collectors.toList());

    }
}
