package bbs.board.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date           : 2024-12-03
 * created by     : 임경재
 * description    :
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("board") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                .pathsToMatch("/board/**") // group에 포함될 API endpoint 경로
                .addOpenApiCustomizer(openApi ->
                        openApi.setInfo(new Info()
                                        .title("board api") // API 제목
                                        .description("게시판 업무 처리를 위한 API") // API 설명
                                        .version("1.0.0") // API 버전
                                        )
                )
                .build();
    }

    @Bean
    public GroupedOpenApi memberGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("member") // group 설정 (API들을 그룹화시켜 그룹에 속한 API들만 확인할 수 있도록 도와줌)
                .pathsToMatch("/member/**") // group에 포함될 API endpoint 경로
                .addOpenApiCustomizer(openApi ->
                        openApi.setInfo(new Info()
                                .title("member api") // API 제목
                                .description("member 업무 처리를 위한 API") // API 설명
                                .version("1.0.0") // API 버전
                        )
                )
                .build();
    }
}