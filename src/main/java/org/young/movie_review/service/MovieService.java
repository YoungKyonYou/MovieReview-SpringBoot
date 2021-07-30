package org.young.movie_review.service;



import org.young.movie_review.dto.MovieDTO;
import org.young.movie_review.dto.MovieImageDTO;
import org.young.movie_review.dto.PageRequestDTO;
import org.young.movie_review.dto.PageResultDTO;
import org.young.movie_review.entity.Movie;
import org.young.movie_review.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    MovieDTO getMovie(Long mno);


    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage>movieImages, Double avg, Long reviewCnt){
        MovieDTO movieDTO=MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList=movieImages.stream().map(movieImage->{
            return MovieImageDTO.builder().imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());
        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
        Map<String, Object> entityMap=new HashMap<>();

        Movie movie=Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        System.out.println("Mno:"+movieDTO.getMno());

        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList=movieDTO.getImageDTOList();

        if(imageDTOList!=null && imageDTOList.size()>0){
            List<MovieImage> movieImageList=imageDTOList.stream().map(movieImageDTO->{
                MovieImage movieImage=MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList",movieImageList);
        }
        return entityMap;
    }
}
