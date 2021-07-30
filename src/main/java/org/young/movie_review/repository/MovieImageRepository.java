package org.young.movie_review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.young.movie_review.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage,Long> {
}
