package bbs.board.config;

import bbs.board.auth.dto.AuthPrincipalMemberDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * date           : 2024-12-20
 * created by     : 임경재
 * description    :
 */
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }

        AuthPrincipalMemberDTO memberDTO = (AuthPrincipalMemberDTO) authentication.getPrincipal();

        return Optional.of(memberDTO.getEmail());
    }
}
