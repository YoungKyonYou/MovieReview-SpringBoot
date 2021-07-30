package org.young.movie_review.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.young.movie_review.entity.Member;
import org.young.movie_review.entity.Movie;
import org.young.movie_review.entity.Review;

import javax.persistence.criteria.ListJoin;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @EntityGraph(attributePaths = {"member"},type= EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member=:member")
    void deleteByMember(Member member);
}
