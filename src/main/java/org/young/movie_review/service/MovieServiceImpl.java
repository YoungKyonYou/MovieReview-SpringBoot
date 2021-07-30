package org.young.movie_review.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.young.movie_review.dto.MovieDTO;
import org.young.movie_review.dto.PageRequestDTO;
import org.young.movie_review.dto.PageResultDTO;
import org.young.movie_review.entity.Movie;
import org.young.movie_review.entity.MovieImage;
import org.young.movie_review.repository.MovieImageRepository;
import org.young.movie_review.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap=dtoToEntity(movieDTO);
        Movie movie=(Movie)entityMap.get("movie");
        List<MovieImage> movieImageList=(List<MovieImage>)entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage->{
            movieImageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable=requestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result=movieRepository.getListPage(pageable);


        //이걸 왜 Object a 로 안 받고 Object[] a로 받아야되냐면
        //result의 형이 Page<Object[]>이다 즉 Object[] 형태로 여러개가 있는 것이다.
        //그래서 이 Object[]가 여러 개 있는 것, 즉 Object 배열을 각각 받기 위해서 아래와 같이 구성한다.
        for(Object[] a: result){
            for(Object b: a){
                System.out.println(a[0].toString());
                System.out.println(((Movie)a[0]).getRegDate());
                System.out.println(a[1].toString());
                System.out.println(a[2].toString());
                System.out.println(a[3].toString());
            }

        }

        //이 fn는 나중에 new PageResultDTO를 하면서 moveiDTO의 배열로 만들어 지는데
        //이것을 PageResultDTO가면 Collection의 List로 바꿔서 변수에 넣어주게 된다.
        //arr-> 이부분을 보면 지금 Object 배열에 요소 하나하나를 꺼내서 entitiesToDTO에
        //넣어줌으로써  각 Object를 MovieDTO로 변환해주는 것이다.
        Function<Object[], MovieDTO> fn=(arr->entitiesToDTO(
                (Movie)arr[0],
                //배열로 되어 있는 것을 리스트 컬랙션으로 바꿔야 되서 asList썼다.
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double)arr[2],
                (Long)arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        System.out.println("MNO:"+mno);
        List<Object[]> result=movieRepository.getMovieWithAll(mno);

        Movie movie=(Movie)result.get(0)[0];

        List<MovieImage> movieImageList=new ArrayList<>();

        result.forEach(arr->{
            MovieImage movieImage=(MovieImage)arr[1];
            movieImageList.add(movieImage);
        });

        Double avg=(Double)result.get(0)[2];
        Long reviewCnt=(Long) result.get(0)[3];

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
