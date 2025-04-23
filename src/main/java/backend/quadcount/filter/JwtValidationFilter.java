package backend.quadcount.filter;

import backend.quadcount.model.User;
import backend.quadcount.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header =request.getHeader("Authorization");
        System.out.println(header+"header");
        if(header!=null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if(jwtTokenUtil.validateToken(token)) {
                Claims claims = jwtTokenUtil.getClaimsFromToken(token);
                String subject = claims.getSubject(); //1,john@test.com,admin
                String[] subArr = subject.split(",");
                User u = new User();
                u.setId(Long.parseLong(subArr[0]));
                u.setEmail(subArr[1]);

//                set SecurityContext
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(u, null,null);
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }
    }

}
