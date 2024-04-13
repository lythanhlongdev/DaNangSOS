package com.capstone2.dnsos.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Thiết lập các tiêu đề CORS trong phản hồi
        response.setHeader("Access-Control-Allow-Origin", "*"); // Cho phép truy cập từ mọi nguồn
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,PATCH"); // Các phương thức HTTP được cho phép
        response.setHeader("Access-Control-Max-Age", "3600"); // Thời gian tối đa mà các thông tin trả về từ phản hồi CORS có thể được lưu trữ
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token"); // Các tiêu đề được cho phép
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token"); // Các tiêu đề được hiển thị cho trình duyệt

        // Nếu là yêu cầu OPTIONS (được sử dụng để kiểm tra CORS)
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK); // Trả về mã trạng thái OK
        } else {
            // Nếu không phải là yêu cầu OPTIONS, tiếp tục chuỗi lọc
            filterChain.doFilter(request, response);
        }}
}
