package org.young.movie_review.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.young.movie_review.entity.Member;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    @Rollback(false)
    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i->{
           Member member=Member.builder()
                   .email("r"+i+"@zerock.org")
                   .pw("1111")
                   .nickname("reviewer"+i).build();
           memberRepository.save(member);
        });
    }

    @Rollback(false)
    @Transactional
    @Commit
    @Test
    public void testDeleteMember(){
        Long mid=1L;
        Member member=Member.builder().mid(mid).build();

        memberRepository.deleteById(mid);
        reviewRepository.deleteByMember(member);
    }
}
