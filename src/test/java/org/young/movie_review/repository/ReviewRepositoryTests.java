package org.young.movie_review.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.young.movie_review.entity.Member;
import org.young.movie_review.entity.Movie;
import org.young.movie_review.entity.Review;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
/*@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)*/
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;



    @Test
    public void insertMovieReviews(){
        IntStream.rangeClosed(1,200).forEach(i->{
            Long mno=(long)(Math.random()*100)+1;
            Long mid=((long)(Math.random()*100)+1);
            Member member= Member.builder().mid(mid).build();

            Review movieReview=Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("feeling about this movie is..."+i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }

    @Rollback(false)
    @Test
    public void testGetMovieReviews(){
        Movie movie=Movie.builder().mno(92L).build();
        List<Review> result=reviewRepository.findByMovie(movie);
        result.forEach(movieReview->{
            System.out.print(movieReview.getReviewnum());
            System.out.print("\t"+movieReview.getGrade());
            System.out.print("\t"+movieReview.getText());
            System.out.print("\t"+movieReview.getMember().getEmail());
            System.out.println("-------------------------------");
        });
    }

}
