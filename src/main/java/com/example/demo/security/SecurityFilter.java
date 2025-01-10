package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTUtilities jwtUtilities;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityFilter(UserDetailsService userDetailsService,
                          JWTUtilities jwtUtilities,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {

        this.userDetailsService = userDetailsService;
        this.jwtUtilities = jwtUtilities;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            boolean isUserAuthenticated = securityContext.getAuthentication()!=null;
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") || isUserAuthenticated) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.getSubject(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(userDetails==null) {
                filterChain.doFilter(request, response);
                return;
            }

            var upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            upat.setDetails(new WebAuthenticationDetailsSource());

            securityContext.setAuthentication(upat);

        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
            filterChain.doFilter(request, response);
        }
    }
}
