package com.lydiaplullc.CarLeasing.security;

import com.lydiaplullc.CarLeasing.security.jwt.AuthTokenFilter;
import com.lydiaplullc.CarLeasing.security.jwt.JwtAuthEntryPoint;
import com.lydiaplullc.CarLeasing.security.user.LeasingUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig {
    private final LeasingUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public AuthTokenFilter authenticationTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 禁用 CSRF
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) // 配置异常处理
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用无状态会话管理
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/roles/**").hasRole("ADMIN")
                        .requestMatchers("/**").permitAll() // 允许所有路径对游客开放访问
                        .anyRequest().authenticated() // 其他任何请求都需要身份验证
                );

        // 添加 JWT 过滤器，确保在用户名密码认证过滤器之前进行 JWT 验证
        // 设置身份验证提供者
        http.authenticationProvider(authenticationProvider());
        // 在用户名密码认证过滤器之前添加 JWT 过滤器
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 构建安全过滤链
        return http.build();
    }

}
