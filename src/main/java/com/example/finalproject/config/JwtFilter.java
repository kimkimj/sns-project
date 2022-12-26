package com.example.finalproject.config;

import com.example.finalproject.domain.entity.User;
import com.example.finalproject.service.UserService;
import com.example.finalproject.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
// token이 있는지 매번 체크
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);


        // token 안보내면 block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("Authorizaiton을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // authorization에서 token 꺼내기
        String token;

        try {
            token = authorization.split(" ")[1];
        } catch (Exception e) {
            log.error("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }


        // token이 expire됐는지 확인
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            log.error("token이 만료 되었습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // username 토큰에서 꺼내기
        String username = JwtTokenUtil.getUsername(token, secretKey);
        log.info("username:{}", username);

        // usernmae으로 userRole을 꺼내기
        User user = userService.getUserByUsername(username);
        log.info("userRole: {}", user.getRole());

        //권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("USER")));

        // detail들을 넣어준다
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
