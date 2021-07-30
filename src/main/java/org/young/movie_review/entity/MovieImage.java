package org.young.movie_review.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
//연관 관계
@ToString(exclude="movie")
public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch= FetchType.LAZY)
    private Movie movie;
}
