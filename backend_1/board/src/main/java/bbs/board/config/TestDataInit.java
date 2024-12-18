package bbs.board.config;

import bbs.board.category.entity.Category;
import bbs.board.category.repository.CategoryRepository;
import bbs.board.domain.Member;
import bbs.board.dto.MemberDTO;
import bbs.board.dto.request.BoardRegisterRequestDTO;
import bbs.board.service.BoardService;
import bbs.board.service.LoginService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@RequiredArgsConstructor
public class TestDataInit {

    private final LoginService loginService;
    private final CategoryRepository categoryRepository;
    private final BoardService boardService;

    @Transactional
    @PostConstruct
    public void init(){
        Member member = new Member("test@test.com");
        MemberDTO memberDTO = new MemberDTO(member);
        memberDTO.setPassword(member.getPassword());
        loginService.saveMember(memberDTO);

        Category topCategory1 = new Category("야구", 1);
        Category topCategory2 = new Category("축구", 1);
        Category topCategory3 = new Category("배드민턴", 1);
        Category topCategory4 = new Category("탁구", 1);

        categoryRepository.save(topCategory1);
        categoryRepository.save(topCategory2);
        categoryRepository.save(topCategory3);
        categoryRepository.save(topCategory4);

        for (int i = 0; i < 210; i++) {
            BoardRegisterRequestDTO requestDTO = new BoardRegisterRequestDTO("title" + i, "content" + i, member.getEmail(), member.getNickname(), (i % 4L) + 1);
            boardService.save(requestDTO);
        }
    }
}
