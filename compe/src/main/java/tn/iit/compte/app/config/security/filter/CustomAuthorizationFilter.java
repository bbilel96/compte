package tn.iit.compte.app.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{
    @Value("${app.jwt.secret}")
    private String secret = "abcdefghdjklmnOPQRSTUVWXYZ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info(request.getServletPath());
        if (
                request.getServletPath().equals("/login") || request.getServletPath().equals("/user") ){
            filterChain.doFilter(request, response);
        }
        else {
            log.info(request.getServletPath());
           String authorizationHeader = request.getHeader(AUTHORIZATION);
           log.info(authorizationHeader);
            System.out.println(authorizationHeader);
           if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
               try{
                   String token = authorizationHeader.substring("Bearer ".length());
                   Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                   JWTVerifier verifier = JWT.require(algorithm).build();
                   DecodedJWT decodedJWT = verifier.verify(token);
                   String email = decodedJWT.getSubject();
                   String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                   Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                   stream(roles).forEach(role -> {
                       authorities.add(new SimpleGrantedAuthority(role));
                   });
                   UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(email, null, authorities);
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   filterChain.doFilter(request, response);
               }catch (Exception exception){
                   ResponseMessage errorMessage = new ResponseMessage("Vérifier votre données.", Behavior.FAIL);
                   log.error("Errror in CustomAuthorisationFilter class, message: {}", exception.getMessage());
                   response.setContentType(APPLICATION_JSON_VALUE);
                   response.setStatus(HttpStatus.BAD_REQUEST.value());
                   new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
               }
           } else {
               filterChain.doFilter(request, response);
           }
        }
    }
}
