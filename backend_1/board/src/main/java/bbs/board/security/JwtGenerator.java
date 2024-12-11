//package bbs.board.security;
//
//import bbs.board.domain.Member;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * date           : 2024-12-10
// * created by     : 임경재
// * description    :
// */
//@Component
//public class JwtGenerator {
//    public String generateAccessToken(final Key ACCESS_SECRET, final long ACCESS_EXPIRATION, Member member) {
//        long now = System.currentTimeMillis();
//
//        return Jwts.builder()
//                .setHeader(createHeader())
//                .setClaims(createClaims(member))
//                .setSubject(String.valueOf(member.getId()))
//                .setExpiration(new Date(now + ACCESS_EXPIRATION))
//                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String generateRefreshToken(final Key REFRESH_SECRET, final long REFRESH_EXPIRATION, Member member) {
//        long now = System.currentTimeMillis();
//
//        return Jwts.builder()
//                .setHeader(createHeader())
//                .setSubject(member.getIdentifier())
//                .setExpiration(new Date(now + REFRESH_EXPIRATION))
//                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private Map<String, Object> createHeader() {
//        Map<String, Object> header = new HashMap<>();
//        header.put("typ", "JWT");
//        header.put("alg", "HS256");
//        return header;
//    }
//
//    private Map<String, Object> createClaims(Member Member) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("Identifier", Member.getIdentifier());
//        claims.put("Role", Member.getRole());
//        return claims;
//    }
//}
