package com.lydiaplullc.CarLeasing.controller;

import com.lydiaplullc.CarLeasing.exception.UserAlreadyExistsException;
import com.lydiaplullc.CarLeasing.model.User;
import com.lydiaplullc.CarLeasing.request.LoginRequest;
import com.lydiaplullc.CarLeasing.response.JwtResponse;
import com.lydiaplullc.CarLeasing.security.jwt.JwtUtils;
import com.lydiaplullc.CarLeasing.security.user.LeasingUserDetails;
import com.lydiaplullc.CarLeasing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;// Spring Security 提供的身份验证管理器
    private final JwtUtils jwtUtils; // JWT 工具类，用于生成和验证 JWT

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request ) {
        // 创建身份验证对象（用户名和密码）
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // 设置安全上下文中的身份验证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成 JWT
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);

        // 获取用户详细信息
        LeasingUserDetails userDetails = (LeasingUserDetails) authentication.getPrincipal();
        // 获取用户角色列表
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 返回包含用户信息和 JWT 的响应
        return ResponseEntity.ok(new JwtResponse(userDetails.getId(), userDetails.getEmail(), jwt, roles));
    }
}
