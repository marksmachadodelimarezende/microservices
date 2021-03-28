package com.microservice.marks.auth.services;

import com.microservice.marks.auth.data.vo.UserVO;
import com.microservice.marks.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = usuarioRepository.findByLoggin(userName);
        if (Optional.ofNullable(user.getId()).isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return user;
    }
}
