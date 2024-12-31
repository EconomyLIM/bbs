package bbs.board.config;

import bbs.board.category.repository.CategoryRepository;
import bbs.board.board.service.BoardService;
import bbs.board.auth.service.LoginService;
import bbs.board.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@Configuration
public class InitConfig {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final BoardService boardService;

    @Autowired
    public InitConfig(final MemberService memberService, final CategoryRepository categoryRepository, final BoardService boardService) {
        this.memberService = memberService;
        this.categoryRepository = categoryRepository;
        this.boardService = boardService;
    }

    @Bean
    @Profile("test")
    public TestDataInit testDataInit() {
        return new TestDataInit(memberService, categoryRepository, boardService);
    }

}
