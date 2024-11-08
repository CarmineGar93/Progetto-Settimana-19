package CarmineGargiulo.Progetto_Settimana_19.security;

import CarmineGargiulo.Progetto_Settimana_19.entities.User;
import CarmineGargiulo.Progetto_Settimana_19.exceptions.UnauthorizedException;
import CarmineGargiulo.Progetto_Settimana_19.service.UsersService;
import CarmineGargiulo.Progetto_Settimana_19.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWT jwt;
    @Autowired
    private UsersService usersService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Authorization header missing or invalid format");
        String token = authHeader.replace("Bearer ", "");
        jwt.verifyToken(token);
        String userId = jwt.extractUserIdFromToken(token);
        User current = usersService.findUserById(UUID.fromString(userId));
        Authentication authentication = new UsernamePasswordAuthenticationToken(current, null,
                current.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


}
