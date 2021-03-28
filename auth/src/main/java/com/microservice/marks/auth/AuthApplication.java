package com.microservice.marks.auth;

import com.microservice.marks.auth.entity.Permission;
import com.microservice.marks.auth.entity.Usuario;
import com.microservice.marks.auth.repository.PermissionRepository;
import com.microservice.marks.auth.repository.UsuarioRepository;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletRequest;
import java.util.Arrays;

@SpringBootApplication
public class AuthApplication {

    private static final Logger log = LoggerFactory.getLogger(AuthApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        logRunnerInitial();
    }

    private static void logRunnerInitial() {
        log.info("MICROSERVICE AUTH IS RUNNING");
    }

    @Bean
    CommandLineRunner init(UsuarioRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            initUsers(userRepository, permissionRepository, passwordEncoder);
        };
    }

    private void initUsers(UsuarioRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordEncoder) {
        String userAdm = "admin";
        String passwordAdm = "123456";
        String permissionAdmin = "Admin";

        Permission permission;
        Permission findPermission = permissionRepository.findByDescription(permissionAdmin);
        if (findPermission == null) {
            permission = new Permission();
            permission.setDescription(permissionAdmin);
            permission = permissionRepository.save(permission);
        } else {
            permission = findPermission;
        }

        Usuario admin = new Usuario();
        admin.setLoggin(userAdm);
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setPassword(passwordEncoder.encode(passwordAdm));
        admin.setPermissions(Arrays.asList(permission));

        Usuario find = userRepository.findByLoggin(userAdm);
        if (find == null) {
            userRepository.save(admin);
        }
    }

}
