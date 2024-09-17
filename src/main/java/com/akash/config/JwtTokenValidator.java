package com.akash.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

       //Standard formate of the JWT is
        //Bearer Token --> so here cut the bearer part
        String jwt = request.getHeader(JwtConstants.JWT_HEADER);
        if(jwt!=null){
            jwt = jwt.substring(7);
            try{
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                //      Here in the above line we get role in the form of ROLE_CUSTOMER, ROLE_ADMIN we get in string formste,, but we
//                have to convert it string to Grantedauthority

                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            }
            catch (Exception e){
                throw new BadCredentialsException("Invalid_Token....");
            }

        }
        filterChain.doFilter(request,response);
    }
}
