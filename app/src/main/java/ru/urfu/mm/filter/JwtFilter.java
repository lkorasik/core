package ru.urfu.mm.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.urfu.mm.service.JWTService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    @Autowired
    public JwtFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if((header != null) && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            try {
                username = jwtService.getUsername(jwt);
            } catch (ExpiredJwtException expiredJwtException) {
                System.out.println("Время жизни токена вышло");
            }
        }
        if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, jwtService.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}