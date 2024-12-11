//package bbs.board.security;
//
//import bbs.board.domain.Member;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
///**
// * date           : 2024-12-10
// * created by     : 임경재
// * description    :
// */
//@Getter
//public class UserPrincipal implements UserDetails {
//
//    private Member user;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserPrincipal(Member user) {
//        this.user = user;
//        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));
//    }
//
//    /**
//     * UserDetails method implements
//     */
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return String.valueOf(user.getId());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
