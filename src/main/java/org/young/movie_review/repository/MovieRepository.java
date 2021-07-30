package org.young.movie_review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.young.movie_review.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    //count와 avg를 group by로 묶어서 결과를 도출함.
    //coalesce는 처음으로 null이 아닌 값을 반환하며 r.grade를 group by로 묶은 것을 평균으로 뽑아내고 grade가 없으면 0를 반환한다.
    @Query("select m, mi,avg(coalesce(r.grade,0)), count(distinct r) from Movie m "+
            "left outer join MovieImage mi on mi.movie=m "+
            "left outer join Review r on r.movie=m group by m")
    Page<Object[]> getListPage(Pageable pageable);
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) "+"from Movie m left outer join MovieImage mi on mi.movie=m "+
            "left outer join Review r on r.movie=m "+
            "where m.mno=:mno group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}
