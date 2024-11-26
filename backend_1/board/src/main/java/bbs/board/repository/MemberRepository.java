package bbs.board.repository;

import bbs.board.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public void save(Member member){
        em.persist(member);
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

}
