package bbs.board.controller;

import bbs.board.domain.Member;
import bbs.board.dto.LoginDTO;
import bbs.board.dto.MemberDTO;
import bbs.board.dto.common.ResponseDTO;
import bbs.board.dto.response.LoginResponseDTO;
import bbs.board.service.LoginService;
import bbs.board.service.MemberService;
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
public class MemberController {
    private final LoginService loginService;
    private final MemberService memberService;

    @PostMapping("/member/add")
    public ResponseEntity<ResponseDTO> addMember(@Valid @RequestBody MemberDTO memberDTO){
        loginService.saveMember(memberDTO);
        return ResponseEntity.ok(new ResponseDTO("OK", null));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        Member loginedMember = loginService.login(loginDTO);
        return ResponseEntity.ok(new LoginResponseDTO(loginedMember));
    }


}
