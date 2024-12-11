package bbs.board.security;

import bbs.board.domain.Member;
import bbs.board.exception.CustomException;
import bbs.board.exception.ErrorCode;
import bbs.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * date           : 2024-12-10
 * created by     : 임경재
 * description    :
 */
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(username)
//                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
//        return new UserPrincipal(member);
//    }
//}
