package bbs.board.member.repository;

import bbs.board.member.entity.Member;
import bbs.board.auth.dto.LoginDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public Member login (LoginDTO loginDTO){
        return em.createQuery("select m from Member m where m.email=:email and m.password=:password", Member.class)
                .setParameter("email", loginDTO.getEmail())
                .setParameter("password", loginDTO.getPassword()).getResultList().stream().findFirst().orElse(null);
    }

    public Optional<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }

    public void deleteAll(){
        em.createQuery("delete from Member").executeUpdate();
    }

}
