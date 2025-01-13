package bbs.board.auth;

import bbs.board.redis.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * date           : 2024-12-10
 * created by     : 임경재
 * description    :
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtFilter;
    private final String [] requestWithoutPermission = {"/login", "/logout", "/member/add", "/swagger-ui/**", "/v3/api-docs/**", "/board/search", "/actuator/**"};

    public SecurityConfig(final JwtTokenProvider jwtTokenProvider, final CustomAuthenticationEntryPoint customAuthenticationEntryPoint, final RedisUtil redisUtil) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider, redisUtil);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) ->
                        requests.requestMatchers(requestWithoutPermission).permitAll() // 인증 불필요
                                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
//                .sessionManagement(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptions-> exceptions.authenticationEntryPoint(customAuthenticationEntryPoint))
                .logout(AbstractHttpConfigurer::disable // Spring Security의 기본 로그아웃 비활성화
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
