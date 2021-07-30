package org.young.movie_review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MovieReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieReviewApplication.class, args);
    }

}
