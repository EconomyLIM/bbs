package bbs.board.auth;

import bbs.board.dto.AuthPrincipalMemberDTO;
import bbs.board.dto.MemberDTO;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * date           : 2024-12-13
 * created by     : 임경재 
 * description    : 
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        try {
            if (token != null) {
                if (jwtTokenProvider.validateToken(token)) {
                    String email = jwtTokenProvider.getEmailFromToken(token);
                    String nickname = jwtTokenProvider.getClaimFromToken("nickname", token);// ???

                    AuthPrincipalMemberDTO authPrincipalMemberDTO = new AuthPrincipalMemberDTO(email, nickname);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(authPrincipalMemberDTO, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                   throw new CustomException(ErrorCode.BAD_REQUEST);
                }
            }
        }catch (SecurityException e){
            setJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return; // 요청 중단
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/login") || path.equals("/member/add");
    }

    private void setJsonResponse(final HttpServletResponse response, final int scUnauthorized, final String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(scUnauthorized);
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
