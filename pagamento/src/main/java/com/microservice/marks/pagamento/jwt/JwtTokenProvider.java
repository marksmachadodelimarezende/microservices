package com.microservice.marks.pagamento.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-token}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = new UserDetails() {
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
            public String getPassword() {
                return "";
            }
            public String getUsername() {
                return "";
            }
            public boolean isAccountNonExpired() {
                return true;
            }
            public boolean isAccountNonLocked() {
                return true;
            }
            public boolean isCredentialsNonExpired() {
                return true;
            }
            public boolean isEnabled() {
                return true;
            }
        };
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ") )
            return bearerToken.substring(7, bearerToken.length());

        return null; //TODO - MARKS: Este null, em caso de requisição não autorizada, esta reportando status 500. Criar exception para retornar 403 (status de não autorizado)
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
