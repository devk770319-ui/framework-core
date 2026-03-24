package com.framwork.core.common.cors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framwork.core.common.message.CommonMessageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AllowedOriginCorsFilter extends OncePerRequestFilter {

    private static final String FORBIDDEN_ORIGIN_CODE = "CORS-0001";

    private final CorsPolicyProperties corsPolicyProperties;
    private final ObjectMapper objectMapper;
    private final CommonMessageService commonMessageService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String origin = request.getHeader("Origin");
        if (!StringUtils.hasText(origin)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!corsPolicyProperties.getAllowedOrigins().contains(origin)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(
                    Map.of(
                            "code", FORBIDDEN_ORIGIN_CODE,
                            "message", commonMessageService.getMessage(FORBIDDEN_ORIGIN_CODE)
                    )
            ));
            return;
        }

        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
