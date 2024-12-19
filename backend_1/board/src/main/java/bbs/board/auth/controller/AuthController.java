package bbs.board.auth.controller;

import bbs.board.auth.JwtTokenProvider;
import bbs.board.auth.dto.AuthPrincipalMemberDTO;
import bbs.board.common.dto.BasicResponse;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * date           : 2024-12-18
 * created by     : 임경재
 * description    :
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "jwt 토큰 확인", description = "헤더에서 jwt 토큰 유효성 검사를 할 수 있는 API")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/validate-token")
    public ResponseEntity<BasicResponse> validateToken(
            @RequestHeader("Authorization") String token
            , @AuthenticationPrincipal final AuthPrincipalMemberDTO memberDto
    ) {

        // "Bearer " 제거
        String jwt = token.replace("Bearer ", "");

        // 토큰 검증 로직 (예: 유효기간, 서명 확인)
        boolean isValid = jwtTokenProvider.validateToken(jwt, memberDto.getEmail());

        if (isValid) {
            return ResponseEntity.ok(new BasicResponse());
        }else{
            throw new CustomException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
