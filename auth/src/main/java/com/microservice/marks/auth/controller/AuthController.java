package com.microservice.marks.auth.controller;

import com.microservice.marks.auth.data.vo.UserVO;
import com.microservice.marks.auth.jwt.JwtTokenProvider;
import com.microservice.marks.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    public static final String INVALID_USER_PASSWORD = "Invalid user/password";
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> login(@RequestBody UserVO userVO) {
        try {
            var userName = userVO.getUserName();
            var password = userVO.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            var usuario = usuarioRepository.findByLoggin(userName);
            var token ="";

            if (usuario != null) {
                token = jwtTokenProvider.createToken(userName, usuario.getRoles());
            } else {
                throw new UsernameNotFoundException(INVALID_USER_PASSWORD);
            }

            Map<Object, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(INVALID_USER_PASSWORD);
        }
    }

    @RequestMapping("/testSecurity")
    public String test(){
        return "Test OK";
    }
}
