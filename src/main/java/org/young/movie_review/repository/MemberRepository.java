package org.young.movie_review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.young.movie_review.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
