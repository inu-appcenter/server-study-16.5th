package org.todo.todo.global.security.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;
import org.todo.todo.global.error.ErrorResponse;
import org.todo.todo.global.error.code.CustomErrorCode;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (JwtException e){
            setErrorResponse(request, response, e);
        }
    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, JwtException e) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse<String> errorResponse = new ErrorResponse<>(
                Integer.valueOf(CustomErrorCode.JWT_NOT_VALID.toString()),
                e.getMessage()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
