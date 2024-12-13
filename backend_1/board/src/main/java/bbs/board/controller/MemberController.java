package bbs.board.controller;

import bbs.board.domain.Member;
import bbs.board.dto.LoginDTO;
import bbs.board.dto.MemberDTO;
import bbs.board.dto.common.BasicResponse;
import bbs.board.dto.response.LoginBasicResponse;
import bbs.board.service.LoginService;
import bbs.board.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * date           : 2024-11-26
 * created by     : 임경재
 * description    :
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
public class MemberController {
    private final LoginService loginService;
    private final MemberService memberService;

    @PostMapping("/member/add")
    public ResponseEntity<BasicResponse> addMember(@Valid @RequestBody MemberDTO memberDTO){
        loginService.saveMember(memberDTO);
        return ResponseEntity.ok(new BasicResponse("OK", null));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginBasicResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response){
        LoginBasicResponse loginBasicResponse = loginService.login(loginDTO);
        return ResponseEntity.ok(loginBasicResponse);
    }
}
