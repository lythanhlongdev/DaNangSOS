package com.capstone2.dnsos.filters;

import com.capstone2.dnsos.component.JwtTokenUtils;
import com.capstone2.dnsos.models.main.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;

    /*
            Moi khi request toi se chay vao class nay dau tien kiem tra quyen
            1. Neu cac request nam trong isByPassToken() => true se khong can kiem tra quyen => WebSecurity => Controller
            2. Cac request khong nam trong isByPassToken() => false se kiem tra quyen
               2.1 lay chuoi "Authorization" o trong header
               2.2 kiem tra xem chuoi Auth co null hoac bat dau khong phai la "Bearer"  => tra ra ngoai le 401
               2.3 Cat chuoi sau tu "Bearer" => index 7
               2.4 dung ham extractPhoneNumber() de lay so dien thoai trong token
               2.5 Kiem tra xem lay so dien thoai thanh khong va kiem tra xem tk da duoc xac thuc chua
                   2.5.1 dung load thong tin nguoi dung bang so dien thoai
                   2.5.2 Kiem tra xem token con han su dung hay khong



    */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            //
            if (this.isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);

            if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber); //

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response); //enable bypass
        } catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }

    }

    // This is Error help me  => 403
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> byPassToken = Arrays.asList(
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/forgot_password/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/histories/media/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/address/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/families/**", apiPrefix), "GET")

                // Swagger
//                Pair.of("/api-docs","GET"),
//                Pair.of("/api-docs/**","GET"),
//                Pair.of("/swagger-resources","GET"),
//                Pair.of("/swagger-resources/**","GET"),
//                Pair.of("/configuration/ui","GET"),
//                Pair.of("/configuration/security","GET"),
//                Pair.of("/swagger-ui/**","GET"),
//                Pair.of("/swagger-ui.html", "GET"),
//                Pair.of("/swagger-ui/index.html", "GET")

        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        for (Pair<String, String> token : byPassToken) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;

//        for (Pair<String, String> item : byPassToken) {
//            if (request.getServletPath().contains(item.getFirst()) && request.getMethod().equals(item.getSecond())) {
//                return true;
//            }
//        }
//        return false;
    }

    //    @Override
//    protected void doFilterInternal(@NonNull  HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            if(isBypassToken(request)) {
//                filterChain.doFilter(request, response); //enable bypass
//                return;
//            }
//            final String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                response.sendError(
//                        HttpServletResponse.SC_UNAUTHORIZED,
//                        "authHeader null or not started with Bearer");
//                return;
//            }
//            final String token = authHeader.substring(7);
//            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
//            if (phoneNumber != null
//                    && SecurityContextHolder.getContext().getAuthentication() == null) {
//                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
//                if(jwtTokenUtil.validateToken(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(
//                                    userDetails,
//                                    null,
//                                    userDetails.getAuthorities()
//                            );
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//            filterChain.doFilter(request, response); //enable bypass
//        }catch (Exception e) {
//            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(e.getMessage());
//        }
//
//    }
//    private boolean isBypassToken(@NonNull HttpServletRequest request) {
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
//                // Healthcheck request, no JWT token required
//                Pair.of(String.format("%s/healthcheck/health", apiPrefix), "GET"),
//                Pair.of(String.format("%s/actuator/**", apiPrefix), "GET"),
//
//                Pair.of(String.format("%s/roles**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/comments**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/coupons**", apiPrefix), "GET"),
//
//                Pair.of(String.format("%s/products**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/categories**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
//                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
//                Pair.of(String.format("%s/users/refreshToken", apiPrefix), "POST"),
//
//                // Swagger
//                Pair.of("/api-docs","GET"),
//                Pair.of("/api-docs/**","GET"),
//                Pair.of("/swagger-resources","GET"),
//                Pair.of("/swagger-resources/**","GET"),
//                Pair.of("/configuration/ui","GET"),
//                Pair.of("/configuration/security","GET"),
//                Pair.of("/swagger-ui/**","GET"),
//                Pair.of("/swagger-ui.html", "GET"),
//                Pair.of("/swagger-ui/index.html", "GET")
//        );
//
//        String requestPath = request.getServletPath();
//        String requestMethod = request.getMethod();
//
//        for (Pair<String, String> token : bypassTokens) {
//            String path = token.getFirst();
//            String method = token.getSecond();
//            // Check if the request path and method match any pair in the bypassTokens list
//            if (requestPath.matches(path.replace("**", ".*"))
//                    && requestMethod.equalsIgnoreCase(method)) {
//                return true;
//            }
//        }
//        return false;
//    }
}