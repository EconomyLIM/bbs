package bbs.board.config;

import bbs.board.category.repository.CategoryRepository;
import bbs.board.service.BoardService;
import bbs.board.service.LoginService;
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

    private final LoginService loginService;
    private final CategoryRepository categoryRepository;
    private final BoardService boardService;

    @Autowired
    public InitConfig(final LoginService loginService, final CategoryRepository categoryRepository, final BoardService boardService) {
        this.loginService = loginService;
        this.categoryRepository = categoryRepository;
        this.boardService = boardService;
    }

    @Bean
    @Profile("test")
    public TestDataInit testDataInit() {
        return new TestDataInit(loginService, categoryRepository, boardService);
    }

}
