package bbs.board.member.controller;

import bbs.board.common.dto.BasicResponse;
import bbs.board.member.dto.MemberDTO;
import bbs.board.member.dto.MemberUpdateRequest;
import bbs.board.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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
@Tag(name = "회원 관련", description = "회원 가입, 수정, 탈퇴")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/add")
    public ResponseEntity<BasicResponse> addMember(@Valid @RequestBody MemberDTO memberDTO){
        BasicResponse response = memberService.saveMember(memberDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/member")
    public ResponseEntity<BasicResponse> updateMember(@Valid @RequestBody MemberUpdateRequest request){
        BasicResponse response = memberService.update(request);
        return ResponseEntity.ok(response);
    }


}
